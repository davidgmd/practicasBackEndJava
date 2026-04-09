package com.example.practica4.demo.repository;

import com.example.practica4.demo.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, String> {
}
