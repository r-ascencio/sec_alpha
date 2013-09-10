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
		<a href="${baseURL}/admin/" class="icon"
                   title="Inicio" alt="Inicio"> 
                    <span class="entypo home"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Especialidad/" alt="Especialidad"
                   title="Especialidad" class="icon"> 
                    <span class="entypo graduation-cap"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Alumno/" class="icon"
                   alt="Alumno" title="Alumno"> 
                    <span class="entypo users"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Presidente/" class="icon"
                   title="Candidatos Presidente" alt="Candidato Presidente"> 
                    <span class="entypo vcard"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Votantes/" class="icon"
                   title="Votantes" alt="Votantes"> 
                    <span class="entypo vcard"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Candidato/" class="icon"
                   alt="Candidato" title="Candidato">
                    <span class="entypo user"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Electo/" class="icon"
                   alt="Consejo" title="Consejo"> 
                    <span class="entypo layout"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Usuario/" class="icon"
                   alt="Usuarios" title="Usuarios"> 
                    <span class="entypo add-user"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/Pregunta/" class="icon"
                   title="Preguntas" alt="Preguntas"> 
                    <span class="entypo help"></span>
		</a>
	</div>
	<div class="item">
		<a href="${baseURL}/admin/configs/" class="icon"
                   alt="Configuraciones" title="Configuraciones"> 
                    <span class="entypo cog"></span>
		</a>
	</div>
</div>