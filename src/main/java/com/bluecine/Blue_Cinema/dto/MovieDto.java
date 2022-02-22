package com.bluecine.Blue_Cinema.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MovieDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String gender;
    private String synopsis;
    private String format;
    private String duration;
    private String image;
    private boolean billboard;
    private float ticketValue;
    private Set<Integer> schedules = new HashSet<>();


    
    public MovieDto() {
    }

    public MovieDto(String title, String gender, String synopsis, String format, String duration, String image,
            boolean billboard, float ticketValue, Set<Integer> schedules) {
        this.title = title;
        this.gender = gender;
        this.synopsis = synopsis;
        this.format = format;
        this.duration = duration;
        this.image = image;
        this.billboard = billboard;
        this.ticketValue = ticketValue;
        this.schedules = schedules;
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
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
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

    public Set<Integer> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Integer> schedules) {
        this.schedules = schedules;
    }
}
