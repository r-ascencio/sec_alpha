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

                request.getSession().setAttribute("codigo", null);
                request.getSession().setAttribute("NIE", null);
                request.getSession().setAttribute("voto", 1);

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

        try {

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

                String message = "";

                request.getSession().setAttribute("codigo", null);
                request.getSession().setAttribute("NIE", null);

                if (alumnos.get(0).get("NIE") != null
                        && alumnos.get(0).get("codigo") != null) {
                    message = "El alumno : ".concat(
                            (String) alumnos.get(0).get("nombre"))
                            .concat(" ya ha realizado la votacion");

                    request.getSession().setAttribute("codigo", null);
                    request.getSession().setAttribute("NIE", null);


                } else {
                    message = "Los datos ingresados son incorrectos";
                }

                request.setAttribute("message", message);

                request.getRequestDispatcher("/WEB-INF/templates/votacionIndex.jsp")
                        .forward(request, response);

            }
        } catch (IOException | ServletException e) {
            // TODO: Fix this.
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IndexOutOfBoundsException e) {
            // TODO: Fix this
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
    }
}
