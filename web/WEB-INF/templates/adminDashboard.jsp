<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="Administracion ${entityName}">
        
    <jsp:attribute name="assets">
    </jsp:attribute>

</t:head>

<t:adminbody adminTitle="Administracion" adminDescp="Informacion basica del sistema">
    <jsp:attribute name="sidemenu">
        <t:adminsidemenu>
        </t:adminsidemenu>
    </jsp:attribute>

    <jsp:attribute name="body">
        <!-- here is supposed to be a fucking method to generate the
         fucking forms  -->
        <h2> <a href="${baseURL}/admin/Usuario">Usuarios</a></h2>
        <h2> <a href="${baseURL}/admin/Usuario/nuevo">Nuevo usuario</a></h2>
        <h2> 
            <a href="${baseURL}/admin/reportes/Candidato"> 
                Reporte de candidatos 
            </a>
        </h2>
        <h2> 
            <a href="${baseURL}/admin/reportes/Alumno"> 
                Reporte de alumnos
            </a>
        </h2>
        <h2> 
            <a href="${baseURL}/admin/reportes/Pregunta"> 
                Reporte de preguntas
            </a>
        </h2>

    </jsp:attribute>
</t:adminbody>
<t:footer></t:footer>