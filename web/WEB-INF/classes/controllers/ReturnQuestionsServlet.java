package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Candidato;
import models.Pregunta;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class ReturnQuestionsServlet extends HttpServlet {

    Pregunta pregunta = new Pregunta();
    Candidato candidato = new Candidato();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // NO FUCKING CACHE (1.1)
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // NO FUCKING CACHE (1.0)
        response.setHeader("Pragma", "no-cache");
        if (request.getAttribute("fromVIndex") != null) {
            if (request.getAttribute("fromVIndex") == true) {
                request.setAttribute("fromVIndex", false);
                ArrayList<String> values = new ArrayList<>();

                // shame on me :(
                values.add("a.nombre as nombre");
                values.add("c.alumno as codigo");
                values.add("c.imagen_src as imagen_src");
                values.add("e.nombre as especialidad");
                String condicion = " c  "
                        + " INNER JOIN Alumno a "
                        + " ON a.codigo = c.alumno"
                        + " JOIN Especialidad e "
                        + " ON e.codigo = a.especialidad"
                        + " WHERE c.alumno";

                List<HashMap<String, Object>> candidatos = HelperSQL.obtenerFilas(
                        candidato.getTableName(), values, condicion);

                values.clear();
                values.add("codigo");
                values.add("descripcion");
                List<HashMap<String, Object>> preguntasFilas =
                        HelperSQL.obtenerFilas(pregunta.getTableName(), values, "");

                request.setAttribute("candidatos",
                        candidatos);

                request.setAttribute("preguntas",
                        preguntasFilas);

                System.out.println(request.getAttribute("codigo"));
                request.getRequestDispatcher("/WEB-INF/templates/votacion.jsp")
                        .forward(request, response);

            }
        } else {
            response.sendRedirect(request.getContextPath() + "/votacion/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // NO FUCKING CACHE (1.1)
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // NO FUCKING CACHE (1.0)
        response.setHeader("Pragma", "no-cache");

        System.out.println("CONTENT TYPE: " + request.getContentType());
        String nombreParam;
        Boolean imok = false;
        Pattern codigo_pregunta = Pattern.compile("^([0-9]+_)([0-9]+)");
        Matcher cp_matcher;
        Integer maxPuntaje;
        ArrayList<String> values = new ArrayList<>();
        // shame on me :(
        values.add("alumno as codigo");
        List<HashMap<String, Object>> candidatos = HelperSQL.obtenerFilas(
                candidato.getTableName(), values, "");
        values.clear();
        values.add("codigo as codigo");
        List<HashMap<String, Object>> preguntas = HelperSQL.obtenerFilas(
                pregunta.getTableName(), values, "");
        maxPuntaje = (preguntas.size() * 4);

        for (int i = 0; i < candidatos.size(); i++) {
            String codigoParam = "";
            Integer puntajeAdquirido = 0;
            HashMap<String, Object> candidatomap = candidatos.get(i);
            Enumeration nParam = request.getParameterNames();
            String codigoCandidato = (String) candidatomap.get("codigo");
            for (int j = 0; nParam.hasMoreElements(); j++) {
                nombreParam = (String) nParam.nextElement();
                cp_matcher = codigo_pregunta.matcher(nombreParam);
                if (cp_matcher.matches()) {
                    codigoParam = cp_matcher.group(2);
                    if (codigoCandidato.equals(codigoParam)) {
                        String[] paramValues = request.getParameterValues(nombreParam);
                        Integer valor = Integer.valueOf(paramValues[0]);
                        if (valor >= 1 && valor <= 4) {
                            puntajeAdquirido += valor;
                        }
                    }

                }
            }
            if (puntajeAdquirido <= maxPuntaje
                    && puntajeAdquirido > 0) {
                imok = true;
                values.add("codigo as codigo");
                HelperSQL.actualizarFila(candidato.getTableName(), "puntaje",
                        "puntaje + " + puntajeAdquirido, "alumno", codigoCandidato);
            }
        }

        if (imok == true) {
            System.out.println("FALSE HERE");
            request.setAttribute("fromVIndex", false);
            getServletContext()
                    .getNamedDispatcher("VotacionCompletadaServlet")
                    .forward(request, response);

        } else {
            System.out.println("ELSE HERE");
            System.out.println("::: " + request.getAttribute("fromVIndex") + " :::");
            if (request.getParameter("codigo") != null) {
                if (request.getAttribute("fromVIndex") == true) {
                    doGet(request, response);
                }
            }
        }
    }
}
