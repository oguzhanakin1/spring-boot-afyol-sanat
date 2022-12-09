package com.example.photographerApp.exception;

public class MessageNotFoundException extends RuntimeException
{
    public MessageNotFoundException(String message)
    {
        super(message);
    }
}
