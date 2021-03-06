package controllers.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Apache/commons
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

// Apache/POI
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// models
import models.Alumno;
import models.Especialidad;
import org.apache.poi.ss.usermodel.Cell;
import utils.HelperSQL;

/**
 *
 * @author _r
 */
public class UploadExcelAlumnoServlet extends HttpServlet {

    private File xlsxFile;
    private String xlsxContentType =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private boolean isMultipart;
    private String uploadPath = "";

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
        Especialidad modeloEspecialidad = new Especialidad();
        Alumno modeloAlumno = new Alumno();

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
                     
                        

                        String httpPath = xlsxFile.getName();

                        System.out.println("httpPath: " + httpPath);

                        String excelFilePath = request.getServletContext().getContextPath() + xlsxFile.getName();

                        System.out.println("excelPath: " + excelFilePath);

                        if (xlsxFile.renameTo(new File(httpPath))) {

                            try {
                                // archivo excel 2007+
                                FileInputStream excelFile = new FileInputStream(
                                        httpPath);
                                XSSFWorkbook wb = new XSSFWorkbook(excelFile);
                                Sheet sheet = wb.getSheet("Alumnos");

                                //numero de filas.
                                int nRows = sheet.getLastRowNum();

                                // Se concatenara al nombre de la Especialidad.
                                HashMap<String, String> romans = new HashMap<>();
                                romans.put("1", "I");
                                romans.put("2", "II");
                                romans.put("3", "III");
                                romans.put("4", "IV");
                                romans.put("5", "V");

                                ArrayList<HashMap<String, String>> rowsExcel = new ArrayList<>();
                                ArrayList<String> nombreEspecialidades = new ArrayList<>();

                                for (int x = 0; x < nRows; x++) {
                                    //Get the row
                                    Row row = sheet.getRow(x);

                                    HashMap<String, String> tmp = new HashMap<>();
                                    // this.
                                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                                    tmp.put("CARNET", row.getCell(1).getStringCellValue());
                                    
                                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                                    tmp.put("NIE", row.getCell(2).getStringCellValue());

                                    String nombre = row.getCell(3).getStringCellValue().trim()
                                            .concat(" " + row.getCell(4).getStringCellValue().trim()
                                            .concat(" " + row.getCell(5).getStringCellValue().trim())
                                            .concat(" " + row.getCell(6).getStringCellValue().trim()));

                                    tmp.put("nombre", nombre);

                                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                                    String especialidadGT = romans.get(
                                            row.getCell(9).getStringCellValue());

                                    String especialidad = row.getCell(8).getStringCellValue()
                                            .concat(" " + especialidadGT);

                                    tmp.put("nombreEspecialidad", especialidad);
                                    // Grupo tecnico;

                                    nombreEspecialidades.add(especialidad);

                                    // Grupo tecnico;

                                    rowsExcel.add(tmp);
                                }



                                ArrayList<String> especialidades = new ArrayList<>();
                                for (int z = 0; z < nombreEspecialidades.size(); z++) {
                                    String especialidad = nombreEspecialidades.get(z);

                                    //shame on me?
                                    if (z == 0) {
                                        especialidades.add(especialidad);
                                    }

                                    Integer k = 0;

                                    for (int j = 0; j < especialidades.size(); j++) {
                                        String tmpEspecialidad = especialidades.get(j);
                                        if (!tmpEspecialidad.equals(especialidad)) {
                                            k++;
                                        }
                                    }

                                    if (k == especialidades.size()) {
                                        especialidades.add(especialidad);
                                    }

                                }


                                for (int m = 0; m < especialidades.size(); m++) {
                                    ArrayList<String> values = new ArrayList<>();
                                    String nombreEspecialidad = especialidades.get(m);
                                    values.add(nombreEspecialidad);
                                    // Shame on me?
                                    values.add("0");
                                    values.add("3");
                                    try {
                                        HelperSQL.insertarFila(
                                                modeloEspecialidad.getTableName(),
                                                modeloEspecialidad.getColsName(),
                                                values);
                                    } catch (Exception e) {
                                        System.out.println("\n\n\t\tERROR : : :: : : : : :  ::  : :: :: ::: \n\n");
                                        System.out.println("\n\n\t\t" + e.getMessage() + "\t\t\n\n");
                                    }
                                }


                                for (int l = 0; l < rowsExcel.size(); l++) {
                                    ArrayList<String> values = new ArrayList<>();
                                    HashMap<String, String> tmpAlumno = rowsExcel.get(l);
                                    //codigo, NIE, nombre, nombreEspecialidad.
                                    values.add(tmpAlumno.get("CARNET"));
                                    values.add(tmpAlumno.get("NIE"));
                                    values.add(tmpAlumno.get("nombre"));
                                    try {
                                        ArrayList<String> selectEspecialidad = new ArrayList<>();
                                        selectEspecialidad.add("codigo");
                                        System.out.println("\n ::: SELECT ::: \n");
                                        List<HashMap<String, Object>> tmpCodigoEspecialidad =
                                                HelperSQL.obtenerFilas(
                                                modeloEspecialidad.getTableName(),
                                                selectEspecialidad,
                                                "WHERE nombre = \'"
                                                .concat(
                                                tmpAlumno.get("nombreEspecialidad")
                                                .concat("\'")));
                                        //shame on me??
                                        HelperSQL.desconectar();
                                        
                                        if (tmpCodigoEspecialidad.size() > 0) {
                                        values.add(tmpCodigoEspecialidad
                                                .get(0).get("codigo").toString());
                                        tmpCodigoEspecialidad = null;
                                        System.out.println("\n ::: SELECT ::: \n");

                                        System.out.println("\n ::: INSERT ::: \n");
                                        HelperSQL.insertarFila(
                                                modeloAlumno.getTableName(),
                                                modeloAlumno.getColsName(),
                                                values);
                                        //shame on me??
                                        HelperSQL.desconectar();
                                        System.out.println("\n ::: INSERT ::: \n");
                                        } else {
                                        System.out.println("\n ::: "+ tmpAlumno.get("nombreEspecialidad")  +" ::: \n");
                                        }

                                    } catch (Exception e) {
                                        System.out.println("\n::::::::::\n");
                                        e.printStackTrace();
                                        System.out.println("\n::::::::::\n");
                                        System.out.println("\nError: Actualizacion tabla.");
                                        System.out.println("\n::::::::::\n");
                                    }
                                    finally {
                                        HelperSQL.desconectar();
                                    }
                                }

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


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
