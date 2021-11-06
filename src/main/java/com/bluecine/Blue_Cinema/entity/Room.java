package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="rooms")
public class Room implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_room;
    private String name;
    private int amount;

   @OneToMany(mappedBy = "rooms", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Movie> movies;

    @OneToMany(mappedBy = "rooms", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Chair> chairs;

    public long getId() {
        return id_room;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    
}
