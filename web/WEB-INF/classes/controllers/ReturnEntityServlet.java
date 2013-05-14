package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReturnEntityServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4174315124441136689L;

    public ReturnEntityServlet() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        models.Tabla tabla = new models.Tabla();

        String entityName = (String) request.getAttribute("entityName");

        tabla.setTableName(entityName);

        System.out.println("This is my json mothefucker");
        out.print(
                tabla.filasJSON(entityName));
        out.flush();
    }
}
