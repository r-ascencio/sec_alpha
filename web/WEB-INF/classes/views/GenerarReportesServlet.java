/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Alumno;
import models.Candidato;
import models.Electo;
import models.Especialidad;
import models.Pregunta;
import models.Tabla;
import utils.HelperSQL;
import utils.Utils;

/**
 *
 * @author _r
 */
public class GenerarReportesServlet extends HttpServlet {

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

        Tabla tabla = null;
        Integer colsNum = 0;
        Pattern hidden = Pattern.compile("^(hidden)[_A-Za-z0-9]+");
        Matcher hidden_matcher;
        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;
        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;
        Pattern src = Pattern.compile("([_A-Za-z]+)(_src)$");
        Matcher src_matcher;

        try {
            if (request.getAttribute("entityName") != null) {
                try {
                    Class<?> tablaClass = Class.forName("models." + request.getAttribute("entityName"));
                    if (request.getAttribute("entityName").equals("Alumno")) {

                        tabla = (Alumno) tablaClass.newInstance();
                        colsNum = tabla.getColsName().length;

                    } else if (request.getAttribute("entityName").equals("Candidato")) {

                        tabla = (Candidato) tablaClass.newInstance();
                        colsNum = tabla.getColsName().length;

                    } else if (request.getAttribute("entityName").equals("Especialidad")) {

                        tabla = (Especialidad) tablaClass.newInstance();
                        colsNum = tabla.getColsName().length;

                    } else if (request.getAttribute("entityName").equals("Pregunta")) {

                        tabla = (Pregunta) tablaClass.newInstance();
                        colsNum = tabla.getColsName().length;

                    } else if (request.getAttribute("entityName").equals("Electo")) {

                        tabla = (Electo) tablaClass.newInstance();
                        colsNum = tabla.getColsName().length;

                    } 

                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
            String relativePath = "/media/reportes/";
            String contextRealPath = request.getServletContext()
                    .getRealPath(relativePath);
            String filename = (String) request.getAttribute("entityName");

            String fullPath = contextRealPath + "/" + filename + ".pdf";

            response.setContentType("application/pdf");

            OutputStream reportePDF = new FileOutputStream(new File(fullPath));
            Document document = new Document();
            PdfWriter.getInstance(document, reportePDF);

            document.open();
            document.add(new Paragraph(filename));
            document.add(new Paragraph("\n\n"));

            PdfPTable pdfTable = new PdfPTable(colsNum);
            pdfTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.setWidthPercentage(100);
            pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfTable.getDefaultCell().setFixedHeight(70);

            for (int i = 0; i < tabla.getColsName().length; i++) {
                String columnName = tabla.getColsName()[i];
                String value = "";

                fk_matcher = fk.matcher(columnName);
                auto_matcher = auto.matcher(columnName);
                src_matcher = src.matcher(columnName);

                if (fk_matcher.matches()) {
                    value = Utils.renderColName(fk_matcher.group(1));
                } else if (auto_matcher.matches()) {
                    value = Utils.renderColName(auto_matcher.group(1));
                } else if (src_matcher.matches()) {
                    value = Utils.renderColName(src_matcher.group(1));
                } else {
                    value = Utils.renderColName(columnName);
                }
                pdfTable.addCell(value);
            }

            ArrayList<String> campos = new ArrayList<>();
            for (int i = 0; i < tabla.getColsName().length; i++) {
                String string = tabla.getColsName()[i];

                fk_matcher = fk.matcher(string);
                auto_matcher = auto.matcher(string);
                src_matcher = src.matcher(string);

                if (fk_matcher.matches()) {
                    campos.add(
                            fk_matcher.group(1));
                } else if (auto_matcher.matches()) {
                    campos.add(
                            auto_matcher.group(1));
                } else {
                    campos.add(
                            string);
                }
            }

            List<HashMap<String, Object>> filas = HelperSQL.obtenerFilas(
                    tabla.getTableName(),
                    campos,
                    "");

            for (int k = 0; k < filas.size(); k++) {
                HashMap<String, Object> hashMap = filas.get(k);
                for (int i = 0; i < tabla.getColsName().length; i++) {
                    String string = tabla.getColsName()[i];
                    fk_matcher = fk.matcher(string);
                    auto_matcher = auto.matcher(string);
                    src_matcher = src.matcher(string);

                    if (string.equals("imagen_src")) {
                        Image candidatoImage = Image.getInstance(
                                new URL(
                                "http://" + request.getServerName() + ":"
                                + request.getServerPort() + "/"
                                + String.valueOf(
                                hashMap.get(string))));
                        pdfTable.addCell(candidatoImage);
                    } else if (fk_matcher.matches()) {
                        ArrayList<String> values = new ArrayList<>();

                        values.add("nombre");

                        List<HashMap<String, Object>> vals = HelperSQL.obtenerFilas(
                                Utils.firstUpper(fk_matcher.group(1)),
                                values,
                                "");

                        pdfTable.addCell(
                                String.valueOf(vals.get(0).get("nombre"))
                                );


                    } else if (auto_matcher.matches()) {
                        pdfTable.addCell(String.valueOf(
                                hashMap.get(auto_matcher.group(1))));
                    } else {
                        pdfTable.addCell(String.valueOf(hashMap.get(string)));
                    }
                }
            }

            document.add(pdfTable);
            document.close();
            reportePDF.close();

            response.sendRedirect(request.getContextPath()
                    + relativePath + filename + ".pdf");
            response.addHeader("Content-Disposition",
                    "attachment; filename=" + filename);
            

        } catch (Exception e) {

            e.printStackTrace();
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
    }
}
