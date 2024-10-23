package com.arushi.resumeportal.config;

import com.arushi.resumeportal.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    /* Class for a component-based security configuration
    * -----------------------
    * NOTE: WebSecurityConfigurerAdapter class used earlier is deprecated
    * https://www.springcloud.io/post/2022-03/spring-security-without-the-websecurityconfigureradapter/
    * https://docs.spring.io/spring-security/site/docs/4.1.4.RELEASE/reference/html/jc.html
    * -----------------------
    * Method security annotations added to enable processing based on different roles. */


    /* Will inject MyUserDetailsService Bean */
    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
/*        The SecurityFilterChain bean defines which URL paths should be secured and which should not*/
        /* secure the endpoints depending on the roles, and leave an anonymous entry point only for login */

        http.authorizeHttpRequests(requests ->
                    requests.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                            .requestMatchers("/admin").hasRole("ADMIN")
                            .requestMatchers("/edit").authenticated()
                            .requestMatchers("/user").hasAnyRole("ADMIN","USER")
                            .requestMatchers("/h2-console").permitAll()
                            .anyRequest().permitAll()
            )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /* Following bean to prevent Forbidden error while accessing H2 console */
    @Bean
    WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    }
}