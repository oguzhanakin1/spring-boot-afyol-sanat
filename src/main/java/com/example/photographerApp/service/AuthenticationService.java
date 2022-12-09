package com.example.photographerApp.service;

import com.example.photographerApp.model.User;
import com.example.photographerApp.repository.UserRepository;
import com.example.photographerApp.request.UserSignInRequest;
import com.example.photographerApp.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtProvider jwtProvider)
    {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public String signInAndReturnJWT(UserSignInRequest signInRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return jwtProvider.generateToken(userPrincipal);
    }
}
