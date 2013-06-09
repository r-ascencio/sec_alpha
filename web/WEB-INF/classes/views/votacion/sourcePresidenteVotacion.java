/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.votacion;

import utils.HelperSQL;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author _r
 */
public class sourcePresidenteVotacion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

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

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        models.Tabla tabla = new models.Tabla();
        tabla.setTableName("Candidato");
        ArrayList<String> values = new ArrayList<>();
        // shame on me :(
        values.add("a.nombre as nombre");
        values.add("c.alumno as alumno");
        values.add("c.imagen_src as imagen_src");
        values.add("e.nombre as especialidad");
        String condicion = " c  "
                + " INNER JOIN Alumno a "
                + " ON a.codigo = c.alumno"
                + " JOIN Especialidad e "
                + " ON e.codigo = a.especialidad"
                + " WHERE c.alumno";


        out.print(
                HelperSQL.getJSON(tabla.getTableName(), values, condicion));
        out.flush();
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
