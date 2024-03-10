package com.aulacube.rbac.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String message){
        super(message);
}
}
