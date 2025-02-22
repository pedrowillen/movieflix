package com.movieflix.controller.request;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {
}
