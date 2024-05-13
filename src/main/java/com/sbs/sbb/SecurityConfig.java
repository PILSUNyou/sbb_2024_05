package com.sbs.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/question/list")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/question/detail/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/style.css")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .anyRequest().authenticated() // 해당 코드를 입력하면 위에 사이트를 제외한 나머지 들은 login 없이 접근이 불가하다.
                )
                .formLogin((formLogin) -> formLogin
                        // GET
                        // 시큐리티에게 우리가 만든 로그인 페이지 urlㅇㄹ 알려준다.
                        // 만약 이걸 하지 않으면, 로그인 페이지 url은 "/login" 이다.
                        .loginPage("/user/login")
                        // POST
                        // 시큐리티에게 로그인 폼 처리 url을 알려준다.
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/"))
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // paivate final로 사용한 것 처럼 어디에서든 사용이 가능하다.
        return new BCryptPasswordEncoder();
    }
}