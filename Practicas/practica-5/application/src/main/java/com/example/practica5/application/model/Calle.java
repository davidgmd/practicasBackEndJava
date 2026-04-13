package com.example.practica5.application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CALLES")
public class Calle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODIGO_CALLE")
    private Integer codigoCalle;

    @Column(name="TIPO_VIA")
    private String tipoVia;

    @Column(name="NOMBRE_CALLE")
    private String nombreCalle;

    @Column(name="PRIMER_NUM_TRAMO")
    private Integer primerNumTramo;

    @Column(name="ULTIMO_NUM_TRAMO")
    private Integer ultimoNumTramo;

    @Column(name="BARRIO")
    private String barrio;

    @Column(name="COD_DISTRITO")
    private Integer codDistrito;

    @Column(name="NOM_DISTRITO")
    private String nomDistrito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoCalle() {
        return codigoCalle;
    }

    public void setCodigoCalle(Integer codigoCalle) {
        this.codigoCalle = codigoCalle;
    }

    public String getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(String tipoVia) {
        this.tipoVia = tipoVia;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public Integer getPrimerNumTramo() {
        return primerNumTramo;
    }

    public void setPrimerNumTramo(Integer primerNumTramo) {
        this.primerNumTramo = primerNumTramo;
    }

    public Integer getUltimoNumTramo() {
        return ultimoNumTramo;
    }

    public void setUltimoNumTramo(Integer ultimoNumTramo) {
        this.ultimoNumTramo = ultimoNumTramo;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public Integer getCodDistrito() {
        return codDistrito;
    }

    public void setCodDistrito(Integer codDistrito) {
        this.codDistrito = codDistrito;
    }

    public String getNomDistrito() {
        return nomDistrito;
    }

    public void setNomDistrito(String nomDistrito) {
        this.nomDistrito = nomDistrito;
    }
}
