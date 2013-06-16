/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.votacion;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class votoVotacionPresidente extends HttpServlet {

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
        
        String NIE = (String) request.getSession().getAttribute("NIE");
        HelperSQL.actualizarFila("Alumno", "voto_p", 1, "NIE", NIE);
        
        //codigo, nombre, data-uid

        String codigo = request.getParameter("cAlumnus");
        request.setAttribute("cCodigo", codigo);

        String nombre = request.getParameter("cName");
        request.setAttribute("cNombre", nombre);

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
