package com.arushi.resumeportal;

import com.arushi.resumeportal.models.MyUserDetails;
import com.arushi.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserProfileRepository userProfileRepository;

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

//        return "profile-templates/mariosmaselli/index";

        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserName(userId);

        userProfileOpt.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOpt.get();

        model.addAttribute("userProfile", userProfile);
        return String.format("profile-templates/%s/index", userProfile.getTheme());
    }



}
