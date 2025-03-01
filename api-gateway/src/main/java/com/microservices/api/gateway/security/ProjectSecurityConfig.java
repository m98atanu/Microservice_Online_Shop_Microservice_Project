package com.microservices.api.gateway.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ProjectSecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception{
		return httpSec
				.cors(corsCustomizer ->{
					corsCustomizer.configurationSource(new CorsConfigurationSource() {

						@Override
						public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
							CorsConfiguration config = new CorsConfiguration();
							config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
							config.setAllowedMethods(Collections.singletonList("*"));
							config.setAllowCredentials(true); //The browser sends a request with credentials only if the origin is explicitly allowed.
							config.setAllowedHeaders(Collections.singletonList("*"));
							config.setMaxAge(3600L); //for 1 hour
							config.setExposedHeaders(Arrays.asList("Authorization")); //JWT related
							return config;
						}
						
					});
				})
				.csrf(csrfConfig-> csrfConfig.disable())
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //jwt rel
				.authorizeHttpRequests(httpRequest -> httpRequest
				.requestMatchers("/demo/secured", "/login/admin").authenticated()
				.requestMatchers("/api/shoppingservice/products").hasAuthority("admin")
				.anyRequest().permitAll()) //here all the other APIs which is not mentioned in above line gets permission without authentication
				.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class) //jwt related
				.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)//jwt related
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //BCrypt password encoder: Default password encoder
	}
}
