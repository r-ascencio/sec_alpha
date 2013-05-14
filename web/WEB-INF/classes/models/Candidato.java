package models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Candidato extends Alumno {

    private String alumno_fk;
    private String imagen_src;
    private Integer hidden_puntaje_auto = 0;
    
    
    private BufferedImage hidden_foto;
    private static Integer hidden_NCandidato = 24;
    private String hidden_fecha_registro;

    public Candidato() {
        super();
        setTableName("Candidato");
    }

    /**
     *
     * @param codigo
     * @param Y
     * @param nie
     * @param nombres
     * @param especialidad
     * @param codigoEspecialidad
     * @param puntaje
     * @param srcfoto
     * @param src
     */
    public Candidato(String codigo, String Y, String nie, String nombres,
            Integer codigoEspecialidad, String src) {

        super(codigo, Y, nie, nombres, codigoEspecialidad);
        this.setSrc(src);
        // this.setFoto(src);
        this.setTableName("Candidatos");
    }

    /**
     * @return the puntaje
     */
    public Integer getPuntaje() {
        return hidden_puntaje_auto;
    }

    public void agregarPuntaje(Integer puntaje) {
        this.hidden_puntaje_auto += puntaje;
    }

    public static Integer getMaxCandidatos() {
        return hidden_NCandidato;
    }

    public BufferedImage getFoto() {
        return hidden_foto;
    }

    public String getSrc() {
        return imagen_src;
    }

    public void setSrc(String src) {
        this.imagen_src = src;
    }

    public void setFoto(String src) {
        try {
            this.hidden_foto = ImageIO.read(new File(src));
            this.setSrc(src);
        } catch (IOException ex) {
            System.out.println("No se pudo leer la imagen");
        }
    }

    public void guardar() {
        try {
            ArrayList<String> campos = new ArrayList<String>();
            construirLista(campos);
            this.insertar(campos);
        } catch (Exception exc) {
            System.out.println("Problema guardando el Candidato");
        }
    }

    public void get() {
        try {
            this.fila = this.obtener(this.getCodigo());
        } catch (Exception exc) {
            System.out.println("No fue posible obtener el candidato");
        }
    }

    public void getAll() {
        filas = obtener();
    }

    public void Borrar() {
        this.Borrar(this.getCodigo());
    }

    /**
     * @param campo
     * @param valor
     * @param key
     */
    public void Actualizar(String campo, String valor, String key) {
        this.Actualizar(campo, valor, key, this.getCodigo());
    }

    /**
     * @param campos
     */
    public void construirLista(ArrayList<String> campos) {
        campos.add(this.getCodigo());
        campos.add(String.valueOf(0));
    }
}