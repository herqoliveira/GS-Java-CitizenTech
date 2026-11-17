package br.com.fiap.model.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontoInteresseDTO(
        @NotNull Long idFonte,
        @NotBlank String nome,
        @NotBlank String tipo,
        @NotNull Double latitude,
        @NotNull Double longitude,
        String observacao
) {
}