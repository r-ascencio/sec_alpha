package models;

public class Usuario extends Tabla {
;
    private String nombre;
    private String descripcion;
    private String pass_auto;
    private Integer hidden_codigo;

    public Usuario() {
        super();
        setTableName("Usuario");
    }
}
