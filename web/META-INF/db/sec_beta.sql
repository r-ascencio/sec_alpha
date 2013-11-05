-- db:mysql -D sec_alpha -u _r
/*
    ===================================
    | SQL SEC.                        |
    ===================================
   + Para todas las tablas la llave primaria
     debera ser llamada 'codigo'.
   + Los campos foraneos, deberan estar escritos
     en minusculas, y deberan ser nombrados
     como la tabla padre.
*/

-- ===========================================================================
DROP DATABASE IF EXISTS sec;
CREATE DATABASE IF NOT EXISTS sec;
USE sec;
-- ===========================================================================

-- =========== Especialidad. =================================================
/**
 * @Tabla: Especialidad (catalogo).
 * @Descripcion:
 *  Almancena las especialidades de estudio.
 *  Se utiliza en las tablas:
 *      + Alumno,
 *      + Candidato,
 *      + Presidente,
 *      + Presidentes.
 */

DROP TABLE IF EXISTS Especialidad;

CREATE TABLE IF NOT EXISTS Especialidad
(
	codigo 		        TINYINT AUTO_INCREMENT UNIQUE NOT NULL,
	nombre              VARCHAR(100) UNIQUE NOT NULL,
	numero_candidatos   INTEGER NOT NULL DEFAULT 3,
	voto_alumnos        TINYINT NOT NULL DEFAULT 0,
	total_alumnos       TINYINT NOT NULL DEFAULT 0
);

-- PK
ALTER TABLE Especialidad
        ADD CONSTRAINT especialidad_codigo_pk
            PRIMARY KEY (codigo);
-- Index
    CREATE INDEX index_especialidad_codigo
        ON Especialidad (codigo);
-- =========== Fin Especialidad. =============================================

-- =========== Alumno. =======================================================
/*
 *  @Tabla: Alumno.
 *  @Descripcion:
        Almacena la información de cada alumno.
        Es utilizada por todas las tablas, con excepcion
        de Especialidad y configuraciones.
 */

CREATE TABLE IF NOT EXISTS Alumno
(
  codigo                VARCHAR(8) UNIQUE NOT NULL,
  NIE                   VARCHAR(11) UNIQUE NOT NULL,
  nombre                VARCHAR(250) NOT NULL,
  especialidad          TINYINT NOT NULL,
  voto                  BOOLEAN DEFAULT FALSE, -- Votacion por candidatos.
  candidato1            VARCHAR(8) DEFAULT '', -- Votacion 2 Presidentes.
  candidato2            VARCHAR(8) DEFAULT '', -- Votacion 2 Presidentes.
  votaciones            BOOLEAN DEFAULT FALSE -- Votacion solo candidatos.
);

-- PK
ALTER TABLE Alumno
    ADD CONSTRAINT alumno_codigo_pk PRIMARY KEY (codigo, NIE);
-- Index
    CREATE INDEX index_alumno_codigo
        ON Alumno (codigo);
    CREATE INDEX index_alumno_especialidad
        ON Alumno (especialidad);
    CREATE INDEX index_alumno_voto
        ON Alumno (voto);
    CREATE INDEX index_alumno_votaciones
        ON Alumno (votaciones);


DROP PROCEDURE IF EXISTS actualizarCAlm;

DELIMITER $$
CREATE PROCEDURE actualizarCAlm()
BEGIN

  DECLARE done          INT DEFAULT 0;
  DECLARE nAlumnos      INT DEFAULT 0;
  DECLARE cEspecialidad INT DEFAULT 0;

  DECLARE CURSORCAlm    CURSOR FOR SELECT e.codigo , COUNT(DISTINCT a.codigo)
					     FROM Alumno a INNER JOIN Especialidad e
						 ON a.especialidad = e.codigo GROUP BY e.codigo;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

  OPEN CURSORCAlm;
  REPEAT
	FETCH CURSORCAlm INTO cEspecialidad, nAlumnos;
	UPDATE Especialidad SET total_alumnos = nAlumnos
    WHERE Especialidad.codigo = cEspecialidad;
  UNTIL done END REPEAT;
  CLOSE CURSORCAlm;
END
$$


-- =========== Fin Alumno. ===================================================

-- =========== Candidato. ====================================================
/*
 *  @Tabla: Candidato.
 *  @Descripcion:
        Almacena los candidatos a utilizar en la primera fase de votación.
 */
CREATE TABLE IF NOT EXISTS Candidato (
  alumno                VARCHAR(8) UNIQUE NOT NULL,
  imagen_src            VARCHAR(250),
  especialidad          TINYINT NOT NULL,
  puntaje               INTEGER DEFAULT 0 NOT NULL
); $$

-- PK
ALTER TABLE Candidato
    ADD CONSTRAINT candidato_codigo_pk PRIMARY KEY (alumno); $$
-- Index

    CREATE INDEX index_candidato_codigo
        ON Candidato (alumno); $$
    CREATE INDEX index_candidato_puntaje
        ON Candidato (puntaje); $$
    CREATE INDEX index_candidato_especialidad
        ON Candidato (especialidad); $$

-- Trigger nCandidatosVoto, cada especialidad almacena un numero diferente
-- de Candidatos.

DROP TRIGGER IF EXISTS insertarCandidatos; $$

DELIMITER $$
CREATE TRIGGER insertarCandidatos BEFORE INSERT ON Candidato FOR EACH ROW BEGIN
	DECLARE nMaxCandidatos 		TINYINT;
	DECLARE nActualCandidatos 	TINYINT;
	DECLARE codigoEspecialidad 	TINYINT;
	DECLARE codigoCandidato 	VARCHAR(8);

	SET codigoEspecialidad = (
             SELECT especialidad
             FROM Alumno
             WHERE codigo = NEW.alumno
             );
	SET NEW.especialidad = codigoEspecialidad;
	SET nMaxCandidatos = (
          SELECT numero_candidatos
          FROM Especialidad
          WHERE codigo = codigoEspecialidad
          );
	SET nActualCandidatos = (
         SELECT COUNT(*)
         FROM Candidato
		 WHERE especialidad = codigoEspecialidad
         );
	IF (nActualCandidatos = nMaxCandidatos) THEN
		SET NEW.alumno = NULL;
	END IF;
END
$$
-- =========== Fin Candidato. ================================================

-- =========== Presidente. ===================================================

CREATE TABLE IF NOT EXISTS Electo (
  alumno                VARCHAR(8) UNIQUE UNIQUE NOT NULL,
  cargo                 VARCHAR(150),
  puntajeP              INTEGER DEFAULT 0,
  fecha_modificacion     TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
$$
-- PK
ALTER TABLE Electo
    ADD CONSTRAINT electo_pk PRIMARY KEY (alumno);
$$

-- =========== Fin Presidente. ===============================================

-- =========== Presidente. ===================================================

CREATE TABLE Presidente (
  alumno                VARCHAR(8) NOT NULL,
  imagen_src            VARCHAR(250) NOT NULL,
  puntaje               INTEGER(11) NOT NULL DEFAULT '0',
  especialidad          TINYINT NOT NULL,
  PRIMARY KEY (`alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
$$

ALTER TABLE Presidente
    ADD CONSTRAINT presidente_codigo_fk
    FOREIGN KEY (alumno) REFERENCES Alumno (codigo)
        ON DELETE RESTRICT ON UPDATE CASCADE;
$$

DROP TRIGGER IF EXISTS agregarCandidatoPresidente;
$$

DELIMITER $$
CREATE TRIGGER agregarCandidatoPresidente BEFORE INSERT ON Presidente FOR EACH ROW BEGIN
    SET NEW.especialidad = (
        SELECT especialidad
        FROM Alumno
        WHERE codigo = NEW.alumno
    );
END
$$

DROP PROCEDURE IF EXISTS reiniciarPresidente;
$$

DELIMITER $$
CREATE PROCEDURE reiniciarPresidente()
BEGIN
  DELETE FROM Presidentes;
END
$$

-- =========== Fin Presidente. ===============================================

-- =========== Votantes. =====================================================

CREATE TABLE IF NOT EXISTS Votantes (
    alumno                VARCHAR(8) NOT NULL,
    especialidad          TINYINT NOT NULL,
    imagen_src            TEXT NOT NULL,
    voto_realizado        BOOLEAN  DEFAULT 0 NOT NULL,
    PRIMARY KEY (`alumno`)
);
$$

DROP TRIGGER IF EXISTS agregarVotantes;
$$

DELIMITER $$
CREATE TRIGGER agregarVotantes BEFORE INSERT ON Votantes FOR EACH ROW BEGIN
    SET NEW.especialidad = (
        SELECT especialidad
        FROM Alumno
        WHERE codigo = NEW.alumno
    );
END
$$

-- =========== Fin Votantes. ==================================================

-- =========== Presidentes. ===================================================

CREATE TABLE Presidentes (
    alumno          VARCHAR(8) NOT NULL PRIMARY KEY,
    imagen_src      VARCHAR(250),
    puntaje         INTEGER DEFAULT 0,
    especialidad    INTEGER(11)
);
$$

DROP TRIGGER IF EXISTS insertPresidentes;
$$

DELIMITER $$
CREATE TRIGGER insertPresidentes BEFORE INSERT ON Presidentes FOR EACH ROW BEGIN
        SET NEW.especialidad = (
            SELECT especialidad
            FROM Alumno
            WHERE codigo = NEW.alumno
        );
END
$$

DROP PROCEDURE IF EXISTS reiniciarPresidentes;
$$

DELIMITER $$
CREATE PROCEDURE reiniciarPresidentes()
BEGIN
  DELETE FROM Presidentes;
END
$$

-- =========== Fin Presidentes. ===============================================

-- =========== Categoria. =====================================================

CREATE TABLE IF NOT EXISTS Categoria (
    codigo                INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre                VARCHAR(150) NOT NULL
);
$$
-- =========== Fin Categoria. ===================================================

-- =========== Pregunta. ======================================================
-- Puntaje de los candidatos en cada categoria.

CREATE TABLE IF NOT EXISTS Pregunta (
  codigo                INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
  descripcion           VARCHAR(255) NOT NULL,
  categoria             INTEGER
);
$$
-- PK
ALTER TABLE Pregunta
    ADD CONSTRAINT pregunta_pk  PRIMARY KEY (codigo);
$$
CREATE INDEX index_pregunta_codigo ON Pregunta (codigo);
$$
-- =========== Fin Pregunta. ==================================================

-- =========== Puntaje. =======================================================
-- Puntaje de los candidatos en cada categoria.

CREATE TABLE Puntaje (
    alumno                VARCHAR(8),
    categoria             INTEGER,
    puntaje               INTEGER DEFAULT 0,
    PRIMARY KEY(alumno, categoria)
);
$$
DELIMITER $$
CREATE PROCEDURE setearPuntaje()
BEGIN
    INSERT INTO Puntaje (
            alumno,
            categoria
       )
       SELECT
           c.alumno AS alumno,
           ca.codigo AS categoria
       FROM
           Candidato c
       CROSS JOIN
           Categoria ca
       WHERE
           EXISTS(
               SELECT
                   p.alumno,
                   p.categoria
               FROM
                   Puntaje p
               WHERE
                   p.alumno = c.alumno
               AND p.categoria = ca.codigo
           ) <> 1;

END
$$

-- =========== Fin Puntaje. =====================================================

-- =========== Usuario. =========================================================

CREATE TABLE IF NOT EXISTS Usuario (
  codigo                TINYINT AUTO_INCREMENT UNIQUE NOT NULL,
  nombre                VARCHAR(100) UNIQUE NOT NULL,
  pass                  TEXT NOT NULL,
  descripcion           VARCHAR(150) DEFAULT ''
);
$$

ALTER TABLE Usuario
    ADD CONSTRAINT user_codigo_pk PRIMARY KEY (codigo);
$$

INSERT INTO Usuario (nombre, pass) VALUES
	('admin',
		'1cf6ae9757404eb495459d82151f8b05348c66077b6512f2e756364ff5fd6a805f304b0c858a14338fd5cf32723faaa64d960bbe9f9c509be9a3fc547b851db4');
$$
-- =========== Fin Usuario. =====================================================


-- =========== Configuraiciones. =================================================
-- Dummy and stupid table to save settings, in a very silly way.
-- I'm ashamed of this.
CREATE TABLE IF NOT EXISTS configuraciones
(
    codigo                INT AUTO_INCREMENT PRIMARY KEY,
    nombre                VARCHAR(150) NOT NULL UNIQUE,
    valor                 BOOLEAN NOT NULL DEFAULT FALSE
); $$

INSERT INTO configuraciones (nombre) VALUES ('votacionNormal'); $$
INSERT INTO configuraciones (nombre) VALUES ('votacionPresidente'); $$
INSERT INTO configuraciones (nombre) VALUES ('faseConcejo'); $$
INSERT INTO configuraciones (nombre) VALUES ('dosElecciones'); $$
INSERT INTO configuraciones (nombre) VALUES ('cincoElecciones'); $$

DROP PROCEDURE IF EXISTS setCincoElecciones; $$

DELIMITER $$
CREATE PROCEDURE setCincoElecciones()
BEGIN
    UPDATE
        configuraciones
    SET
        valor = 0;
    UPDATE
        configuraciones
    SET
        valor = 1
    WHERE
        nombre = 'cincoElecciones';
END
$$

DROP PROCEDURE IF EXISTS EscogerVotacionN; $$

DELIMITER $$
CREATE PROCEDURE EscogerVotacionN()
BEGIN
    UPDATE configuraciones SET valor = 0;
    UPDATE configuraciones SET valor = 1 WHERE nombre = 'votacionNormal';
END
$$

DROP PROCEDURE IF EXISTS EscogerVotacionP; $$

DELIMITER $$
CREATE PROCEDURE EscogerVotacionP()
BEGIN
    UPDATE configuraciones SET valor = 0;
    UPDATE configuraciones SET valor = 1 WHERE nombre = 'votacionPresidente';
END
$$


DELIMITER $$
CREATE PROCEDURE EscogerFaseVotacion()
BEGIN
	UPDATE configuraciones SET valor = 0;
    UPDATE configuraciones SET valor = 1 WHERE nombre = 'faseConcejo';
END
$$

-- =========== Fin configuraciones. ==============================================

-- =========== Procedimientos. ==================================================

DROP PROCEDURE IF EXISTS reiniciarElecciones; $$
DELIMITER $$
CREATE PROCEDURE reiniciarElecciones()
BEGIN
  DELETE FROM Electo;
  DELETE FROM Candidato;
  DELETE FROM Votantes;
  DELETE FROM Presidente;
  DELETE FROM Alumno;
  DELETE FROM Especialidad;
END
$$
-- =========== Fin Procedimientos. ===============================================

-- =========== INSERTS Preguntas. ================================================

INSERT INTO Categoria (nombre)
VALUES ('elecciones'), ('preguntas');
$$

INSERT INTO Pregunta (categoria, descripcion)
VALUES ((SELECT codigo FROM Categoria WHERE nombre = 'elecciones'), 'Pregunta 1'),
((SELECT codigo FROM Categoria WHERE nombre = 'elecciones'), 'Pregunta 2'),
((SELECT codigo FROM Categoria WHERE nombre = 'elecciones'), 'Pregunta 3'),
((SELECT codigo FROM Categoria WHERE nombre = 'elecciones'), 'Pregunta 4'),
((SELECT codigo FROM Categoria WHERE nombre = 'elecciones'), 'Pregunta 5');
$$


INSERT INTO Pregunta (categoria, descripcion)
VALUES
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Se muestra cercano con los/las compañeros'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Utiliza un lenguaje amable y afectivo'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Estimula y refuerza la participación de todos'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe manejar sus emociones'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es honesto y transparente'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Manifiesta identificacion con la Institución'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Se identifica con el carisma salesiano'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Participa dinamicamente Con las actividades del colegio'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Muestra estabilidad emocional ante las dificultades que se le presentan'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe reconocer sus errores'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sus sentimientos son autenticos ante los demás'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es solidario y servicial con sus semejantes'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es responsable ante los compromisos adquiridos'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Reacciona con calma ante la presión'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es tolerante ante la diversidad de pensamiento'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe valorar su propio trabajo y el de los demás'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es accesible a los demás'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Su record disciplinario es aceptable'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Da testimonio de su fe ante los demás'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Participa activamente en las actividades religiosas del colegio'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es creativo a la hora de proponer soluciones ante las dificultades'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Genera un ambiente de estabilidad cuando trabaja en equipo'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Evidencia seguridad en sus responsabilidades (tareas, trabajos de grupo, expo, etc)'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe tomar decisiones en el momento oportuno'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe trabajar en equipo'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Se adapta rápidamente a nuevas situaciones'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe dialogar y negociar'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe expresar sus ideas'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es puntual'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Toma iniciativa ante lo que se le presenta'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es imparcial en su toma de decisiones'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Sabe comunicarse adecuadamente con los demas'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Capacidad de organizarse ante múltiples actividades'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Se dirige a los/las compañeras/os con respeto'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Manifiesta entusiasmo durante la realizacion de un proyecto hasta finalizarlo'),
((SELECT codigo FROM Categoria WHERE nombre = 'preguntas'),
	'Es capaz de escuchar y toma en cuenta la opinion de los demas, buscando lo mejor para la comunidad');
$$
-- =========== INSERTS Preguntas. ================================================
