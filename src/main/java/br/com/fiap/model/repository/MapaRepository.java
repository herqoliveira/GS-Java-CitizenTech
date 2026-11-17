package br.com.fiap.model.repository;

import br.com.fiap.model.entity.Mapa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MapaRepository extends JpaRepository<Mapa, Long> {
    Mapa findByIdMapa(Long idMapa);
    List<Mapa> findByIdRegiao(Long idRegiao);
}
