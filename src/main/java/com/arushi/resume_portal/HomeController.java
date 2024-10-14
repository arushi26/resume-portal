package com.arushi.resume_portal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<html>Hello! <br/> <a href='/logout'>Logout</a></html>";
    }
}
