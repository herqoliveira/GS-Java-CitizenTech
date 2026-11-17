package br.com.fiap.model.repository;

import br.com.fiap.model.entity.PontoInteresse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PontoInteresseRepository extends JpaRepository<PontoInteresse, Long> {
    PontoInteresse findByIdPonto(Long idPonto);
    List<PontoInteresse> findByIdFonte(Long idFonte);
}