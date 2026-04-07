package com.example.practica3.services;

import com.example.practica3.entity.Usuario;
import com.example.practica3.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserService userService;

    private final String fakeDni = "123456789";

    private Usuario crearUsuario(String dni){
        return new Usuario(dni, "Pepe", "Garcia", 25);
    }

    private Usuario crearUsuario(){
        return new Usuario("12345678A", "Pepe", "Garcia", 25);
    }

    @Test
    @DisplayName("Devuelve la lista de usuarios")
    void listarUsuariosTest() {
        List<Usuario> usuariosMock = List.of(
                new Usuario("111A", "Ana", "Lopez", 20),
                new Usuario("222B", "Luis", "Perez", 30)
        );

        when(usuarioRepository.listarUsuarios()).thenReturn(usuariosMock);

        List<Usuario> resultado = userService.listarUsuarios();

        assertEquals(2, resultado.size());
        verify(usuarioRepository).listarUsuarios();
    }

    @Test
    @DisplayName("Devuelve un usuario filtrado por dni")
    void listarUsuarioTest() {
        Usuario mockUser = this.crearUsuario();

        when(usuarioRepository.listarUsuariosDni(fakeDni)).thenReturn(Optional.of(mockUser));

        Usuario resultado = userService.listarUsuario(fakeDni);

        assertEquals("Pepe", resultado.getName());
        assertEquals("Garcia", resultado.getSurName());
        assertEquals(25, resultado.getEdad());
        verify(usuarioRepository).listarUsuariosDni(fakeDni);
    }

    @Test
    @DisplayName("Si el usuario no existe lo añade")
    void anadirUsuarioTest() {
        Usuario mockUser = this.crearUsuario(fakeDni);

        when(usuarioRepository.existeUsuarioPorDni(fakeDni)).thenReturn(false);
        when(usuarioRepository.save(mockUser)).thenReturn(mockUser);

        Usuario resultado = userService.anadirUsuario(mockUser);

        assertEquals("Pepe", resultado.getName());
        assertEquals("Garcia", resultado.getSurName());
        assertEquals(25, resultado.getEdad());
        verify(usuarioRepository).existeUsuarioPorDni(fakeDni);
        verify(usuarioRepository).save(mockUser);
    }

    @Test
    @DisplayName("Comprueba si el usuario existe para modificarlo")
    void modificarUsuarioTest() {
        Usuario mockUser = this.crearUsuario(fakeDni);

        when(usuarioRepository.buscarUsuarioParaModificar(fakeDni)).thenReturn(Optional.of(mockUser));
        when(usuarioRepository.save(mockUser)).thenReturn(mockUser);

        Usuario resultado = userService.modificarUsuario(fakeDni, mockUser);

        assertEquals("Pepe", resultado.getName());
        assertEquals("Garcia", resultado.getSurName());
        assertEquals(25, resultado.getEdad());
        verify(usuarioRepository).buscarUsuarioParaModificar(fakeDni);
        verify(usuarioRepository).save(resultado);
    }

    @Test
    @DisplayName("Si el usuario existe lo elimina")
    void eliminarUsuarioTest() {
        when(usuarioRepository.existeParaEliminar(fakeDni)).thenReturn(true);

        userService.eliminarUsuario(fakeDni);

        verify(usuarioRepository).existeParaEliminar(fakeDni);
        verify(usuarioRepository).deleteById(fakeDni);
    }
}
