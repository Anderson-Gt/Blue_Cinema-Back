package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "users")
public class User implements Serializable {
     
    @Column
    private String documentType;
    
    @Id
    @Column(nullable = false, unique = true)   
    private long documentNumber;

    @Column(length = 50)
    private String names;

    private String surnames;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Reserve> reserves = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userxrole", joinColumns = @JoinColumn(name = "documentNumber"),
    inverseJoinColumns = @JoinColumn(name = "idRole"))
    private Set<Role> roles = new HashSet<>();

    public User(){
        
    }

    public User(String documentType, long documentNumber, String names, String surnames, String email,
            String password) {
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

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

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

    public Set<Reserve> getReserves() {
        return reserves;
    }

    public void setReserves(Set<Reserve> reserves) {
        this.reserves = reserves;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

   
}
