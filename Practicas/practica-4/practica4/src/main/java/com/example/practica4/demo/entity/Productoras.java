package com.example.practica4.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTORAS")
public class Productoras {
    @Id
    @Column(name="id_productora")
    private String idProductora;
    @Column(name="nombre")
    private String nombre;
    @Column(name="ano_fundacion")
    private Integer anoFundacion;

    public String getIdProductora() {
        return idProductora;
    }

    public void setIdProductora(String idProductora) {
        this.idProductora = idProductora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoFundacion() {
        return anoFundacion;
    }

    public void setAnoFundacion(Integer anoFundacion) {
        this.anoFundacion = anoFundacion;
    }
}
