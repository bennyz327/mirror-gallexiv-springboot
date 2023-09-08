package com.team.gallexiv.config;

import com.team.gallexiv.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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

    RequestMatcher logoutMatcher = new AntPathRequestMatcher(LOGOUT_URI, POST.name());

    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    OauthLoginSuccessHandler oauthLoginSuccessHandler;
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

    //這邊設定本APP第一個認證鍊，但認證鍊可以有多個 TODO 有時間可以設定記住我功能
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                //請求安全設定 統一用下面的寫法
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(POST, LOGIN_URI).permitAll()
                        .requestMatchers(GET, "/captcha", "/test/**").permitAll()
                        .requestMatchers(POST,"/**").permitAll()
                        .requestMatchers(OPTIONS, "/**").permitAll()
                        .requestMatchers(ADMIN_API_URL).hasRole("admin")
                        .anyRequest()
                        .authenticated()
                )
                //不須要Session
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        //.maximumSessions(1).maxSessionsPreventsLogin(true)
                )
                //google登入
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauthLoginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .permitAll()
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
                        .logoutRequestMatcher(logoutMatcher)
                        .logoutSuccessUrl(LOGIN_URI)
                        .logoutSuccessHandler(jwtLogoutSuccessHandler)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                //登入驗證碼
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                //JWT身份驗證
                .addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(CORS_ALLOWED_ORIGINS);
        configuration.setAllowedMethods(CORS_ALLOWED_METHODS);
        configuration.setAllowedHeaders(CORS_ALLOWED_HEADERS);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    JwtAuthenticationFilter JwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//若要非持久帳號測試，可以用下面的設定方法
//    @Bean
//    UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//        users.createUser(User.withUsername("javaboy").password("1234").roles("admin").build());
//        users.createUser(User.withUsername("benny").password("root").roles("admin").build());
//        return users;
//    }

}
