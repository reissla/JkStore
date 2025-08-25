package com.reis.JKStore.domain;

public enum Role {

    ADMIN("Admin"),
    USUARIO("Usuário");

    private final String role;

    Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
