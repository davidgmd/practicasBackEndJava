package com.example.practica5.application.model;

import lombok.ToString;

@ToString
public class CalleCsv {
    private String codigoCalle;
    private String tipoVia;
    private String nombreCalle;
    private String primerNumTramo;
    private String ultimoNumTramo;
    private String barrio;
    private String codDistrito;
    private String nomDistrito;

    public String getCodigoCalle() {
        return codigoCalle;
    }

    public void setCodigoCalle(String codigoCalle) {
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

    public String getPrimerNumTramo() {
        return primerNumTramo;
    }

    public void setPrimerNumTramo(String primerNumTramo) {
        this.primerNumTramo = primerNumTramo;
    }

    public String getUltimoNumTramo() {
        return ultimoNumTramo;
    }

    public void setUltimoNumTramo(String ultimoNumTramo) {
        this.ultimoNumTramo = ultimoNumTramo;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCodDistrito() {
        return codDistrito;
    }

    public void setCodDistrito(String codDistrito) {
        this.codDistrito = codDistrito;
    }

    public String getNomDistrito() {
        return nomDistrito;
    }

    public void setNomDistrito(String nomDistrito) {
        this.nomDistrito = nomDistrito;
    }
}
