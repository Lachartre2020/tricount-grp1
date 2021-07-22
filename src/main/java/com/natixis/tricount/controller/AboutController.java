package com.natixis.tricount.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController
{
    @GetMapping("/contact")
    public String getContact() {
        return "contact";
    }
}
