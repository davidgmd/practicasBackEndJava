# Práctica 1 - Hola Mundo con Spring Boot

## Indice
1. [Explicación de la práctica](#explicación-de-la-práctica)
2. [Preparación inicial](#preparación-inicial)
- Instalación del IDE
- Instalación de los plugins
3. [Desarrollo de la práctica](#desarrollo-de-la-práctica)
- Creación del proyecto
- Pasos para realizar la práctica
4. [Resultado final](#resultado-final)
5. [Anexo](#anexo)

---

## 1. Explicación de la práctica
Esta práctica consiste en un proyecto en **Spring Boot** con un servicio REST que devuelva la cadena `"Hello World"`.

Para poder realizar la práctica se necesitará haber realizado los cursos de **Spring Core**, **Spring MVC** y **Spring Boot**.

> ℹ️ **Nota**: Esta práctica puede realizarse con diferentes IDEs como Eclipse, NetBeans o VS Code, pero aquí se explicará usando **IntelliJ IDEA**, ya que el proceso es muy similar en todos.

---

## 2. Preparación inicial

### Instalación del IDE
Lo primero es tener instalado **IntelliJ IDEA** en nuestro equipo.  
[Descargamos IntelliJ IDEA desde su página oficial.](bloqueado)

### Instalación de los plugins
Una vez instalado IntelliJ IDEA, debemos instalar los siguientes plugins necesarios para esta práctica y otras:

- **JavaDoc** → Para generar y visualizar documentación Java.
- **Lombok** → Para reducir código boilerplate en las clases Java.
- **SonarQube for IDE** → Para análisis de calidad de código.
- **InspectionLens** *(recomendado)* → Para identificar errores y problemas de forma más sencilla.

Para instalar un plugin en IntelliJ IDEA:
1. Ir a `File → Settings → Plugins`.
2. Buscar el plugin por nombre.
3. Instalar y reiniciar IntelliJ IDEA.

---

## 3. Desarrollo de la práctica

### Creación del proyecto
Una vez instalado IntelliJ IDEA y los plugins, podemos empezar con la creación del proyecto.

Existen dos formas de crear un proyecto Spring Boot:

1. **Desde la página web** → [Spring Initializr](bloqueado) podemos crear un proyecto indicando nombre, paquetería, grupo, artifact, dependencias, etc.  
   En esta práctica solo será necesario añadir la dependencia **Spring Web** para que, al ejecutar el proyecto, Spring Boot levante un servidor Tomcat.

`{{img_p2_0}}`

Una vez configurado, se genera un ZIP, se extrae y se importa en IntelliJ IDEA (`File → Open`).

2. **Desde IntelliJ IDEA** → Ir a `File → New → Project → Spring Initializr`. Aparecerá una ventana similar a la opción anterior.

### Pasos para realizar la práctica
- Crear un sistema de paquetes siguiendo la estructura MVC (ver anexo).
- En esta práctica solo necesitamos el paquete de controladores.
- Crear una clase **Controller** con un método que, al llamarlo desde una URL, devuelva `"Hola Mundo"`.
- Buscar información sobre cómo implementar un **RestController**.

---

## 4. Resultado final
Al poner en marcha la práctica y acceder en el navegador a la URL asociada al controlador REST, se mostrará la frase `"Hello World"`.

`{{img_p3_0}}`

---

## 5. Anexo

En todos los proyectos MVC tendremos los siguientes paquetes:

- **Controller** → Contiene los controladores que llaman a los servicios y gestionan las URLs.
- **Services** → Contiene la lógica de negocio y se comunica con repositorios u otros servicios.
- **Repositories** → Gestiona las peticiones a la base de datos.
- **Entities** → Representa las tablas de la base de datos como objetos.
- **Utils** → Contiene constantes y métodos generales.
- **Exception** → Excepciones propias de la aplicación, extendiendo de `ResponseStatusException`.
- **Beans** → Define objetos de request y response, usados por controladores y servicios.

---
