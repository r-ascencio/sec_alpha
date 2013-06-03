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
import javax.servlet.http.HttpSession;
import models.Alumno;
import models.Candidato;
import models.Pregunta;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class ReturnQuestionsServlet extends HttpServlet {

    private Pregunta pregunta = new Pregunta();
    private Candidato candidato = new Candidato();
    private Alumno alumno = new Alumno();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("HERE GET RETURNQUESTIONSERVLET");

        ArrayList<String> values = new ArrayList<>();
        
        // shame on me :(
        values.add("a.nombre as nombre");
        values.add("c.alumno as codigo");
        values.add("a.especialidad as especialidadAlumno");
        values.add("c.imagen_src as imagen_src");
        values.add("e.nombre as especialidad");
        String condicion = " c  "
                + " INNER JOIN Alumno a "
                + " ON a.codigo = c.alumno"
                + " JOIN Especialidad e "
                + " ON e.codigo = a.especialidad"
                + " WHERE a.especialidad = "
                + "(SELECT especialidad FROM Alumno WHERE codigo  = "
                + request.getSession().getAttribute("codigo").toString() + ")";

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
        
        request.setAttribute("displayme", "FOO");
        
        request.getRequestDispatcher("/WEB-INF/templates/votacion.jsp")
                .forward(request, response);

        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("HERE POSTRETURNQUESTIONSERVLET");
        // NO FUCKING CACHE (1.1)
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // NO FUCKING CACHE (1.0)
        response.setHeader("Pragma", "no-cache");
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
                        for (int k = 0; k < paramValues.length; k++) {
                            Integer valor = Integer.valueOf(paramValues[k]);
                            if (valor >= 1 && valor <= 4) {
                                puntajeAdquirido += valor;
                            }
                        }
                    }
                }
            }
            if (puntajeAdquirido <= maxPuntaje
                    && puntajeAdquirido > 0) {
                imok = true;
                System.out.println("CODIGO: " + codigoCandidato);
                values.add("codigo as codigo");
                HelperSQL.actualizarFila(candidato.getTableName(), "puntaje",
                        "puntaje + " + puntajeAdquirido, "alumno", codigoCandidato);
            } else {
                imok = false;
            }
        }
        if (imok == true) {
            HelperSQL.actualizarFila(alumno.getTableName(), "voto",
                    1, "codigo", request.getSession().getAttribute("codigo"));
            HttpSession session = request.getSession(true);
            session.setAttribute("voto", 1);
            response.sendRedirect(request.getContextPath()
                    + "/votacion/completada/");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/votacion/");
        }
    }
}
