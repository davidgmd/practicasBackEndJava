package com.example.practica4.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="PELICULAS")
public class Peliculas {
    @Id
    @Column(name="id_pelicula")
    private String idPelicula;
    @Column(name="titulo")
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
    //Name por convencion tablaA_tablaB, joinColumns las nuestras, inverseJoin las foraneas
    @JoinTable(
            name = "peliculas_actores",
            joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_actor")
    )
    private List<Actores> actores;

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
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
}
