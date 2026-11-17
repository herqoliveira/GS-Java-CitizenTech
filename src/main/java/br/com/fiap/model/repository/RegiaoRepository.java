package br.com.fiap.model.repository;

import br.com.fiap.model.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    Regiao findByIdRegiao(Long idRegiao);
}