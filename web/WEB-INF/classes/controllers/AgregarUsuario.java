/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Usuario;
import utils.HelperSQL;
import utils.Utils;

/**
 *
 * @author _r
 */
@WebServlet(name = "AgregarUsuario", urlPatterns = {"/admin/Usuario/nuevo"})
public class AgregarUsuario extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String form = buildForm(new Usuario(), "./nuevo");

        request.setAttribute("grid", form);
        request.setAttribute("entityName", "Nuevo usuario");
        request.setAttribute("adminDesc", "Añada un nuevo usuario");

        request.getRequestDispatcher("/WEB-INF/templates/entidad.jsp")
                .forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = new Usuario();
        String[] cols = {"nombre", "descripcion", "pass"};
        ArrayList<String> values = new ArrayList<>();
        values.add((String) request.getParameter("nombre"));
        values.add((String) request.getParameter("descripcion"));
        String passTodb = Utils.encodeToSHA512(
                (String) request.getParameter("pass"));
        values.add(passTodb);
        HelperSQL.insertarFila(usuario.getTableName(), cols, values);
        response.sendRedirect(request.getContextPath() + "/admin/Usuario/");
    }

    
    
    @SuppressWarnings("unused")
    private String buildForm(final models.Tabla entidad, String action) {
        StringBuilder dom = new StringBuilder();
        String datatype = new String();
        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;
        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;

        dom.append("<form method=\"POST\" name=\"frm" + entidad.getTableName() + "\" "
                + "action=\""+action+"\">");
        dom.append("<fieldset>");
        dom.append("<legend id=\"lengend\">" + entidad.getTableName() + "</legend>");
        dom.append("<br/>");

        for (Map.Entry<String, String> field : entidad.getCols().entrySet()) {
            String key = field.getKey();
            String type = "text";
            auto_matcher = auto.matcher(key);
            fk_matcher = fk.matcher(key);


            if (field.getValue() == "Integer") {
                datatype = "integer";
            } else if (field.getValue() == "String") {
                datatype = "string";
            } else if (field.getValue() == "Float") {
                datatype = "float";
            }

            if (fk_matcher.matches()) {
                key = fk_matcher.group(1);
            } else if (auto_matcher.matches()) {
                key = auto_matcher.group(1);
                type = "password";
            }

            if (key == "codigo" && field.getValue() == "Integer") {
                dom.append("<input type=\"hidden\" data-type=\"auto_increment\" "
                        + "name=\"" + key + "\" value = \" " + entidad.getCols().size() + 1 + " \" />");
            } else {

                dom.append("<div class=\"elevencol centered\">");
                dom.append("<div class=\"twocol last \">");
                dom.append("<span class=\"prefix\"> " + Utils.renderColName(key) + "</span>");
                dom.append("</div>");
                dom.append("<div class=\"tencol\">");
                dom.append("<input type=\"" + type + "\" data-type=" + datatype + " name=\"" + key + "\" />");
                dom.append("</div>");
                dom.append("</div>");

            }
        }
        dom.append("<input type=\"hidden\" name=\"" + entidad.getTableName() + "\" />");
        dom.append("<input type=\"submit\" value=\"Guardar\" class=\"twocol rgt btn success\"/>");
        dom.append("</fieldset>");
        dom.append("</form>");
        dom.append("</div>");
        dom.append("<br/>");
        return dom.toString();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
