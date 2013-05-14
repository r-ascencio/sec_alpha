package controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private Pattern           ADMIN_ENTITY;

    /**
     * Start and Finish a session based on credentials.
     */
    private Pattern           ADMIN_LOGIN;
    private Pattern           ADMIN_LOGOUT;

    /**
     * Get a table object and create, read/return, update, delete the object and
     * sync in the database, please forgive me i will put a javadoc for each
     * later.
     */

    private Pattern           ADMIN_ENTITY_C;
    private Pattern           ADMIN_ENTITY_U;
    private Pattern           ADMIN_ENTITY_R;
    private Pattern           ADMIN_ENTITY_D;

    /** matcher for the urls patterns. */
    Matcher                   matcher;

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Enumeration enumeration = request.getParameterNames();
        // while (enumeration.hasMoreElements()) {
        // String parameterName = (String) enumeration.nextElement();
        // System.out.println(request.getAttribute(parameterName) + " : " +
        // parameterName);
        // }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // req.getPathInfo: instead of '/sec_prev/admin/shit/' we get
        // '/admin/shit/'
        // see req.getRequestURI()

        ADMIN_ENTITY = Pattern.compile("/admin/([A-Za-z]+)(/?)");
        ADMIN_ENTITY_C = Pattern.compile("/admin/c/([A-Za-z]+)(/?)");
        ADMIN_ENTITY_U = Pattern.compile("/admin/u/([A-Za-z]+)(/?)");
        ADMIN_ENTITY_R = Pattern.compile("/admin/r/([A-Za-z]+)(/?)");
        ADMIN_ENTITY_D = Pattern.compile("/admin/d/([A-Za-z]+)(/?)");
        ADMIN_LOGIN = Pattern.compile("/admin/login(/?)");
        ADMIN_LOGOUT = Pattern.compile("/admin/logout(/?)");

        String pathInfo = request.getRequestURI().substring(request.getContextPath().length());

        // Enumeration enumeration = request.getParameterNames();
        // while (enumeration.hasMoreElements()) {
        // String parameterName = (String) enumeration.nextElement();
        // System.out.println(request.getAttribute(parameterName) + " : " +
        // parameterName);
        // }

        // ADMIN INDEX:
        matcher = ADMIN_ENTITY.matcher(pathInfo);

        if (matcher.matches()) {

            request.setAttribute("entityName", matcher.group(1));

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

            if (matcher.group(2) != null) {
                request.setAttribute("entityID", matcher.group(2));
            }

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

        matcher = ADMIN_LOGOUT.matcher(pathInfo);
        if (matcher.matches()) {

            request.
                    getServletContext()
                    .getNamedDispatcher("AdminSessionServlet")
                    .forward(request, response);
            return;
        }

        System.out.println("********************************************");
        System.out.println("****************404*************************");
        System.out.println("********************************************");
        // why i'm here D:
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        assert false;
    }
}
