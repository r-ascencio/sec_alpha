/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;
import utils.HelperSQL;
import utils.Utils;

/**
 *
 * @author _r
 */
public class AdminLogServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String message = "Ingrese su usuario y codigo de seguridad";

        request.setAttribute("message", message);

        request.getRequestDispatcher("/WEB-INF/templates/adminLogin.jsp")
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
        try {
            Usuario usuario = new Usuario();
            String userName = (String) request.getParameter("userName");
            String userPass = (String) request.getParameter("userPass");
            ArrayList<String> values = new ArrayList<>();
            values.add("*");
            if (userName != null
                    && userPass != null) {

                String encriptedPass = Utils.encodeToSHA512(userPass);

                List<HashMap<String, Object>> selectUser = HelperSQL
                        .obtenerFilas(usuario.getTableName(), values,
                        "WHERE nombre = \'" + userName + "\'");

                if (selectUser.size() == 1) {
                    if (encriptedPass.toLowerCase(Locale.ENGLISH).equals(selectUser.get(0).get("pass"))) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("userName", userName);
                        response.sendRedirect(request.getContextPath()
                                + "/admin/");
                    } else {

                        System.out.println(selectUser.get(0).get("pass"));
                        System.out.println(encriptedPass);

                        request.setAttribute("message",
                                "<br/><br/>"
                                + "<span class=\" alert critical\">"
                                + "nombre de usuario o contrase&nacute;a erroneos."
                                + "</span> "
                                + "<br/><br/>");

                        request.getRequestDispatcher("/WEB-INF/templates/adminLogin.jsp")
                                .forward(request, response);
                    }
                } else {
                    //ANOTHER STUPID SHIT 

                    request.setAttribute("message",
                            "<br/><br/>"
                            + "<span class=\" alert critical\">"
                            + "nombre de usuario o contrase&nacute;a erroneos."
                            + "</span> "
                            + "<br/><br/>");

                    request.getRequestDispatcher("/WEB-INF/templates/adminLogin.jsp")
                            .forward(request, response);

                }
            } else {

                request.setAttribute("message",
                        "<br/><br/>"
                        + "<span class=\" alert critical\">"
                        + "nombre de usuario o contrase&nacute;a erroneos."
                        + "</span> "
                        + "<br/><br/>");

                request.getRequestDispatcher("/WEB-INF/templates/adminLogin.jsp")
                        .forward(request, response);
            }
        } catch (Exception e) {

            request.setAttribute("message",
                    "<br/><br/>"
                    + "<span class=\" alert critical\">"
                    + "Ha ocurrido un error en la conexion."
                    + "</span> "
                    + "<br/><br/>");

            request.getRequestDispatcher("/WEB-INF/templates/adminLogin.jsp")
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
