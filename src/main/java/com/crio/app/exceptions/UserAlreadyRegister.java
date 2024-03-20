package com.crio.app.exceptions;

public class UserAlreadyRegister extends Exception {
    public UserAlreadyRegister(String message) {
        super(message);
    }
}
