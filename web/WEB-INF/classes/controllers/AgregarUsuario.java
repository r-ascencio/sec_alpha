/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
        String form;
        form = Utils.buildForm(new Usuario(), "./nuevo");

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
