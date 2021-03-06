package com.simfony.challenge.currency.controllers;

import com.simfony.challenge.currency.model.RateExchange;
import com.simfony.challenge.currency.services.RateExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Controller
public class EuropeExchangeController {

    private final RateExchangeService rateExchangeService;

    private final HttpServletRequest request;

    @Autowired
    public EuropeExchangeController(RateExchangeService rateExchangeService, HttpServletRequest request) {
        this.rateExchangeService = rateExchangeService;
        this.request = request;
    }

    @GetMapping(value = "/")
    public String getHome() {
        return "index";
    }

    @GetMapping(value = "/europe")
    public String getRateHome() {
        return "europe";
    }

    @GetMapping(value = "/europe/{base}")
    public String getRate(@RequestParam(value = "transform", required = false, defaultValue = "RON") String symbol,
                          @PathVariable("base") String base, Model model) {
        try {
            RateExchange rateExchange = rateExchangeService.getRateExchange(base, symbol);
            model.addAttribute("rateExchange", rateExchange);
            return "europe";
        } catch (Exception ex) {
            return "error";
        }

    }

    @GetMapping(value = "/logout")
    public String logout() throws ServletException {
        request.logout();
        return "redirect:/";
    }

}
