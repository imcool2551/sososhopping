package com.sososhopping.common.config;

import com.sososhopping.auth.JwtAuthenticationFilter;
import com.sososhopping.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt를 통해 인증을 처리하기 때문에 세션 생성 비활성화
                .and()
                .authorizeRequests()
                .antMatchers(DELETE,
                        "/api/v1/owner/auth").hasRole("OWNER")
                .antMatchers(
                        "/api/v1/owner/auth/info/**").hasRole("OWNER")
                .antMatchers(
                        "/api/v1/owner/auth/**"
                        , "/api/v1/owner/auth"
                        , "/api/v1/users/auth/**"
                        , "/api/v1/admin/auth/login"
                        , "/admin/login"
                        , "/api/v1/users/stores"
                        , "/api/v1/users/search/page").permitAll()
                .antMatchers("/api/v1/users/stores/**/check").hasRole("USER")
                .antMatchers(HttpMethod.GET
                        , "/api/v1/users/stores/**").permitAll()
                .antMatchers(HttpMethod.POST
                        , "/api/v1/users/stores/**").hasRole("USER")
                .antMatchers(
                        "/api/v1/users/my/**").hasRole("USER")
                .antMatchers("/api/v1/users/**").hasRole("USER")
                .antMatchers("/api/v1/owner/**").hasRole("OWNER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
