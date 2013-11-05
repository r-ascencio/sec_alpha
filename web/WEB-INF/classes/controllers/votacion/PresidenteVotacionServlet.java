/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.votacion;

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
public class PresidenteVotacionServlet extends HttpServlet {

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
        String alumnoCodigo = "";

        // this is from the form?
        if (null == request.getSession().getAttribute("codigo")) {
            // go to login if you don't exists.
            response.sendRedirect(request.getContextPath()
                    + "/votacion/presidente/login/");
        } else {
            alumnoCodigo = String.valueOf(request.getSession().getAttribute("codigo"));
            // getting the "alumno"


            /*  SELECT to Alumno */
            Alumno alumno = new Alumno();
            ArrayList<String> values = new ArrayList<>();
            values.add("*");
            List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                    alumno.getTableName(), values, "WHERE codigo = "
                    + alumnoCodigo);


            if (alumnos.size() > 0
                    || alumnos.get(0).get("voto_p").equals("false")
                    || alumnos.get(0).get("voto_p") == 0) {

                request.getRequestDispatcher("/WEB-INF/templates/votacionPresidente.jsp")
                        .forward(request, response);

            }
        }

        return;
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
