package com.example.photographerApp.service;

import com.example.photographerApp.model.User;

public interface IAuthenticationService
{
    String signInAndReturnJWT(User signInRequest);
}
