package com.Sportagram.sportagram.config;

import com.Sportagram.sportagram.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.util.Base64;
import java.util.Date;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final String secretKey = "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";
    String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());


    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {

        this.customOAuth2UserService = customOAuth2UserService;
    }

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)
                        )
                        // 성공 핸들러 추가
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication
                            ) throws IOException {
                                // 로그인 성공 후 대시보드 또는 메인 페이지로 리다이렉트
                                response.sendRedirect("/api/**"); // 원하는 페이지 경로로 변경
                            }
                        })
                )
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

     */


    // 이 코드는 postman에서의 test를 위한 코드이므로 실제 실행 시 주석 처리 요망

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            // JWT 토큰 생성
                            String secretKey = "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";  // 비밀키
                            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // 비밀키를 Base64로 인코딩

                            String token = Jwts.builder()
                                    .setSubject(authentication.getName())
                                    .claim("authorities", authentication.getAuthorities())
                                    .setIssuedAt(new Date())
                                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                                    .signWith(SignatureAlgorithm.HS512, encodedKey)
                                    .compact();

                            response.setContentType("application/json");
                            response.getWriter().write("{\"token\": \"" + token + "\"}");
                            response.sendRedirect("http://localhost:3000/fanselect");
                        })
                )
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/**").permitAll()  // API 엔드포인트 허용
                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
