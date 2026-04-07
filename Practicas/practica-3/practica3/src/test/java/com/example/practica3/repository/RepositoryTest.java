package com.example.practica3.repository;

import com.example.practica3.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario crearUsuario(String dni){
        return new Usuario(dni, "Pepe", "Garcia", 25);
    }

    private Usuario crearUsuario(){
        return new Usuario("12345678A", "Pepe", "Garcia", 25);
    }

    @Test
    @DisplayName("Lista todos los usuarios")
    void listaTodosLosUsuarios() {
        usuarioRepository.save(new Usuario("111A", "Ana", "Lopez", 20));
        usuarioRepository.save(new Usuario("222B", "Luis", "Perez", 30));
        usuarioRepository.flush();

        List<Usuario> usuarios = usuarioRepository.listarUsuarios();

        assertEquals(2, usuarios.size());
    }

    @Test
    @DisplayName("Lista usuario por DNI")
    void listarPorDni() {
        Usuario usuario = this.crearUsuario();

        usuarioRepository.save(usuario);
        usuarioRepository.flush();

        Optional<Usuario> resultado = usuarioRepository.listarUsuariosDni("12345678A");

        assertTrue(resultado.isPresent());
        assertEquals("Pepe", resultado.get().getName());
        assertEquals("Garcia", resultado.get().getSurName());
        assertEquals(25, resultado.get().getEdad());
    }

    @Test
    @DisplayName("Comprueba si existe un usuario buscando por su DNI")
    void existeUsuarioPorDni() {
        Usuario usuario = new Usuario("12345678A", "Pepe", "Garcia", 25);

        usuarioRepository.save(usuario);
        usuarioRepository.flush();

        boolean resultado = usuarioRepository.existeUsuarioPorDni("12345678A");

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Busca un usuario por dni para luego modificar sus datos")
    void buscarUsuarioParaModificar() {
        Usuario usuario = new Usuario("12345678A", "Pepe", "Garcia", 25);

        usuarioRepository.save(usuario);
        usuarioRepository.flush();

        Optional<Usuario> resultado = usuarioRepository.buscarUsuarioParaModificar("12345678A");

        assertTrue(resultado.isPresent());
        assertEquals("Pepe", resultado.get().getName());
        assertEquals("Garcia", resultado.get().getSurName());
        assertEquals(25, resultado.get().getEdad());
    }

    @Test
    @DisplayName("Busca un usuario por dni para eliminar")
    void existeParaEliminar() {
        Usuario usuario = new Usuario("12345678A", "Pepe", "Garcia", 25);

        usuarioRepository.save(usuario);
        usuarioRepository.flush();

        boolean resultado = usuarioRepository.existeParaEliminar("12345678A");

        assertTrue(resultado);
    }
}
