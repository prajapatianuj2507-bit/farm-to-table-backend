package com.farm.farm_marketplace.Config;

import com.farm.farm_marketplace.Config.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth



                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/crops/**").hasRole("FARMER")
                        .requestMatchers(HttpMethod.POST, "/api/orders/**").hasRole("BUYER")

                        .requestMatchers(HttpMethod.GET, "/api/crops/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/orders/buyer").hasRole("BUYER")
                        .requestMatchers(HttpMethod.GET, "/api/orders/farmer").hasRole("FARMER")

                        .requestMatchers(HttpMethod.GET, "/api/crops/farmer/**").hasRole("FARMER")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasRole("FARMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/crops/**").hasRole("FARMER")
                        .anyRequest().authenticated()
                )

                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();



    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}