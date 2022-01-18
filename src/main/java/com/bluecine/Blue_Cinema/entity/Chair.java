package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="chairs")
public class Chair implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idChair;

    /*@ManyToMany(fetch = FetchType.LAZY,mappedBy = "chairs")*/
    @ManyToMany(mappedBy = "chairs")
    private Set<Reserve> reserves;

    public long getIdChair() {
        return idChair;
    }
    
}
