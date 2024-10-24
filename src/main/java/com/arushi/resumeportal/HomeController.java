package com.arushi.resumeportal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

//    @GetMapping("/")
//    public String home() {
//        return "<html>Hello! <br/> <a href='/logout'>Logout</a></html>";
//    }
//
//    @GetMapping("/edit")
//    public String edit() {
//        return "edit page";
//    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "profile";
    }


}
