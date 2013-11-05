/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.routers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class RouterVotacionServlet extends HttpServlet {

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

        // URL Patterns
        Pattern VOTACION = Pattern.compile("/votacion(/?)$");
        Pattern VOTACION_LOGIN = Pattern.compile("/votacion/login(/?)$");
        Pattern VOTACIONES = Pattern.compile("/votacion/eleccion(/?)$");
        Pattern VOTACIONES_LOGIN = Pattern.compile("/votacion/eleccion/login(/?)$");
        Pattern VOTACION_PRESIDENTE = Pattern.compile("/votacion/presidente(/?)$");
        Pattern VOTACION_ELECTOS = Pattern.compile("/votacion/presidente/electos(/?)$");
        Pattern VOTACION_ELECTOS_P = Pattern.compile("/votacion/presidentes/electos(/?)$");
        Pattern VOTACION_PRESIDENTE_LOGIN = Pattern.compile("/votacion/presidente/login(/?)$");
        Pattern VOTACION_PRESIDENTES_LOGIN = Pattern.compile("/votacion/presidentes/login(/?)$");
        Pattern VOTACION_PRESIDENTES = Pattern.compile("/votacion/presidentes(/?)$");
        Pattern VOTACION_2PRESIDENTES_LOGIN = Pattern.compile("/votacion/elecciones/login(/?)$");
        Pattern VOTACION_2PRESIDENTES = Pattern.compile("/votacion/elecciones(/?)$");
        Pattern VOTACION_ELECTOS_2P = Pattern.compile("/votacion/elecciones/electos(/?)$");
        Pattern VOTACION_COMPLETADA = Pattern.compile("/votacion/completada(/?)$");
        // /URL Patterns
        Matcher matcher;
        // url requested
        String pathInfo = request.getRequestURI().
                substring(request.getContextPath().length());

        ArrayList<String> values = new ArrayList<>();
        values.clear();
        values.add("nombre, valor");
        List<HashMap<String, Object>> tipoVotacion = HelperSQL.obtenerFilas(
                "configuraciones",
                values, "");

        request.getSession().setAttribute("ConfVotacionP", tipoVotacion.get(0).get("valor"));
        request.getSession().setAttribute("ConfVotacionN", tipoVotacion.get(1).get("valor"));
        request.getSession().setAttribute("faseConsejo", tipoVotacion.get(2).get("valor"));
        request.getSession().setAttribute("dosElecciones", tipoVotacion.get(3).get("valor"));
        request.getSession().setAttribute("cincoElecciones", tipoVotacion.get(4).get("valor"));

        // /votacion/login/

        matcher = VOTACIONES_LOGIN.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionesIndexServlet")
                    .forward(request, response);
            return;
        }
        // /votacion/
        matcher = VOTACIONES.matcher(pathInfo);
        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("ReturnEleccionesQuestionsServlet")
                    .forward(request, response);
            return;
        }

        // /votacion/
        matcher = VOTACION.matcher(pathInfo);
        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("ReturnQuestionsServlet")
                    .forward(request, response);
            return;
        }
// /votacion/login/

        matcher = VOTACION_LOGIN.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionIndexServlet")
                    .forward(request, response);
            return;
        }


        // /votacion/presidente

        matcher = VOTACION_PRESIDENTE.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("PresidenteVotacionServlet")
                    .forward(request, response);
            return;
        }

        matcher = VOTACION_PRESIDENTES.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionPresidenteServlet")
                    .forward(request, response);
            return;
        }

        // votacion/presidente/electos

        // Votacion Elecciones Presidentes 2 {{
        matcher = VOTACION_2PRESIDENTES.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("Eleccion2PresidenteServlet")
                    .forward(request, response);
            return;
        }
        // }} Votacion Elecciones Presidentes 2

        /**
         * Retorna un JSON con los datos del consejo.
         */
        matcher = VOTACION_ELECTOS.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("sourcePresidenteVotacion")
                    .forward(request, response);
            return;
        }

        /**
         * Retorna un JSON con los datos de la gente escogida para ser
         * presidente.
         */
        matcher = VOTACION_ELECTOS_P.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("SourceOnlyPresidenteVotacion")
                    .forward(request, response);
            return;
        }

        // /votacion/presidente/login


        /**
         * Retorna un JSON con los datos de la gente escogida para ser
         * presidente.
         */
        matcher = VOTACION_ELECTOS_2P.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("sourceCandidato2Votacion")
                    .forward(request, response);
            return;
        }


        matcher = VOTACION_PRESIDENTE_LOGIN.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionIndexPresidenteServlet")
                    .forward(request, response);
            return;
        }

        matcher = VOTACION_PRESIDENTES_LOGIN.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionPresidenteIndexServlet")
                    .forward(request, response);
            return;
        }

        // /votacion/completada/

        // Votacion Elecciones Presidentes 2 {{
        matcher = VOTACION_2PRESIDENTES_LOGIN.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionIndex2PresidenteServlet")
                    .forward(request, response);
            return;
        }
        // }} Votacion Elecciones Presidentes 2


        matcher = VOTACION_COMPLETADA.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionCompletadaServlet")
                    .forward(request, response);
            return;
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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
