package com.invisibles.scheduleservice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/docs");

        http
                .authorizeHttpRequests()
                .requestMatchers("/lesson/**").permitAll()
                .requestMatchers("/group/**").permitAll()
                .requestMatchers("/auditorium/**").permitAll()
                .requestMatchers("/teacher/**").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .formLogin()
                .successHandler(successHandler)
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable()
                .rememberMe()
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(1209600);

        return http.build();
    }
}
