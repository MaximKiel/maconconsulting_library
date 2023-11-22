package ru.maconconsulting.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.maconconsulting.library.services.MaconUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MaconUserDetailsService service;

    @Autowired
    public SecurityConfig(MaconUserDetailsService service) {
        this.service = service;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        http.userDetailsService(service)
                .authorizeHttpRequests(authorizeHttpRequest ->
                        authorizeHttpRequest
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/users", "/users/new", "/users/create").hasRole("ADMIN")
                                .anyRequest().hasAnyRole("USER", "MANAGER", "ADMIN")
                )
                .formLogin((formLogin) -> formLogin
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/projects")
                        .failureUrl("/auth/login?error")
                        .loginProcessingUrl("/process_login")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
