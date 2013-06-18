-- DELIMITER $$
-- CREATE TRIGGER especialidadCandidato AFTER INSERT ON Candidato FOR EACH ROW 
-- BEGIN
-- 
--   DECLARE currentCandidatoEsp 	TINYINT;
-- 
-- 
--   SET currentCandidatoEsp = (SELECT especialidad FROM Alumno 
-- 	WHERE codigo = NEW.alumno);
-- 
--   UPDATE Candidato SET especialidad = currentCandidatoEsp 
--   WHERE codigo = NEW.alumno;
-- END
-- $$

-- DELIMITER $$
-- CREATE TRIGGER especialidadCandidato BEFORE INSERT ON Candidato FOR EACH ROW
--   BEGIN
-- 	SET new.especialidad = ( SELECT especialidad FROM Alumno WHERE codigo = NEW.alumno ) ;
--   END;
-- $$

  -- ----------------------------------------------------------------------------

  -- CREATE TRIGGER especialidadCandidato ON Candidato INSTEAD 
  -- AS
  -- BEGIN
  --   INSERT INTO Candidato  (
	-- 	alumno,
	-- 	imagen_src,
	-- 	especialidad
	--   ) SELECT
  --   i.alumno,
  --   i.imagen_src,
  --   e.codigo
  -- ) FROM INSERTED INNER JOIN Especialidad e ON e.codigo  = (SELECT especialidad 
  --   FROM Alumno WHERE codigo = INSERTED.alumno);
-- END
-- GO

use sec;
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
  UNTIL done END REPEAT;
  CLOSE cursorCandidato;

  IF (nCandidatos > 3) THEN
	SET NEW.alumno = null;
  END IF;
  END IF;
END
$$
