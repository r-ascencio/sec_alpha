package models;

public class Usuario extends Tabla {
    
    private Integer codigo_auto;
    private String nombre;
    private String pass_auto;
    private String descripcion;

    public Usuario() {
        super();
        setTableName("Usuario");
    }
}
