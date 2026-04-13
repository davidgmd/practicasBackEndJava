package com.example.practica5.application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DISTRITO")
public class ResumenDistritos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NOMBRE_CALLE")
    private String nombreCalle;

    @Column(name="NUM_CASAS")
    private Integer numCasas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public Integer getNumCasas() {
        return numCasas;
    }

    public void setNumCasas(Integer numCasas) {
        this.numCasas = numCasas;
    }
}
