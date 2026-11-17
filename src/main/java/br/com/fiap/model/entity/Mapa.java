package br.com.fiap.model.entity;

import br.com.fiap.model.model.dto.MapaDTO;
import jakarta.persistence.*;

@Entity(name = "Mapa")
@Table(name = "MAPA")
public class Mapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMapa;

    private Long idRegiao;
    private String titulo;
    private String objetivo;
    private String status;

    public Mapa() {
    }

    public Mapa(MapaDTO mapaDTO) {
        this.idRegiao = mapaDTO.idRegiao();
        this.titulo = mapaDTO.titulo();
        this.objetivo = mapaDTO.objetivo();
        this.status = "RASCUNHO";
    }

    public Long getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(Long idMapa) {
        this.idMapa = idMapa;
    }

    public Long getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(Long idRegiao) {
        this.idRegiao = idRegiao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}