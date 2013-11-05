<%@tag description="menu bar a side" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" 
          uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="baseURL" 
       value="${fn:replace(pageContext.request.requestURL, 
                pageContext.request.requestURI, 
                pageContext.request.contextPath)}" />

<div id="sidebar" class="twocol last">
    <c:if test="${cincoElecciones == true}">
        <style type="text/css">
            #sidebar .item a.icon {
                color: #81A8B8;
            }
            #sidebar.item:hover {
                color: #4A78A7;
            }
            #sidebar .item .icon > * {
                color: #81A8B8;
            }
            #sidebar .item .icon > *:hover {
                color: #4A78A7;
            }
        </style>
    </c:if>
    <c:if test="${ConfVotacionP == true}">
        <style type="text/css">
            #sidebar .item a.icon {
                color: #333333;
            }
            #sidebar.item:hover {
                color: #990100;
            }
            #sidebar .item .icon > * {
                color: #333333;
            }
            #sidebar .item .icon > *:hover {
                color: #990100;
            }
        </style>
    </c:if>
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


    <c:if test="${cincoElecciones == true}">
        <div class="item">
            <a href="${baseURL}/admin/Presidentes/" class="icon"
               title="Candidatos Presidente" alt="Candidato Presidente"> 
                <span class="entypo layout"></span>
            </a>
        </div> 
        <div class="item">
            <a href="${baseURL}/admin/Pregunta/" class="icon"
               title="Preguntas" alt="Preguntas"> 
                <span class="entypo help"></span>
            </a>
        </div> 
    </c:if>

    <!-- VOTACION PRESIDENTE -->

    <c:if test="${ConfVotacionP}">
        <div class="item">
            <a href="${baseURL}/admin/Votantes/" class="icon"
               title="Votantes" alt="Votantes"> 
                <span class="entypo vcard"></span>
            </a>
        </div>
        <div class="item">
            <a href="${baseURL}/admin/Presidente/" class="icon"
               title="Candidatos Presidente" alt="Candidato Presidente"> 
                <span class="entypo layout"></span>
            </a>
        </div>          
    </c:if>

    <!-- /VOTACION PRESIDENTE -->

    <!-- VOTACION CANDIDATO -->

    <c:if test="${ConfVotacionN}">
        <c:choose>
            <c:when test="${faseConsejo == true}">
                <div class="item">
                    <a href="${baseURL}/admin/Presidente/" class="icon"
                       title="Candidatos Presidente" alt="Candidato Presidente"> 
                        <span class="entypo layout"></span>
                    </a>
                </div>
                <div class="item">
                    <a href="${baseURL}/admin/Votantes/" class="icon"
                       title="Votantes" alt="Votantes"> 
                        <span class="entypo vcard"></span>
                    </a>
                </div> 
            </c:when>
            <c:otherwise>
                <div class="item">
                    <a href="${baseURL}/admin/Candidato/" class="icon"
                       alt="Candidato" title="Candidato">
                        <span class="entypo user"></span>
                    </a>
                </div>
                <div class="item">
                    <a href="${baseURL}/admin/Pregunta/" class="icon"
                       title="Preguntas" alt="Preguntas"> 
                        <span class="entypo help"></span>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <!-- /VOTACION CANDIDATO -->
    <!-- SYS -->
    <div class="item">
        <a href="${baseURL}/admin/Usuario/" class="icon"
           alt="Usuarios" title="Usuarios"> 
            <span class="entypo add-user"></span>
        </a>
    </div>
    <div class="item">
        <a href="${baseURL}/admin/configs/" class="icon"
           alt="Configuraciones" title="Configuraciones"> 
            <span class="entypo cog"></span>
        </a>
    </div>
    <!-- SYS -->
</div>