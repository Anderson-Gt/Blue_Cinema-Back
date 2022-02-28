package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="reserves")
public class Reserve implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long idReserve;

    @Column
    private int idSchedule;

    @Column
    private int amount;

    @Column
    private long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documentNumber", nullable = false)    
    private User users;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idMovie", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Movie movies;

    /*@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)*/
    @ManyToMany
    @JoinTable(name="chairxreserve",joinColumns = @JoinColumn(name="idReserve"),inverseJoinColumns = @JoinColumn(name="idChair"))
    private Set<Chair> chairs;


    public Reserve() {
    }

    

    public Reserve(int idSchedule, User users, Movie movies, Set<Chair> chairs) {
        this.idSchedule = idSchedule;
        this.users = users;
        this.movies = movies;
        this.chairs = chairs;
        this.amount = chairs.size();
        this.totalPrice = (long)(chairs.size()*movies.getTicketValue());
    }

    public void updateReserve(Set<Chair> chairs){
        this.amount=chairs.size();
        this.totalPrice = (long)(chairs.size()*movies.getTicketValue());
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

    public long getUsers() {
        return users.getDocumentNumber();
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Movie getMovies() {
        return movies;
    }

    public void setMovies(Movie movies) {
        this.movies = movies;
    }

    public Set<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(Set<Chair> chairs) {
        this.chairs = chairs;
    }

    public int getIdSchedule() {
        return idSchedule;
    }    
}
