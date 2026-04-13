package com.example.practica5.application.repository;

import com.example.practica5.application.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalleRepository extends JpaRepository<Calle, Long>{
}
