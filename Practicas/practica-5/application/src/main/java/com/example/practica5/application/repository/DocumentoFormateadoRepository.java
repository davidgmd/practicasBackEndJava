package com.example.practica5.application.repository;

import com.example.practica5.application.batch.paralelo.DocumentoFormateadoParalelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoFormateadoRepository extends JpaRepository<DocumentoFormateadoParalelo, Long> {
}
