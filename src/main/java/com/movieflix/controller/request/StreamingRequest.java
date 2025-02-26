package com.movieflix.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record StreamingRequest(@NotEmpty(message = "Nome de servico de streaming é obrigatório") String name) {
}
