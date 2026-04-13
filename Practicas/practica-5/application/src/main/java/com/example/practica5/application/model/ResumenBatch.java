package com.example.practica5.application.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESUMEN_BATCH")
public class ResumenBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FILTRO_USADO")
    private String filtroUsado;

    @Column(name = "NUM_REGISTROS_GUARDADOS")
    private Integer numRegistrosGuardados;

    @Column(name = "ESTADO_BATCH")
    private String estadoBatch;

    @Column(name = "TIMESTAMP_OPERACION")
    private LocalDateTime timestampOperacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiltroUsado() {
        return filtroUsado;
    }

    public void setFiltroUsado(String filtroUsado) {
        this.filtroUsado = filtroUsado;
    }

    public Integer getNumRegistrosGuardados() {
        return numRegistrosGuardados;
    }

    public void setNumRegistrosGuardados(Integer numRegistrosGuardados) {
        this.numRegistrosGuardados = numRegistrosGuardados;
    }

    public String getEstadoBatch() {
        return estadoBatch;
    }

    public void setEstadoBatch(String estadoBatch) {
        this.estadoBatch = estadoBatch;
    }

    public LocalDateTime getTimestampOperacion() {
        return timestampOperacion;
    }

    public void setTimestampOperacion(LocalDateTime timestampOperacion) {
        this.timestampOperacion = timestampOperacion;
    }
}
