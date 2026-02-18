package com.blandino.demo_park_api.exception;

public class EntityNotFoundException extends RuntimeException {


    public EntityNotFoundException(String msg) {

        super(msg);
    }
}
