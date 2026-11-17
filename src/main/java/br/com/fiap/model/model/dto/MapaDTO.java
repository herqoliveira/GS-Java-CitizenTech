package br.com.fiap.model.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MapaDTO(
        @NotNull Long idRegiao,
        @NotBlank String titulo,
        @NotBlank String objetivo
) {
}