package com.bluecine.Blue_Cinema.dto;

import java.io.Serializable;
import java.util.List;

public class ReserveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private Long idMovie;
    private Integer idSchedule;
    private List<Integer> chairs;

    public ReserveDto() {
    }

    public ReserveDto(String email, Long idMovie, Integer idSchedule, List<Integer> chairs) {
        this.email = email;
        this.idMovie = idMovie;
        this.idSchedule = idSchedule;
        this.chairs = chairs;
    }
    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getIdMovie() {
        return idMovie;
    }
    public void setIdMovie(Long idMovie) {
        this.idMovie = idMovie;
    }
    
    public Integer getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(Integer idSchedule) {
        this.idSchedule = idSchedule;
    }

    public List<Integer> getChairs() {
        return chairs;
    }
    public void setChairs(List<Integer> chairs) {
        this.chairs = chairs;
    }
    
    
}
