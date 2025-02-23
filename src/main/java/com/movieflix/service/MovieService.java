package com.movieflix.service;

import com.movieflix.config.JWTUserData;
import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import com.movieflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public Movie save(Movie movie) {
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreaming(this.findStreamings(movie.getStreaming()));
        return movieRepository.save(movie);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<Category> findCategories(List<Category> categories) {
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category ->
            categoryService.findById(category.getId()).ifPresent(categoriesFound::add)
        );
        return categoriesFound;
    }

    public List<Streaming> findStreamings(List<Streaming> streamings) {
        List<Streaming> streamingsFound = new ArrayList<>();
        streamings.forEach(streaming ->
                streamingService.findById(streaming.getId()).ifPresent(streamingsFound::add)
        );
        return streamingsFound;
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> update(Long movieId, Movie updateMovie) {
        Optional<Movie> optMovie = movieRepository.findById(movieId);
        if(optMovie.isPresent()) {

            List<Category> categories = this.findCategories(updateMovie.getCategories());
            List<Streaming> streamings = this.findStreamings(updateMovie.getStreaming());

            JWTUserData principal = (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Movie movie = optMovie.get();
            movie.setTitle(updateMovie.getTitle());
            movie.setDescription(updateMovie.getDescription());
            movie.setReleaseDate(updateMovie.getReleaseDate());
            movie.setRating(updateMovie.getRating());

            movie.getCategories().clear();
            movie.getCategories().addAll(categories);

            movie.getStreaming().clear();
            movie.getStreaming().addAll(streamings);

            return Optional.of(movieRepository.save(movie));
        }

        return Optional.empty();
    }

    public List<Movie> findByCategoryId(Long categoryId) {
        return movieRepository.findMoviesByCategories(List.of(Category.builder().id(categoryId).build()));
    }

    public void delete(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
