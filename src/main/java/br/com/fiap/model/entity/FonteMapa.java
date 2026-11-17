package br.com.fiap.model.entity;

import br.com.fiap.model.model.dto.FonteMapaDTO;
import jakarta.persistence.*;

@Entity(name = "FonteMapa")
@Table(name = "FONTE_MAPA")
public class FonteMapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFonte;

    private Long idMapa;
    private String titulo;
    private String url;

    public FonteMapa() {
    }

    public FonteMapa(FonteMapaDTO fonteMapaDTO) {
        this.idMapa = fonteMapaDTO.idMapa();
        this.titulo = fonteMapaDTO.titulo();
        this.url = fonteMapaDTO.url();
    }

    public Long getIdFonte() {
        return idFonte;
    }

    public void setIdFonte(Long idFonte) {
        this.idFonte = idFonte;
    }

    public Long getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(Long idMapa) {
        this.idMapa = idMapa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}