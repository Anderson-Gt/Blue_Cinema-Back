package com.bluecine.Blue_Cinema.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMovie;

    @Column(nullable = false)    
    private String title;

    @Column(nullable = false)
    private String gender;

    private String synopsis;

    private String format;

    private String duration;

    private String image;

    private boolean billboard=true;

    private boolean registered=true;

    private float ticketValue;

    @OneToMany(mappedBy = "movies", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Reserve> reserves = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="schedulexmovie",joinColumns = @JoinColumn(name="idMovie"),inverseJoinColumns = @JoinColumn(name="idSchedule"))
    private Set<Schedule> schedules;

    


    public Movie() {
    }

    public Movie(String title, String gender, String synopsis, String format, String duration, String image,
            boolean billboard, float ticketValue) {
        this.title = title;
        this.gender = gender;
        this.synopsis = synopsis;
        this.format = format;
        this.duration = duration;
        this.image = image;
        this.billboard = billboard;
        this.ticketValue = ticketValue;
    }

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

    public boolean isBillboard() {
        return billboard;
    }

    public void setBillboard(boolean billboard) {
        this.billboard = billboard;
    }

    public float getTicketValue() {
        return ticketValue;
    }

    public void setTicketValue(float ticketValue) {
        this.ticketValue = ticketValue;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setReserves(Set<Reserve> reserves) {
        this.reserves = reserves;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }


    
    
}
