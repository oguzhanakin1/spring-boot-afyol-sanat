package com.example.photographerApp.security;

import com.example.photographerApp.model.User;
import com.example.photographerApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userService.findOneUserByEmail(username);
        if (user == null)
        {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = org.springframework.security.core.userdetails
                .User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getAuthority().getName()).build();
        return userDetails;
    }
}
