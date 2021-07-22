package com.natixis.tricount.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController
{
    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }
}
