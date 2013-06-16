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

        //SELECT a.nombre, c.imagen_src, a.codigo  FROM Electo e  
        //INNER JOIN Candidato c ON e.alumno =  c.alumno 
        //JOIN Alumno a ON a.codigo = e.alumno LIMIT 1 \G

        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        models.Tabla tabla = new models.Tabla();
        tabla.setTableName("Electo");
        ArrayList<String> values = new ArrayList<>();
        // shame on me :(
        values.add("a.nombre as nombre");
        values.add("c.imagen_src as imagen_src");
        values.add("e.alumno as alumno");
        String condicion = " as e  "
                + " INNER JOIN Candidato as c "
                + " ON e.alumno =  c.alumno "
                + " JOIN Alumno as a"
                + " ON a.codigo = e.alumno ";


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
