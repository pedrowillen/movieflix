package com.movieflix.controller;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.entity.Movie;
import com.movieflix.mapper.CategoryMapper;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> save(@RequestBody MovieRequest request) {
        Movie savedMovie = movieService.save(MovieMapper.toMovie(request));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(savedMovie));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(movies
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findByMovieYd(@PathVariable Long id) {
        return movieService.findById(id)
                .map(mv -> ResponseEntity.ok(MovieMapper.toMovieResponse(mv)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody MovieRequest request) {
        return movieService.update(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategoryId(@RequestParam Long category) {
        return ResponseEntity.ok(
                movieService.findByCategoryId(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<Movie> optionalMovie = movieService.findById(id);

        if(optionalMovie.isPresent()) {
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
