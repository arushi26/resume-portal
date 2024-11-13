package com.arushi.resumeportal;

import com.arushi.resumeportal.models.Job;
import com.arushi.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    UserProfileRepository userProfileRepository;

    public HomeController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/")
    public String home() {
        createTestData();
        return "hello";
    }

    @GetMapping("/edit")
    public String edit(Principal principal, Model model,
                       @RequestParam(required = false) String add) {
        // Spring Security gives Principal object that tells us the currently logged in user

        String userId = principal.getName();
        model.addAttribute("userId", userId);

        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserName(userId);
        userProfileOpt.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        UserProfile userProfile = userProfileOpt.get();
        if ("job".equalsIgnoreCase(add)) {
            userProfile.getJobs().add(new Job());
        }
        model.addAttribute("userProfile", userProfile);

        return "profile-edit";
    }


    @GetMapping("/delete")
    public String delete(Principal principal, Model model,
                         @RequestParam String type, @RequestParam int index) {
        // Spring Security gives Principal object that tells us the currently logged in user
        // Spring Security gives Principal object that tells us the currently logged in user

        String userId = principal.getName();
        model.addAttribute("userId", userId);

        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserName(userId);
        userProfileOpt.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        UserProfile userProfile = userProfileOpt.get();
        if ("job".equalsIgnoreCase(type)) {
            userProfile.getJobs().remove(index);
        }

        userProfileRepository.save(userProfile);
        return "redirect:/edit";
    }

    @PostMapping("/edit")
    public String postEdit(Principal principal, Model model, @ModelAttribute UserProfile userProfile) {
        String userName = principal.getName();

        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserName(userName);
        userProfileOpt.orElseThrow(() -> new RuntimeException("Not found: " + userName));

        UserProfile savedUserProfile = userProfileOpt.get();
        // Save fields not in Edit form that should not be erased
        userProfile.setId(savedUserProfile.getId());
        userProfile.setUserName(userName);
        userProfile.setDesignation(savedUserProfile.getDesignation());

        for (Map.Entry<String, Integer> skill : savedUserProfile.getSkills().entrySet()) {
            userProfile.addSkills(skill.getKey(), skill.getValue());
        }
        // save updated values
        userProfileRepository.save(userProfile);
        return "redirect:/view/" + userName;
    }

    @GetMapping("/view/{id}")
    public String view(Principal principal, @PathVariable("id") String userId, Model model) {

        boolean currentUsersProfile = false;

        if(principal!=null && principal.getName()!=null) {
            currentUsersProfile = principal.getName().equals(userId);
        }
        model.addAttribute("currentUsersProfile", currentUsersProfile);

        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserName(userId);

        userProfileOpt.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOpt.get();

        model.addAttribute("userProfile", userProfile);
        return String.format("profile-templates/%s/index", userProfile.getTheme());
    }


    private void createTestData() {

        /* Test Data */
        Optional<UserProfile> profileOptional = userProfileRepository.findByUserName("albert");
        profileOptional.orElseThrow(() -> new RuntimeException("Not found"));

        UserProfile profile1 = profileOptional.get();

        Job job1 = new Job();
        job1.setCompany("Company 1");
        job1.setDesignation("Designation 1");
        job1.setStartDate(LocalDate.of(2020, 1, 12));
        job1.setEndDate(LocalDate.of(2023, 6, 4));
        job1.getResponsibilities().clear();
        job1.getResponsibilities().add("Developed theory of relativity");
        job1.getResponsibilities().add("The mass–energy equivalence formula E = mc2, which arises from special relativity, has been called \"the world's most famous equation\"");

        Job job2 = new Job();
        job2.setCompany("Company 2");
        job2.setDesignation("Designation 2");
        job2.setStartDate(LocalDate.of(2023, 6, 7));
        job2.setCurrentJob(true);

        job2.getResponsibilities().clear();
        job2.getResponsibilities().add("Discovery of the law of the photoelectric effect, a pivotal step in the development of quantum theory.");
        job2.getResponsibilities().add("Important contributions to statistical mechanics and quantum theory.");

        profile1.getJobs().clear();
        profile1.getJobs().addAll(List.of(job1, job2));

        profile1.addSkills("Java", 9);
        profile1.addSkills("HTML / HTML5", 7);
        profile1.addSkills("Javascript", 5);
        profile1.addSkills("Python", 7);
        profile1.addSkills("Spring Boot", 8);

        userProfileRepository.save(profile1);

        profileOptional = userProfileRepository.findByUserName("maria");
        profileOptional.orElseThrow(() -> new RuntimeException("Not found"));

        UserProfile profile2 = profileOptional.get();

        job1 = new Job();
        job1.setCompany("Trapp Family Choir");
        job1.setDesignation("Singer");
        job1.setStartDate(LocalDate.of(2021, 4, 12));
        job1.setEndDate(LocalDate.of(2022, 6, 4));
        job1.getResponsibilities().clear();
        job1.getResponsibilities().add("Perform in the United States and Canada as Von Trapp Family Choir");
        job1.getResponsibilities().add("Touring the world giving concert performances.");

        job2 = new Job();
        job2.setCompany("Company ~ 2");
        job2.setDesignation("Designation ~ 2");
        job2.setStartDate(LocalDate.of(2022, 6, 7));
        job2.setCurrentJob(true);

        job2.getResponsibilities().clear();
        job2.getResponsibilities().add("Wrote The Story of the Trapp Family Singers, which was published in 1949 and was the inspiration for the 1956 West German film The Trapp Family, which in turn inspired the 1959 Broadway musical The Sound of Music and its 1965 film version");
        job2.getResponsibilities().add("After the war, founded the Trapp Family Austrian Relief fund, which sent food and clothing to the impoverished in Austria.");


        profile2.getJobs().clear();
        profile2.getJobs().addAll(List.of(job1, job2));

        profile2.addSkills("Vocal Control", 9);
        profile2.addSkills("Piano", 6);

        userProfileRepository.save(profile2);

        /* Test Data end */

    }
}
