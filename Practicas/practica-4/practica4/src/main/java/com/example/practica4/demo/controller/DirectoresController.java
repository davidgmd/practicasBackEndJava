package com.example.practica4.demo.controller;

import com.example.practica4.demo.entity.Directores;
import com.example.practica4.demo.services.DirectoresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DirectoresController {
    private final DirectoresService directoresService;

    public DirectoresController(DirectoresService directoresService) {
        this.directoresService = directoresService;
    }

    @GetMapping("/directores")
    public ResponseEntity<List<Directores>> listarDirectores(@RequestParam(defaultValue = "jpa") String repo) {
        return ResponseEntity.ok(directoresService.listarDirectores(repo));
    }

    @GetMapping("/directores/{dni}")
    public ResponseEntity<Directores> listarDirector(@PathVariable String dni,
                                                     @RequestParam(defaultValue = "jpa") String repo) {
        Directores directoresAMostrar = directoresService.listarDirector(dni, repo);
        return ResponseEntity.status(HttpStatus.OK).body(directoresAMostrar);
    }

    @PostMapping("/directores")
    public ResponseEntity<Directores> anadirDirector(@RequestBody Directores directores,
                                                     @RequestParam(defaultValue = "jpa") String repo) {
        directoresService.anadirDirector(directores, repo);
        return new ResponseEntity<>(directores, HttpStatus.CREATED);
    }

    @PutMapping("/directores/{dni}")
    public ResponseEntity<Directores> modificarDirector(@PathVariable String dni,
                                                        @RequestBody Directores directoresModificado,
                                                        @RequestParam(defaultValue = "jpa") String repo) {
        Directores directoresOriginal = directoresService.modificarDirector(dni, directoresModificado, repo);
        return ResponseEntity.ok(directoresOriginal);
    }

    @DeleteMapping("/directores/{dni}")
    public ResponseEntity<Void> borrarDirector(@PathVariable String dni,
                                               @RequestParam(defaultValue = "jpa") String repo) {
        directoresService.eliminarDirector(dni, repo);
        return ResponseEntity.noContent().build();
    }
}
