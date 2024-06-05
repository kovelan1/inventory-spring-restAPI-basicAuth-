package com.assignment.inventory.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BasicAuthSecurity {

    private static final String[] swagger_whitelist={
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    /**
     * Configures the HTTP security for the application.
     *
     * @param http the HttpSecurity object to be configured
     * @return the SecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection (not recommended for production)
        http.csrf(csrf -> csrf.disable())
                // Configure authorization rules
                .authorizeHttpRequests((authorize) -> {
                    // Allow swagger resources
                    authorize.requestMatchers(swagger_whitelist).permitAll();
                    // Allow unauthenticated access to the /token endpoint
                    authorize.requestMatchers("/token").permitAll();
                    // Allow unauthenticated access to OPTIONS requests (CORS pre-flight requests)
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    // Require authentication for all other requests
                    authorize.anyRequest().authenticated();
                })
                // Enable HTTP Basic authentication
                .httpBasic(Customizer.withDefaults());

        // Build and return the SecurityFilterChain
        return http.build();
    }

    /**
     * Configures in-memory authentication with two users: admin and user.
     *
     * @param auth the AuthenticationManagerBuilder to configure
     * @param passwordEncoder the PasswordEncoder to use for encoding passwords
     * @throws Exception if an error occurs during configuration
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                // Set the password encoder
                .passwordEncoder(passwordEncoder)
                // Configure the admin user with role ADMIN
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .and()
                // Configure the user user with role USER
                .withUser("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER");
    }
}
