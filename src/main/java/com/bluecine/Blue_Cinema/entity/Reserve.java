package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="reserves")
public class Reserve implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long idReserve;

    @Column
    private int amount;

    @Column
    private long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documentNumber", nullable = false)    
    private User users;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idMovie", nullable = false)
    private Movie movies;

    @OneToMany(mappedBy = "reserves", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Chair> chairs;    


    public Reserve() {
    }

    public long getIdReserve() {
        return idReserve;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public void setMovies(Movie movies){
        this.movies = movies;
    }


   

    
}
