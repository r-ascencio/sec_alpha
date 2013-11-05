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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int cantidadCandidatos = 3;
        try {
        if (null != request.getSession().getAttribute("codigo")) {
            Pregunta pregunta = new Pregunta();
            Candidato candidato = new Candidato();
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
                    HelperSQL.obtenerFilas(pregunta.getTableName(), 
                        values, 
                        " WHERE categoria <> (SELECT codigo FROM Categoria WHERE nombre = \'elecciones\')"
                        + "ORDER BY categoria");
            
            values.clear();
            values.add("*");
            List<HashMap<String, Object>> especialidad = HelperSQL.obtenerFilas(
                    "Especialidad", 
                    values, 
                    "WHERE codigo ="+candidatos.get(0).get("especialidadAlumno")
                    );

            
            System.out.println(
                    "\n-|::::::::::::::::|-\n" + 
                    especialidad.get(0).get("numero_candidatos")
                    + "\n-|::::::::::::::::|-\n"
                    );
            
            if (null != Integer.valueOf(especialidad.get(0).get("numero_candidatos").toString())) {
                cantidadCandidatos = Integer.valueOf(especialidad.get(0).get("numero_candidatos").toString());
            }
            if (candidatos.size() != cantidadCandidatos) { 
                request.setAttribute("cantidad", true);
                request.setAttribute("message",
                        "No existe la candidad necesaria de candidatos.\n"
                        + " Cantidad Actual Candidatos: " + cantidadCandidatos
                        );
            }

            request.setAttribute("maxCandidatos", 
                    cantidadCandidatos);
            
            request.setAttribute("candidatos",
                    candidatos);

            request.setAttribute("preguntas",
                    preguntasFilas);


            request.getRequestDispatcher("/WEB-INF/templates/votacion.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath()+"/votacion/login");
        }
        } catch (Exception excep) {
            excep.printStackTrace();
            response.sendRedirect(request.getContextPath()+"/votacion/login");
        }
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\nVotacion ESTO es VOTACION\n");
        HelperSQL.callProc("setearPuntaje");
        // START PREGUNTA
        Pregunta pregunta = new Pregunta();
        Candidato candidato = new Candidato();
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
                + " ON e.codigo = a.especialidad"
                + " WHERE a.especialidad = "
                + "(SELECT especialidad FROM Alumno WHERE codigo  = "
                + request.getSession().getAttribute("codigo").toString() + ")";

        List<HashMap<String, Object>> candidatos = HelperSQL.obtenerFilas(
                candidato.getTableName(), values, condicion);
        
        values.clear();
        values.add("codigo as codigo");

        List<HashMap<String, Object>> preguntas = HelperSQL.obtenerFilas(
                pregunta.getTableName(), values, "ORDER BY categoria");

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
                                HelperSQL.actualizarFilaSinCuidado(
                                        "Puntaje", 
                                        "puntaje", 
                                        "puntaje + " + paramValues[k], 
                                        "WHERE alumno = " +
                                        codigoCandidato + " AND categoria = "
                                        + "(SELECT categoria FROM Pregunta WHERE codigo = "+cp_matcher.group(1).substring(0, 1)+")");
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


            HelperSQL.actualizarFila(alumno.getTableName(), "voto",
                    1, "codigo", request.getSession().getAttribute("codigo"));
            request.getSession().setAttribute("voto", 1);
            
            request.setAttribute("action", "/votacion/login");
            
            response.sendRedirect(request.getContextPath()
                    + "/votacion/completada/");

        } else {

            response.sendRedirect(request.getContextPath()
                    + "/votacion/login/");

        }
    }
}
