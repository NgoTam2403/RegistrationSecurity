package com.example.TestSecurity.security.config;

import com.example.TestSecurity.appuser.AppUserSevice;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig  {

    private final AppUserSevice appUserSevice;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProviderBean() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserSevice);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .authorizeRequests((requests) -> requests
                        .requestMatchers("/api/v1/registration").permitAll() // Sử dụng antMatchers thay vì requestMatchers
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form.permitAll()
                );

        return http.build();
    }
}
