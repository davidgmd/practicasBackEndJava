package com.example.practica3.exception;

public class UsuarioDuplicadoException extends RuntimeException{
    public UsuarioDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
