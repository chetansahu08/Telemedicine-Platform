package com.project.telemedicine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
//               .authorizeHttpRequests(auth -> auth.requestMatchers("/api/patients/register","/api/patients/login").permitAll().anyRequest()
//                       .authenticated()).httpBasic(withDefaults());
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
       return http.build();

    }
}
