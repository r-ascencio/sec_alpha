USE sec;

DELIMITER $$
CREATE PROCEDURE declararConsejo()
BEGIN
  DECLARE done				INT DEFAULT 0;
  DECLARE cCodigo			VARCHAR(8);
  DECLARE cursorCandidatos	CURSOR FOR SELECT alumno
							  FROM Candidato ORDER BY puntaje DESC LIMIT 12;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;


  OPEN cursorCandidatos;
  REPEAT
	FETCH cursorCandidatos INTO cCodigo;
	INSERT INTO Electo (alumno) VALUES (cCodigo);
  UNTIL done END REPEAT;
  CLOSE cursorCandidatos;
END
$$
