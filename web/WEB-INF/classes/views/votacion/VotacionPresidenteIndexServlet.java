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
import models.Votantes;
import models.Alumno;
import utils.HelperSQL;

/**
 * Voto por los alumnos seleccionados para correr por la presidencia.
 *
 * @author _r
 */
public class VotacionPresidenteIndexServlet extends HttpServlet {

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

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        String message = "Ingrese los siguientes datos para proceder a la votacion por presidente";
        String voto_realizado;
        voto_realizado =
                String.valueOf(
                request.getSession().getAttribute("voto_realizado"));

        if (voto_realizado != null) {
            if (voto_realizado.equals("true")
                    || voto_realizado.equals("1")) {
                message = "El alumno "
                        + request.getSession().getAttribute("NIE")
                        + " ya ha realizado la votacion";
            }
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher(
                "/WEB-INF/templates/votacionPresidentesIndex.jsp")
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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        /*  SELECT to Alumno */
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        String alumnoCodigo = "";

        // this is from the form?
        if (request.getParameter("codigo") != null) {
            alumnoCodigo = request.getParameter("codigo");
        } else {
            // go to login if you don't exists.

            request.setAttribute("message",
                    "<br/><br/>"
                    + "<span class=\" alert critical\">"
                    + "nombre de usuario o contrase&nacute;a erroneos."
                    + "</span> "
                    + "<br/><br/>");
            request.getRequestDispatcher(
                    "/WEB-INF/templates/votacionPresidentesIndex.jsp")
                    .forward(request, response);
        }

        // getting the "alumno"
        List<HashMap<String, Object>> votantes = HelperSQL.obtenerFilas(
                alumno.getTableName(), values,
                " as alumno INNER JOIN Votantes as votante ON "
                + " votante.alumno = alumno.codigo WHERE votante.alumno = "
                + alumnoCodigo + " LIMIT 1");
//shame on me.
        if (votantes.size() > 0
                && (votantes.get(0).get("voto_realizado").equals("false")
                || votantes.get(0).get("voto_realizado") == 0
                || votantes.get(0).get("voto_realizado") == false)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("codigo", votantes.get(0).get("codigo"));
            session.setAttribute("NIE", votantes.get(0).get("NIE"));
            session.setAttribute("voto_realizado", votantes.get(0).get("voto_realizado"));
            session.setAttribute("alumno_imagen", votantes.get(0).get("alumno_imagen"));
            System.out.println(votantes.get(0).get("codigo") + "\n::::\n");
            response.sendRedirect(request.getContextPath()
                    + "/votacion/presidentes/");
        } else {
            // not a valid student.
                        request.setAttribute("message",
                    "<br/><br/>"
                    + "<span class=\" alert critical\">"
                    + "nombre de usuario o contrase&nacute;a erroneos."
                    + "</span> "
                    + "<br/><br/>");
            request.getRequestDispatcher(
                    "/WEB-INF/templates/votacionPresidentesIndex.jsp")
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
