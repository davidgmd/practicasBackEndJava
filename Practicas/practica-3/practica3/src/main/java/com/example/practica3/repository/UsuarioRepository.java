package com.example.practica3.repository;

import com.example.practica3.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query(value = "SELECT * FROM M_USER", nativeQuery = true)
    List<Usuario> listarUsuarios();

    @Query(value = "SELECT * FROM M_USER WHERE M_DNI = :dni", nativeQuery = true)
    Optional<Usuario> listarUsuariosDni(@Param("dni") String dni);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO M_USER (M_DNI, M_NAME, M_SURNAME, M_AGE) VALUES (:dni, :name, :surName, :edad)", nativeQuery = true)
//    void AnadirUsuario(@Param("dni") String dni, @Param("name") String name, @Param("surName") String surName, @Param("edad") Integer edad);

    @Query(value = "SELECT COUNT(*) > 0 FROM M_USER WHERE M_DNI = :dni", nativeQuery = true)
    boolean existeUsuarioPorDni(@Param("dni") String dni);

    @Query(value = "SELECT * FROM M_USER WHERE M_DNI = :dni", nativeQuery = true)
    Optional<Usuario> buscarUsuarioParaModificar(@Param("dni") String dni);

    @Query(value = "SELECT COUNT(*) > 0 FROM M_USER WHERE M_DNI = :dni", nativeQuery = true)
    boolean existeParaEliminar(@Param("dni") String dni);
}
