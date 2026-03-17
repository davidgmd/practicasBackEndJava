# Práctica 2 - Usuarios

## Índice

1. Explicación de la práctica
2. Preparación inicial
- Instalación de Postman
3. Explicación de la práctica
- Definición de la práctica
- Excepción / Validaciones
- Postman
4. Resultado final

---

## 1. Explicación de la práctica

Vamos a realizar una práctica consistente en un proyecto **Spring Boot con MVC** que consistirá en listar, añadir, modificar y borrar usuarios de una lista estática (respetaremos la paquetería vista en el anexo del ejercicio 1, aunque no accedemos en este ejercicio a base de datos).

Es obligatorio haber realizado la práctica anterior para comprender conceptos que aquí no se detallarán. Antes de comenzar, es necesaria una preparación inicial.

---

## 2. Preparación inicial

### Instalación de Postman
Postman es una aplicación que nos permite realizar pruebas a nuestros proyectos. Es un cliente HTTP que nos da la posibilidad de testear *HTTP requests* a través de una interfaz gráfica, obteniendo diferentes tipos de respuesta que posteriormente deberán ser validados.

Utilizaremos esta herramienta para probar que todos los métodos funcionan correctamente.

Una vez finalizado el desarrollo de un método, es necesario probar todos los casos posibles, tanto si la operación se realiza correctamente como si hay algún error.

Usaremos la versión de escritorio de Postman para mayor comodidad, aunque también se puede usar la versión web.

Ir al enlace https://www.postman.com/downloads/ y descargar la aplicación de escritorio para Windows

Tras instalar, se creará un acceso directo en el escritorio.  
Ejecutamos la aplicación y continuamos sin crear un usuario (se creará un entorno en local).

---

## 3. Explicación de la práctica

Al crear el proyecto, incluir las siguientes dependencias:

- Spring Web
- Lombok

### Definición de la práctica
1. Crear una lista estática en una clase del paquete `Repository`.
2. En el controlador, implementar los siguientes métodos:
- **ListarUsuarios**: retorna todos los usuarios.
- **ListarUsuarioPorDni**: retorna un usuario según el DNI.
- **AñadirUsuario**: añade un nuevo usuario.
- **ModificarUsuarioPorDni**: modifica un usuario según el DNI.
- **BorrarUsuarioPorDni**: borra un usuario según el DNI.

Todos los métodos deben devolver un `ResponseEntity` y/o el `HttpStatus` correspondiente.  
La clase `Usuario` debe tener:
- DNI
- Name
- Surname

"Usar herramienta Lombok para crear el objeto usuario"  
https://openwebinars.net/blog/como-instalar-y-usar-lombok/

---

### Excepción / Validaciones
Validar que los datos del usuario sean correctos (no nulos, no vacíos, DNI válido, etc.).  
En caso de error, devolver el `HttpStatus` adecuado y un mensaje de error.  
La excepción debe extender de `ResponseStatusException`.

---

### Postman
La entrega debe incluir una colección de Postman llamada **practica2SpringBoot** con todos los casos posibles para cada método, por ejemplo en **AñadirUsuario**:
- Creación correcta
- Usuario existente
- DNI válido
- ...

---

## 4. Resultado final
El resultado final se visualizará en Postman, mostrando datos y códigos de error.

### Ejemplos:
- Obtener todos los usuarios
- Obtener usuario por DNI
- Añadir usuario (Body con datos)
- Modificar usuario (Body + DNI en URL)
- Borrar usuario (DNI en URL)

### Casos de error:
- DNI no válido
- Nombre del usuario vacío
- Apellidos del usuario vacíos

