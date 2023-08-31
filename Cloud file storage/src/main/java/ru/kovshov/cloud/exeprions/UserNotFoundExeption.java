package ru.kovshov.cloud.exeprions;

public class UserNotFoundExeption extends RuntimeException{
    public UserNotFoundExeption(String msg){
        super(msg);
    }
}
