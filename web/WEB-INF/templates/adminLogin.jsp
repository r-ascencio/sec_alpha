<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:head pageTitle="Administracion">
    <jsp:attribute name="preassets">
        <!-- foundation -->
        <link rel="stylesheet" 
              href="${baseURL}/assets/css/foundationforms/css/foundation.css" />
        <!-- /foundation -->
    </jsp:attribute>
</t:head>    

<t:adminbody adminTitle="Ingresar Administracion" adminDescp="${message}">
    <jsp:attribute name="sidemenu">
        <div class="twocol last"></div>
    </jsp:attribute>
    <jsp:attribute name="frm">
        <form method="POST" action="${baseURL}/login/admin"
              name="adminLogin">
            <fieldset>
                <div class="twelvecol">
                    <div class="threecol last">
                        <span class="prefix">Usuario</span>
                    </div>
                    <div class="ninecol">
                        <input type="text" name="userName"
                               value="${fn:escapeXml(htmlCode)}"/>
                    </div>
                </div>
                <div class="twelvecol">
                    <div class="threecol last">
                        <span class="prefix">Password</span>
                    </div>
                    <div class="ninecol">
                        <input type="password" name="userPass"
                               value="${fn:escapeXml(htmlCode)}"/> 
                    </div>
                </div>
                <input type="submit" 
                       value="<c:out value="Ingresar"/>"  class="btn"/>
            </fieldset>
        </form>     
    </jsp:attribute>
</t:adminbody>
