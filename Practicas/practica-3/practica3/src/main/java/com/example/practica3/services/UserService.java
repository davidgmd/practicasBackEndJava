package com.example.practica3.services;

import com.example.practica3.entity.Usuario;
import com.example.practica3.exception.UsuarioDuplicadoException;
import com.example.practica3.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UsuarioRepository usuarioRepository;

    public UserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        //return usuarioRepository.findAll();
        return usuarioRepository.listarUsuarios();
    }

    public Usuario listarUsuario(String dni) {

        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("DNI inválido"); // luego lo mejoramos
        }

        //        return usuarioRepository.findById(dni)
        //                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioRepository.listarUsuariosDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario anadirUsuario(Usuario usuario) {

        if (usuario == null || usuario.getDni() == null || usuario.getDni().isBlank()) {
            throw new IllegalArgumentException("Usuario inválido");
        }

        if (usuarioRepository.existeUsuarioPorDni(usuario.getDni())) {
            throw new UsuarioDuplicadoException("No se puede añadir dos veces el mismo usuario");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario modificarUsuario(String dni, Usuario usuarioModificado) {

//        Usuario usuarioOriginal = usuarioRepository.findById(dni)
//                .orElseThrow(() -> new RuntimeException("No existe"));

        Usuario usuarioOriginal = usuarioRepository.buscarUsuarioParaModificar(dni)
                .orElseThrow(() -> new RuntimeException("No existe"));

        usuarioOriginal.setName(usuarioModificado.getName());
        usuarioOriginal.setSurName(usuarioModificado.getSurName());
        usuarioOriginal.setEdad(usuarioModificado.getEdad());

        return usuarioRepository.save(usuarioOriginal);
    }

    public void eliminarUsuario(String dni) {

        if (!usuarioRepository.existeParaEliminar(dni)) {
            throw new RuntimeException("No existe");
        }

        usuarioRepository.deleteById(dni);
    }
}
