package com.example.practica4.demo.repository;

import com.example.practica4.demo.entity.Productoras;

import java.util.List;
import java.util.Optional;

public interface ProductorasCriteriaRepository {
    List<Productoras> findAllCriteria();
    Optional<Productoras> findByIdCriteria(String idProductora);
    Productoras saveCriteria(Productoras productora);
    void deleteByIdCriteria(String idProductora);
}
