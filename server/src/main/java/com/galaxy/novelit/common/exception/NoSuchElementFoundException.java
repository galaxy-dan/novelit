package com.galaxy.novelit.common.exception;

public class NoSuchElementFoundException extends RuntimeException{
    public NoSuchElementFoundException(final String errorMsg){
        super(errorMsg);
    }
}
