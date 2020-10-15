package com.simfony.challenge.currency.services.impl;

import com.simfony.challenge.currency.model.RateExchange;
import com.simfony.challenge.currency.model.xml.Cube;
import com.simfony.challenge.currency.model.xml.DataSet;
import com.simfony.challenge.currency.services.RateExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class RateExchangeServiceImpl implements RateExchangeService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${exchange.url}")
    private String url;

    @Value("${exchange.symbol.param}")
    private String symbolParam;

    @Value("${exchange.base.param}")
    private String baseParam;

    @Value("${exchange.failOver.url}")
    private String failOverUrl;

    Logger logger = LoggerFactory.getLogger(RateExchangeServiceImpl.class);


    @Override
    public RateExchange getRateExchange(String base, String symbol) {
        RateExchange rateExchange;

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam(symbolParam, symbol)
                .queryParam(baseParam, base);

        try {
            rateExchange = restTemplate.getForObject(
                    builder.toUriString(),
                    RateExchange.class
            );
            if (rateExchange == null) {
                throw new Exception("Failed to obtain exchange rate from European Central Bank, going for BNR!");
            }
        } catch (Exception err) {
            logger.error(err.getMessage());
            rateExchange = failOverMethod();
        }

        return rateExchange;
    }

    private RateExchange failOverMethod() {
        try {
            DataSet dataSet = restTemplate.getForObject(failOverUrl, DataSet.class);
            if (dataSet == null) {
                throw new Exception("Failed to also obtain exchange rate from BNR!");
            }
            Map<String, Double> rates = new HashMap<>();

            Cube cube = dataSet.getBody().getCube();
            cube.getRates().forEach(xmlExchangeRate -> {
                rates.put(xmlExchangeRate.getCurrency(), xmlExchangeRate.getValue());
            });

            return RateExchange.builder().date(dataSet.getHeader().getPublishingDate()).base("RON").rates(rates).build();
        } catch (Exception err) {
            logger.error(err.getMessage());
            return null;
        }

    }


}
