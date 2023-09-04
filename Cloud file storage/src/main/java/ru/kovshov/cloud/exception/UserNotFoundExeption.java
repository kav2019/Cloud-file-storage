package ru.kovshov.cloud.exception;

public class UserNotFoundExeption extends RuntimeException{
    public UserNotFoundExeption(String msg){
        super(msg);
    }
}
