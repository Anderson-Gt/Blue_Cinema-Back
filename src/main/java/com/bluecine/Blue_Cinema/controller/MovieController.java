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
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MovieDto movieDto){
        if(StringUtils.isBlank(movieDto.getTitle()))
        return new ResponseEntity(new Message("El nombre de la pelicula es obligatorio"), HttpStatus.BAD_REQUEST);
        if(movieDto.getTicketValue()<0)
        return new ResponseEntity(new Message("El valor de la boleta no puede ser un valor negativo"), HttpStatus.BAD_REQUEST);
        if(movieService.existsByTitle(movieDto.getTitle()))
        return new ResponseEntity(new Message("La pelicula ya se encuentra registrada por este titulo"), HttpStatus.BAD_REQUEST);

        Movie movie = new Movie(movieDto.getTitle(),movieDto.getGender(),movieDto.getSynopsis(),movieDto.getFormat(),movieDto.getDuration(),movieDto.getImage(),movieDto.isBillboard(),movieDto.getTicketValue());
        movieService.save(movie);
        return new ResponseEntity(new Message("Pelicula creada exitosamente"),HttpStatus.OK);
    }
    //update a movie
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long movieId,@RequestBody MovieDto movieDto){
        if(!movieService.existById(movieId))
        return new ResponseEntity(new Message("no se encuentra pelicula por ID"), HttpStatus.NOT_FOUND);
        if(movieService.existsByTitle(movieDto.getTitle()) && movieService.getByTitle(movieDto.getTitle()).get().getIdMovie() != movieId)
        return new ResponseEntity(new Message("ya existe una pelicula con ese nombre"),HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(movieDto.getTitle()))
        return new ResponseEntity(new Message("El nombre de la pelicula es obligatorio"), HttpStatus.BAD_REQUEST);
        if(movieDto.getTicketValue()<0)
        return new ResponseEntity(new Message("El valor de la boleta no puede ser un valor negativo"), HttpStatus.BAD_REQUEST);

        Movie movie = movieService.findById(movieId).get();
        movie.setTitle(movieDto.getTitle());
        movie.setGender(movieDto.getGender());
        movie.setSynopsis(movieDto.getSynopsis());
        movie.setFormat(movieDto.getFormat());
        movie.setImage(movieDto.getImage());
        movie.setBillboard(movieDto.isBillboard());
        movie.setTicketValue(movieDto.getTicketValue());
        movieService.save(movie);
        return new ResponseEntity(new Message("Pelicula actualizada"),HttpStatus.OK);
    }
    //update a movie billboard

     //read all movies
     @GetMapping("/all")
     public ResponseEntity<Iterable<Movie>> readAll(){
        Iterable<Movie> movies = movieService.findAll();
        return new ResponseEntity(movies, HttpStatus.OK);

     }

    //read all movies on billboard
    @GetMapping("/billboard")
    public ResponseEntity<List<Movie>> readByBillboard(){
        List<Movie> movies = movieService.findByBillboard(true);
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

    //delete a movie
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable(value = "id") Long movieId){
       if(!movieService.existById(movieId))
       return new ResponseEntity(new Message("no existe por ID"),HttpStatus.NOT_FOUND);
       movieService.deleteById(movieId);
       return new ResponseEntity(new Message("pelicula eliminada"), HttpStatus.OK);
    }
}
