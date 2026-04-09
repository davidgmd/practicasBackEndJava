package com.example.practica4.demo.services;

import com.example.practica4.demo.entity.Directores;
import com.example.practica4.demo.exception.DuplicadoException;
import com.example.practica4.demo.repository.DirectoresCriteriaRepository;
import com.example.practica4.demo.repository.DirectoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoresService {

    private final DirectoresRepository directoresRepository;
    private final DirectoresCriteriaRepository directoresCriteriaRepository;

    public DirectoresService(DirectoresRepository directoresRepository,
                             DirectoresCriteriaRepository directoresCriteriaRepository) {
        this.directoresRepository = directoresRepository;
        this.directoresCriteriaRepository = directoresCriteriaRepository;
    }

    public List<Directores> listarDirectores(String repo) {
        if ("criteria".equalsIgnoreCase(repo)) {
            return directoresCriteriaRepository.findAllCriteria();
        }
        return directoresRepository.findAll();
    }

    public Directores listarDirector(String dni, String repo) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("DNI inválido");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            return directoresCriteriaRepository.findByIdCriteria(dni)
                    .orElseThrow(() -> new RuntimeException("Director no encontrado"));
        }

        return directoresRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Director no encontrado"));
    }

    public Directores anadirDirector(Directores director, String repo) {
        if (director == null || director.getDni() == null || director.getDni().isBlank()) {
            throw new IllegalArgumentException("Director inválido");
        }

        boolean existe = "criteria".equalsIgnoreCase(repo)
                ? directoresCriteriaRepository.findByIdCriteria(director.getDni()).isPresent()
                : directoresRepository.findById(director.getDni()).isPresent();

        if (existe) {
            throw new DuplicadoException("No se puede añadir dos veces el mismo director");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            return directoresCriteriaRepository.saveCriteria(director);
        }

        return directoresRepository.save(director);
    }

    public Directores modificarDirector(String dni, Directores nuevoValor, String repo) {
        Directores director = "criteria".equalsIgnoreCase(repo)
                ? directoresCriteriaRepository.findByIdCriteria(dni)
                .orElseThrow(() -> new RuntimeException("No existe"))
                : directoresRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("No existe"));

        director.setNombre(nuevoValor.getNombre());
        director.setApellidos(nuevoValor.getApellidos());
        director.setEdad(nuevoValor.getEdad());
        director.setNacionalidad(nuevoValor.getNacionalidad());

        if ("criteria".equalsIgnoreCase(repo)) {
            return directoresCriteriaRepository.saveCriteria(director);
        }

        return directoresRepository.save(director);
    }

    public void eliminarDirector(String dni, String repo) {
        boolean existe = "criteria".equalsIgnoreCase(repo)
                ? directoresCriteriaRepository.findByIdCriteria(dni).isPresent()
                : directoresRepository.findById(dni).isPresent();

        if (!existe) {
            throw new RuntimeException("No existe");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            directoresCriteriaRepository.deleteByIdCriteria(dni);
        } else {
            directoresRepository.deleteById(dni);
        }
    }
}
