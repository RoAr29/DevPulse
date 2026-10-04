package com.devpulse.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .formLogin(form -> form.disable())
	        .httpBasic(basic -> basic.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/health", "/api/projects/**").permitAll()
	            .anyRequest().authenticated()
	        );

	    return http.build();
	}
}