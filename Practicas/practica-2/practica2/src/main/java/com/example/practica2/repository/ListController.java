package com.example.practica2.repository;

import com.example.practica2.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ListController {
    public List<Usuario> getListaInterna() {
        return listaInterna;
    }

    public void setListaInterna(List<Usuario> listaUsuarios) {
        this.listaInterna = listaUsuarios;
    }

    private List<Usuario> listaInterna = new ArrayList<Usuario>();

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(this.listaInterna);
    }

    //Si fuera /listar?dni=123 seria con @requestParam pero como es listar/123 es con PathVariable
    @GetMapping("/usuarios/{dni}")
    public ResponseEntity<List<Usuario>> listarUsuariosDni(@PathVariable String dni){
        if (dni == null || dni.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI inválido");
        }

        List<Usuario> usuarioAMostrar = this.listaInterna.stream()
                .filter(usuario -> usuario.getDni().equals(dni))
                .toList();

        if(usuarioAMostrar.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAMostrar);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> AnadirUsuario(@RequestBody Usuario usuario){
        if(usuario == null || usuario.getDni() == null || usuario.getDni().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario erroneo");
        }

        boolean existe = this.listaInterna.stream()
                .anyMatch(user -> user.getDni().equals(usuario.getDni()));

        if(existe){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede añadir dos veces el mismo usuario");
        }

        this.listaInterna.add(usuario);
        return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }

    @PutMapping("/usuarios/{dni}")
    public ResponseEntity<Usuario> ModificarUsuario(@PathVariable String dni, @RequestBody Usuario usuarioModificado){
        if (usuarioModificado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos del usuario son inválidos");
        }

        if (dni == null || dni.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI inválido");
        }

        Usuario usuarioAModificar = this.listaInterna.stream()
                .filter((user)->user.getDni().equals(dni))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un usuario con ese dni")
                );

        //Normalmente, no se modificaría, porque es clave del usuario
        //usuarioAModificar.setDni(usuarioModificado.getDni());
        usuarioAModificar.setName(usuarioModificado.getName());
        usuarioAModificar.setSurName(usuarioModificado.getSurName());

        return ResponseEntity.ok(usuarioAModificar);
    }

    @DeleteMapping("/usuarios/{dni}")
    public ResponseEntity<Void> BorrarUsuario(@PathVariable String dni){

        if(dni == null || dni.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dni erroneo");
        }

        Usuario usuarioAEliminar = this.listaInterna.stream()
                .filter((user)->user.getDni().equals(dni))
                .findFirst()
                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un usuario con ese DNI"));

        this.listaInterna.remove(usuarioAEliminar);

        return ResponseEntity.noContent().build();
    }
}
