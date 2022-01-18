package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bluecine.Blue_Cinema.enums.RoleName;
import org.springframework.lang.NonNull;

@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idRole;

    @NonNull
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public Role(){

    }

    public Role(@NonNull RoleName roleName) {
        this.roleName = roleName;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
    
}
