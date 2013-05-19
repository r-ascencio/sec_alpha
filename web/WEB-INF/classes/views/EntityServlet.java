package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Utils;
import models.Tabla;
import utils.HelperSQL;

/**
 * Servlet implementation class EntidadServlet
 */
public class EntityServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    String id = "codigo";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(ServletConfig config) throws ServletException {
        return;
    }

    @SuppressWarnings({"rawtypes", "unused"})
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // building fields
        Class entidadNombre = null;
        Tabla entidad = null;

        try {
            entidadNombre = Class.forName(
                    "models." + (String) request.getAttribute("entityName"));
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

        request.setAttribute("adminDesc", "Administracion");
        request.setAttribute("fields", buildFields(entidad));
        request.setAttribute("columns", buildColumns(entidad));
        request.setAttribute("ID", id);
        request.getRequestDispatcher("/WEB-INF/templates/entidad.jsp").forward(request, response);
        return;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        return;
    }

    private String buildColumns(final models.Tabla entidad) {
        // [
        // "ProductName",
        // { field: "UnitPrice", title: "Unit Price", format: "{0:c}", width:
        // "100px" },
        // { field: "UnitsInStock", title:"Units In Stock", width: "100px" },
        // { field: "Discontinued", width: "100px" },
        // { command: ["edit", "destroy"], title: "&nbsp;", width: "172px" }],

        StringBuilder object = new StringBuilder();
        Pattern hidden = Pattern.compile("^(hidden)[_A-Za-z0-9]+");
        Matcher hidden_matcher;
        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;
        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;
        Pattern src = Pattern.compile("([_A-Za-z]+)(_src)$");
        Matcher src_matcher;

        fk_matcher = fk.matcher(entidad.getColsName()[0]);
        auto_matcher = auto.matcher(entidad.getColsName()[0]);
        id = "codigo";
        for (int i = 0; i < entidad.getColsName().length; i++) {
            String field = entidad.getColsName()[i];

            if (i == 0 & fk_matcher.matches()
                    & !auto_matcher.matches()) {
                id = fk_matcher.group(1);
            }
            hidden_matcher = hidden.matcher(field);

            if (!hidden_matcher.matches()) {

                fk_matcher = fk.matcher(field);
                auto_matcher = auto.matcher(field);
                src_matcher = src.matcher(field);

                if (fk_matcher.matches()) {

                    ArrayList<String> values = new ArrayList<>();

                    values.add("codigo");
                    values.add("nombre");

                    List<HashMap<String, Object>> vals = HelperSQL.obtenerFilas(
                            Utils.firstUpper(fk_matcher.group(1)),
                            values,
                            "");

                    int tmpMax = vals.size() - 1;
                    int tmpI = 0;

                    StringBuilder arrayValues = new StringBuilder();
                    arrayValues.append("[");
                    for (HashMap<String, Object> hashMap : vals) {
                        arrayValues.append("{");
                        arrayValues.append("\"value\":")
                                .append(hashMap.get("codigo")).append(",");
                        arrayValues.append("\"text\": \'")
                                .append(hashMap.get("nombre")).append("\'}");
                        if (tmpI == tmpMax) {
                            arrayValues.append("]");
                        } else {
                            arrayValues.append(",");
                        }

                        tmpI++;
                    }
                    object.append("{ field: \'").append(fk_matcher.group(1))
                            .append("\', title: \'")
                            .append(Utils.renderColName(fk_matcher.group(1)))
                            .append("\'")
                            .append(", values: ").append(arrayValues.toString())
                            .append(", editor: fkEditor")
                            .append("},");

                } else if (auto_matcher.matches()) {
                    field = auto_matcher.group(1);
                    object.append("{ field: \'").append(field)
                            .append("\', title: \'")
                            .append(Utils.renderColName(field)).append("\'},");

                } else if (src_matcher.matches()) {
                    String tmp_field = src_matcher.group(1);
                    object.append("{ field: \'").append(field)
                            .append("\', title: \'")
                            .append(Utils.renderColName(tmp_field)).append("\',")
                            .append("filterable: false, sortable: false, groupable: false,")
                            .append("template: \'<img src=\"#= imagen_src #\" />\',")
                            .append("editor: imgEditorCandidato,")
                            .append("},");

                } else {
                    object.append("{ field: \'").append(field)
                            .append("\', title: \'")
                            .append(Utils.renderColName(field)).append("\'},");
                }
            }
        }

        object.append("{command: [{name: \'edit\', text: { edit: \'Editar\', "
                + "update: \'Guardar\', cancel: \'Cancelar\'}}, "
                + "{name: \'destroy\', text: \'Eliminar\' }], "
                + "title: \'&nbsp;\'}");

        return "[" + object.toString() + "]";

    }

    private String buildFields(final models.Tabla entidad) {
        // {
        // ProductID: { editable: false, nullable: true },
        // ProductName: { validation: { required: true } },
        // UnitPrice: { type: "number", validation: { required: true, min: 1} },
        // Discontinued: { type: "boolean" },
        // UnitsInStock: { type: "number", validation: { min: 0, required: true
        // } }
        // }

        StringBuilder object = new StringBuilder();
        String type = "";
        String validation = "";
        Integer i = 0;
        Pattern hidden = Pattern.compile("^(hidden)[_A-Za-z0-9]+");
        Matcher matcher;

        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;


        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;

        // Pattern auto = Pattern.compile("[_A-Za-z0-9]+(auto)$");
        // Matcher auto_matcher;

        for (Entry<String, String> field : entidad.getCols().entrySet()) {
            String fieldName = field.getKey();
            String fieldValue = field.getValue();


            auto_matcher = auto.matcher(fieldName);

            if (fieldValue.equals("String")
                    && fieldName.equals("codigo")) {
                type = "";
                validation = "required: true, editable: true";

            } else if (auto_matcher.matches()) {

                type = "type: \"number\",";
                validation = "editable: false, nullable: true";

            } else if (fieldValue.equals("Integer")) {

                type = "type: \"number\",";
                validation = "required: true, min: 0";

            } else if (fieldValue.equals("String")) {

                type = "";
                validation = "required: true";

            } else if (fieldValue == "Date") {
                type = "type: \"date\" ,";
                validation = "editable: true";
            }

            matcher = hidden.matcher(field.getKey());

            if (!matcher.matches()) {

                fk_matcher = fk.matcher(fieldName);
                if (fk_matcher.matches()) {
                    fieldName = fk_matcher.group(1);
                } else if (auto_matcher.matches()) {
                    fieldName = auto_matcher.group(1);
                }
                if ((entidad.getCols().entrySet().size()) == i) {
                    object.append(fieldName + ": {" + type + " validation: {" + validation + "}}");
                } else if (auto_matcher.matches()) {
                    object.append(fieldName + ": {" + validation + "},");
                } else {
                    object.append(fieldName + ": {" + type + " validation: {" + validation + "}},");
                }
            } else {
                System.out.println(field.getKey());
            }
            i++;
        }

        System.out.println("Columnas: " + object.toString());
        return "{" + object.toString() + "}";
    }
}