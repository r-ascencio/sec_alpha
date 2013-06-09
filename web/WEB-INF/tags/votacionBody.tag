<%@tag description="required assets" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />


<!-- fragmest -->
<%@attribute name="preguntas" fragment="true"%>
<%@attribute name="listView" fragment="true"%>

<div class="container normal">
    <div class="row normal">
        <div class="twelvecol">
            <div class="twocol"></div>
            <div id="preguntas-container" class="eightcol">
                <!-- wtf, i did this why i just not... -->
                <!-- this is stupid -->
                <jsp:invoke fragment="preguntas"></jsp:invoke>
                <jsp:invoke fragment="listView"></jsp:invoke>
            </div>
        </div>
    </div>
</div>