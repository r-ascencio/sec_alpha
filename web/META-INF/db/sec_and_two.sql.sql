-- HERE ONLY TEN PERSONS ARE GOING TO MAKE IT....
CREATE TABLE Presidentes ( 
    alumno VARCHAR(8) NOT NULL PRIMARY KEY, 
    imagen_src VARCHAR(250), 
    puntaje INTEGER DEFAULT 0, 
    especialidad INTEGER(11)
);

-- EVERY PERSON CHOOSE TWO - ONLY TWO, NO MORE.

-- THE BUG' : IF THE FIELD IS NULL DESTROY THE "ALUMNO" ADMIN VIEW
ALTER TABLE Alumno ADD candidato1 VARCHAR(8) DEFAULT ''; -- THIS IS NOT FK BECAUSE THAT BUG'
ALTER TABLE Alumno ADD candidato2 VARCHAR(8) DEFAULT ''; -- THIS IS NOT FK BECAUSE THAT BUG' 
ALTER TABLE Alumno ADD votaciones BOOLEAN DEFAULT FALSE; -- THIS IS NOT FK BECAUSE THAT BUG'

-- TRIGGER : "WE SAY ONLY 10 PERSONS WILL MAKE IT!"
-- NOTHING FANCY, NOTHING REALLY FANCY...
DROP TRIGGER IF EXISTS diezPresidentes;
DELIMITER $$
CREATE TRIGGER diezPresidentes BEFORE INSERT ON Presidentes FOR EACH ROW BEGIN
    IF ((SELECT COUNT(*) FROM Presidentes) > 10) THEN
        SET NEW.alumno = NULL;
    ELSE
        SET NEW.especialidad = ( SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno );
    END IF;
END
$$

INSERT INTO configuraciones (nombre) VALUES ('dosElecciones');
INSERT INTO configuraciones (nombre) VALUES ('cincoElecciones');


use sec_alpha;
DROP PROCEDURE IF EXISTS setCincoElecciones;
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
-- I NEED SOMETHING ELSE ?
-- NO.
-- JUST GET THE SHITS DONE.
-- I'M GOING TO USE ALL THE SHITS I ALREADY HAVE
-- WHAT?
-- I CAN'T, 'cause I HAVE JS MESSING AROUND.
-- MESSED.
-- FUCK!
-- SHIT!
-- I WISH THIS SHIT DOESN'T START AS A HOMEWORK.
-- SHIT...
-- I CAN'T MAKE 
-- Or...
-- I CAN START AGAIN...
-- NO!
-- FUCKCKCKCUFCK SHIT GRAPES MOTHEFUCKAS!
-- IT'S EASY
-- BUT MESSED
-- SPAGUETTI
-- FUCK :(