
package com.example.practica5.application.batch.paralelo;

import jakarta.persistence.*;

@Entity
public class DocumentoFormateadoParalelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;

    @Lob
    private String contenido;

    public Long getId() { return id; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
