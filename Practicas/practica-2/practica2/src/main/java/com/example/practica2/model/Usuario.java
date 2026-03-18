package com.example.practica2.model;

public class Usuario {
    private String dni;
    private String name;
    private String surName;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.getName() + " Apellido:" + this.getSurName() + " Dni:" + this.getDni();
    }
}
