USE sec_alpha;

DELIMITER $$
CREATE PROCEDURE declararPresidente()
BEGIN

  DECLARE done				INT DEFAULT 0;
  DECLARE cCodigo			VARCHAR(8);
  DECLARE cursorCandidatos	CURSOR FOR SELECT alumno
							  FROM  Electo ORDER BY puntajeP DESC LIMIT 1;

  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;


  OPEN cursorCandidatos;
  REPEAT
	FETCH cursorCandidatos INTO cCodigo;
	UPDATE Electo SET cargo = 'Presidente' WHERE alumno = cCodigo;
  UNTIL done END REPEAT;
  CLOSE cursorCandidatos;

END
$$
