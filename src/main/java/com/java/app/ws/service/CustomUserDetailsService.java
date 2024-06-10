package com.java.app.ws.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.app.ws.entity.UserEntity;
import com.java.app.ws.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;


@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private UserRepository userRepository;
    BCryptPasswordEncoder encoder;



    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        Collection<GrantedAuthority> authorities = mapRoleToAuthorities(user.getRole());
        return new User(user.getEmail(), user.getPassword(),authorities);
    }

    private Collection<GrantedAuthority> mapRoleToAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}