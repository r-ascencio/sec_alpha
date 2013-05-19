/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

/**
 *
 * @author _r
 */
public class UploadExcelAlumnoServlet extends HttpServlet {

    private File xlsxFile;
    private String xlsxContentType =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private boolean isMultipart;
    private String uploadPath;

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
        // verificar si es subida
        isMultipart = ServletFileUpload.isMultipartContent(request);
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.print("No es un archivo");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Guardar en temporar si es demasiado pesado.
        factory.setRepository(new File("/tmp/"));
        // org.apache.fileupload gestiona la subida.
        ServletFileUpload upload = new ServletFileUpload(factory);

        response.setContentType("application/json");
        JSONObject respuesta = new JSONObject();
        try {

            List fileItems = upload.parseRequest(request);

            // procesar a subida
            Iterator i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // obtener parametro individuales.
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // descarga en el servidor

                    if (contentType.equals(xlsxContentType)) {

                        if (fileName.lastIndexOf("\\") >= 0) {
                            xlsxFile = new File(uploadPath
                                    + fileName.substring(fileName.lastIndexOf("\\")));
                        } else {
                            xlsxFile = new File(uploadPath
                                    + fileName.substring(fileName.lastIndexOf("\\") + 1));
                        }
                        fi.write(xlsxFile);
                        ///String userPath = System.getenv("PLACE_TO_UPLOAD_MOTHEFUCKA");
                        String relativePath = "/assets/uploads/excel/";
                        String userPath =
                                request.getServletContext()
                                .getRealPath(relativePath) + "/" + xlsxFile.getName();
                        String excelFilePath = request.getServletContext().getContextPath()
                                + "/" + relativePath + xlsxFile.getName();
                        if (xlsxFile.renameTo(new File(userPath))) {
                            respuesta.put("excelFile", excelFilePath);
                        }
                    }
                    out.println(respuesta);

                } else {
                    respuesta.put("ERROR", "El archivo debe ser una hoja de calculo"
                            + "compatible con excel 2007+");
                    out.println(respuesta);
                }
                
                out.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex);
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
