package ru.kovshov.cloud.payload.responce.error;


import lombok.Data;

@Data
public class UserExistError {
    private String massage = "user exists with this email";
}
