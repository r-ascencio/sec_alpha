/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

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
public class VotacionIndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "Ingrese los siguientes datos: ";
        String votacion_realizada;
        votacion_realizada =
                String.valueOf(request.getSession().getAttribute("voto"));

        System.out.println("::::::::::" + votacion_realizada);
        if (votacion_realizada != null) {
            if (votacion_realizada == "true") {
                message = "El alumno " + 
                        request.getSession().getAttribute("codigo")
                        + " ya ha realizado la votacion";
                request.getSession().invalidate();
            } 
        }
        
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/templates/votacionIndex.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        String alumnoCodigo = "";
        if (request.getParameter("codigo") != null) {
            alumnoCodigo = request.getParameter("codigo");
        } else {
            response.sendRedirect(request.getContextPath() + "/login/votacion/");
        }
        List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                alumno.getTableName(), values, "WHERE codigo = "
                + alumnoCodigo);
//shame on me.
        if (alumnos.size() == 1) {
            HttpSession session = request.getSession(true);
            session.setAttribute("codigo", alumnos.get(0).get("codigo"));
            session.setAttribute("nie", alumnos.get(0).get("NIE"));
            session.setAttribute("voto", alumnos.get(0).get("voto"));
            response.sendRedirect(request.getContextPath()
                    + "/votacion/");
        } else {
            response.sendRedirect(request.getContextPath()+"/login/votacion/");
        }
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
