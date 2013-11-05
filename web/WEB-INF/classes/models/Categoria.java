/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author _r
 */
public class Categoria extends Tabla {
    private Integer codigo_auto;
    private String nombre;
    private String hidden_bar;
    
    
    public Categoria() {
        this.setTableName("Categoria");
    }
}
