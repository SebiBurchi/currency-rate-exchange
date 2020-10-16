package com.simfony.challenge.currency.services;


import com.simfony.challenge.currency.model.RateExchange;

public interface RateExchangeService {

     RateExchange getRateExchange(String base, String symbol) throws Exception;

}
