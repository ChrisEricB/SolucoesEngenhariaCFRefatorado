package model;

import java.time.LocalDateTime;

public class Auditoria {

    private int idAuditoria;
    private int idProjeto;
    private String tipo;
    private LocalDateTime dataAgendada;
    private LocalDateTime dataRealizacao;
    private int idAuditorResponsavel;
    private String resultado;

    public Auditoria() {
    }

    public int getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDateTime dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public int getIdAuditorResponsavel() {
        return idAuditorResponsavel;
    }

    public void setIdAuditorResponsavel(int idAuditorResponsavel) {
        this.idAuditorResponsavel = idAuditorResponsavel;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Auditoria{" +
                "idAuditoria=" + idAuditoria +
                ", idProjeto=" + idProjeto +
                ", tipo='" + tipo + '\'' +
                ", dataAgendada=" + dataAgendada +
                ", dataRealizacao=" + dataRealizacao +
                ", idAuditorResponsavel=" + idAuditorResponsavel +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}