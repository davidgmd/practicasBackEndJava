# Práctica 1.2 – Swagger

## 1. Explicación de la práctica
Para realizar esta práctica, partiremos del ejercicio anterior, con el proyecto inicial de “Hello World”.  
Vamos a añadir la configuración de Swagger, para poder hacer uso de esta interfaz.

## 2. Desarrollo de la práctica

### 1- Añadir las siguientes dependencias en el pom
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```
### 2- Crear el fichero de configuración
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.HolaMundo.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo(
                "Nombre del proyecto",
                "Descripción proyecto",
                "1.0",
                "termsOfServiceUrl",
                new Contact("nombre", "url", "mail"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
```

Con cada uno de los métodos creados en la práctica anterior.
En la carpeta de prácticas > entregas del equipo del Teams, subiréis el Word guardado como Apellidos_Nombre_Practica1.2 que contendrá las capturas que evidencien el correcto funcionamiento del Swagger.