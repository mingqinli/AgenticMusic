package com.musicagent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/songs";
    }
    
    @GetMapping("/home")
    public String homePage() {
        return "redirect:/songs";
    }
} 