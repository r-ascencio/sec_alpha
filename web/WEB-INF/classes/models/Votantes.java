package models;

import java.util.ArrayList;

public class Votantes extends Alumno {

    private String alumno_fk;
    private Integer especialidad_fk;
    private String imagen_src;

    public Votantes() {
        super();
        setTableName("Votantes");
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
    public Votantes(String codigo, String Y, String nie, String nombres,
            Integer codigoEspecialidad, String src) {

        super(codigo, Y, nie, nombres, codigoEspecialidad);
        // this.setFoto(src);
        this.setTableName("Votantes");
    }

    /**
    public String getSrc() {
        return imagen_src;
    }

    public void setSrc(String src) {
        this.imagen_src = src;
    }

    public void setFoto(String src) {
        try {
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