<%@tag description="menu bar a side" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" 
          uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="baseURL" 
       value="${fn:replace(pageContext.request.requestURL, 
                pageContext.request.requestURI, 
                pageContext.request.contextPath)}" />

<div id="sidebar" class="twocol last">
	<div class="item">
		<a href="${baseURL}/admin/" class="icon"> 
                    <span class="entypo home"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Especialidad/" class="icon"> 
                    <span class="entypo graduation-cap"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Alumno/" class="icon"> 
                    <span class="entypo users"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Candidato/" class="icon">
                    <span class="entypo user"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Pregunta/" class="icon"> 
                    <span class="entypo help"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Electo/" class="icon"> 
                    <span class="entypo add-user"></span>
		</a>
	</div>
</div>