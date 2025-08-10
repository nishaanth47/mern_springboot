package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	// Replaces Spring Security's default filter chain with custom config
	// If no rules are added, default security (deny all) still applies

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		// CSRF is disabled because this is a stateless API using token/basic auth — no sessions to protect

		return http
			    .csrf(csrf -> csrf.disable()) // Disable CSRF protection
			    .authorizeHttpRequests(auth -> auth
			    	    .requestMatchers("/register", "/login").permitAll()
			    	    .requestMatchers("/admin/**").hasRole("ADMIN")
			    	    .anyRequest().authenticated())
// All requests need authentication
			    .httpBasic(Customizer.withDefaults()) // Use HTTP Basic authentication
			    .sessionManagement(session -> session
			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No server-side sessions (stateless)
			    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			    .build();
 
	}
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
}
