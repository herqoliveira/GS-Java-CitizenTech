package br.com.fiap.model.entity;

import br.com.fiap.model.model.dto.PontoInteresseDTO;
import jakarta.persistence.*;

@Entity(name = "PontoInteresse")
@Table(name = "PONTO_INTERESSE")
public class PontoInteresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPonto;

    private Long idFonte;
    private String nome;
    private String tipo;
    private Double latitude;
    private Double longitude;
    private String statusVerificacao;
    private String observacao;

    public PontoInteresse() {
    }

    public PontoInteresse(PontoInteresseDTO pontoInteresseDTO) {
        this.idFonte = pontoInteresseDTO.idFonte();
        this.nome = pontoInteresseDTO.nome();
        this.tipo = pontoInteresseDTO.tipo();
        this.latitude = pontoInteresseDTO.latitude();
        this.longitude = pontoInteresseDTO.longitude();
        this.statusVerificacao = "PENDENTE";
        this.observacao = pontoInteresseDTO.observacao();
    }

    public Long getIdPonto() {
        return idPonto;
    }

    public Long getIdFonte() {
        return idFonte;
    }

    public void setIdFonte(Long idFonte) {
        this.idFonte = idFonte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getStatusVerificacao() {
        return statusVerificacao;
    }

    public void setStatusVerificacao(String statusVerificacao) {
        this.statusVerificacao = statusVerificacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}