package com.example.practica4.demo.services;

import com.example.practica4.demo.entity.Productoras;
import com.example.practica4.demo.exception.DuplicadoException;
import com.example.practica4.demo.repository.ProductorasCriteriaRepository;
import com.example.practica4.demo.repository.ProductorasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductorasService {
    //Service for productoras
    private final ProductorasRepository productorasRepository;
    private final ProductorasCriteriaRepository productorasCriteriaRepository;

        public ProductorasService(ProductorasRepository productorasRepository, ProductorasCriteriaRepository productorasCriteriaRepository) {
            this.productorasRepository = productorasRepository;
            this.productorasCriteriaRepository = productorasCriteriaRepository;
        }

        public List<Productoras> listarProductoras(String repo) {
            if ("criteria".equalsIgnoreCase(repo)) {
                return productorasCriteriaRepository.findAllCriteria();
            }
            return productorasRepository.findAll();
        }

        public Productoras listarProductora(String idProductora, String repo) {

            if (idProductora == null || idProductora.isBlank()) {
                throw new IllegalArgumentException("idProductora inválido"); // luego lo mejoramos
            }

            if ("criteria".equalsIgnoreCase(repo)) {
                return productorasCriteriaRepository.findByIdCriteria(idProductora)
                        .orElseThrow(() -> new RuntimeException("Productora no encontrada"));
            }

            return productorasRepository.findById(idProductora)
                    .orElseThrow(() -> new RuntimeException("no encontrado"));
        }

        public Productoras anadirProductora(Productoras productoras, String repo) {

            if (productoras == null || productoras.getIdProductora() == null || productoras.getIdProductora().isBlank()) {
                throw new IllegalArgumentException("id incorrecto o valores nulos");
            }

            boolean existe = "criteria".equalsIgnoreCase(repo)
                    ? productorasCriteriaRepository.findByIdCriteria(productoras.getIdProductora()).isPresent()
                    : productorasRepository.findById(productoras.getIdProductora()).isPresent();

            if (existe) {
                throw new DuplicadoException("No se puede añadir dos veces el mismo");
            }

            if ("criteria".equalsIgnoreCase(repo)) {
                return productorasCriteriaRepository.saveCriteria(productoras);
            }

            return productorasRepository.save(productoras);
        }

        public Productoras modificarProductora(String idProductora, Productoras nuevoValor, String repo) {

            Productoras element = "criteria".equalsIgnoreCase(repo)
                    ? productorasCriteriaRepository.findByIdCriteria(idProductora)
                    .orElseThrow(() -> new RuntimeException("No existe"))
                    : productorasRepository.findById(idProductora)
                    .orElseThrow(() -> new RuntimeException("No existe"));


            element.setNombre(nuevoValor.getNombre());
            element.setAnoFundacion(nuevoValor.getAnoFundacion());

            if ("criteria".equalsIgnoreCase(repo)) {
                return productorasCriteriaRepository.saveCriteria(element);
            }

            return productorasRepository.save(element);
        }

        public void eliminarProductora(String idProductora, String repo) {

            boolean existe = "criteria".equalsIgnoreCase(repo)
                    ? productorasCriteriaRepository.findByIdCriteria(idProductora).isPresent()
                    : productorasRepository.findById(idProductora).isPresent();

            if (!existe) {
                throw new RuntimeException("No existe");
            }

            if ("criteria".equalsIgnoreCase(repo)) {
                productorasCriteriaRepository.deleteByIdCriteria(idProductora);
            } else {
                productorasRepository.deleteById(idProductora);
            }
        }
}
