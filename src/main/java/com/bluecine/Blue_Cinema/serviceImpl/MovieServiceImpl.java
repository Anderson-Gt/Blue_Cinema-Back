package com.bluecine.Blue_Cinema.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bluecine.Blue_Cinema.dto.MovieDto;
import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.entity.Schedule;
import com.bluecine.Blue_Cinema.repository.MovieRepository;
import com.bluecine.Blue_Cinema.service.MovieService;
import com.bluecine.Blue_Cinema.service.ScheduleService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Movie> findAll() {        
        return movieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Movie> findByBillboard(Boolean billboard) {
       return movieRepository.findByBillboard(billboard);
        
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public boolean existById(Long Id) {
        return movieRepository.existsById(Id);
    }

    @Override
    @Transactional
    public boolean existsByTitle(String title) {
        return movieRepository.existsByTitle(title);
    }

    @Override
    @Transactional
    public Optional<Movie> getByTitle(String name){
        return movieRepository.findByTitle(name);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        movieRepository.deleteById(id);        
    }

    @Override
    @Transactional
    public String createMovie(MovieDto movieDto){
        Movie movie = new Movie(movieDto.getTitle(),movieDto.getGender(),movieDto.getSynopsis(),movieDto.getFormat(),movieDto.getDuration(),movieDto.getImage(),movieDto.isBillboard(),movieDto.getTicketValue());

        Set<Schedule> schedules = new HashSet<>();
        for(int schedulemovie:movieDto.getSchedules()){
            schedules.add(scheduleService.findById(schedulemovie).get());
            movie.setSchedules(schedules);
            
        }
        save(movie);   

        return "Pelicula creada exitosamente";
    }

    @Override
    @Transactional
    public String updateMovie(Long movieId, MovieDto movieDto){
        Movie movie = findById(movieId).get();
        movie.setTitle(movieDto.getTitle());
        movie.setGender(movieDto.getGender());
        movie.setSynopsis(movieDto.getSynopsis());
        movie.setFormat(movieDto.getFormat());
        movie.setImage(movieDto.getImage());
        movie.setBillboard(movieDto.isBillboard());
        movie.setTicketValue(movieDto.getTicketValue());
        save(movie);

        return "Pelicula actualizada";
    }
}
