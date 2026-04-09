package com.example.practica4.demo.services;

import com.example.practica4.demo.entity.Series;
import com.example.practica4.demo.exception.DuplicadoException;
import com.example.practica4.demo.repository.SeriesCriteriaRepository;
import com.example.practica4.demo.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesCriteriaRepository seriesCriteriaRepository;

    public SeriesService(SeriesRepository seriesRepository, SeriesCriteriaRepository seriesCriteriaRepository) {
        this.seriesRepository = seriesRepository;
        this.seriesCriteriaRepository = seriesCriteriaRepository;
    }

    public List<Series> listarSeries(String repo) {
        if ("criteria".equalsIgnoreCase(repo)) {
            return seriesCriteriaRepository.findAllCriteria();
        }
        return seriesRepository.findAll();
    }

    public Series listarSerie(String idSerie, String repo) {
        if (idSerie == null || idSerie.isBlank()) {
            throw new IllegalArgumentException("idSerie inválido");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            return seriesCriteriaRepository.findByIdCriteria(idSerie)
                    .orElseThrow(() -> new RuntimeException("Serie no encontrada"));
        }

        return seriesRepository.findById(idSerie)
                .orElseThrow(() -> new RuntimeException("Serie no encontrada"));
    }

    public Series anadirSerie(Series serie, String repo) {
        if (serie == null || serie.getIdSerie() == null || serie.getIdSerie().isBlank()) {
            throw new IllegalArgumentException("id incorrecto o valores nulos");
        }

        boolean existe = "criteria".equalsIgnoreCase(repo)
                ? seriesCriteriaRepository.findByIdCriteria(serie.getIdSerie()).isPresent()
                : seriesRepository.findById(serie.getIdSerie()).isPresent();

        if (existe) {
            throw new DuplicadoException("No se puede añadir dos veces la misma serie");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            return seriesCriteriaRepository.saveCriteria(serie);
        }

        return seriesRepository.save(serie);
    }

    public Series modificarSerie(String idSerie, Series nuevoValor, String repo) {
        Series element = "criteria".equalsIgnoreCase(repo)
                ? seriesCriteriaRepository.findByIdCriteria(idSerie)
                .orElseThrow(() -> new RuntimeException("No existe"))
                : seriesRepository.findById(idSerie)
                .orElseThrow(() -> new RuntimeException("No existe"));

        element.setActores(nuevoValor.getActores());
        element.setAno(nuevoValor.getAno());
        element.setDirector(nuevoValor.getDirector());
        element.setProductoras(nuevoValor.getProductoras());
        element.setTitulo(nuevoValor.getTitulo());

        if ("criteria".equalsIgnoreCase(repo)) {
            return seriesCriteriaRepository.saveCriteria(element);
        }

        return seriesRepository.save(element);
    }

    public void eliminarSerie(String idSerie, String repo) {
        boolean existe = "criteria".equalsIgnoreCase(repo)
                ? seriesCriteriaRepository.findByIdCriteria(idSerie).isPresent()
                : seriesRepository.findById(idSerie).isPresent();

        if (!existe) {
            throw new RuntimeException("No existe");
        }

        if ("criteria".equalsIgnoreCase(repo)) {
            seriesCriteriaRepository.deleteByIdCriteria(idSerie);
        } else {
            seriesRepository.deleteById(idSerie);
        }
    }
}
