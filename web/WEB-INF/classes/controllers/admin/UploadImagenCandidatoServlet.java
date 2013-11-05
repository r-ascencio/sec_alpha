/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

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
    private int maxFileSize = 600 * 1024;
    private int maxMemSize = 400 * 1024;
    private File file;

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        // verificar si es subida
        isMultipart = ServletFileUpload.isMultipartContent(request);
        String relativePath = "/media/candidatos/";
        String filePath = request.getServletContext()
                .getRealPath("/media/candidatos");
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.print("No es un archivo");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // tamaño maximo de la foto

        factory.setSizeThreshold(maxMemSize);
        // Guardar en temporar si es mas pesado.

        factory.setRepository(
                new File("/tmp/"));

        // org.apache.fileupload gestiona la subida.
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximo peso ah subir.

        upload.setSizeMax(maxFileSize);

        response.setContentType(
                "application/json");
        JSONObject json_response = new JSONObject();


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
                        file = new File(filePath + "/"
                                + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + "/"
                                + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    ///String userPath = System.getenv("PLACE_TO_UPLOAD_MOTHEFUCKA");
                    String userPath = file.getAbsolutePath();
                    String imgPath = request.getContextPath() + relativePath + file.getName();
                    if (file.renameTo(new File(userPath))) {
                        System.out.println("\n::::::::::::::::::::::::::::::::::::::::::\n");
                        System.out.println("\n\n" + file.getPath() + "\n\n");
                        System.out.println("\n::::: USER PATH: " + userPath + "\n:::::");
                        json_response.put("imagen_src", imgPath);
                        System.out.println("\n::::: IMG PATH: " + imgPath + "\n:::::");
                        System.out.println("\n::::::::::::::::::::::::::::::::::::::::::\n");
                    }
                }
                out.println(json_response);
                out.flush();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
