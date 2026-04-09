package com.example.practica4.demo.controller;

import com.example.practica4.demo.entity.Series;
import com.example.practica4.demo.services.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/series")
    public ResponseEntity<List<Series>> listarSeries(@RequestParam(defaultValue = "jpa") String repo) {
        return ResponseEntity.ok(seriesService.listarSeries(repo));
    }

    @GetMapping("/series/{idSerie}")
    public ResponseEntity<Series> listarSerie(@PathVariable String idSerie,
                                              @RequestParam(defaultValue = "jpa") String repo) {
        Series seriesAMostrar = seriesService.listarSerie(idSerie, repo);
        return ResponseEntity.status(HttpStatus.OK).body(seriesAMostrar);
    }

    @PostMapping("/series")
    public ResponseEntity<Series> anadirSerie(@RequestBody Series series,
                                              @RequestParam(defaultValue = "jpa") String repo) {
        seriesService.anadirSerie(series, repo);
        return new ResponseEntity<>(series, HttpStatus.CREATED);
    }

    @PutMapping("/series/{idSerie}")
    public ResponseEntity<Series> modificarSerie(@PathVariable String idSerie,
                                                 @RequestBody Series seriesModificado,
                                                 @RequestParam(defaultValue = "jpa") String repo) {
        Series seriesOriginal = seriesService.modificarSerie(idSerie, seriesModificado, repo);
        return ResponseEntity.ok(seriesOriginal);
    }

    @DeleteMapping("/series/{idSerie}")
    public ResponseEntity<Void> borrarSerie(@PathVariable String idSerie,
                                            @RequestParam(defaultValue = "jpa") String repo) {
        seriesService.eliminarSerie(idSerie, repo);
        return ResponseEntity.noContent().build();
    }
}
