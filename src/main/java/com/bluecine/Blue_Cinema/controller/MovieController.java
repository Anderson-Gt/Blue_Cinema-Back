package com.bluecine.Blue_Cinema.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //create new movie
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Movie movie){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(movie));
    }

    //read a movie
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value="id") Long movieId){
        Optional<Movie>oMovie = movieService.findById(movieId);
        if(!oMovie.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oMovie);
    }

    @GetMapping("/billboard")
    public ResponseEntity<?> readByBillboard(){
        List<Movie>oMovie = movieService.findByBillboard(true);
        if(oMovie==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oMovie);
    }

    //update a movie
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Movie movieDetails, @PathVariable(value = "id") Long movieId){
        Optional<Movie> movie = movieService.findById(movieId);
        if(!movie.isPresent()){
            return ResponseEntity.notFound().build();
        }

        movie.get().setTitle(movieDetails.getTitle());
        movie.get().setGender(movieDetails.getGender());
        movie.get().setSynopsis(movieDetails.getSynopsis());
        movie.get().setFormat(movieDetails.getGender());
        movie.get().setImage(movieDetails.getImage());
        movie.get().setSchedule(movieDetails.getSchedule());
        movie.get().setBillboard(movieDetails.isBillboard());
        movie.get().setTicketValue(movieDetails.getTicketValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(movie.get()));
    }
    //delete a movie
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long movieId){
        if(!movieService.findById(movieId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        movieService.deleteById(movieId);
        return ResponseEntity.ok().build();
    }

    //read all movies
    @GetMapping
    public List<Movie>readAll(){
        List<Movie>movies= StreamSupport
        .stream(movieService.findAll().spliterator(), false)
        .collect(Collectors.toList());
        return movies;
    }
    
}
