/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.routers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        Pattern VOTACION_PRESIDENTE = Pattern.compile("/votacion/presidente(/?)$");
        Pattern VOTACION_ELECTOS = Pattern.compile("/votacion/presidente/electos(/?)$");
        Pattern VOTACION_PRESIDENTE_LOGIN = Pattern.compile("/votacion/presidente/login(/?)$");
        Pattern VOTACION_COMPLETADA = Pattern.compile("/votacion/completada(/?)$");
        // /URL Patterns
        Matcher matcher;
        // url requested
        String pathInfo = request.getRequestURI().
                substring(request.getContextPath().length());

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

        // votacion/presidente/electos
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

        // /votacion/presidente/login

        matcher = VOTACION_PRESIDENTE_LOGIN.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionIndexPresidenteServlet")
                    .forward(request, response);
            return;
        }

        // /votacion/completada/

        matcher = VOTACION_COMPLETADA.matcher(pathInfo);

        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("VotacionCompletadaServlet")
                    .forward(request, response);
            return;
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
