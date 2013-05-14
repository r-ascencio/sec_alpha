package models;

import java.util.ArrayList;


public class Usuarios extends Tabla {

    private String   codigo;
    private String   passwd;

    public Usuarios() {
        super();
        setTableName("usr");
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getPasswd()
    {
        return passwd;
    }

    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }

    public void guardar()
    {
        try {
            ArrayList<String> campos = new ArrayList<String>();
            construirLista(campos);
            this.insertar(campos);
        } catch (Exception exc) {
            System.out.println("Problema guardando el Candidato");
        }
    }

    public void get()
    {
        try {
            this.fila = this.obtener(this.getCodigo());
        } catch (Exception exc) {
            System.out.println("No fue posible obtener el candidato");
        }
    }
    
    public void getAll()
    {
        filas = obtener();
    }
    
    public void construirLista(ArrayList<String> campos)
    {
        campos.add(this.getCodigo());
        campos.add(this.getPasswd());
    }
}
