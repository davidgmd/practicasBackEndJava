package com.example.practica4.demo.controller;

import com.example.practica4.demo.entity.Peliculas;
import com.example.practica4.demo.services.PeliculasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PeliculasController {

    private final PeliculasService peliculasService;

    public PeliculasController(PeliculasService peliculasService) {
        this.peliculasService = peliculasService;
    }

    @GetMapping("/peliculas")
    public ResponseEntity<List<Peliculas>> listarPeliculas(@RequestParam(defaultValue = "jpa") String repo) {
        return ResponseEntity.ok(peliculasService.listarPeliculas(repo));
    }

    @GetMapping("/peliculas/{idPelicula}")
    public ResponseEntity<Peliculas> listarPelicula(@PathVariable String idPelicula,
                                                     @RequestParam(defaultValue = "jpa") String repo) {
        Peliculas peliculasAMostrar = peliculasService.listarPelicula(idPelicula, repo);
        return ResponseEntity.status(HttpStatus.OK).body(peliculasAMostrar);
    }

    @PostMapping("/peliculas")
    public ResponseEntity<Peliculas> anadirPelicula(@RequestBody Peliculas peliculas,
                                                     @RequestParam(defaultValue = "jpa") String repo) {
        peliculasService.anadirPelicula(peliculas, repo);
        return new ResponseEntity<>(peliculas, HttpStatus.CREATED);
    }

    @PutMapping("/peliculas/{idPelicula}")
    public ResponseEntity<Peliculas> modificarPelicula(@PathVariable String idPelicula,
                                                        @RequestBody Peliculas peliculasModificado,
                                                        @RequestParam(defaultValue = "jpa") String repo) {
        Peliculas peliculasOriginal = peliculasService.modificarPelicula(idPelicula, peliculasModificado, repo);
        return ResponseEntity.ok(peliculasOriginal);
    }

    @DeleteMapping("/peliculas/{idPelicula}")
    public ResponseEntity<Void> borrarPelicula(@PathVariable String idPelicula,
                                                @RequestParam(defaultValue = "jpa") String repo) {
        peliculasService.eliminarPelicula(idPelicula, repo);
        return ResponseEntity.noContent().build();
    }
}
