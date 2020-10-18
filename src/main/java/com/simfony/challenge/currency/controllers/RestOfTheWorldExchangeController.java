package com.simfony.challenge.currency.controllers;

import com.simfony.challenge.currency.model.RateExchange;
import com.simfony.challenge.currency.services.RateExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RestOfTheWorldExchangeController {

    private final RateExchangeService rateExchangeService;

    private final HttpServletRequest request;

    public RestOfTheWorldExchangeController(RateExchangeService rateExchangeService, HttpServletRequest request) {
        this.rateExchangeService = rateExchangeService;
        this.request = request;
    }

    @GetMapping(value = "/world")
    public String getRateHome() {
        return "world";
    }

    @GetMapping(value = "/world/{base}")
    public String getRate(@RequestParam(value = "transform", required = false, defaultValue = "RON") String symbol,
                          @PathVariable("base") String base, Model model) {
        try {
            RateExchange rateExchange = rateExchangeService.getRateExchange(base, symbol);
            model.addAttribute("rateExchange", rateExchange);
            return "world";
        } catch (Exception ex) {
            return "error";
        }

    }

}
