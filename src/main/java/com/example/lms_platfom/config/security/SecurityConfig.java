package com.example.lms_platfom.config.security;

import com.example.lms_platfom.enums.Role;
import com.example.lms_platfom.token.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig{
    private final JwtRequestFilter jwtRequestFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/api/teacher/**",
                        "/api/courses/create-course",
                        "/api/teachers/**",
                        "/api/lessons/create",
                        "/api/assignment/create",
                        "/api/student-assignment/create-assignment-for-users",
                        "/api/test/**",
                        //

                        "/api/question/**")
                .hasAuthority(Role.TEACHER.name())


                //
                .antMatchers("/api/basket/**",
                        "/api/users/**",
                        "/api/enrollment/**",
                        "/api/lessons/**")
                .hasAnyAuthority(Role.STUDENT.name(), Role.TEACHER.name())


                //
                .antMatchers("/api/auth/**",
                            "/youtube/**",
                            "/api/courses/all-courses",
                            "/api/courses/course/**"
                        ).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}