INSERT INTO actores (id_actor, nombre, apellidos, edad, nacionalidad) VALUES ('111A', 'Leonardo', 'DiCaprio', 49, 'USA');
INSERT INTO actores (id_actor, nombre, apellidos, edad, nacionalidad) VALUES ('222B', 'Brad', 'Pitt', 60, 'USA');
INSERT INTO actores (id_actor, nombre, apellidos, edad, nacionalidad) VALUES ('333C', 'Scarlett', 'Johansson', 39, 'USA');
INSERT INTO actores (id_actor, nombre, apellidos, edad, nacionalidad) VALUES ('444D', 'Penelope', 'Cruz', 50, 'España');
INSERT INTO actores (id_actor, nombre, apellidos, edad, nacionalidad) VALUES ('555E', 'Antonio', 'Banderas', 64, 'España');

INSERT INTO directores (id_director, nombre, apellidos, edad, nacionalidad) VALUES ('D111', 'Christopher', 'Nolan', 54, 'UK');
INSERT INTO directores (id_director, nombre, apellidos, edad, nacionalidad) VALUES ('D222', 'Quentin', 'Tarantino', 61, 'USA');
INSERT INTO directores (id_director, nombre, apellidos, edad, nacionalidad) VALUES ('D333', 'Pedro', 'Almodovar', 75, 'España');

INSERT INTO productoras (id_productora, nombre, ano_fundacion) VALUES (1, 'Warner Bros', 1923);
INSERT INTO productoras (id_productora, nombre, ano_fundacion) VALUES (2, 'Universal Pictures', 1912);
INSERT INTO productoras (id_productora, nombre, ano_fundacion) VALUES (3, 'El Deseo', 1985);

INSERT INTO peliculas (id_pelicula, titulo, ano, id_director, id_productora)
VALUES (1, 'Inception', 2010, 'D111', 1);

INSERT INTO peliculas (id_pelicula, titulo, ano, id_director, id_productora)
VALUES (2, 'Pulp Fiction', 1994, 'D222', 2);

INSERT INTO peliculas (id_pelicula, titulo, ano, id_director, id_productora)
VALUES (3, 'Dolor y Gloria', 2019, 'D333', 3);

INSERT INTO series (id_serie, titulo, ano, id_director, id_productora)
VALUES (1, 'Westworld', 2016, 'D111', 1);

INSERT INTO series (id_serie, titulo, ano, id_director, id_productora)
VALUES (2, 'La Casa de Papel', 2017, 'D333', 3);

INSERT INTO peliculas_actores (id_pelicula, id_actor) VALUES (1, '111A');
INSERT INTO peliculas_actores (id_pelicula, id_actor) VALUES (1, '222B');

INSERT INTO peliculas_actores (id_pelicula, id_actor) VALUES (2, '222B');
INSERT INTO peliculas_actores (id_pelicula, id_actor) VALUES (2, '333C');

INSERT INTO peliculas_actores (id_pelicula, id_actor) VALUES (3, '444D');
INSERT INTO peliculas_actores (id_pelicula, id_actor) VALUES (3, '555E');

INSERT INTO series_actores (id_serie, id_actor) VALUES (1, '111A');
INSERT INTO series_actores (id_serie, id_actor) VALUES (1, '333C');

INSERT INTO series_actores (id_serie, id_actor) VALUES (2, '444D');
INSERT INTO series_actores (id_serie, id_actor) VALUES (2, '555E');