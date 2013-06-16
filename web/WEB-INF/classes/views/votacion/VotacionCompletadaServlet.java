/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.votacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Alumno;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class VotacionCompletadaServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            Alumno alumno = new Alumno();

            ArrayList<String> values = new ArrayList<>();

            values.add("a.nombre as nombre");
            values.add("a.codigo as codigo");
            values.add("a.voto as voto");
            values.add("a.voto_p as voto_p");
            values.add("a.especialidad as Aespecialidad");
            values.add("e.nombre as especialidad");
            String condicion = " a  "
                    + " INNER JOIN Especialidad e "
                    + " ON a.especialidad = e.codigo"
                    + " WHERE a.codigo = "
                    + request.getSession().getAttribute("codigo");

            List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                    alumno.getTableName(), values, condicion);

            request.setAttribute("nombre",
                    alumnos.get(0).get("nombre"));

            request.setAttribute("codigo",
                    alumnos.get(0).get("codigo"));

            request.setAttribute("especialidad",
                    alumnos.get(0).get("especialidad"));

            request.getSession().setAttribute("codigo", null);
            request.getSession().setAttribute("NIE", null);
            
            request.getSession().setAttribute("voto", 
                    alumnos.get(0).get("voto"));
            
            request.getSession().setAttribute("voto", 
                    alumnos.get(0).get("voto_p"));
            
            request.getRequestDispatcher(
                    "/WEB-INF/templates/votacionCompletada.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "No se encuentra el codigo");
            request.getRequestDispatcher(
                    "/WEB-INF/templates/votacionCompletada.jsp")
                    .forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
