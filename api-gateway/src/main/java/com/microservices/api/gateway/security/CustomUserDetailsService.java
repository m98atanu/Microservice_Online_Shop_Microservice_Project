package com.microservices.api.gateway.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.microservices.api.gateway.entity.Admin;
import com.microservices.api.gateway.repository.AdminRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	AdminRepository adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepo.findByEmail(username)
				.orElseThrow(()->
					new UsernameNotFoundException("UserDetails not found for usere: "+ username)
				);
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(admin.getRole()));
		return new User(admin.getEmail(), admin.getPwd(), authorities);
	}
	
}
