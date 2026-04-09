package com.example.practica4.demo.services;

import com.example.practica4.demo.entity.Peliculas;
import com.example.practica4.demo.exception.DuplicadoException;
import com.example.practica4.demo.repository.PeliculasCriteriaRepository;
import com.example.practica4.demo.repository.PeliculasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculasService {

    private final PeliculasRepository peliculasRepository;
    private final PeliculasCriteriaRepository peliculasCriteriaRepository;

    public PeliculasService(PeliculasRepository peliculasRepository,
                            PeliculasCriteriaRepository peliculasCriteriaRepository) {
        this.peliculasRepository = peliculasRepository;
        this.peliculasCriteriaRepository = peliculasCriteriaRepository;
    }

    public List<Peliculas> listarPeliculas(String repo) {
        if ("criteria".equalsIgnoreCase(repo)) {
            return peliculasCriteriaRepository.findAllCriteria();
        }
        return peliculasRepository.findAll();
    }

    public Peliculas listarPelicula(String idPelicula, String repo) {
        if (idPelicula == null || idPelicula.isBlank()) {
            throw new IllegalArgumentException("idPelicula inválido");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            return peliculasCriteriaRepository.findByIdCriteria(idPelicula)
                    .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));
        }

        return peliculasRepository.findById(idPelicula)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrada"));
    }

    public Peliculas anadirPelicula(Peliculas pelicula, String repo) {
        if (pelicula == null || pelicula.getIdPelicula() == null || pelicula.getIdPelicula().isBlank()) {
            throw new IllegalArgumentException("id incorrecto o valores nulos");
        }

        boolean existe = "criteria".equalsIgnoreCase(repo)
                ? peliculasCriteriaRepository.findByIdCriteria(pelicula.getIdPelicula()).isPresent()
                : peliculasRepository.findById(pelicula.getIdPelicula()).isPresent();

        if (existe) {
            throw new DuplicadoException("No se puede añadir dos veces la misma película");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            return peliculasCriteriaRepository.saveCriteria(pelicula);
        }

        return peliculasRepository.save(pelicula);
    }

    public Peliculas modificarPelicula(String idPelicula, Peliculas nuevoValor, String repo) {
        Peliculas element = "criteria".equalsIgnoreCase(repo)
                ? peliculasCriteriaRepository.findByIdCriteria(idPelicula)
                .orElseThrow(() -> new RuntimeException("No existe"))
                : peliculasRepository.findById(idPelicula)
                .orElseThrow(() -> new RuntimeException("No existe"));

        element.setActores(nuevoValor.getActores());
        element.setAno(nuevoValor.getAno());
        element.setDirector(nuevoValor.getDirector());
        element.setProductoras(nuevoValor.getProductoras());
        element.setTitulo(nuevoValor.getTitulo());

        if ("criteria".equalsIgnoreCase(repo)) {
            return peliculasCriteriaRepository.saveCriteria(element);
        }

        return peliculasRepository.save(element);
    }

    public void eliminarPelicula(String idPelicula, String repo) {
        boolean existe = "criteria".equalsIgnoreCase(repo)
                ? peliculasCriteriaRepository.findByIdCriteria(idPelicula).isPresent()
                : peliculasRepository.findById(idPelicula).isPresent();

        if (!existe) {
            throw new RuntimeException("No existe");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            peliculasCriteriaRepository.deleteByIdCriteria(idPelicula);
        } else {
            peliculasRepository.deleteById(idPelicula);
        }
    }
}
