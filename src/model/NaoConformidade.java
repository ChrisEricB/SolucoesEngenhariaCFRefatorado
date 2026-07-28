package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NaoConformidade {

    private int idNaoConformidade;
    private Integer idAuditoria;
    private int idProjeto;
    private String descricao;
    private String causaRaiz;
    private String gravidade;
    private LocalDateTime dataRegistro;
    private int idResponsavelCorrecao;
    private LocalDate prazoCorrecao;
    private String status;
    private LocalDateTime dataCorrecao;

    public NaoConformidade() {
    }

    public int getIdNaoConformidade() {
        return idNaoConformidade;
    }

    public void setIdNaoConformidade(int idNaoConformidade) {
        this.idNaoConformidade = idNaoConformidade;
    }

    public Integer getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCausaRaiz() {
        return causaRaiz;
    }

    public void setCausaRaiz(String causaRaiz) {
        this.causaRaiz = causaRaiz;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public int getIdResponsavelCorrecao() {
        return idResponsavelCorrecao;
    }

    public void setIdResponsavelCorrecao(int idResponsavelCorrecao) {
        this.idResponsavelCorrecao = idResponsavelCorrecao;
    }

    public LocalDate getPrazoCorrecao() {
        return prazoCorrecao;
    }

    public void setPrazoCorrecao(LocalDate prazoCorrecao) {
        this.prazoCorrecao = prazoCorrecao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCorrecao() {
        return dataCorrecao;
    }

    public void setDataCorrecao(LocalDateTime dataCorrecao) {
        this.dataCorrecao = dataCorrecao;
    }

    @Override
    public String toString() {
        return "NaoConformidade{" +
                "idNaoConformidade=" + idNaoConformidade +
                ", idAuditoria=" + idAuditoria +
                ", idProjeto=" + idProjeto +
                ", descricao='" + descricao + '\'' +
                ", causaRaiz='" + causaRaiz + '\'' +
                ", gravidade='" + gravidade + '\'' +
                ", dataRegistro=" + dataRegistro +
                ", idResponsavelCorrecao=" + idResponsavelCorrecao +
                ", prazoCorrecao=" + prazoCorrecao +
                ", status='" + status + '\'' +
                ", dataCorrecao=" + dataCorrecao +
                '}';
    }
}