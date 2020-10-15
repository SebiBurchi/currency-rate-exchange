package com.simfony.challenge.currency.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RateExchange {

    private Map<String, Double> rates;

    private String base;

    private String date;
}
