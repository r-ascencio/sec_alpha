-- db:mysql -D sec_alpha -u _r 
-- raul_ascencio@lavabit.com
-- DROP DATABASE sec_alpha;
CREATE DATABASE IF NOT EXISTS sec;

USE sec;

/**
 * Especialidad
 */
DROP TABLE IF EXISTS Especialidad;
CREATE TABLE IF NOT EXISTS Especialidad (
  codigo TINYINT AUTO_INCREMENT UNIQUE NOT NULL,
  nombre VARCHAR(100) UNIQUE NOT NULL,
  total_alumnos TINYINT NOT NULL,
  voto_alumnos TINYINT NOT NULL DEFAULT 0,
  fecha_registro DATE,
  numero_candidatos INTEGER NOT NULL DEFAULT 3,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



ALTER TABLE Especialidad
ADD CONSTRAINT especialidad_codigo_pk
PRIMARY KEY (codigo);

use sec_alpha;
DROP TRIGGER IF EXISTS tresCandidatos;
DELIMITER $$
CREATE TRIGGER tresCandidatos BEFORE INSERT ON Candidato FOR EACH ROW BEGIN
DECLARE done 					INT DEFAULT 0;
DECLARE maxCandidatos 			TINYINT;
DECLARE Nespecialidad 			TINYINT;
DECLARE nCandidatos 			TINYINT;
DECLARE CurrentCandidato 		VARCHAR(8);
DECLARE currentCandidatoEsp 	TINYINT;
DECLARE nCandidatosEsp			TINYINT;
DECLARE countCandidato			TINYINT;
DECLARE cursorCandidato 		CURSOR FOR SELECT alumno FROM Candidato;
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

SET maxCandidatos = 3;
SET nCandidatos = 0;
SET countCandidato = (SELECT COUNT(*) FROM Candidato);
SET NEW.especialidad = ( SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno ) ;
IF  countCandidato > 0 THEN

  OPEN cursorCandidato;
  REPEAT	
  FETCH cursorCandidato INTO CurrentCandidato;
  SET currentCandidatoEsp = (SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno);
  SET Nespecialidad = (SELECT especialidad FROM Alumno WHERE codigo = CurrentCandidato);
  IF (Nespecialidad = currentCandidatoEsp) THEN
	SET nCandidatos = nCandidatos + 1;	
  END IF; 
  SELECT alumno INTO CurrentCandidato FROM Candidato LIMIT 1;
  UNTIL done END REPEAT;
  CLOSE cursorCandidato;

  IF (nCandidatos > 3 OR NEW.especialidad <= 0) THEN
	SET NEW.alumno = null;
  END IF;
  END IF;
END
$$

/**
 * /Especialidad
 */



CREATE TABLE IF NOT EXISTS Alumno (
  codigo VARCHAR(8) UNIQUE NOT NULL,
  NIE varchar(11) UNIQUE NOT NULL,
  nombre varchar(150) NOT NULL,
  especialidad tinyint NOT NULL,
  fecha_registro date,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- one more column

ALTER TABLE Alumno
ADD voto BIT NOT NULL DEFAULT 0;

-- PK
ALTER TABLE Alumno
ADD CONSTRAINT alumno_codigo_pk
PRIMARY KEY (codigo, NIE);

-- FK

ALTER TABLE Alumno
ADD CONSTRAINT alumno_especialidad_fk
FOREIGN KEY (especialidad) REFERENCES Especialidad(codigo) ON DELETE CASCADE ON UPDATE CASCADE;

/**
 * /Alumno
 */

CREATE TABLE IF NOT EXISTS Candidato (
  alumno VARCHAR(8) UNIQUE NOT NULL,
  imagen_src VARCHAR(250),
  especialidad tinyint NOT NULL,
  fecha_registro DATE,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  puntaje smallint DEFAULT 0 NOT NULL
);

-- SELECT *

-- PK

ALTER TABLE Candidato
ADD CONSTRAINT candidato_codigo_pk
PRIMARY KEY (alumno);
-- FK Candidato  
ALTER TABLE Candidato
ADD CONSTRAINT candidato_codigo_fk
FOREIGN KEY (alumno) REFERENCES Alumno (codigo) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Candidato
ADD CONSTRAINT candidato_especialidad_fk
FOREIGN KEY (especialidad) REFERENCES Especialidad (codigo) ON DELETE CASCADE ON UPDATE CASCADE;

/**
 * /Candidato
 */


CREATE TABLE IF NOT EXISTS Electo (
  alumno VARCHAR(8) UNIQUE UNIQUE NOT NULL,
  cargo VARCHAR(150),
  fecha_registro DATE,
  puntajeP INTEGER,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- PK
ALTER TABLE Electo
ADD CONSTRAINT electo_pk
PRIMARY KEY (alumno);

-- FK

ALTER TABLE Electo 
ADD CONSTRAINT electo_codigo_fk
FOREIGN KEY (alumno) REFERENCES Candidato (alumno) ON DELETE CASCADE ON UPDATE CASCADE;

/**
 * /Electo
 */


CREATE TABLE IF NOT EXISTS Pregunta (
  codigo SMALLINT AUTO_INCREMENT UNIQUE NOT NULL,
  descripcion varchar(255) NOT NULL,
  fecha_registro DATE,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- PK

ALTER TABLE Pregunta
ADD CONSTRAINT pregunta_pk
PRIMARY KEY (codigo);

-- FK 

-- DROP TABLE Usuario;
-- SELECT * FROM Usuario;

CREATE TABLE IF NOT EXISTS Usuario (
  codigo TINYINT AUTO_INCREMENT UNIQUE NOT NULL,
  nombre VARCHAR(100) UNIQUE NOT NULL,
  pass TEXT NOT NULL,
  descripcion VARCHAR(150)
);

ALTER TABLE Usuario
ADD CONSTRAINT user_codigo_pk
PRIMARY KEY (codigo);


-- index 

CREATE INDEX indexcodigo
on Alumno (codigo);

CREATE INDEX indexusuario
on Usuario (nombre);

CREATE INDEX index_candidato_codigo
on Candidato (alumno);

CREATE INDEX index_pregunta_codigo
on Pregunta (codigo);


CREATE TABLE Presidente (  
    alumno varchar(8) NOT NULL, 
    imagen_src VARCHAR(250) NOT NULL, 
    puntaje int NOT NULL DEFAULT 0, PRIMARY KEY (alumno)
);

TRUNCATE TABLE Candidato;
DELIMITER $$
CREATE TRIGGER tresCandidatos BEFORE INSERT ON Candidato FOR EACH ROW BEGIN
DECLARE done 					INT DEFAULT 0;
DECLARE maxCandidatos 			TINYINT;
DECLARE Nespecialidad 			TINYINT;
DECLARE nCandidatos 			TINYINT;
DECLARE CurrentCandidato 		VARCHAR(8);
DECLARE currentCandidatoEsp             TINYINT;
DECLARE nCandidatosEsp			TINYINT;
DECLARE countCandidato			TINYINT;
DECLARE nTmpVotantes                    TINYINT;
DECLARE cursorCandidato 		CURSOR FOR SELECT alumno FROM Candidato;
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

SET maxCandidatos = 3;
SET nCandidatos = 0;
SET countCandidato = (SELECT COUNT(*) FROM Candidato);
IF  countCandidato > 0 THEN

  OPEN cursorCandidato;
  REPEAT	
  FETCH cursorCandidato INTO CurrentCandidato;
  SET currentCandidatoEsp = (SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno);
  SET Nespecialidad = (SELECT especialidad FROM Alumno WHERE codigo = CurrentCandidato);
  IF (Nespecialidad = currentCandidatoEsp) THEN
	SET nCandidatos = nCandidatos + 1;	
  END IF; 
  SELECT alumno INTO CurrentCandidato FROM Candidato LIMIT 1;
    SET NEW.especialidad = ( SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno ) ;
    SET nTmpVotantes = ( SELECT numero_candidatos FROM Especialidadd WHERE especialidad = NEW.especialidad) ;
    UNTIL done END REPEAT;
  CLOSE cursorCandidato;
  
    
  IF (nCandidatos > nTmpVotantes) THEN
	SET NEW.alumno = null;
  END IF;
  END IF;
END
$$



-- PROCEDURE FOR COUNT ALUMNOS

-- DROP PROCEDURE numeroAlumnos;
-- SELECT e.codigo , COUNT(DISTINCT a.codigo) FROM Alumno a INNER JOIN
-- Especialidad e ON a.especialidad = e.codigo GROUP BY e.codigo;
-- WHERE e.codigo = @nEspecialidad;


DELIMITER //
CREATE PROCEDURE actualizarCAlm()
BEGIN

  DECLARE done          INTEGER;
  DECLARE nAlumnos      INTEGER;
  DECLARE cEspecialidad INTEGER;

  DECLARE CURSORCAlm    CURSOR FOR SELECT e.codigo , COUNT(DISTINCT a.codigo) 
					     FROM Alumno a INNER JOIN Especialidad e 
						 ON a.especialidad = e.codigo GROUP BY e.codigo;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
   
   SET done = 0;
   SET nAlumnos = 0;
   SET cEspecialidad = 0;

  OPEN CURSORCAlm;
  REPEAT
	FETCH CURSORCAlm INTO cEspecialidad, nAlumnos;
	UPDATE Especialidad SET total_alumnos = nAlumnos
    WHERE Especialidad.codigo = cEspecialidad;
  UNTIL done END REPEAT;
  CLOSE CURSORCAlm;
END
//

-- PROCEDURE UNLOCK PRESIDENTE

-- Un procedimiento que evalue si los votos han sido completos en cada Especialidad
-- Un disparador que se ejecute al actualizar la columno puntajeP de la tabla 
-- Electo.

-- SET Consejo

DELIMITER $$
CREATE PROCEDURE declararConsejo()
BEGIN
  DECLARE done          INT;
  DECLARE cCodigo			VARCHAR(8);
  DECLARE cursorCandidatos CURSOR FOR SELECT codigo FROM Candidato ORDER BY puntaje DESC LIMIT 12;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

  SET done = 0;

  OPEN cursorCandidatos;
  REPEAT
	FETCH cursorCandidatos INTO cCodigo;
	INSERT INTO Electo (alumno) VALUES (cCodigo);
  UNTIL done END REPEAT;
  CLOSE cursorCandidatos;
END
$$


-- set presidente
DELIMITER //
CREATE PROCEDURE declararPresidente()
BEGIN

  DECLARE done				INTEGER;
  DECLARE cCodigo			VARCHAR(8);
  DECLARE cursorCandidatos	CURSOR FOR SELECT alumno FROM  Electo ORDER BY puntajeP DESC LIMIT 1;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

  SET done = 0;
  SET cCodigo = '';
  IF (SELECT COUNT(*) FROM Electo) > 0 THEN
  OPEN cursorCandidatos;
  REPEAT
	FETCH cursorCandidatos INTO cCodigo;
	UPDATE Electo SET cargo = 'Presidente' WHERE alumno = cCodigo;
  UNTIL done END REPEAT;
  CLOSE cursorCandidatos;
  END IF;
END
//


-- PROCEDURE RESTART ELECTIONS
use sec_alpha;
DROP PROCEDURE IF EXISTS reiniciarElecciones;
DELIMITER $$
CREATE PROCEDURE reiniciarElecciones()
BEGIN
  DELETE FROM Electo;
  DELETE FROM Candidato;
  DELETE FROM Votantes;
  DELETE FROM Presidente;
  DELETE FROM Alumno;
  DELETE FROM Especialidad;
  DELETE FROM Pregunta;
END
$$



CREATE TABLE `Presidente` (
  `alumno` varchar(8) NOT NULL,
  `imagen_src` varchar(250) NOT NULL,
  `puntaje` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use sec_alpha;
DROP PROCEDURE IF EXISTS EscogerFaseVotacion;
DELIMITER //
CREATE PROCEDURE EscogerFaseVotacion()
BEGIN
    IF (SELECT valor FROM configuraciones WHERE nombre = 'faseConcejo') THEN
        UPDATE configuraciones 
			SET valor = 0 
			WHERE nombre = 'faseConcejo';
    ELSE
        UPDATE configuraciones 
			SET valor = 1 
			WHERE nombre = 'faseConcejo';
    END IF;
END
//



-- PROCEDURE RESTART PRESIDENTE
use sec_alpha;
DROP PROCEDURE IF EXISTS reiniciarPresidente;
DELIMITER $$
CREATE PROCEDURE reiniciarPresidente()
BEGIN
  DELETE FROM Presidentes;
END
$$


use sec_alpha;
DROP TRIGGER IF EXISTS insertarCandidatos;
DROP TRIGGER IF EXISTS tresCandidatos;
DELIMITER $$
CREATE TRIGGER insertarCandidatos BEFORE INSERT ON Candidato FOR EACH ROW BEGIN
	DECLARE nMaxCandidatos 		TINYINT;
	DECLARE nActualCandidatos 	TINYINT;
	DECLARE codigoEspecialidad 	TINYINT;
	DECLARE codigoCandidato 	VARCHAR(8);

	SET codigoEspecialidad = (SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno);
	SET NEW.especialidad = codigoEspecialidad;
	SET nMaxCandidatos = (SELECT numero_candidatos FROM Especialidad WHERE codigo = codigoEspecialidad);
	SET nActualCandidatos = (SELECT COUNT(*) FROM Candidato WHERE especialidad = codigoEspecialidad);
	IF (nActualCandidatos = nMaxCandidatos) THEN 
		SET NEW.alumno = NULL;
	END IF;
END
$$

SELECT   a.nombre as nombre, c.alumno as codigo, a.especialidad as especialidadAlumno, c.imagen_src as imagen_src, e.nombre as especialidad  FROM Candidato  c   INNER JOIN Alumno a  ON a.codigo = c.alumno JOIN Especialidad e  ON e.codigo = a.especialidad WHERE a.especialidad = (SELECT especialidad FROM Alumno WHERE codigo  = 20090369);

