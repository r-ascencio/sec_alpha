package models;

import java.util.ArrayList;

public class Pregunta extends Tabla {

    private Integer codigo_auto;
    private String descripcion;
    private String hidden_fecha_modificacion;

    public Pregunta(int codigo, String Ycreacion, String descripcion) {
        this.setCodigo(codigo);
        this.setYcreacion(Ycreacion);
        this.setDescripcion(descripcion);
        this.setTableName("Pregunta");
    }

    public Pregunta() {
        this.setTableName("Pregunta");
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return String.valueOf(codigo_auto);
    }

    /**
     * @param i the codigo to set
     */
    public void setCodigo(int i) {
        this.codigo_auto = i;
    }

    /**
     * @return the ycreacion
     */
    public String getYcreacion() {
        return hidden_fecha_modificacion;
    }

    /**
     * @param ycreacion the ycreacion to set
     */
    public void setYcreacion(String ycreacion) {
        hidden_fecha_modificacion = ycreacion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (filas != null) {
            filas.clear();
            System.out.println(filas.size());
        }

        filas = obtener();
        System.out.println(filas.size());
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
        campos.add(this.getYcreacion());
        campos.add(this.getDescripcion());
    }
}
