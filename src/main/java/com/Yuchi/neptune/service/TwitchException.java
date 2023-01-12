package com.Yuchi.neptune.service;

public class TwitchException extends RuntimeException{
    public TwitchException(String errorMessage) {
        super(errorMessage);
    }
}
