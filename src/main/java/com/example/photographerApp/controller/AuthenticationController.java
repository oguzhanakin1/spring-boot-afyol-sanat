package com.example.photographerApp.controller;

import com.example.photographerApp.request.UserSignInRequest;
import com.example.photographerApp.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/authentication")//pre-path
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }
    @PostMapping("sign-in")// api/authentication/sign-in
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInRequest signInRequest)
    {
        return new ResponseEntity<>(authenticationService.signInAndReturnJWT(signInRequest), HttpStatus.OK);
    }
}
