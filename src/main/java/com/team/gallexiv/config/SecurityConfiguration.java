package com.team.gallexiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/captcha")
                                .permitAll()
//                        .hasAuthority("*")
//                        .anyRequest()
//                        .authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .usernameParameter("name")
                        .passwordParameter("passwd")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );
//        http.addFilterBefore(new CaptchaFilter(), CaptchaFilter.class);
        return http.build();
    }


//    @Bean
//    UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//        users.createUser(User.withUsername("javaboy").password("1234").roles("admin").build());
//        users.createUser(User.withUsername("benny").password("root").roles("admin").build());
//        return users;
//    }

}
