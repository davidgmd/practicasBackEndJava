package com.example.practica2.repository;

import com.example.practica2.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ListController.class)
public class ListControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ListController controller;

    @BeforeEach
    void setUp() {
        controller.getListaInterna().clear();
    }

    @Test
    @DisplayName("Comprobar que se devuelven todos los usuarios")
    public void listarUsuarios() throws Exception {
        mvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Nested
    @DisplayName("GET /usuarios/{dni}")
    class GetUsuarioPorDniTest {
        @Test
        @DisplayName("Comprueba que se inserto el usuario con dni 123456789 y obtiene ok")
        public void listarUsuariosDni() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario u = new Usuario("123456789", "Pepe", "García");
            String postObject = mapper.writeValueAsString(u);

            // Primero creas el usuario
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isCreated());

            MvcResult result = mvc.perform(get("/usuarios/{dni}", "123456789"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andReturn();

            // Convertir respuesta
            String responseJson = result.getResponse().getContentAsString();

            Usuario usuarioRespuesta = mapper.readValue(responseJson, Usuario.class);
            //Assertions
            assertNotNull(usuarioRespuesta);
            assertEquals("123456789", usuarioRespuesta.getDni());
            assertEquals("Pepe", usuarioRespuesta.getName());
            assertEquals("García", usuarioRespuesta.getSurName());
        }

        @Test
        @DisplayName("Intenta encontrar el usuario pero no existe, respondiendo not found")
        public void listarUsuariosDniNotFound() throws Exception{
            mvc.perform(get("/usuarios/{dni}", "123456789"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Intenta encontrar el usuario pero manda incorrectamente los datos")
        public void listarUsuariosDniBadRequest() throws Exception{
            mvc.perform(get("/usuarios/{dni}", " "))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName(("POST /usuarios/"))
    class PostUsuarios{
        @Test
        @DisplayName("Se crea y se añade correctamente el usuario")
        public void crearUsuario() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario u = new Usuario("123456789", "Pepe", "García");
            String postObject = mapper.writeValueAsString(u);

            // Creamos el usuario con el usuario u anterior
            MvcResult result = mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseJson = result.getResponse().getContentAsString();
            Usuario responseUser = mapper.readValue(responseJson, Usuario.class);

            //primero esperado, luego actual
            assertEquals(u.getDni(), responseUser.getDni());
            assertEquals(u.getName(), responseUser.getName());
            assertEquals(u.getSurName(), responseUser.getSurName());
        }

        @Test
        @DisplayName("Se crea y se añade correctamente el usuario")
        public void crearUsuarioBadRequest() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario u = null;
            String postObject = mapper.writeValueAsString(u);

            // Llamamos pero esta vez recibiremos un isBadRequest porque u es null
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Se intenta crear pero el usuario ya existe")
        public void crearUsuarioConflict() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario u = new Usuario("123456789", "Pepe", "García");
            String postObject = mapper.writeValueAsString(u);

            //Creamos una primera vez
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isCreated());

            //Intentamos crear otra vez pero como ya esta creado, falla
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName(("PUT /usuarios"))
    class PutUsuarios{
        @Test
        @DisplayName("Modifica el usuario, basandose en el dni proporcionado")
        public void modificarUsuarioDni() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario original = new Usuario("123456789", "Pepe", "Garcia");
            Usuario modificado = new Usuario("123456789", "Luis", "Mariña");
            String postObject = mapper.writeValueAsString(original);

            // Primero creas el usuario
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isCreated());

            postObject = mapper.writeValueAsString(modificado);

            //Modificamos el usuario
            MvcResult result = mvc.perform(put("/usuarios/{dni}", "123456789")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isOk())
                    .andReturn();

            // Convertir respuesta
            String responseJson = result.getResponse().getContentAsString();

            Usuario usuarioRespuesta = mapper.readValue(responseJson, Usuario.class);

            //Assertions
            assertNotNull(usuarioRespuesta);
            assertEquals("123456789", usuarioRespuesta.getDni());
            assertEquals("Luis", usuarioRespuesta.getName());
            assertEquals("Mariña", usuarioRespuesta.getSurName());
        }

        @Test
        @DisplayName("Dni erroneo, obtiene badRequest")
        public void modificarUsuarioDniBadRequest() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario original = new Usuario("123456789", "Pepe", "Garcia");
            Usuario modificado = new Usuario("123456789", "Luis", "Mariña");
            String postObject = mapper.writeValueAsString(original);

            // Primero creas el usuario
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isCreated());

            postObject = mapper.writeValueAsString(modificado);

            //Modificamos el usuario
            mvc.perform(put("/usuarios/{dni}", " ")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isBadRequest());

        }

        @Test
        @DisplayName("El usuario no es encontrado")
        public void modificarUsuarioDniNotFound() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario original = new Usuario("123456789", "Pepe", "Garcia");
            String postObject = mapper.writeValueAsString(original);

            //Como el usuario no fue creado falla con un not found
            mvc.perform(put("/usuarios/{dni}", "123456789")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isNotFound());

        }
    }

    @Nested
    @DisplayName(("DELETE /usuarios"))
    class DeleteUsuarios{
        @Test
        @DisplayName("Eliminamos al usuario, basandonos en su dni")
        public void eliminarUsuarioDni() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            Usuario original = new Usuario("123456789", "Pepe", "Garcia");
            String postObject = mapper.writeValueAsString(original);

            // Primero creas el usuario
            mvc.perform(post("/usuarios")
                            .contentType("application/json")
                            .content(postObject))
                    .andExpect(status().isCreated());

            //Eliminamos el usuario esperando nos de un ok
            mvc.perform(delete("/usuarios/{dni}", "123456789")
                            .contentType("application/json"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Intentamos eliminar al usuario pero, el dni no es valido")
        public void eliminarUsuarioDniBadRequest() throws Exception{
            //Eliminamos el usuario esperando nos de un badRequest
            mvc.perform(delete("/usuarios/{dni}", " ")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Intentamos eliminar al usuario pero, el dni no es valido")
        public void eliminarUsuarioDniNotFound() throws Exception{
            //Eliminamos el usuario esperando nos de un badRequest
            mvc.perform(delete("/usuarios/{dni}", "123456789")
                            .contentType("application/json"))
                    .andExpect(status().isNotFound());
        }
    }
}
