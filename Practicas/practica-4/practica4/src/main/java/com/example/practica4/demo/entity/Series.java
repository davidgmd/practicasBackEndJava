package com.example.practica4.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SERIES")
public class Series {
    @Id
    @Column(name="id_serie")
    private String idSerie;
    @Column(name="Titulo")
    private String titulo;
    @Column(name="ano")
    private Integer ano;
    @ManyToOne
    @JoinColumn(name = "id_director")
    private Directores director;
    @ManyToOne
    @JoinColumn(name = "id_productora")
    private Productoras productoras;
    @ManyToMany
    @JoinTable(
            name = "series_actores",
            joinColumns = @JoinColumn(name = "id_serie"),
            inverseJoinColumns = @JoinColumn(name = "id_actor")
    )
    private List<Actores> actores;

    public Directores getDirector() {
        return director;
    }

    public void setDirector(Directores director) {
        this.director = director;
    }

    public Productoras getProductoras() {
        return productoras;
    }

    public void setProductoras(Productoras productoras) {
        this.productoras = productoras;
    }

    public List<Actores> getActores() {
        return actores;
    }

    public void setActores(List<Actores> actores) {
        this.actores = actores;
    }

    public String getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(String idSerie) {
        this.idSerie = idSerie;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
