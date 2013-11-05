ALTER TABLE Candidato ADD especialidad tinyint(4); 
 ALTER TABLE Candidato ADD CONSTRAINT especialidad_fk FOREIGN KEY (especialidad) REFERENCES Especialidad(codigo); 
