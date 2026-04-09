package com.example.practica4.demo.controller;

import com.example.practica4.demo.entity.Productoras;
import com.example.practica4.demo.services.ProductorasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductorasController {
    //controller para la productoras
    private final ProductorasService productorasService;

        public ProductorasController(ProductorasService productorasService) {
            this.productorasService = productorasService;
        }

        @GetMapping("/productoras")
        public ResponseEntity<List<Productoras>> listarProductoras(@RequestParam(defaultValue = "jpa") String repo){
            return ResponseEntity.ok(productorasService.listarProductoras(repo));
        }

        //Si fuera /listar?dni=123 seria con @requestParam pero como es listar/123 es con PathVariable
        @GetMapping("/productoras/{idProductora}")
        public ResponseEntity<Productoras> listarProductora(@PathVariable String idProductora, @RequestParam(defaultValue = "jpa") String repo){
            Productoras productorasAMostrar = productorasService.listarProductora(idProductora, repo);
            return ResponseEntity.status(HttpStatus.OK).body(productorasAMostrar);
        }

        @PostMapping("/productoras")
        public ResponseEntity<Productoras> anadirProductora(@RequestBody Productoras productoras, @RequestParam(defaultValue = "jpa") String repo){
            productorasService.anadirProductora(productoras, repo);
            return new ResponseEntity<Productoras>(productoras, HttpStatus.CREATED);
        }

        @PutMapping("/productoras/{idProductora}")
        public ResponseEntity<Productoras> modificarProductora(@PathVariable String idProductora, @RequestBody Productoras productorasModificado, @RequestParam(defaultValue = "jpa") String repo){
            Productoras productorasOriginal = productorasService.modificarProductora(idProductora, productorasModificado, repo);
            return ResponseEntity.ok(productorasOriginal);
        }

        @DeleteMapping("/productoras/{idProductora}")
        public ResponseEntity<Void> borrarProductora(@PathVariable String idProductora, @RequestParam(defaultValue = "jpa") String repo){
            productorasService.eliminarProductora(idProductora, repo);
            return ResponseEntity.noContent().build();
        }
}
