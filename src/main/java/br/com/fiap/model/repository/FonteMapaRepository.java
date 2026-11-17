package br.com.fiap.model.repository;

import br.com.fiap.model.entity.FonteMapa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FonteMapaRepository extends JpaRepository<FonteMapa, Long> {
    FonteMapa findByIdFonte(Long idFonte);
    List<FonteMapa> findByIdMapa(Long idMapa);
}