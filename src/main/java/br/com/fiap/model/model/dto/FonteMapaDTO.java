package br.com.fiap.model.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FonteMapaDTO(
        @NotNull Long idMapa,
        @NotBlank String titulo,
        @NotBlank String url
) {
}