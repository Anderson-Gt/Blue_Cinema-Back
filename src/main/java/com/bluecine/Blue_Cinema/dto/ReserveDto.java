package com.bluecine.Blue_Cinema.dto;

import java.io.Serializable;
import java.util.List;

import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReserveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private Long idMovie;
    private Integer amount;
    private Long totalPrice;
    private List<Integer>chairs;
    
}
