package com.sbs.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin((formLogin) -> formLogin
                        // GET
                        // 시큐리티에게 우리가 만든 로그인 페이지 urlㅇㄹ 알려준다.
                        // 만약 이걸 하지 않으면, 로그인 페이지 url은 "/login" 이다.
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/")

                )
                .logout(
                        logout -> logout
                                .logoutUrl("/user/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // paivate final로 사용한 것 처럼 어디에서든 사용이 가능하다.
        return new BCryptPasswordEncoder();
    }
}