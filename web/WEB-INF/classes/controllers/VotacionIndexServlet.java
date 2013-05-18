/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        // NO FUCKING CACHE (1.1)
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // NO FUCKING CACHE (1.0)
        response.setHeader("Pragma", "no-cache");
        request.setAttribute("logged", false);
        request.getRequestDispatcher("/WEB-INF/templates/votacionIndex.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // NO FUCKING CACHE (1.1)
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // NO FUCKING CACHE (1.0)
        response.setHeader("Pragma", "no-cache");
        Alumno alumno = new Alumno();
        ArrayList<String> values = new ArrayList<>();
        values.add("*");
        String alumnoCodigo = "";
        if (request.getParameter("codigo") != null) {
            alumnoCodigo = request.getParameter("codigo");
        } else {
            alumnoCodigo = "0";
        }
        List<HashMap<String, Object>> alumnos = HelperSQL.obtenerFilas(
                alumno.getTableName(), values, "WHERE codigo = "
                + alumnoCodigo);
//shame on me.
        if (alumnos.size() == 1) {
            //shame on me...
            Boolean puedoIr = false;
            System.out.println("SEND ATTRIB: " + request.getAttribute("fromVIndex"));
            if (request.getAttribute("fromVIndex") == null) {
                request.setAttribute("fromVIndex", true);
                puedoIr = true;
            } else {
                if (request.getAttribute("fromVIndex") == true) {
                    puedoIr = true;
                }
            }
            if (puedoIr) {
                System.out.println("\n FOOOOOOOOOOOO \n");
                request.setAttribute("logged", true);
                getServletContext()
                        .getNamedDispatcher("ReturnQuestionsServlet")
                        .forward(request, response);
            }
        } else {
            if (request.getAttribute("fromVIndex") != null) {
                if (request.getAttribute("codigo") == true) {
                    System.out.println(request.getAttribute("fromVIndex") + " 2 :::::::::::::::::::::::::::.");
                    getServletContext()
                            .getNamedDispatcher("ReturnQuestionsServlet")
                            .forward(request, response);
                }
            }
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
