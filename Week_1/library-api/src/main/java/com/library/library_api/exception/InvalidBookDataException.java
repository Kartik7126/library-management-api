package com.library.library_api.exception;

public class InvalidBookDataException extends RuntimeException{
    public InvalidBookDataException(String message){
        super(message);
    }
}
