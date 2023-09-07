package com.team.gallexiv.config;

import com.team.gallexiv.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.team.gallexiv.common.lang.Const.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@ConfigurationProperties(prefix = "gallexiv.security")
public class SecurityConfiguration {

    //陣列導入用
    //完全開放的 URL/API 列表
    AntPathRequestMatcher[] OPEN_URL = new AntPathRequestMatcher[]{
            AntPathRequestMatcher.antMatcher(GET, "/captcha"),
            AntPathRequestMatcher.antMatcher(POST, "/login"),
            AntPathRequestMatcher.antMatcher(POST, "/logout"),
            AntPathRequestMatcher.antMatcher(GET, "/test/**"),
    };
    //需要管理員身份的 URL/API 列表
    private final String ADMIN_API_CONTEXT_PATTERN = "/admin/**";
    AntPathRequestMatcher[] ADMIN_API_URL = new AntPathRequestMatcher[]{
            AntPathRequestMatcher.antMatcher(GET, ADMIN_API_CONTEXT_PATTERN),
            AntPathRequestMatcher.antMatcher(POST, ADMIN_API_CONTEXT_PATTERN),
            AntPathRequestMatcher.antMatcher(PUT, ADMIN_API_CONTEXT_PATTERN),
            AntPathRequestMatcher.antMatcher(DELETE, ADMIN_API_CONTEXT_PATTERN),
    };
    //其餘涉及使用者都需要驗證

    //API版本號設定 TODO 版本號不會在這邊設定
    private final String LOGIN_URI_WITH_VERSION = API_VERSION_URI + LOGIN_URI;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    CaptchaFilter captchaFilter;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    //TODO 有時間可以設定記住我功能
    //這邊設定本APP第一個認證鍊，但其實認證鍊可以有多個
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        //TODO 統一用下面這行這種寫法
                        .requestMatchers(POST, LOGIN_URI).permitAll()
                        .requestMatchers(GET, "/captcha", "/test/**").permitAll()
                        .requestMatchers(ADMIN_API_URL).hasRole("admin")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        //不須要Session
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true)
                )
                .formLogin(form -> form
                        .loginProcessingUrl(LOGIN_URI)
                        .usernameParameter("name")
                        .passwordParameter("passwd")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .logoutSuccessHandler(jwtLogoutSuccessHandler)
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                //登入用
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                //驗證用
                .addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return httpSecurity.build();
    }


    @Bean
    JwtAuthenticationFilter JwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//        users.createUser(User.withUsername("javaboy").password("1234").roles("admin").build());
//        users.createUser(User.withUsername("benny").password("root").roles("admin").build());
//        return users;
//    }

}
