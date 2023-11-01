//package ru.maconconsulting.library.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import ru.maconconsulting.library.services.MaconUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    private final MaconUserDetailsService service;
//
//    @Autowired
//    public SecurityConfig(MaconUserDetailsService service) {
//        this.service = service;
//    }
//
////    TODO: configure filter chains
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests((authorizeRequests) ->
//                        authorizeRequests
//                                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
//                                .anyRequest().hasAnyRole("USER", "MANAGER", "ADMIN")
//                )
//                .formLogin((formLogin) ->
//                        formLogin
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .loginPage("/auth/login")
//                                .defaultSuccessUrl("/hello", true)
//                                .failureUrl("/auth/login?error")
//                                .loginProcessingUrl("/process_login")
//                )
//                .logout((logout) ->
//                        logout.logoutUrl("/logout")
//                                .logoutSuccessUrl("/auth/login"));
//        return http.build();
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(service).passwordEncoder(getPasswordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
