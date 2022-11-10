package com.example.photographerApp.security;

import com.example.photographerApp.model.User;
import com.example.photographerApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private IUserService userService;

    @Autowired
    public CustomUserDetailsService(IUserService userService)
    {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userService.findOneUserByEmail(username);
        return new UserPrincipal(user);
    }
}