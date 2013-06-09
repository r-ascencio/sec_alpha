<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<t:head pageTitle="Votacion">
    <jsp:attribute name="assets">       
        <link rel="stylesheet" 
              href="${baseURL}/assets/css/foundationforms/css/foundation.min.css" />
        <link rel="stylesheet" href="${baseURL}/assets/css/main.css" />

        <script src="${baseURL}/assets/js/stepy/js/jquery.stepy.min.js" 
        type="text/javascript"></script>

        <script src="${baseURL}/assets/js/stepy/js/jquery.validate.min.js" 
        type="text/javascript"></script>
    </jsp:attribute>
</t:head>

<t:votacionBody>
    <jsp:attribute name="preguntas">

        <!-- TODO : FIXTHIS -->
        <c:if test="${candidatos.size() <= 0}">
            <div class="twelvecol">
                <div class="hero">
                    <div class="twocol"></div>
                    <div class="eightcol">
                        
                        <div class="eightcol">
                            El numero de candidatos tiene que ser igual a 3.
                        </div>
                        
                        <div class="threecol">
                            <a href="${baseURL}/login/votacion/" class="btn">
                                Salir
                            </a>
                        </div>
                        
                    </div>
                    <div class="twocol last"></div>
                </div>
            </div>
        </c:if>
        <!-- /TODO -->

        <c:if test="${candidatos.size() == 3}">


            <form id="preguntas-form" method="POST" action="${baseURL}/votacion/">
                <style type="text/css">
                    #preguntas-Notitle > ul {
                        display: none;
                    }
                </style>

                <div id="preguntas-Notitle">
                </div>
                <c:forEach items="${candidatos}" var="candidato">
                    <fieldset>
                        <div class="twelvecol  candidato-header">
                            <div class="twocol"></div>
                            <div class="eightcol">
                                <div class="candidato-img fourcol">
                                    <div class="onecol"></div>
                                    <div class="tencol">
                                        <img src="${candidato.imagen_src}" height="96" alt=""/> 
                                    </div>
                                    <div class="onecol"></div>
                                </div>
                                <div class="candidato-descripcion sevencol">
                                    <dl>
                                        <dt> Nombre:  </dt>
                                        <dd> ${candidato.nombre} </dd>
                                        <dt> Especialidad:  </dt>
                                        <dd> ${candidato.especialidad} </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="twocol last"></div>
                        </div>
                        <c:forEach items="${preguntas}" var="p" varStatus="loopStatus">
                            <div class="pregunta twelvecol">
                                <div class="twelvecol">
                                    <div class="onecol">
                                        <div class="center">
                                            ${loopStatus.count}
                                        </div>
                                    </div>
                                    <div class="pregunta-descripcion tencol">
                                        <div class="center">
                                            ${p.descripcion} 
                                        </div>
                                    </div>
                                    <div class="onecol last">
                                        <div class="pregunta-icon center ">
                                            <span class="entypo help"></span>
                                        </div>
                                    </div>
                                    <div class="twelvecol">
                                        <hr/>
                                    </div>
                                </div>
                                <div class="twelvecol">
                                    <div class="pregunta-respuesta">
                                        <div class="threecol pregunta-opcion">
                                            <div class="twelvecol center">
                                                <label for="">
                                                    1
                                                </label>
                                            </div>
                                            <div class="center">
                                                <input type="RADIO"
                                                       name="<c:out 
                                                           value="${p.codigo}_${candidato.codigo}" 
                                                           />" 
                                                       value="<c:out value="1"/>" required/>
                                            </div>
                                        </div>
                                        <div class="threecol pregunta-opcion">
                                            <div class="twelvecol center">
                                                <label for="">
                                                    2
                                                </label>

                                            </div>
                                            <div class="center">
                                                <input type="RADIO"
                                                       name="<c:out 
                                                           value="${param.email}${p.codigo}_${candidato.codigo}" 
                                                           />"  
                                                       value="<c:out value="2"/>"/>
                                            </div>
                                        </div>
                                        <div class="threecol pregunta-opcion">
                                            <div class="twelvecol center">
                                                <label for="">
                                                    3
                                                </label>

                                            </div>
                                            <div class="twelvecol center last">
                                                <input type="RADIO"
                                                       name="${p.codigo}_${candidato.codigo}" 
                                                       value="<c:out value="3" />"/>
                                            </div>
                                        </div>
                                        <div class="threecol last pregunta-opcion">
                                            <div class="twelvecol center">
                                                <label for="">
                                                    4
                                                </label>
                                            </div>
                                            <div class="twelvecol center last">
                                                <input type="RADIO"
                                                       name="<c:out 
                                                           value="${param.email}${p.codigo}_${candidato.codigo}" 
                                                           />" 
                                                       value="<c:out value="4" />"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="twelvecol">
                                    <br />
                                    <br />
                                </div>
                            </div>
                        </c:forEach>
                    </fieldset>
                </c:forEach>
                <input type="submit" class="finish" value="Terminar"/>
            </form>

        </c:if>

    </jsp:attribute>
</t:votacionBody>

<t:footer>
    <jsp:attribute name="foot">
        <script src="${baseURL}/assets/js/Steps.js" 
        type="text/javascript"></script> 
    </jsp:attribute>
</t:footer>
