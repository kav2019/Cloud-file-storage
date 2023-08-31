package ru.kovshov.cloud.model;

public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
