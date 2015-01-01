<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="iwantnav" value="true"></c:set>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<head>
    <title>
        sec - inicio
    </title>
    <c:set var="baseURL" value=""></c:set>

    <link rel="stylesheet" href="${baseURL}/assets/css/grid.css" />
    <link rel="stylesheet" href="${baseURL}/assets/css/fonts.css" />
    <link rel="stylesheet" href="${baseURL}/assets/css/entypo.css" />
    <link rel="stylesheet" href="${baseURL}/assets/css/base.css" />
</head>

<t:adminbody
    adminTitle="Sistema de elecciones estudiantiles."  
    adminDescp=""
    iwantnav="false"
    >

    <jsp:attribute name="sidemenu">
        <div class="twocol last"></div>
    </jsp:attribute>

    <jsp:attribute name="frm">
        <div class="twelvecol">
            <div class="eigthcol" style="
                 line-height: 1.5em;
                 ">
                <h3>
                    Introducción sistema administrativo 
                </h3>
                <p>
                    Bienvenido al sistema de elecciones online Consejo Central de Estudiantes, 
                    de acuerdo al año que se escoja o se agregue. Para utilizar el sistema 
                    se debe tener en cuenta que primero se debe elegir el año o agrégalo, 
                    luego hacemos todos los agregados desde alumnos, 
                    especialidades y preguntas.
                    <br/><br/>
                </p>
                <p>
                    Luego podemos pasar a la parte de cliente donde los 
                    alumnos podrán votar por los tres candidatos por grado.
                    <br/><br/>
                </p>
                <div class="twelvecol">
                    <div class="threecol"></div>
                    <div class="sixcol">
                        <a class="btn"
                           style="padding: 40px;"
                           href="${baseURL}/admin/">
                            <div class="entypo rocket"
                                 style="font-size: 40px;"></div>
                        </a>
                    </div>
                    <div class="threecol last"></div>
                </div>
                <p class="rgt">
                    <br/>
                    Att. Webmaster
                <p>
            </div>
        </div>

    </jsp:attribute>

</t:adminbody>