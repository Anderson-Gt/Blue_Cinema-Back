package com.bluecine.Blue_Cinema.controller;

import java.util.List;

import com.bluecine.Blue_Cinema.dto.Message;
import com.bluecine.Blue_Cinema.dto.MovieDto;
import com.bluecine.Blue_Cinema.entity.Movie;
import com.bluecine.Blue_Cinema.service.MovieService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    

    @Autowired
    private MovieService movieService;

    //create new movie
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MovieDto movieDto){
        if(StringUtils.isBlank(movieDto.getTitle()))
        return new ResponseEntity(new Message("El nombre de la pelicula es obligatorio"), HttpStatus.BAD_REQUEST);
        if(movieDto.getTicketValue()<0)
        return new ResponseEntity(new Message("El valor de la boleta no puede ser un valor negativo"), HttpStatus.BAD_REQUEST);
        

        String create = movieService.createMovie(movieDto);

        return new ResponseEntity(new Message(create),HttpStatus.OK);

    }
    //update a movie
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long movieId,@RequestBody MovieDto movieDto){
        if(!movieService.existById(movieId))
        return new ResponseEntity(new Message("no se encuentra pelicula por ID"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(movieDto.getTitle()))
        return new ResponseEntity(new Message("El nombre de la pelicula es obligatorio"), HttpStatus.BAD_REQUEST);
        if(movieDto.getTicketValue()<0)
        return new ResponseEntity(new Message("El valor de la boleta no puede ser un valor negativo"), HttpStatus.BAD_REQUEST);

        String update = movieService.updateMovie(movieId, movieDto);

        return new ResponseEntity(new Message(update),HttpStatus.OK);
    }

    //delete a movie setting "registered" to false
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long movieId){
        if(!movieService.existById(movieId))
        return new ResponseEntity(new Message("no existe por ID"),HttpStatus.NOT_FOUND);
        movieService.deleteById(movieId);
        return new ResponseEntity(new Message("pelicula eliminada"), HttpStatus.OK);
    }
    //update a movie billboard

     //read all movies
     @GetMapping("/all")
     public ResponseEntity<Iterable<Movie>> readAll(){
        Iterable<Movie> movies = movieService.findAllByRegistered(true);
        return new ResponseEntity(movies, HttpStatus.OK);
     }

    //read all movies on billboard
    @GetMapping("/billboard")
    public ResponseEntity<List<Movie>> readByBillboard(){
        List<Movie> movies = movieService.findByBillboardAndRegistered(true,true);
        if(movies==null){
            return new ResponseEntity(new Message("No hay peliculas en cartelera"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(movies, HttpStatus.OK);
    }
    //read a movie
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable(value="id") Long movieId){
        if(!movieService.existById(movieId))
        return new ResponseEntity(new Message("No existe por ID"), HttpStatus.NOT_FOUND);
        Movie movie = movieService.findById(movieId).get();
        return new ResponseEntity(movie, HttpStatus.OK);
    }

}
