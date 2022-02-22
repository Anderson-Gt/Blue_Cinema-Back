package com.bluecine.Blue_Cinema.dto;

import java.io.Serializable;
import java.util.List;

public class EditReserveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Integer> chairs;

    public EditReserveDto() {
    }

    public EditReserveDto(List<Integer> chairs) {
        this.chairs = chairs;
    }

    public List<Integer> getChairs() {
        return chairs;
    }

    public void setChairs(List<Integer> chairs) {
        this.chairs = chairs;
    }
    
}
