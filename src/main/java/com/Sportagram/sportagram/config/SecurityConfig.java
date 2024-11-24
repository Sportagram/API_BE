package com.Sportagram.sportagram.config;
//
//import com.Sportagram.sportagram.service.CustomOAuth2UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import java.io.IOException;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
//
//        this.customOAuth2UserService = customOAuth2UserService;
//    }
//
//    /*
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf((csrf) -> csrf.disable())
//                .formLogin((login) -> login.disable())
//                .httpBasic((basic) -> basic.disable())
//                .oauth2Login((oauth2) -> oauth2
//                        .userInfoEndpoint((userInfoEndpointConfig) ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService)
//                        )
//                        // 성공 핸들러 추가
//                        .successHandler(new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(
//                                    HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    Authentication authentication
//                            ) throws IOException {
//                                // 로그인 성공 후 대시보드 또는 메인 페이지로 리다이렉트
//                                response.sendRedirect("/api/**"); // 원하는 페이지 경로로 변경
//                            }
//                        })
//                )
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//
//     */
//
//
//    // 이 코드는 postman에서의 test를 위한 코드이므로 실제 실행 시 주석 처리 요망
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf((csrf) -> csrf.disable())
//                .formLogin((login) -> login.disable())
//                .httpBasic((basic) -> basic.disable())
//                .oauth2Login((oauth2) -> oauth2
//                        .userInfoEndpoint((userInfoEndpointConfig) ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService)
//                        )
//                        .successHandler((request, response, authentication) -> {
//                            // 로그인 성공 후 React로 리디렉션
//                            response.sendRedirect("http://localhost:3000/fanselect");
//                        })
//                )
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/api/**").permitAll()  // API 엔드포인트 허용
//                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//
//}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/teams", "/api/settings").permitAll() // `/api/profile` 인증 없이 허용
                        .anyRequest().authenticated() // 다른 모든 요청은 인증 필요
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


