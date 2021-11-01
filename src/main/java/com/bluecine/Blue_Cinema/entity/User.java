package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User implements Serializable {
     
    private String Document_type;
    
    @Id
    @Column(nullable = false, unique = true)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)    
    private long Document_number;

    @Column(length = 50)
    private String Names;

    private String Surnames;

    @Column(name="email", nullable = false, length = 40, unique = true)
    private String Email;

    @Column(nullable = false)
    private String Password;

    public String getDocument_type() {
        return Document_type;
    }

    public void setDocument_type(String document_type) {
        Document_type = document_type;
    }

    public long getDocument_number() {
        return Document_number;
    }

    public void setDocument_number(long document_number) {
        Document_number = document_number;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getSurnames() {
        return Surnames;
    }

    public void setSurnames(String surnames) {
        Surnames = surnames;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
