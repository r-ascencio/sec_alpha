package models;

import utils.HelperSQL;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tabla {

    protected String tableName = "";
    protected List<HashMap<String, Object>> fila;
    protected List<HashMap<String, Object>> filas;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int nCols() {
        return this.getColsName().length;
    }

    public List<HashMap<String, Object>> getFila() {
        try {
            return this.fila;
        } catch (Exception exc) {
            throw new Error("Ejecute entidad.Obtener()");
        }
    }

    public List<HashMap<String, Object>> getFilas() {
        try {
            return filas;
        } catch (Exception exc) {
            throw new Error("Ejecute entidad.Select()");
        }
    }

    public void insertar(ArrayList<String> values) {
        HelperSQL.insertarFila(this.getTableName(), this.getColsName(), values);
    }

    public void insertar(Boolean passCols, ArrayList<String> values) {
        if (passCols == true) {
            HelperSQL.insertarFila(this.getTableName(), this.getColsName(), values);
        } else {
            HelperSQL.insertarFila(this.getTableName(), values);
        }
    }

    /**
     * @param campo
     * @param valor
     * @param key
     * @param id
     */
    public void Actualizar(String campo, String valor, String key, String id) {
        HelperSQL.actualizarFila(this.getTableName(), campo, valor, key, id);
    }

    public void Borrar(String codigo) {
        ArrayList<String> params = new ArrayList<>();
        params.add(codigo);

        boolean response;
        response = HelperSQL.borrarFila(this.getTableName(), params);

        if (response) {
            System.out.println("This shit works :D ");
        } else {
            System.out.println("You're patetic and you should feel bad about it");
        }
    }

    /**
     * @param campos
     * @param codigo
     * @return a row
     */
    public List<HashMap<String, Object>> obtener(String codigo) {
        ArrayList<String> campos = new ArrayList<>();
        campos.addAll(Arrays.asList(this.getColsName()));
        return HelperSQL.obtenerFilas(this.getTableName(), campos, "WHERE codigo =" + codigo);
    }

    public List<HashMap<String, Object>> obtener() {
        ArrayList<String> campos = new ArrayList<>();
        campos.addAll(Arrays.asList(this.getColsName()));
        return HelperSQL.obtenerFilas(this.getTableName(), campos, "");
    }

    public List<HashMap<String, Object>> obtenerJoin(ArrayList<String> campos, String table2) {
        String field = ".codigo = codigo;";
        String join = "INNER JOIN " + table2 + " AS t2 ON " + this.getTableName() + field;
        return HelperSQL.obtenerFilas(this.getTableName(), campos, join);
    }

    public void actualizarCampos(ArrayList<String> values) {
        String key = "codigo";
        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;

        fk_matcher = fk.matcher(this.getColsName()[0]);

        if (!this.getColsName()[0].equals(key)
                && fk_matcher.matches()) {
            key = fk_matcher.group(1);
        }

        System.out.println(key);
        HelperSQL.actualizarCampos(this.getTableName(),
                this.getColsName(), values, key);
    }

    public String[] obtenerIds() {

        List<HashMap<String, Object>> campos = HelperSQL.obtenerFilas(this.getTableName(), "codigo");

        String[] codigos = new String[campos.size()];
        int i = 0;
        for (HashMap<String, Object> hashMap : campos) {
            codigos[i] = String.valueOf(hashMap.get("codigo"));
            i++;
        }

        return codigos;
    }

    public String[] obtenerIds(String codigo) {

        List<HashMap<String, Object>> campos = HelperSQL.obtenerFilas(this.getTableName(), codigo);

        String[] codigos = new String[campos.size()];
        int i = 0;

        for (HashMap<String, Object> hashMap : campos) {
            codigos[i] = String.valueOf(hashMap.get(codigo));
            i++;
        }

        return codigos;
    }

    public void guardar() {
        this.guardar();
    }

    public void getAll() {
        filas = null;
        this.getAll();
    }

    public JSONArray filasJSON() {
        ArrayList<String> campos = new ArrayList<String>();
        campos.add("*");
        return HelperSQL.getJSON(this.getTableName(), campos, "");
    }

    public JSONArray filasJSON(String tableName) {
        ArrayList<String> campos = new ArrayList<String>();
        campos.add("*");
        return HelperSQL.getJSON(tableName, campos, "");
    }

    public JSONArray filaJSON(String key, String value) {
        ArrayList<String> campos = new ArrayList<String>();
        campos.add("*");
        return HelperSQL.getJSON(this.getTableName(), campos, "WHERE " + key + "=" + value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": ");
        for (Field f : getClass().getDeclaredFields()) {
            sb.append(f.getName());
            sb.append("=");
            try {
                sb.append(f.get(this));
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            sb.append(", ");
        }
        return sb.toString();
    }

    public JSONArray toJSON() {

        JSONArray json = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        for (Field f : getClass().getDeclaredFields()) {
            try {
                jsonObj.put(f.getName(), f.get(this));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        json.put(jsonObj);
        return json;
    }

    /**
     * Devuelve un HashMap que contiene el nombre y tipo de los campos
     * declarados.
     *
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getCols() {
        Pattern hidden = Pattern.compile("^(hidden)[_A-Za-z0-9]+");
        Matcher matcher;
        HashMap<String, String> cols = new HashMap<String, String>();
        for (Field f : getClass().getDeclaredFields()) {
            matcher = hidden.matcher(f.getName());
            if (!matcher.matches()) {
                try {
                    if (f.getType().isAssignableFrom(Integer.class)) {
                        cols.put(f.getName(), "Integer");
                    } else if (f.getType().isAssignableFrom(String.class)) {
                        cols.put(f.getName(), "String");
                    } else if (f.getType().isAssignableFrom(Float.class)) {
                        cols.put(f.getName(), "Float");
                    }
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return cols;
    }

    public String[] getColsName() {
        Pattern hidden = Pattern.compile("^(hidden)[_A-Za-z0-9]+");
        Matcher hidden_matcher;
        String[] cols = new String[this.getCols().size()];

        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;

        int i = 0;

        for (Field f : getClass().getDeclaredFields()) {
            hidden_matcher = hidden.matcher(f.getName());
            if (i < cols.length) {
                if (!hidden_matcher.matches()) {
                    cols[i] = f.getName();
                }
            }
            i++;
        }
        return cols;
    }
}
