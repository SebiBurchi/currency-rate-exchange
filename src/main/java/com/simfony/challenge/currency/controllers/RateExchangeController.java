package com.simfony.challenge.currency.controllers;

import com.simfony.challenge.currency.model.RateExchange;
import com.simfony.challenge.currency.services.RateExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateExchangeController {

    @Autowired
    private RateExchangeService rateExchangeService;


    @GetMapping(value = "/{base}")
    public RateExchange getRate(@RequestParam(value = "transform", required = false, defaultValue = "RON") String symbol,
                                @PathVariable("base") String base) {
        return rateExchangeService.getRateExchange(base, symbol);

    }


}
