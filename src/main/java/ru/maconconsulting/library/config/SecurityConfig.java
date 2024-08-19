package ru.maconconsulting.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

                                .requestMatchers("/projects/new", "/projects/create", "/projects/{number}/edit").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/projects/{number}").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/projects/{number}").hasAnyRole("MANAGER", "ADMIN")

                                .requestMatchers("/publications/new", "/publications/create", "/publications/{id}/edit").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/publications/{id}").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/publications/{id}").hasAnyRole("MANAGER", "ADMIN")

                                .requestMatchers("/types/new", "/types/create", "/types/{name}/edit").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/types/{name}").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/types/{name}").hasAnyRole("MANAGER", "ADMIN")

                                .requestMatchers("/segments/new", "/segments/create", "/segments/{name}/edit").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/segments/{name}").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/segments/{name}").hasAnyRole("MANAGER", "ADMIN")

                                .requestMatchers("/formats/new", "/formats/create", "/formats/{name}/edit").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/formats/{name}").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/formats/{name}").hasAnyRole("MANAGER", "ADMIN")

                                .requestMatchers("/types_of_publication/new", "/types_of_publication/create", "/types_of_publication/{name}/edit").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/types_of_publication/{name}").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/types_of_publication/{name}").hasAnyRole("MANAGER", "ADMIN")

                                .anyRequest().hasAnyRole("USER", "MANAGER", "ADMIN")
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/main"))
                .formLogin((formLogin) -> formLogin
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/main")
                        .failureUrl("/auth/login?error")
                        .loginProcessingUrl("/process_login")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                )
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me-new")
                        .rememberMeCookieName("remember-me-cookie")
                        .tokenValiditySeconds(31536000)
                );
        /*
        key("uniqueAndSecret").tokenValiditySeconds(31536000)
         */
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
