package com.nhom10.pbl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.nhom10.pbl.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityAppConfig {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers("/*").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
                .formLogin((formLogin) -> formLogin.loginPage("/login").loginProcessingUrl("/login")
                        .usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/admin", true));

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/css/**", "/js/**");
    }
}
