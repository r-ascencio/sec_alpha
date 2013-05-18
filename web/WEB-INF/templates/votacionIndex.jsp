<%-- 
    Document   : votacionIndex
    Created on : 15-may-2013, 23:03:24
    Author     : _r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" 
       value="${fn:replace(pageContext.request.requestURL, 
                pageContext.request.requestURI, 
                pageContext.request.contextPath)}" />

<!-- 
<attribute name="adminTitle" required="true"%>
-->

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${logged == true}">
                <h1>You're a logged mothefucker</h1>
            </c:when>
            <c:when test="${logged == false}">
                <form method="POST" action="${baseURL}/votacion/">
                    <fieldset>
                        <legend> Ingresar </legend>
                        <input type="text" name="codigo" 
                               value="${fn:escapeXml(htmlCode)}"/>
                        <input type="text" name="nie" 
                               value="${fn:escapeXml(htmlCode)}"/> 
                        <input type="submit" 
                               value="<c:out value="Ingresar"/>" />
                    </fieldset>
                </form> 
            </c:when>
        </c:choose>
    </body>
</html>


<!-- TODO: Replace with c:out everywhere -->