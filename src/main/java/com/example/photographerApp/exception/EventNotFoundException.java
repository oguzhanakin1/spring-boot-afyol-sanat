package com.example.photographerApp.exception;

public class EventNotFoundException extends RuntimeException
{
    public EventNotFoundException(String message)
    {
        super(message);
    }
}