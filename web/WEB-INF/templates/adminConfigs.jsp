
<!-- servlet: views.admin.AdminDashboardServlet.java -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="Configuración del sistema.">

    <jsp:attribute name="assets">

        <script type="text/javascript"
                src="${baseURL}/assets/js/utils.js">
        </script>

        <style type="text/css">

            .progressbar-container {
                margin: 10px auto;
            }

        </style>

    </jsp:attribute>

</t:head>

<t:adminbody adminTitle="Configuración" 
             adminDescp="Personalización de propiedades del sistema.">
    <jsp:attribute name="sidemenu">
        <t:adminsidemenu>
        </t:adminsidemenu>
    </jsp:attribute>

    <jsp:attribute name="body">

        <div class="twelvecol">
            <style type="text/css">
                .hidden {
                    display: none;
                }
            </style>
            <div class="twelvecol">
                <span class="twocol"></span>
                <span class="alert sevencol hidden" style="text-align: center" id="message">
                    <br/>
                </span>

                <span class="threecol last"></span>

            </div>
            <!-- //TODO: Agregar mas opciones -->
            <div class="twocol"></div>
            <div class="sevencol" style="text-align: center;">
                <button id="btnOpenQstnModal" class="btn">
                    Reiniciar el sistema.
                </button>
            </div>
            <div class="threecol last"></div>
            <div class="hidden" id="questionModal">
                <div class="twelvecol">
                    <p>
                        Reiniciar implica borrar todos los datos almacenados 
                        sobre la votacion hasta la fecha actual.
                    </p>
                    <hr/>
                    <br/>
                </div>
                <div class="twevelcol">
                    <button id="btnProcceddRestore" class="btn success">
                        Reiniciar
                    </button>
                    <hr/>
                    <button id="btnCloseQstnModal" class="btn critical">
                        Cancelar.
                    </button>
                </div>
            </div>
        </div> <!-- /reiniciar -->


        <div class="twelvecol">
            <br/>
            <hr/>
            <br/>
            <style type="text/css">
                .hidden {
                    display: none;
                }
            </style>
            <div class="twelvecol">
                <span class="twocol"></span>
                <span class="alert sevencol hidden" style="text-align: center" id="message">
                    <br/>
                </span>

                <span class="threecol last"></span>

            </div>
            <!-- //TODO: Agregar mas opciones -->
            <div class="twocol"></div>
            <div class="sevencol" style="text-align: center;">
                <button id="btnOpenRstPModal" class="btn">
                    Reiniciar solamente votacion presidentes.
                </button>
            </div>
            <div class="threecol last"></div>
            <div class="hidden" id="RstPModal">
                <div class="twelvecol">
                    <p>
                        Reiniciar implica borrar todos los datos almacenados 
                        sobre la votacion hasta la fecha actual.
                    </p>
                    <hr/>
                    <br/>
                </div>
                <div class="twevelcol">
                    <button id="btnProcceddRestoreP" class="btn success">
                        Reiniciar
                    </button>
                    <hr/>
                    <button id="btnCloseRstPModal" class="btn critical">
                        Cancelar.
                    </button>
                </div>
            </div>
        </div> <!-- /reiniciar presidente -->

        <div class="twelvecol">
            <br/>
            <hr/>
            <br/>
            <div class="twocol"></div>
            <div class="sevencol">
                <div class="twelvecol">
                    <button id="btnOpentVotacionModal" class="btn">
                        Seleccione el tipo de votación.
                    </button>
                </div>
            </div>
            <div class="twocol last"></div>

            <div class="hidden" id="tVotacionModal">
                <div class="twelvecol">
                    <c:if test="${ConfVotacionN == false
                          || cincoElecciones == true}">
                        <p>
                        <h3>Votacion Candidatos: </h3>
                        <p>
                            Votacion en la que participan todos los alumnos 
                            registrados, para escoger a sus candidatos para el
                            consejo estudiantil.
                        </p>
                        <br/>
                        <hr/>
                        <button id="btnProcceddtVotacionP" class="btn">
                            Votacion Candidatos.
                        </button>
                        <hr/>
                        </p>    
                    </c:if>
                    <c:if test="${ConfVotacionN == true
                            && cincoElecciones == false}">
                        <p>
                        <h3>Votacion Presidentes: </h3>
                        <p>
                            En esta votacion, se escogen las persona que votaran
                            por un conjunto de candidatos previamente seleccionados.
                        </p>
                        <br/>
                        <hr/>
                        <button id="btnProcceddtVotacionN" class="btn">
                            Votacion Presidentes.
                        </button>
                        <hr/>
                        </p>
                    </c:if>
                    <c:if test="${cincoElecciones == false}">
                        <p>
                        <h3>Votación solamente por candidatos: </h3>
                        <p>
                            En esta votacion, se escogen 10 alumnos, los cuales pasaran 
                            por una evaluación de 5 preguntas, hecha por cada alumno del
                            instituto.
                        </p>
                        <br/>
                        <hr/>
                        <button id="btnProcceddtVotacion5" class="btn">
                            Votación solamente por candidatos.
                        </button>
                        <hr/>
                        </p>
                    </c:if>
                    <br/>
                </div>
            </div>

        </div>

        <c:if test="${ConfVotacionN == true && true == false}">
            <div class="twelvecol">
                <br/>
                <hr/>
                <br/>
                <div class="twocol"></div>
                <div class="sevencol">
                    <div class="twelvecol">
                        <button id="btnOpentVotacionCModal" class="btn">
                            Escoger Fase de Votación.
                        </button>
                    </div>
                </div>
                <div class="twocol last"></div>

                <div class="hidden" id="tVotacionCModal">
                    <div class="twelvecol">
                        <p>
                        <h3>Fase Candidatos: </h3>
                        <p>
                            Todos los alumnos votan por los candidatos escogidos 
                            albitrariamente, para determinar quienes formaran parte
                            del Consejo Central de Alumnos y seran Candidatos a la 
                            Presidencia.
                        </p>
                        </p> 
                        <p>
                        <h3>Fase Presidentes: </h3>
                        <p>
                            Los alumnos votan por su candidato predilecto para que
                            sea el Presidente del Consejo de Alumnos
                        </p>
                        </p> 
                        <br/>
                        <hr/>
                    </div>
                    <div class="twevelcol">
                        <button id="btnProcceddtVotacionCP" class="btn">
                            <c:choose>
                                <c:when test="${faseConsejo == true}">
                                    Cambiar Fase Candidato
                                </c:when>
                                <c:otherwise>
                                    Cambiar Fase Electo
                                </c:otherwise>
                            </c:choose>
                        </button>
                        <hr/>
                        <button id="btnClosetVotacionCModal" class="btn critical">
                            Cancelar.
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <script type="text/javascript" src="${baseURL}/assets/js/settingsApp.js"></script>
    </jsp:attribute>
</t:adminbody>
<t:footer>
    <jsp:attribute name="foot">
        <!-- i should write a script for init and shit -->
    </jsp:attribute>
</t:footer>