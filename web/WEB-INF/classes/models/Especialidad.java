package models;

import java.util.ArrayList;
import java.util.Date;

import utils.HelperSQL;
import utils.Utils;

public class Especialidad extends Tabla {
    // {"nombre":"Informatica II","codigo_auto":1,
    // "fecha_modificacion":"2013-05-05 17:45:37.0","voto_alumnos":0,
    // "total_alumnos":35,"fecha_registro":"2013-05-05"}
    private Integer codigo_auto;
    private String  nombre;
    private Integer total_alumnos;
    private Integer hidden_voto_alumnos;
    private String  hidden_fecha_registro;
    private String  hidden_fecha_modificacion;

    public Especialidad()
    {
        super();
        setTableName("Especialidad");
    }

    /**
     * @return the codigo_auto
     */
    public String getCodigo()
    {
        return String.valueOf(codigo_auto);
    }

    /**
     * @param codigo_auto
     *            the codigo_auto to set
     */
    public void setCodigo(String codigo)
    {
        if (Utils.isInt(codigo)) {
            this.codigo_auto = Integer.valueOf(codigo);
        } else {
            throw new Error("el codigo debe ser un entero");
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    public void Borrar()
    {
        this.Borrar(this.getCodigo());
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre)
    {
        if (this.codigo_auto != null) {
            this.nombre = nombre;
        } else {
            throw new Error("No se especificado el codigo.");
        }
    }

    public Especialidad(String codigo, String year, String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
        System.out.println(this.nCols());
        setTableName("Especialidad");
    }

    public void insertar()
    {
        ArrayList<String> values = new ArrayList<String>();
        values.add(this.getCodigo());
        values.add(this.getNombre());
        HelperSQL.insertarFila(this.getTableName(), values);
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
            this.fila = this.obtener(this.getCodigo());
        } catch (Exception exc) {
            System.out.println("Imposible Obtener la entidad " + this.getTableName());
        }
    }

    public void getAll()
    {
        try {
            filas = obtener();
        } catch (Exception exc) {
            System.out.println("Imposible Obtener la entidad " + this.getTableName());
        }
    }

    /**
     * @param campos
     */
    public void construirLista(ArrayList<String> campos)
    {
        campos.add(this.getCodigo());
        campos.add(this.getNombre());
    }

    public Integer getTotal_alumnos()
    {
        return total_alumnos;
    }

    public void setTotal_alumnos(Integer total_alumnos)
    {
        this.total_alumnos = total_alumnos;
    }
}
