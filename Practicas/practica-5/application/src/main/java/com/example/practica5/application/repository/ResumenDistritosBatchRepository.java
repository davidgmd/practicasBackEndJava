package com.example.practica5.application.repository;

import com.example.practica5.application.model.ResumenDistritos;
import com.example.practica5.application.projection.ResumenDistritosView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumenDistritosBatchRepository extends JpaRepository<ResumenDistritos, Long> {
    @Query("""
           SELECT c.nombreCalle AS nombreCalle, COUNT(c) AS numCasas
           FROM Calle c
           GROUP BY c.nombreCalle
           """)
    List<ResumenDistritosView> countCallesPorDistrito();
}
