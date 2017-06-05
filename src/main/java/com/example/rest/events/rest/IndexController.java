package com.example.rest.events.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for root path
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String root() {
        return "redirect:/swagger-ui.html";
    }
}
