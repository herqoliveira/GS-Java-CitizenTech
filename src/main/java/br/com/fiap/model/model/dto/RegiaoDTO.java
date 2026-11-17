package br.com.fiap.model.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegiaoDTO(
        @NotBlank String nome,
        @NotBlank String municipio,
        @NotBlank String uf,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
}