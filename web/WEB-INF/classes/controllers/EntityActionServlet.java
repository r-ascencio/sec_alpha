package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tabla;

import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class EntityActionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private JSONArray models = new JSONArray();
    private Tabla tabla;
    private Class tablaClass;
    Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
    Matcher fk_matcher;
    Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
    Matcher auto_matcher;

    public EntityActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getAttribute("entityName") != null) {
            try {
                tablaClass = Class.forName("models." + request.getAttribute("entityName"));
                tabla = (Tabla) tablaClass.newInstance();
                System.out.println("You're here tabla....");

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }

        if (request.getAttribute("action") != null) {
            String action = (String) request.getAttribute("action");
            if (request.getParameter("models") != null) {
                models = new JSONArray(request.getParameter("models"));
                switch (action) {

                    case "create":
                        for (int i = 0; i < models.length(); i++) {
                            JSONObject model = models.getJSONObject(i);
                            ArrayList<String> values = new ArrayList<>();

                            for (int j = 0; j < tabla.getColsName().length; j++) {
                                String fieldName = tabla.getColsName()[j];

                                fk_matcher = fk.matcher(fieldName);
                                auto_matcher = auto.matcher(fieldName);

                                if (fk_matcher.matches()) {
                                    fieldName = fk_matcher.group(1);
                                }
                                if (!auto_matcher.matches()) {
                                    String value = String.valueOf(
                                            model.get(fieldName));

                                    values.add(value);
                                }

                            }

                            tabla.insertar(values);
                        }
                        break;

                    case "delete":
                        for (int i = 0; i < models.length(); i++) {
                            JSONObject model = models.getJSONObject(i);
                            String field = tabla.getColsName()[0];
                            fk_matcher = fk.matcher(field);
                            try {
                                if (fk_matcher.matches()) {
                                    field = fk_matcher.group(1);
                                } else {
                                    field = "codigo"; // dont hur't me babe, dont hur't me.
                                }
                                tabla.Borrar(
                                        String.valueOf(model.get(field))
                                        );
                            } catch (Exception ex) {
                                Logger.getLogger(EntityActionServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                    case "update":
                        for (int i = 0; i < models.length(); i++) {
                            JSONObject model = models.getJSONObject(i);
                            ArrayList<String> values = new ArrayList<>();

                            for (int j = 0; j < tabla.getColsName().length; j++) {
                                String fieldName = tabla.getColsName()[j];

                                fk_matcher = fk.matcher(fieldName);
                                auto_matcher = auto.matcher(fieldName);

                                if (fk_matcher.matches()) {
                                    fieldName = fk_matcher.group(1);
                                } else if (auto_matcher.matches()) {
                                    fieldName = auto_matcher.group(1);
                                }

                                String value = String.valueOf(
                                        model.get(fieldName));
                                values.add(value);
                            }

                            tabla.actualizarCampos(values);
                        }
                        break;
                }
            }
        } else {
            // TODO: REMOVE THIS.
            System.out.println("Haha motherfucker! chuchu die!!!   ");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("InPOST");
        @SuppressWarnings("rawtypes")
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            System.out.println(request.getParameter(parameterName) + " : " + parameterName);
        }
    }
}
