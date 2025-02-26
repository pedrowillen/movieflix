package com.movieflix.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MovieRequest(
        @NotEmpty(message = "Título do filme é obrigatório") String title,
        String description,
        LocalDate releaseDate,
        double rating,
        List<Long> categories,
        List<Long> streamings
) {

}
