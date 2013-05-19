/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

// Import required java libraries
import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

public class UploadImagenCandidatoServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        // verificar si es subida
        isMultipart = ServletFileUpload.isMultipartContent(request);
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.print("No es un archivo");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // tamaño maximo de la foto
        // TODO: extender.
        factory.setSizeThreshold(maxMemSize);
        // Guardar en temporar si es mas pesado.
        factory.setRepository(new File("/tmp/"));

        // org.apache.fileupload gestiona la subida.
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximo peso ah subir.
        upload.setSizeMax(maxFileSize);
        response.setContentType("application/json");
        JSONObject _r = new JSONObject();
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
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath
                                + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath
                                + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    ///String userPath = System.getenv("PLACE_TO_UPLOAD_MOTHEFUCKA");
                    String relativePath = "/assets/uploads/";
                    String userPath =
                            request.getServletContext()
                            .getRealPath(relativePath) + "/" + file.getName();
                    String imgPath = request.getServletContext().getContextPath()
                            + "/" + relativePath + file.getName();
                    if (file.renameTo(new File(userPath))) {
                        _r.put("imagen_src", imgPath);

                    }
                }
                out.println(_r);
                out.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}