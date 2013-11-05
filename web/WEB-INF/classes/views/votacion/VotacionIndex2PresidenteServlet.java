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
public class VotacionIndex2PresidenteServlet extends HttpServlet {

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

        String message = "MM";
        String votacion_realizada;
        votacion_realizada =
                String.valueOf(request.getSession().getAttribute("voto_p"));

        System.out.println("\n\n..........ELECCIONES.............\n\n");

        if (votacion_realizada != null) {
            if (votacion_realizada.equals("true")
                    || votacion_realizada.equals("1")) {
                if (request.getSession().getAttribute("NIE") != null) {
                    message = "El alumno "
                            + request.getSession().getAttribute("NIE")
                            + " ya ha realizado la votacion";
                }
            }
        }

        request.setAttribute("message", message);

        // not a valid student.
        request.getRequestDispatcher("/WEB-INF/templates/votacion2PresidenteIndex.jsp")
                .forward(request, response);
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

        /*  SELECT to Alumno */
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        String alumnoCodigo = "";

        // this is from the form?
        if (request.getParameter("codigo") != null) {
            System.out.println("\n\n..........ELECCIONES.............\n\n");
            alumnoCodigo = request.getParameter("codigo");
        } else {
            // go to login if you don't exists.
            response.sendRedirect(request.getContextPath()
                    + "/votacion/elecciones/login/");
        }

        // getting the "alumno"
        List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                alumno.getTableName(), values, "WHERE codigo = "
                + alumnoCodigo);
//shame on me.
        if (alumnos.size() == 1) {
            HttpSession session = request.getSession(true);
            session.setAttribute("codigo", alumnos.get(0).get("codigo"));
            session.setAttribute("NIE", alumnos.get(0).get("NIE"));
            session.setAttribute("voto_p", alumnos.get(0).get("voto_p"));
            response.sendRedirect(request.getContextPath()
                    + "/votacion/elecciones");
        } else {
            // not a valid student.
            request.getRequestDispatcher("/WEB-INF/templates/votacion2PresidenteIndex.jsp")
                    .forward(request, response);
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
