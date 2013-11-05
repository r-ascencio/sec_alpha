package views.admin;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Alumno;
import models.Candidato;
import models.Electo;
import models.Especialidad;
import models.Presidente;
import models.Presidentes;
import models.Tabla;
import models.Votantes;
import org.json.JSONArray;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String votados = "Presidentes";
        String votantes = "Votantes";
        request.setAttribute("fieldPie", "puntaje");

        request.setAttribute("adminTitle", "Elección de Consejo");
        request.setAttribute("adminDescp", "Fase de elección de Consejo, "
                + "las opciones disponibles son:<ul><br/> "
                + "<li> <span class=\"alert\"> Administración de Candidatos:"
                + "<span style=\"font-size: 30px\" class=\"entypo user\"></span></span>"
                + " y <span class=\"alert\"> Preguntas: "
                + "<span class=\"entypo help\" style=\"font-size: 30px; padding: 0px;\"></span></span></li></ul>");


        if ((boolean) request.getSession().getAttribute("ConfVotacionP")) {
            votados = "Presidentes";
            votantes = "Votantes";

            request.setAttribute("adminTitle", "Votación por Presidentes");
            request.setAttribute("adminDescp", "Fase de votación por Presidente, "
                    + "las opciones disponibles son:<ul><br/> "
                    + "<li> <span class=\"alert\"> Administración de Votantes:"
                    + "<span style=\"font-size: 30px\" class=\"entypo vcard\"></span></span>"
                    + " y <span class=\"alert\"> Candidatos a Presidencia: "
                    + "<span class=\"entypo layout\" style=\"font-size: 30px; padding: 0px;\"></span></span></li></ul>");
        } else if ((boolean) request.getSession().getAttribute("ConfVotacionN")) {
            votados = "Candidato";

            if ((boolean) request.getSession().getAttribute("faseConsejo")) {
                votados = "Electos";
                request.setAttribute("fieldPie", "puntajeP");
            }

            votantes = "Alumno";
        } else if ((boolean) request.getSession().getAttribute("cincoElecciones")) {
            votados = "Presidentes";
            votantes = "Alumno";
            request.setAttribute("adminTitle", "Votación por Presidente");
            request.setAttribute("adminDescp", "Fase de votación por Presidente,  "
                    + "recuerda que solamente las <span class=\'alert\'>preguntas bajo la categoria \"elecciones\", seran utilizadas.</span>"
                    + "<br/><br/>Las opciones disponibles son:<ul><br/>"
                    + "<li> <span class=\"alert\"> Administración de Candidatos:"
                    + "<span style=\"font-size: 30px\" class=\"entypo layout\"></span></span>"
                    + " y <span class=\"alert\"> Adminsitracion de Preguntas: "
                    + "<span class=\"entypo help\" style=\"font-size: 30px; padding: 0px;\"></span></span></li></ul>");
        }

        request.setAttribute("dataCandidatos", dataCandidatos(votados,
                (boolean) request.getSession().getAttribute("faseConsejo")));

        request.setAttribute("dataVotacion", dataVotacion(votantes,
                (boolean) request.getSession().getAttribute("faseConsejo")));

        request.getRequestDispatcher("/WEB-INF/templates/adminDashboard.jsp")
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
    }// </editor-fold>

    private JSONArray dataVotacion(String tabla, Boolean phase) {

        /*
         * SELECT e.nombre, COUNT(*),
         * SUM( CASE WHEN voto != 0 THEN 1 ELSE 0 END ) 
         * FROM Especialidad as e 
         * INNER JOIN Alumno a ON a.especialidad = e.codigo 
         * GROUP BY especialidad;   */

        Especialidad especialidad = new Especialidad();
        Tabla alumno = new Votantes();
        String voto_check = "voto_realizado";

        if (tabla.equals("Alumno")
                || tabla.equals("Alumnos")) {
            alumno = new Alumno();
            voto_check = "voto";

            if (tabla.equals("Alumnos")) {
                voto_check = "votaciones";
            }

            ArrayList<String> values = new ArrayList<>();

            // shame on me :(

            values.add("e.nombre as nombre");
            // cantidad de votacion realizadas
            values.add("SUM( CASE WHEN " + voto_check + " != 0 THEN 1 ELSE 0 END ) * 100 / (SELECT COUNT(*) FROM Alumno WHERE especialidad = a.especialidad) as \" % porcentaje votación \"");

            String condicion = " as e  "
                    + " INNER JOIN " + alumno.getTableName() + " as a "
                    + " ON a.especialidad = e.codigo "
                    + " GROUP BY a.especialidad ";

            return HelperSQL.getJSON(
                    especialidad.getTableName(), values, condicion);
        }

        voto_check = "voto";

        ArrayList<String> values = new ArrayList<>();

        // shame on me :(

        // cantidad de votacion realizadas
        values.add("SUM( CASE WHEN voto_realizado != 0 THEN 1 ELSE 0 END ) * 100 / (SELECT COUNT(*) FROM Votantes) as \" % porcentaje votación \"");
        values.add("\"Proceso de Votación\"");
        String condicion = " ";

        // alumno it's supossed to be called "votantes"...
        return HelperSQL.getJSON(
                alumno.getTableName(), values, condicion);

    }

    /**
     * This method just return the points of each candidate as JSONArray for use
     * with KendoUI Charts. [{ category: "Nombre Candidato", value:
     * puntaje_candidato } ...]
     *
     * @return a JSONArray with candidates score.
     */
    private JSONArray dataCandidatos(String tabla, Boolean phase) {

        // SELECT a.nombre, c.puntaje FROM Alumno a 
        // JOIN Candidato c ON c.alumno = a.codigo;

        Tabla candidato = new Candidato();
        String puntaje = "puntaje";


        if (tabla.equals("Presidente")) {
            candidato = new Presidente();
        } else if (tabla.equals("Presidentes")) {
            candidato = new Presidentes();
        } else {
            if (phase) {
                candidato = new Electo();
                puntaje = "puntajeP"; // this was a very fucking stupid take.
            }
        }

        Alumno alumno = new Alumno();

        ArrayList<String> values = new ArrayList<>();

        // shame on me :(

        values.add("a.nombre as nombre");

        values.add("c." + puntaje);

        String condicion = " AS a  "
                + " INNER JOIN " + candidato.getTableName() + " AS c "
                + " ON c.alumno = a.codigo";

        return HelperSQL.getJSON(
                alumno.getTableName(), values, condicion);
    }
}
