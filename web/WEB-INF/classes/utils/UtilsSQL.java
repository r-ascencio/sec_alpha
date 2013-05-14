package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UtilsSQL {

    /*
     * Convierte un ResultSet a uan Lista, para que pueda ser tratada con mayor
     * libertad.
     * 
     * @parametro ResultSet rs: El ResultSet a convertir.
     */
    public static List<HashMap<String, Object>> convertirResultSetaLista(ResultSet rs) throws SQLException {

        // ResultSetMetaData: permite conocer mas información sobre las
        // columnas,
        // dentro del resultset.

        ResultSetMetaData metadatos = rs.getMetaData();

        int columnas = metadatos.getColumnCount();
        ArrayList<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();

        while (rs.next()) {

            /*
             * Hasmap: son similares a un diccionario y son utilizados como eso,
             * un diccionario de datos. Hashmap< nombre del campo , valor del
             * campo>
             */

            HashMap<String, Object> fila = new HashMap<String, Object>(columnas);

            for (int i = 1; i <= columnas; ++i) {
                fila.put(metadatos.getColumnName(i), rs.getObject(i));
            }

            lista.add(fila);
        }

        return lista;

    }

    public static JSONArray ResultsetToJSON(ResultSet rs)
            throws SQLException, JSONException {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            int numColumns = rsmd.getColumnCount();

            JSONObject obj = new JSONObject();

            for (int i = 1; i <= numColumns; ++i) {

                String column_name = rsmd.getColumnName(i);

                if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                    obj.put(column_name, rs.getArray(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                    obj.put(column_name, rs.getBoolean(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                    obj.put(column_name, rs.getBlob(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                    obj.put(column_name, rs.getDouble(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                    obj.put(column_name, rs.getFloat(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                    obj.put(column_name, rs.getNString(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                    obj.put(column_name, rs.getInt(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
                    obj.put(column_name, rs.getString(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                    obj.put(column_name, rs.getDate(column_name));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                    obj.put(column_name, rs.getTimestamp(column_name));
                } else {
                    obj.put(column_name, rs.getObject(column_name));
                }

            }
            json.put(obj);
        }

        return json;
    }
}
