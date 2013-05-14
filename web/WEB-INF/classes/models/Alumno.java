package models;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Alumno extends Tabla {

    private String  codigo;
    private String  NIE;
    private String  nombre;
    private Integer especialidad_fk;
    private String  hidden_fecha_registro;

    public Alumno(String codigo, String fecha, String nie, String nombres,
            Integer codigoEspecialidad) {

        this.setCodigo(codigo);
        this.setNIE(nie);
        this.setNombres(nombres);
        this.setCodigoEspecialidad(codigoEspecialidad);
        this.setFecha_registro(fecha);
        this.setTableName("Alumno");
    }

    public Alumno() {
        this.setTableName("Alumno");
    }

    /**
     * @return the codigo
     */
    public String getCodigo()
    {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }
    
    /**
     * @return the nIE
     */
    public String getNIE()
    {
        return NIE;
    }

    /**
     * @param nIE
     *            the nIE to set
     */
    public void setNIE(String nie)
    {
        this.NIE = nie;
    }

    /**
     * @return the nombre
     */
    public String getNombres()
    {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombres(String nombres)
    {
        this.nombre = nombres;
    }

    /**
     * @return the codigoEspecialidad
     */
    public Integer getCodigoEspecialidad()
    {
        return especialidad_fk;
    }

    /**
     * @param codigoEspecialidad
     *            the codigoEspecialidad to set
     */
    public void setCodigoEspecialidad(Integer codigoEspecialidad)
    {
        this.especialidad_fk = codigoEspecialidad;
    }

    /**
     * Save by getting it's parameters.
     */
    public void guardar()
    {
        ArrayList<String> campos = new ArrayList<String>();
        construirLista(campos);
        this.insertar(campos);
    }

    /**
     * @param campo
     * @param valor
     * @param key
     */
    public void Actualizar(String campo, String valor, String key)
    {
        this.Actualizar(campo, valor, key, this.getCodigo());
    }

    public void get()
    {
        try {
            ArrayList<String> campos = new ArrayList<String>();
            //TODO REFLECT
            System.out.println(this.getCodigo());
            this.fila = this.obtener(this.getCodigo());
        } catch (Exception exc) {
            System.out.println("No fue posible obtener el candidato");
        }
    }

    public void Borrar()
    {
        this.Borrar(this.getCodigo());
    }

    public void getAll()
    {
        filas = obtener();
    }

    /**
     * @param campos
     */
    public void construirLista(ArrayList<String> campos)
    {
        campos.add(this.getCodigo());
        campos.add(String.valueOf(this.getNIE()));
        campos.add(this.getNombres());
        campos.add(String.valueOf(this.getCodigoEspecialidad()));
    }

    public String getFecha_registro()
    {
        return hidden_fecha_registro;
    }

    public void setFecha_registro(String fecha_registro)
    {
        this.hidden_fecha_registro = fecha_registro;
    }

}
