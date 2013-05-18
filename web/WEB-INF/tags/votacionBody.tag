<%@tag description="required assets" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />


<!-- fragmest -->
<%@attribute name="preguntas" fragment="true"%>

<div class="container normal">
    <div class="row normal">
        <div class="twelvecol">
            <div class="twocol"></div>
            <div id="preguntas-container" class="eightcol">
                <form id="preguntas-form" method="POST" action="${baseURL}/votacion/">
                    <jsp:invoke fragment="preguntas"></jsp:invoke>
                    <input type="submit" class="finish"/>
                </form>
            </div>
        </div>
    </div>
</div>