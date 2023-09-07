package com.team.gallexiv.config;

import com.team.gallexiv.security.CaptchaFilter;
import com.team.gallexiv.security.LoginFailureHandler;
import com.team.gallexiv.security.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfiguration {

    // 完全開放的 URL/API 列表
    AntPathRequestMatcher[] OPEN_URL = new AntPathRequestMatcher[] {
            AntPathRequestMatcher.antMatcher(GET, "/captcha"),
            AntPathRequestMatcher.antMatcher(POST, "/login"),
            AntPathRequestMatcher.antMatcher(POST, "/logout"),
            AntPathRequestMatcher.antMatcher(GET, "/test/**"),
    };
    // 需要管理員身份的 URL/API 列表
    private final String ADMIN_API_CONTEXT_PATTERN = "/admin/**";
    AntPathRequestMatcher[] ADMIN_API_URL = new AntPathRequestMatcher[] {
            AntPathRequestMatcher.antMatcher(GET, ADMIN_API_CONTEXT_PATTERN),
            AntPathRequestMatcher.antMatcher(POST, ADMIN_API_CONTEXT_PATTERN),
            AntPathRequestMatcher.antMatcher(PUT, ADMIN_API_CONTEXT_PATTERN),
            AntPathRequestMatcher.antMatcher(DELETE, ADMIN_API_CONTEXT_PATTERN),
    };
    // 其餘涉及使用者都需要驗證
    private static final String[] URL_WHITELIST = {
            "/comments/**",

    };

    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    CaptchaFilter captchaFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(OPEN_URL)
                        .permitAll()
                        .requestMatchers(URL_WHITELIST)
                        .permitAll()
                        .requestMatchers(ADMIN_API_URL)
                        .hasAuthority("admin")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .usernameParameter("name")
                        .passwordParameter("passwd")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .permitAll())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // @Bean
    // UserDetailsService userDetailsService() {
    // InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
    // users.createUser(User.withUsername("javaboy").password("1234").roles("admin").build());
    // users.createUser(User.withUsername("benny").password("root").roles("admin").build());
    // return users;
    // }

}
