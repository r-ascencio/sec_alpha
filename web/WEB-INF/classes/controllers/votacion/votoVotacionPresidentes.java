package controllers.votacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class votoVotacionPresidentes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
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

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        String codigo = (String) request.getSession().getAttribute("codigo");
        HelperSQL.actualizarFila("Votantes", "voto_realizado", 1, "alumno", codigo);


        HelperSQL.actualizarFila("Presidente", "puntaje", "puntaje + 1",
                "alumno", request.getParameter("cAlumnus"));


        /*  SELECT to Alumno */
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        String alumnoNIE = codigo;

        // getting the "alumno"
        List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                alumno.getTableName(), values, "WHERE codigo = "
                + alumnoNIE);

        System.out.println("'cause fuck u: " + alumnoNIE);

        //codigo, nombre, data-uid

        request.setAttribute("cCodigo", alumnos.get(0).get("codigo"));

        request.setAttribute("cNombre", alumnos.get(0).get("nombre"));

        String img = request.getParameter("cImg");
        request.setAttribute("cImg", img);

        String dataUID = request.getParameter("cUID");


        response.sendRedirect(request.getContextPath() + "/votacion/completada");
//
//        request.getRequestDispatcher("/WEB-INF/templates/votacionPresidenteFin.jsp")
//                .forward(request, response);
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
