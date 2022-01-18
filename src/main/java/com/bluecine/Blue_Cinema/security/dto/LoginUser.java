package com.bluecine.Blue_Cinema.security.dto;

import lombok.NonNull;

public class LoginUser {
    @NonNull
    private String email;
    @NonNull
    private String password;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    
}
