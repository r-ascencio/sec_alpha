package controllers.routers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HelperSQL;

/**
 * AdminRouterServlet, es un intento por hacer las url's dinamicas.
 */
public class RouterAdminServlet extends HttpServlet {

    public RouterAdminServlet() {
        super();
    }
    /**
     * URL for administration table, example: /admin/Alumnos/
     */
    private Pattern ADMIN_INDEX;
    private Pattern ADMIN_ENTITY;
    private Pattern ADMIN_ENTITY_REPORT;
    private Pattern ADMIN_ENTITY_REPORT_VOTO;
    /**
     * Start and Finish a session based on credentials.
     */
    private Pattern ADMIN_LOGIN;
    private Pattern ADMIN_LOGOUT;
    /**
     * Get a table object and create, read/return, update, delete the object and
     * sync in the database, please forgive me i will put a javadoc for each
     * later.
     */
    private Pattern ADMIN_ENTITY_C;
    private Pattern ADMIN_ENTITY_U;
    private Pattern ADMIN_ENTITY_R;
    private Pattern ADMIN_ENTITY_D;
    private Pattern ADMIN_SETTINGS;
    /**
     * matcher for the urls patterns.
     */
    Matcher matcher;
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enumeration enumeration = request.getParameterNames();
        // while (enumeration.hasMoreElements()) {
        // String parameterName = (String) enumeration.nextElement();
        // System.out.println(request.getAttribute(parameterName) + " : " +
        // parameterName);
        // }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // req.getPathInfo: instead of '/sec_prev/admin/shit/' we get
        // '/admin/shit/'
        // see req.getRequestURI()

        ADMIN_INDEX = Pattern.compile("/admin(/?)$");
        ADMIN_ENTITY_REPORT = Pattern.compile("/admin/reportes/([A-Za-z]+)(/?)$");
        ADMIN_ENTITY_REPORT_VOTO = Pattern.compile("/admin/reportes/voto/([A-Za-z]+)(/?)$");
        ADMIN_ENTITY = Pattern.compile("/admin/([A-Za-z]+)(/?)$");
        ADMIN_ENTITY_C = Pattern.compile("/admin/c/([A-Za-z]+)(/?)$");
        ADMIN_ENTITY_U = Pattern.compile("/admin/u/([A-Za-z]+)(/?)$");
        ADMIN_ENTITY_R = Pattern.compile("/admin/r/([A-Za-z]+)(/?)$");
        ADMIN_ENTITY_D = Pattern.compile("/admin/d/([A-Za-z]+)(/?)$");
        ADMIN_SETTINGS = Pattern.compile("/admin/configs(/?)");
        ADMIN_LOGIN = Pattern.compile("/admin/login(/?)");
        ADMIN_LOGOUT = Pattern.compile("/admin/logout(/?)");

        String pathInfo = request.getRequestURI().substring(request.getContextPath().length());

        System.out.println("::::: " + pathInfo + " :::::");
        
        
        ArrayList<String> values = new ArrayList<>();
        values.clear();
        values.add("nombre, valor");
        List<HashMap<String, Object>> tipoVotacion = HelperSQL.obtenerFilas(
                "configuraciones",
                values, "");
        
        request.getSession().setAttribute("ConfVotacionP", tipoVotacion.get(0).get("valor"));
        request.getSession().setAttribute("ConfVotacionN", tipoVotacion.get(1).get("valor"));
        request.getSession().setAttribute("faseConsejo", tipoVotacion.get(2).get("valor"));
        request.getSession().setAttribute("dosElecciones", tipoVotacion.get(3).get("valor"));
        request.getSession().setAttribute("cincoElecciones", tipoVotacion.get(4).get("valor"));
        
        // Enumeration enumeration = request.getParameterNames();
        // while (enumeration.hasMoreElements()) {
        // String parameterName = (String) enumeration.nextElement();
        // System.out.println(request.getAttribute(parameterName) + " : " +
        // parameterName);
        // }

        matcher = ADMIN_LOGOUT.matcher(pathInfo);
        if (matcher.matches()) {
            request.
                    getServletContext()
                    .getNamedDispatcher("AdminEndSessionServlet")
                    .forward(request, response);
            return;
        }

        // ADMIN INDEX:
        matcher = ADMIN_INDEX.matcher(pathInfo);

        if (matcher.matches()) {

            getServletContext()
                    .getNamedDispatcher("AdminDashboardServlet")
                    .forward(request, response);

            return;
        }

        matcher = ADMIN_SETTINGS.matcher(pathInfo);

        if (matcher.matches()) {
            getServletContext()
                    .getNamedDispatcher("ConfiguracionServlet")
                    .forward(request, response);
        }

        // ADMIN INDEX:
        matcher = ADMIN_ENTITY_REPORT.matcher(pathInfo);

        if (matcher.matches()) {

            request.setAttribute("entityName", matcher.group(1));
            System.err.println("::::::" + matcher.group(1) + ":::::::::::");
            getServletContext()
                    .getNamedDispatcher("GenerarReportesServlet")
                    .forward(request, response);

            return;
        }

        // ADMIN INDEX:
        matcher = ADMIN_ENTITY_REPORT.matcher(pathInfo);

        if (matcher.matches()) {

            request.setAttribute("entityName", matcher.group(1));
            System.err.println("::::::" + matcher.group(1) + ":::::::::::");
            getServletContext()
                    .getNamedDispatcher("GenerarReportesServlet")
                    .forward(request, response);

            return;
        }


        // ADMIN INDEX:
        matcher = ADMIN_ENTITY_REPORT_VOTO.matcher(pathInfo);

        if (matcher.matches()) {

            request.setAttribute("entityName", matcher.group(1));
            System.err.println("::::::" + matcher.group(1) + ":::::::::::");
            getServletContext()
                    .getNamedDispatcher("GenerarReportesServlet")
                    .forward(request, response);

            return;
        }

        // ADMIN ENTITY :
        matcher = ADMIN_ENTITY.matcher(pathInfo);

        if (matcher.matches()) {

            request.setAttribute("entityName", matcher.group(1));
            request.setAttribute("userName", request
                    .getSession().getAttribute("userName"));

            getServletContext()
                    .getNamedDispatcher("EntityServlet")
                    .forward(request, response);

            return;
        }

        // All Url logic:

        // READ ENTINTY
        matcher = ADMIN_ENTITY_R.matcher(pathInfo);
        if (matcher.matches()) {
            final String entityName = matcher.group(1);

            request.setAttribute("entityName", entityName);

            getServletContext()
                    .getNamedDispatcher("ReturnEntityServlet")
                    .forward(request, response);

            return;

        }
        // /READ ENTINTY

        matcher = ADMIN_ENTITY_C.matcher(pathInfo);
        if (matcher.matches()) {

            final String entityName = matcher.group(1);
            request.setAttribute("entityName", entityName);

            request.setAttribute("action", "create");

            getServletContext()
                    .getNamedDispatcher("EntityActionServlet")
                    .forward(request, response);
            return;
        }

        matcher = ADMIN_ENTITY_U.matcher(pathInfo);
        if (matcher.matches()) {

            final String entityName = matcher.group(1);
            request.setAttribute("entityName", entityName);

            request.setAttribute("action", "update");

            getServletContext()
                    .getNamedDispatcher("EntityActionServlet")
                    .forward(request, response);
            return;
        }

        matcher = ADMIN_ENTITY_D.matcher(pathInfo);

        if (matcher.matches()) {

            final String entityName = matcher.group(1);

            request.setAttribute("entityName", entityName);
            request.setAttribute("action", "delete");
            getServletContext()
                    .getNamedDispatcher("EntityActionServlet")
                    .forward(request, response);
            return;
        }

        matcher = ADMIN_LOGIN.matcher(pathInfo);
        if (matcher.matches()) {

            getServletContext()
                    .getNamedDispatcher("AdminSessionServlet")
                    .forward(request, response);
            return;
        }


        // "********************************************"
        // "****************404*************************"
        // "********************************************"
        // why i'm here D:
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
