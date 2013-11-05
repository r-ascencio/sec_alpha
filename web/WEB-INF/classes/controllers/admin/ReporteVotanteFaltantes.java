/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author _r
 */
public class ReporteVotanteFaltantes extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        ServletOutputStream out = response.getOutputStream();

        try {

            String base = "sec";
            String usuario = "adminHM3bwaA";
            String password = "7p2G2iTXxRpc";
            String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
            String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + base;

            Class.forName(
                    "com.mysql.jdbc.Driver");

            if (System.getenv(
                    "OPENSHIFT_MYSQL_DB_HOST") == null
                    && System.getenv("OPENSHIFT_MYSQL_DB_PORT") == null) {

                usuario = "claroline_usr";
                password = "ucsp2009";
                host = "127.0.0.1";
                port = "3306";
                base = "sec";

                url = "jdbc:mysql://" + host + ":" + port + "/" + base + "";
            }
            Connection cn = DriverManager.getConnection(url, usuario, password);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(
                    getServletContext().getRealPath("/WEB-INF/reports/AlumnosRestantesVotoPresidente.jasper"));
            Map parametros = new HashMap();

            parametros.put(
                    "baseURL",
                    "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/");

            //parametros.put("ot_entidad_id", request.getSession().getAttribute("ot_entidad_id"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);
            JRExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
