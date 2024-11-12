package com.arushi.resumeportal;


import com.arushi.resumeportal.models.MyUserDetails;
import com.arushi.resumeportal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    /* Implement UserDetailsService to customize */

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        /* Override method to fetch User details from our own Database */
        Optional<User> user = userRepository.findByUserName(userName);


        /* Convert user into a custom User Details class */
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
    }
}
