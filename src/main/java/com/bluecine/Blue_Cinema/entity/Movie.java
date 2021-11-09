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
@Table(name = "movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMovie;

    @Column(nullable = false, unique = true)    
    private String title;

    @Column(nullable = false)
    private String gender;

    private String synopsis;

    private String format;

    private String duration;

    private String image;

    private String schedule;

    private boolean billboard=true;

    private long ticketValue;

    @OneToMany(mappedBy = "movies", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Reserve> reserves;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRoom", nullable = false)
    private Room rooms;


    public long getIdMovie() {
        return idMovie;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public boolean isBillboard() {
        return billboard;
    }

    public void setBillboard(boolean billboard) {
        this.billboard = billboard;
    }

    public long getTicketValue() {
        return ticketValue;
    }

    public void setTicketValue(long ticketValue) {
        this.ticketValue = ticketValue;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setRooms(Room rooms) {
        this.rooms = rooms;
    }



    
    
}
