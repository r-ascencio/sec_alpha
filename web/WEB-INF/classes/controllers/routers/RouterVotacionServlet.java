package controllers.routers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AdminRouterServlet, es un intento por hacer las url's dinamicas.
 */
public class RouterVotacionServlet extends HttpServlet {

    public RouterVotacionServlet() {
        super();
    }
    /**
     * Start and Finish a session based on credentials.
     */
    private Pattern VOTACION;
    private Pattern VOTACION_SUCCESS;
    private Pattern VOTACION_LOGOUT;
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

        VOTACION = Pattern.compile("/votacion(/?)$");
        VOTACION_LOGOUT = Pattern.compile("/votacion/salir(/?)$");
        VOTACION_SUCCESS = Pattern.compile("/votacion/completada(/?)$");

        String pathInfo = request.getRequestURI().substring(request.getContextPath().length());

        System.out.println("...::" + pathInfo + "::..");

        // VOTACION INDEX:
        matcher = VOTACION.matcher(pathInfo);

        if (matcher.matches()) {

            getServletContext()
                    .getNamedDispatcher("VotacionIndexServlet")
                    .forward(request, response);

            return;
        }

        // All Url logic:

        // READ ENTINTY
        matcher = VOTACION_LOGOUT.matcher(pathInfo);
        if (matcher.matches()) {

            getServletContext()
                    .getNamedDispatcher("VotacionLogOutServlet")
                    .forward(request, response);

            return;

        }
        // /READ ENTINTY

        matcher = VOTACION_SUCCESS.matcher(pathInfo);
        if (matcher.matches()) {
            getServletContext()
                    .getNamedDispatcher("VotacionCompletadaServlet")
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
