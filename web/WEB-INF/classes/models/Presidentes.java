package models;

import java.util.ArrayList;

public class Presidentes extends Tabla {

    private String alumno_fk;
    private Integer puntaje_auto;
    private Integer especialidad_fk;
    private String imagen_src;

    public Presidentes(String codigo, String cargo) {
        this.setCodigo(codigo);
        this.setTableName("Presidentes");
    }

    public Presidentes() {
        this.setTableName("Presidentes");
    }

    public String getCodigo() {
        return alumno_fk;
    }

    public void setCodigo(String codigo) {
        this.alumno_fk = codigo;
    }
    
    /**
     * Save by getting it's parameters.
     */
    @Override
    public void guardar() {
        ArrayList<String> campos = new ArrayList<>();
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

    @Override
    public void getAll() {
        filas = obtener();
    }

    /**
     * @param campos
     */
    public void construirLista(ArrayList<String> campos) {
        campos.add(this.getCodigo());
    }
}
