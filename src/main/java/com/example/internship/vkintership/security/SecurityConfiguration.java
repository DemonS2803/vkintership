package com.example.internship.vkintership.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.internship.vkintership.enums.UserAuthority;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthEntryExceptionHandler authEntryPointJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/posts/get/**").hasAnyAuthority(UserAuthority.ROLE_POSTS_VIEWER.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/posts/edit/**").hasAnyAuthority(UserAuthority.ROLE_POSTS_EDITOR.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/albums/get/**").hasAnyAuthority(UserAuthority.ROLE_ALBUMS_VIEWER.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/albums/edit/**").hasAnyAuthority(UserAuthority.ROLE_ALBUMS_EDITOR.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/users/get/**").hasAnyAuthority(UserAuthority.ROLE_USERS_VIEWER.name(), UserAuthority.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/users/edit/**").hasAnyAuthority(UserAuthority.ROLE_USERS_EDITOR.name(), UserAuthority.ROLE_ADMIN.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .cors().and()
                .csrf().disable()
                // .addFilterAfter(new AuthEntryPointSuccess(), BasicAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
}
