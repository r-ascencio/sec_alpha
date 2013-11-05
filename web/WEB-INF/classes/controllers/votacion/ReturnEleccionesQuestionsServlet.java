package controllers.votacion;

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
import models.Alumno;
import models.Pregunta;
import models.Presidentes;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class ReturnEleccionesQuestionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
        if (null != request.getSession().getAttribute("codigo")) {
            Pregunta pregunta = new Pregunta();
            Presidentes candidato = new Presidentes();
            Alumno alumno = new Alumno();

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
                    + " ON e.codigo = a.especialidad";

            List<HashMap<String, Object>> candidatos = HelperSQL.obtenerFilas(
                    candidato.getTableName(), values, condicion);

            System.out.println("\n "+ candidatos.toString() +" \n");
            values.clear();
            values.add("codigo");
            values.add("descripcion");
            
            List<HashMap<String, Object>> preguntasFilas =
                    HelperSQL.obtenerFilas(pregunta.getTableName(), 
                        values, 
                       " WHERE Pregunta.categoria = (SELECT codigo FROM Categoria WHERE nombre = \'elecciones\')"
                       + " ORDER BY categoria"
                       + " LIMIT 5");
            
            
            System.out.println("\n "+ preguntasFilas.toString() +" \n");
            
            values.clear();

            request.setAttribute("candidatos",
                    candidatos);

            request.setAttribute("preguntas",
                    preguntasFilas);


            request.getRequestDispatcher("/WEB-INF/templates/votacion1.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()+"/votacion/eleccion/login");
        }
        } catch (Exception excep) {
            excep.printStackTrace();
            response.sendRedirect(request.getContextPath()+"/votacion/eleccion/login");
        }
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\nVotacion ESTO es VOTACIONm\n");
        
        // START PREGUNTA
        Pregunta pregunta = new Pregunta();
        Presidentes candidato = new Presidentes();
        Alumno alumno = new Alumno();
        Integer count = 0;
        String nombreParam;
        Boolean imok = false;
        Pattern codigo_pregunta = Pattern.compile("^([0-9]+_)([0-9]+)");
        Matcher cp_matcher;
        Integer maxPuntaje;
        ArrayList<String> values = new ArrayList<>();
        // shame on me :(
        values.add("alumno as codigo");

        String condicion = " c  "
                + " INNER JOIN Alumno a "
                + " ON a.codigo = c.alumno"
                + " JOIN Especialidad e "
                + " ON e.codigo = a.especialidad";
        
        List<HashMap<String, Object>> candidatos = HelperSQL.obtenerFilas(
                candidato.getTableName(), values, condicion);
        
        values.clear();
        values.add("codigo as codigo");

        List<HashMap<String, Object>> preguntas = HelperSQL.obtenerFilas(
                pregunta.getTableName(), values, 
                       " WHERE Pregunta.categoria = (SELECT codigo FROM Categoria WHERE nombre = \'elecciones\')"
                       + " ORDER BY categoria"
                       + " LIMIT 5");

        maxPuntaje = (preguntas.size() * 5);
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
                            if (valor >= 1 && valor <= 5) {
                                puntajeAdquirido += valor;
                            }
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
                System.out.println("\n");
            } else {

                imok = false;
            }

        }


        if (imok == true) {


            HelperSQL.actualizarFila(alumno.getTableName(), "votaciones",
                    1, "codigo", request.getSession().getAttribute("codigo"));
            request.getSession().setAttribute("votaciones", 1);
            request.setAttribute("action", "/votacion/eleccion/login");
            
            response.sendRedirect(request.getContextPath()
                    + "/votacion/completada/");

        } else {
             System.out.println(":: error ::");
            response.sendRedirect(request.getContextPath()
                    + "/votacion/eleccion/login/");

        }
    }
}
