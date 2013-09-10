package views.admin;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Alumno;
import models.Candidato;
import models.Especialidad;
import models.Presidente;
import models.Votantes;
import org.json.JSONArray;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class AdminDashboardServlet extends HttpServlet {

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
        
        request.setAttribute("dataCandidatos", dataCandidatos());
        request.setAttribute("dataVotacion", dataVotacion());
        
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

    
    private JSONArray dataVotacion() {

        /*
         * SELECT e.nombre, COUNT(*),
         * SUM( CASE WHEN voto != 0 THEN 1 ELSE 0 END ) 
         * FROM Especialidad as e 
         * INNER JOIN Alumno a ON a.especialidad = e.codigo 
         * GROUP BY especialidad;   */
        
        Especialidad especialidad = new Especialidad();
        Votantes alumno = new Votantes();

        ArrayList<String> values = new ArrayList<>();

        // shame on me :(

        values.add("e.nombre as nombre");
        // cantidad de alumnos
        values.add("COUNT(*) as alumnos");
        // cantidad de votacion realizadas
        values.add("SUM( CASE WHEN voto_realizado != 0 THEN 1 ELSE 0 END )"
                + "as votaciones_realizadas");

        String condicion = " as e  "
                + " INNER JOIN " + alumno.getTableName() + " as a "
                + " ON a.especialidad = e.codigo "
                + " GROUP BY a.especialidad ";

        return HelperSQL.getJSON(
                especialidad.getTableName(), values, condicion);
        
    }

    /**
     * This method just return the points of each candidate as JSONArray for use
     * with KendoUI Charts. [{ category: "Nombre Candidato", value:
     * puntaje_candidato } ...]
     * 
     * @return a JSONArray with candidates score.
     */
    
    private JSONArray dataCandidatos() {

        // SELECT a.nombre, c.puntaje FROM Alumno a 
        // JOIN Candidato c ON c.alumno = a.codigo;

        Presidente candidato = new Presidente();
        Alumno alumno = new Alumno();

        ArrayList<String> values = new ArrayList<>();

        // shame on me :(

        values.add("a.nombre as nombre");
        values.add("c.puntaje as puntaje");

        String condicion = " AS a  "
                + " JOIN " + candidato.getTableName() + " AS c "
                + " ON c.alumno = a.codigo";

        return HelperSQL.getJSON(
                alumno.getTableName(), values, condicion);
    }
}
