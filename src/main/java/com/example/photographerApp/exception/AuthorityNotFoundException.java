package com.example.photographerApp.exception;

public class AuthorityNotFoundException extends RuntimeException
{
    public AuthorityNotFoundException(String message)
    {
        super(message);
    }
}
