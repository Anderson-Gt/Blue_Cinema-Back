package com.bluecine.Blue_Cinema.service;

import java.util.List;
import java.util.Optional;

import com.bluecine.Blue_Cinema.dto.EditReserveDto;
import com.bluecine.Blue_Cinema.dto.ReserveDto;
import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.entity.Reserve;
import com.bluecine.Blue_Cinema.entity.User;

public interface ReserveService {

    public Iterable<Reserve>findAll();

    public Optional<Reserve>findById(Long Id);

    public Reserve save(Reserve reserve);

    public void deleteById(Long Id);

    public boolean existsById(Long Id);

    public String createReserve(ReserveDto reserveDto);

    public String updateReserve(Long reserveId, EditReserveDto editReserveDto);

    public List<Integer> getReservedChairs(Integer idSchedule, Long idMovie);

    public List<Reserve> readReservesByUser(String email);

    public List<Reserve> findByIdScheduleAndMovies(Integer idSchedule, Movie movie);

    public List<Reserve>findByUsers(User user);

    public String buildEmail(String name,String movieLink, String movieTitle, Integer idReserve, String genre, String hour, double price, String chairs);
    
}
