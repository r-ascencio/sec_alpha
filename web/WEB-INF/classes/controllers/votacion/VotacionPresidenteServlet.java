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
import javax.servlet.http.HttpSession;
import models.Alumno;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class VotacionPresidenteServlet extends HttpServlet {

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

        String alumnoCodigo = null;
        request.setAttribute("entityName", "Nuevo usuario");
        request.setAttribute("adminDesc", "Añada un nuevo usuario");
        request.setAttribute("eleccionPresidente", "not null");
        
        
        /*  SELECT to Alumno */ 
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        
        
        HttpSession session = request.getSession();
        // this is from the form?
        if (session.getAttribute("codigo") != null) {
            alumnoCodigo = String.valueOf(session.getAttribute("codigo"));
            
            
        // getting the "alumno"
        List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                alumno.getTableName(), values, "WHERE codigo = "
                + alumnoCodigo);
        
        
        if (alumnos.size() == 1
                || alumnos.get(0).get("voto_p").equals("false")
                || alumnos.get(0).get("voto_p") == 0
                || alumnos.get(0).get("voto_p") == false) {
            
            System.out.println(alumnos.get(0).get("voto_p"));
            
            request.getRequestDispatcher("/WEB-INF/templates/votacionPresidentes.jsp")
                .forward(request, response);
            
        }
            
        } else {
            // go to login if you don't exists.
            response.sendRedirect(request.getContextPath()
                    + "/votacion/presidentes/login/");
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
