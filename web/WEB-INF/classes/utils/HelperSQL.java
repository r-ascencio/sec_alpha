/*
 * Esta clase es un intento por disminuir y hacer mas legible el codigo, al
 * momento de utilizar JDBC.
 */
package utils;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.Tabla;

import org.json.JSONArray;

public final class HelperSQL {

    private static Connection coneccion = null;
    protected static String base = "sec";
    protected static String usuario = "adminHM3bwaA";
    protected static String password = "7p2G2iTXxRpc";
    private static String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
    private static String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
    protected static String url = "jdbc:mysql://" + host + ":" + port + "/" + base;

    static {
        try {

            if (System.getenv("OPENSHIFT_MYSQL_DB_HOST") == null
                    && System.getenv("OPENSHIFT_MYSQL_DB_PORT") == null) {

                usuario = "claroline_usr";
                password = "ucsp2009";
                host = "127.0.0.1";
                port = "3306";
                base = "sec";

                url = "jdbc:mysql://" + host + ":" + port + "/" + base + "";
            }

            System.out.println(url);

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return the base
     */
    public static String getBase() {
        return base;
    }

    /**
     * @param db the base to set
     */
    public static void setBase(String base) {
        HelperSQL.base = base;
    }

    /**
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public static void setUsuario(String usuario) {
        HelperSQL.usuario = usuario;
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @param passwd the password to set
     */
    public static void setPassword(String password) {
        HelperSQL.password = password;
    }

    /**
     * @return the url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public static void setUrl(String url) {
        HelperSQL.url = url;
    }

    private static Connection obtenerConneccion() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }

    public static void desconectar() throws SQLException {
        coneccion.close();
    }

    /**
     * obtenerFilas
     *
     * Devuelve una lista de las filas dentro del select, o en su defecto un
     * objeto null.
     *
     * Supone facilitar la lectura del codigo al ser usada para realizar
     * selecciones a la base.
     *
     * Parametros:
     *
     * @campos ArrayList<String> : Los campos a seleccionarse.
     *
     * @tabla String: La tabla a ser seleccionada.
     *
     * @condicion String: Filtros que deseen ser añadidos.
     *
     * Retorna:
     *
     * @resultados List<HashMap<String, Object>>: Una lista que contiene cada
     * fila, con sus respectivos campos que pueden ser accesados por su nombre.
     */
    public static List<HashMap<String, Object>> obtenerFilas(String tabla, ArrayList<String> campos,
            String condicion) {

        System.out.println(getUrl());

        /**/
        List<HashMap<String, Object>> resultados = new ArrayList<>();
        try {

            String listaCampos = " ";

            // TODO: Reducir el siguiente for a una linea.
            for (Integer count = 0; count < campos.size(); count++) {
                if (count == (campos.size() - 1)) {
                    listaCampos += " " + campos.get(count) + " ";
                } else {
                    listaCampos += " " + campos.get(count) + ",";
                }
            }

            String sql = "SELECT " + listaCampos + " FROM " + tabla + " "
                    + condicion + ";";

            System.out.println("SELECT: " + sql);

            coneccion = obtenerConneccion();
            try (Statement comando = coneccion.createStatement()) {
                ResultSet rs = comando.executeQuery(sql);
                // se convierte el resultset a List
                resultados = UtilsSQL.convertirResultSetaLista(rs);
            }
            desconectar();

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }

        return resultados;
    }

    /* TODO: Betters this */
    /**
     * getJSON
     *
     * @param String tabla.
     * @param ArrayList
     * <String> campos.
     * @param String condicion.
     * @return JSONArray resultados.
     *
     */
    public static JSONArray getJSON(String tabla, ArrayList<String> campos, String condicion) {
        JSONArray resultados = new JSONArray();

        try {

            String listaCampos = " ";

            // TODO: Reducir el siguiente for a una linea.
            for (Integer count = 0; count < campos.size(); count++) {
                if (count == (campos.size() - 1)) {
                    listaCampos += " " + campos.get(count) + " ";
                } else {
                    listaCampos += " " + campos.get(count) + ",";
                }
            }

            String sql = "SELECT " + listaCampos + " FROM " + tabla + " "
                    + condicion + ";";

            System.out.println("SELECT (JSON) : " + sql);

            coneccion = obtenerConneccion();
            Statement comando = coneccion.createStatement();
            ResultSet rs = comando.executeQuery(sql);
            // se convierte el resultset a List
            System.out.println(rs);
            resultados = UtilsSQL.ResultsetToJSON(rs);
            comando.close();
            desconectar();

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }

        return resultados;
    }

    // Agregando valores por defecto, si solo se recibe un parametro.
    public static List<HashMap<String, Object>> obtenerFilas(String tabla) {
        ArrayList<String> defaultcampos = new ArrayList<String>();
        defaultcampos.add("* ");
        return obtenerFilas(tabla, defaultcampos, "");
    }

    public static List<HashMap<String, Object>> obtenerFilas(String tabla, String campo) {
        ArrayList<String> defaultcampos = new ArrayList<String>();
        defaultcampos.add(campo);
        return obtenerFilas(tabla, defaultcampos, "");
    }

    public static void insertarFila(String tabla, String[] cols, ArrayList<String> values) {

        Class entidadNombre = null;
        Tabla entidad = null;

        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;
        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;

        try {
            entidadNombre = Class.forName(
                    "models." + tabla);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            entidad = (Tabla) entidadNombre.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }


        try {
            PreparedStatement command = null;
            Integer count = 0;
            ArrayList<String> namesCols = new ArrayList<>();
            String vals = "";
            String colsS = "";

            // TODO: Reducir el siguiente for a una linea.
            for (count = 0; count < values.size(); count++) {
                if (values.get(count) != "null") {
                    if (count == (values.size() - 1)) {
                        vals += "?";
                    } else {
                        vals += "?,";
                    }
                }
            }

            for (int i = 0; i < cols.length; i++) {

                String fieldName = cols[i];

                fk_matcher = fk.matcher(fieldName);
                auto_matcher = auto.matcher(fieldName);

                if (fk_matcher.matches()) {
                    fieldName = fk_matcher.group(1);
                } else if (auto_matcher.matches()) {
                    fieldName = auto_matcher.group(1);
                }

                if (i == (cols.length - 1)) {
                    colsS += " " + fieldName + ")";
                    namesCols.add(fieldName);
                } else if (i == 0 && !auto_matcher.matches()) {
                    colsS += " (";
                    colsS += fieldName + " ,";
                    namesCols.add(fieldName);
                } else if (auto_matcher.matches()) {
                    colsS += "";
                    if (i == 0) {
                        colsS += " ( ";
                    }
                } else {
                    colsS += " " + fieldName + " ,";
                    namesCols.add(fieldName);
                }
            }

            // TODO: Definir los campos a insertar, using a MAP instead of a
            // ArrayList
            String query = "INSERT INTO " + tabla + " " + colsS + " VALUES (" + vals + ")";
            coneccion = obtenerConneccion();

            System.out.println(":::::: QUERY " + query + ":::::::::::.");
            command = coneccion.prepareStatement(query, values.size());

            Integer index = 1;
            for (count = 0; count < namesCols.size(); count++) {

                String fieldType = entidad.getCols().get(
                        namesCols.get(count));


                Integer i = 0;
                while (fieldType == null) {
                    String[] temps = {"_fk", "_auto"};
                    fieldType = entidad.getCols().get(
                            namesCols.get(count) + temps[i]);
                    i++;
                }

                System.out.println("::::::" + values.get(count) + "::::::::::::");

                if (fieldType != null) {
                    if (!values.get(count).equals("null")
                            || !values.get(count).equals("")) {
                        switch (fieldType) {
                            case "Integer":
                                command.setInt(index, Integer.valueOf(values.get(count)));
                                break;
                            case "String":
                                command.setString(index, new String(values.get(count).getBytes(), StandardCharsets.UTF_8));
                                break;
                        }
                        index++;
                    }
                }
            }

            command.executeUpdate();
            command.close();
            desconectar();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void insertarFila(String tabla, ArrayList<String> values) {
        String[] colsInsert = {""};
        insertarFila(tabla, colsInsert, values);
    }

    public static void actualizarCampos(String tabla, String[] cols,
            ArrayList<String> values, String Key) {

        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;
        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;

        Class entidadNombre = null;
        Tabla entidad = null;

        ArrayList<HashMap<Integer, String>> indexName = new ArrayList<>();

        try {
            entidadNombre = Class.forName(
                    "models." + tabla);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            entidad = (Tabla) entidadNombre.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            coneccion = obtenerConneccion();
            // TODO: Definir los campos a insertar, using a MAP instead of a
            // ArrayList
            String sql = "UPDATE " + tabla + " SET ";

            for (int j = 0; j < cols.length; j++) {
                String fieldName = cols[j];
                HashMap<Integer, String> registro =
                        new HashMap<>();

                if (j == 0) {
                    sql += "  ";
                }

                fk_matcher = fk.matcher(fieldName);
                auto_matcher = auto.matcher(fieldName);

                if (fk_matcher.matches()) {
                    sql += " " + fk_matcher.group(1) + " = ? ";
                    registro.put(j, fk_matcher.group(1));
                } else if (auto_matcher.matches()) {
                    sql += " ";
                } else {
                    sql += " " + fieldName + " = ? ";
                    registro.put(j, fieldName);
                }

                if (j != (cols.length - 1)
                        && !auto_matcher.matches()
                        && !(fieldName == "codigo_auto")
                        && !(fieldName == "numero_candidatos")) {
                    sql += " , ";
                } else {
                    sql += " ";
                }

                indexName.add(registro);
            }

            sql += " WHERE " + Key + " =  " + values.get(0);

            System.out.println("\n\n\n\n ::::::: " + sql + " ::::::: \n\n\n\n");

            PreparedStatement exc = coneccion.prepareStatement(sql);

            Integer index = 1;
            Integer count = 0;

            for (HashMap<Integer, String> hashMap : indexName) {
                if (index <= indexName.size()) {
                    for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
                        String nombre = entry.getValue();
                        String tipo = entidad.getCols().get(nombre);

                        if (tipo == null) {
                            tipo = entidad.getCols().get(nombre + "_fk");
                        }

                        switch (tipo) {
                            case "Integer":
                                exc.setInt(index, 
                                        Integer.valueOf(values.get(count)));
                                break;
                            case "String":
                                exc.setString(index, 
                                        new String(
                                        String.valueOf(values.get(count))
                                            .getBytes(), 
                                        StandardCharsets.UTF_8));
                                break;
                        }
                        System.out.println(":: UPDATE VALUE :: :" 
                                + values.get(count));
                        index++;
                    }
                }
                count++;
            }

            exc.executeUpdate();
            exc.close();
            desconectar();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void actualizarFila(String tabla, String campo, Object valor, String key, Object id) {
        String sql = "UPDATE " + tabla + " SET " + campo
                + " = " + valor + " WHERE " + key + " = ?;";

        try {
            coneccion = obtenerConneccion();
            if (coneccion != null) {
                PreparedStatement comando;
                //tabla, valor, campo, key, id
                comando = coneccion.prepareStatement(sql);
                comando.setObject(1, id);
                System.out.println("\n" + id + "::::: \n");
                System.out.println("\n" + sql + ":::: \n");
                comando.executeUpdate();
            } else {
                throw new Error("No existe una coneccion, utilize la funcion conectar()");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL ERROR:" + e.getMessage());
        }
    }

    public static void actualizarFilaSinCuidado(String tabla, String campo, Object valor, String condicion) {
        String sql = "UPDATE " + tabla + " SET " + campo
                + " = " + valor + " " + condicion + ";";
        
        System.out.println(sql);
        
        try {
            coneccion = obtenerConneccion();
            if (coneccion != null) {
                PreparedStatement comando;
                //tabla, valor, campo, key, id
                comando = coneccion.prepareStatement(sql);
                comando.executeUpdate();
            } else {
                throw new Error("No existe una coneccion, utilize la funcion conectar()");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL ERROR:" + e.getMessage());
        }
    }

// Pasar un String con el query, y los parametros.
// also params is stupid, it should be a constant.
    public static boolean borrarFila(String tabla, ArrayList<String> params,
            String id) {
        Boolean response = false;
        try {
            coneccion = obtenerConneccion();
            String sql = "DELETE FROM " + tabla + " WHERE " + id + "= ?";
            PreparedStatement pexec = coneccion.prepareStatement(sql);

            pexec.setString(1, params.get(0));

            if (pexec.executeUpdate() != 0) {
                response = true;


            }
        } catch (SQLException ex) {
            Logger.getLogger(HelperSQL.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    /**
     *
     */
    public static void callProc(String procName) {
        try {
            coneccion = obtenerConneccion();
            CallableStatement callableStatement;
            String proc = "{call ".concat(procName).concat("}");
            callableStatement = coneccion.prepareCall(proc);
            callableStatement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(HelperSQL.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}