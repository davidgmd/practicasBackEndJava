package com.example.practica4.demo.repository;

import com.example.practica4.demo.entity.Peliculas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculasRepository extends JpaRepository<Peliculas, String> {
}
