package com.movieflix.mapper;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

@UtilityClass
public class MovieMapper {

    public static Movie toMovie(MovieRequest request) {

        List<Category> categories = request.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Streaming> streamings = request.streamings().stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build())
                .toList();

        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streaming(streamings)
                .build();
    }

    public static MovieResponse toMovieResponse(Movie movie) {

        List<CategoryResponse> categories = movie.getCategories()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        List<StreamingResponse> streamings = movie.getStreaming()
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }
}
