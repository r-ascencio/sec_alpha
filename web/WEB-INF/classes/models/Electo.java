package models;

import java.util.ArrayList;

public class Electo extends Tabla {

    private String alumno_fk;
    private Integer puntajeP_auto;
    private String cargo;

    public Electo(String codigo, String cargo) {
        this.setCargo(cargo);
        this.setCodigo(codigo);
        this.setTableName("Electo");
    }

    public Electo() {
        this.setTableName("Electo");
    }

    public String getCodigo() {
        return alumno_fk;
    }

    public void setCodigo(String codigo) {
        this.alumno_fk = codigo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Save by getting it's parameters.
     */
    public void guardar() {
        ArrayList<String> campos = new ArrayList<String>();
        construirLista(campos);
        this.insertar(campos);
    }

    /**
     * @param campo
     * @param valor
     * @param key
     */
    public void Actualizar(String campo, String valor, String key) {
        this.Actualizar(campo, valor, key, this.getCodigo());
    }

    public void get() {
        try {
            this.fila = this.obtener(this.getCodigo());
        } catch (Exception exc) {
            System.out.println("No fue posible obtener el candidato");
        }
    }

    public void Borrar() {
        this.Borrar(this.getCodigo());
    }

    public void getAll() {
        filas = obtener();
    }

    /**
     * @param campos
     */
    public void construirLista(ArrayList<String> campos) {
        campos.add(this.getCodigo());
        campos.add(this.getCargo());
    }
}
