package com.bluecine.Blue_Cinema.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.NonNull;



public class NewUserDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @NonNull
    private String documentType;
    
    @NonNull
    private long documentNumber;

    @NonNull
    private String names;

    @NonNull
    private String surnames;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private Set<String> roles = new HashSet<>();

    

    public NewUserDto(@NonNull String documentType, @NonNull long documentNumber, @NonNull String names,
            @NonNull String surnames, @NonNull String email, @NonNull String password) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.names = names;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
    }

    public String getDocumentType() {
        return documentType;
    }

    public long getDocumentNumber() {
        return documentNumber;
    }

    public String getNames() {
        return names;
    }

    public String getSurnames() {
        return surnames;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public Set<String> getRoles(){
        return roles;
    }

    public void setRoles(Set<String> roles){
        this.roles = roles;
    }
}
