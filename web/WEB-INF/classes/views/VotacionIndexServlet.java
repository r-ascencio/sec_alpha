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
import models.Candidato;
import models.Pregunta;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class VotacionIndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("HERE GET VOTACIONINDEX");
        String message = (String) request.getSession().getAttribute("message");
        if (message == null) {
            message = "Ingrese los siguientes datos: ";
        }
        String votacion_realizada;
        votacion_realizada =
                String.valueOf(request.getSession().getAttribute("voto"));


        if (votacion_realizada != null) {
            if (votacion_realizada.equals("true")) {
                message = "El alumno "
                        + request.getSession().getAttribute("codigo")
                        + " ya ha realizado la votacion";
                request.getSession().invalidate();
            }
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/templates/votacionIndex.jsp")
                .forward(request, response);

        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("HERE poST VOTACIONINDEX");
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        String alumnoCodigo;
        String alumnoNIE;
        alumnoCodigo = request.getParameter("codigo");
        alumnoNIE = request.getParameter("NIE");

        List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                alumno.getTableName(), values, "WHERE codigo = "
                + alumnoCodigo + "  AND NIE = " + alumnoNIE);
//shame on me.

        if (alumnos.size() == 1) {

            System.out.println("\n INICIANDO SESION \n");
            HttpSession session = request.getSession(true);
            session.setAttribute("codigo", alumnos.get(0).get("codigo"));
            session.setAttribute("nombre", alumnos.get(0).get("nombre"));
            session.setAttribute("NIE", alumnos.get(0).get("NIE"));
            session.setAttribute("voto", alumnos.get(0).get("voto"));

            response.sendRedirect(request.getContextPath()
                    + "/votacion/");
            return;
        } else {
            System.out.println("\nNo EXISTE EL ALUMNO \n");
            String message;
            request.getSession().invalidate();
            if (alumnos.get(0).get("NIE") != null
                    && alumnos.get(0).get("codigo") != null) {
                message = "El alumno : ".concat(
                        (String) alumnos.get(0).get("nombre"))
                        .concat(" ya ha realizado la votacion");

            } else {
                request.setAttribute("message", "Los datos ingresados son incorrectos");
            }
            request.getRequestDispatcher("/WEB-INF/templates/votacionIndex.jsp")
                    .forward(request, response);
            return;
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
