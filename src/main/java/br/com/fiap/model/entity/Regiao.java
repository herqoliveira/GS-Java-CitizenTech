package br.com.fiap.model.entity;

import jakarta.persistence.*;
import br.com.fiap.model.model.dto.RegiaoDTO;

@Entity(name = "Regiao")
@Table(name = "REGIAO")
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegiao;

    private String nome;
    private String municipio;
    private String uf;
    private Double latitude;
    private Double longitude;

    public Regiao() {
    }

    public Regiao(RegiaoDTO regiaoDTO) {
        this.nome = regiaoDTO.nome();
        this.municipio = regiaoDTO.municipio();
        this.uf = regiaoDTO.uf();
        this.latitude = regiaoDTO.latitude();
        this.longitude = regiaoDTO.longitude();
    }

    public Long getIdRegiao() {
        return idRegiao;
    }

    public void setIdRegiao(Long idRegiao) {
        this.idRegiao = idRegiao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
