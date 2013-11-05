<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!-- 
<attribute name="adminTitle" required="true"%>
-->

<t:head pageTitle="Inicio de Votacion">
    <jsp:attribute name="preassets">
        <!-- foundation -->
        <link rel="stylesheet" 
              href="${baseURL}/assets/css/foundationforms/css/foundation.css" />
        <!-- /foundation -->
    </jsp:attribute>
</t:head>


<t:adminbody adminTitle="Votacion" adminDescp="${message}">
    <jsp:attribute name="sidemenu">
        <div class="twocol last"></div>
    </jsp:attribute>
    <jsp:attribute name="frm">
        <form method="POST" action="${baseURL}/votacion/eleccion/login/">
            <fieldset>
                <div class="twelvecol">
                    <div class="threecol last">
                        <span class="prefix">Carnet</span>
                    </div>
                    <div class="ninecol">
                        <input type="text" name="codigo" 
                               value="${fn:escapeXml(htmlCode)}"/>
                    </div>
                </div>
                <div class="twelvecol">
                    <div class="threecol last">
                        <span class="prefix">N.I.E.</span>
                    </div>
                    <div class="ninecol">
                        <input type="text" name="NIE" 
                               value="${fn:escapeXml(htmlCode)}"/> 
                    </div>
                </div>
                <input type="submit" 
                       value="<c:out value="Ingresar"/>" class="btn" />
            </fieldset>
        </form>     
    </jsp:attribute>
</t:adminbody>
<!-- TODO: Replace with c:out everywhere -->
<!-- Shit that i even remember why!!  -->