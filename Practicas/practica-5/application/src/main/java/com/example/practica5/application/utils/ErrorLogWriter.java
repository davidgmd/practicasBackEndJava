package com.example.practica5.application.utils;

import com.example.practica5.application.utils.ErrorLogWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorLogWriter {
    private static final String LOG_FILE = "fichero.log";

    public void writeError(String mensaje) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(mensaje);

        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo en el fichero de log", e);
        }
    }
}
