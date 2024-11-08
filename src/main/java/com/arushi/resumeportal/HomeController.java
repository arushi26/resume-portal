package com.arushi.resumeportal;

import com.arushi.resumeportal.models.Job;
import com.arushi.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String home() {
        createTestData();
        return "hello";
    }

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



    private void createTestData() {

        /* Test Data */
        UserProfile profile1 = new UserProfile();
        profile1.setId(1);
        profile1.setUserName("mira");
        profile1.setFirstName("Mira");
        profile1.setLastName("Joshi");
        profile1.setEmail("mira.joshi@abc.com");
        profile1.setPhone("343298");
        profile1.setDesignation("Software Developer");
        profile1.setTheme("mariosmaselli");
        Job job1 = new Job();
        job1.setCompany("Company 1");
        job1.setDesignation("Designation 1");
        job1.setStartDate(LocalDate.of(2020, 1, 12));
        job1.setEndDate(LocalDate.of(2023, 6, 4));
        Job job2 = new Job();
        job2.setCompany("Company 2");
        job2.setDesignation("Designation 2");
        job2.setStartDate(LocalDate.of(2023, 6, 7));
        job2.setEndDate(LocalDate.of(2024, 5, 15));
        profile1.setJobs(List.of(job1, job2));
        userProfileRepository.save(profile1);

        UserProfile profile2 = new UserProfile();
        profile2.setId(2);
        profile2.setUserName("aria");
        profile2.setFirstName("Aria");
        profile2.setLastName("Hans");
        profile2.setEmail("aria.hans@abc.com");
        profile2.setPhone("683422");
        profile2.setDesignation("Software Developer");
        profile2.setTheme("onepageresume");
        job1 = new Job();
        job1.setCompany("Company ~ 1");
        job1.setDesignation("Designation ~ 1");
        job1.setStartDate(LocalDate.of(2021, 4, 12));
        job1.setEndDate(LocalDate.of(2022, 6, 4));
        job2 = new Job();
        job2.setCompany("Company ~ 2");
        job2.setDesignation("Designation ~ 2");
        job2.setStartDate(LocalDate.of(2022, 6, 7));
        job2.setEndDate(LocalDate.of(2024, 9, 15));
        profile2.setJobs(List.of(job1, job2));
        userProfileRepository.save(profile2);

        /* Test Data end */

    }
}
