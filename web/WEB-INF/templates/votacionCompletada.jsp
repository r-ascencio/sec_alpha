<!-- VotacionCompleatadaServlet -->

<%-- 
    Document   : votacionFinal
    Created on : 10-jun-2013, 18:31:02
    Author     : _r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" 
       value="${fn:replace(pageContext.request.requestURL, 
                pageContext.request.requestURI, 
                pageContext.request.contextPath)}" />

<t:head pageTitle="Votacion Terminada ${codigo}">
    <jsp:attribute name="assets">
    </jsp:attribute>
</t:head>

<t:votacionBody>
    <jsp:attribute name="preguntas">
        <div class="twelvecol">
            <div class="twocol"></div>
            <div class="eightcol"
                 style="
                 margin: 20px auto;
                 display: block;
                 ">
                <div class="twelvecol"
                     style="
                     display: block;
                     ">
                    <div class="twelvecol" style="
                         background: #2e2e2e; 
                         text-align: center;
                         padding: 20px;
                         display: block;
                         vertical-align: middle;">

                        <span class="onecol" style="
                              text-align: center; 
                              ">

                            <span class="entypo check"
                                  style="
                                  font-size: 28px;
                                  color: #f7f7f7;" >

                            </span>

                        </span>
                        <span class="elevenol last"
                              style="
                              color: #f7f7f7;">
                            Votacion Completada
                        </span>
                    </div>
                    <!-- content -->
                    <div class="twelvecol"
                         style="
                         display: block;
                         ">
                        <div class="twelvecol hero" 
                             style="padding: 19px;">

                            <div class="twelvecol">
                                <span class="twocol"></span>
                                <span class="eightcol">
                                    <img 
                                        src="${alumno_imagen}"
                                        />
                                </span>
                                <span class="twocol last"></span>
                            </div>
                            <!-- -->
                            <div class="twelvecol"
                                 style="
                                 margin: 10px auto;
                                 ">

                                <span class="twocol"></span>

                                <span class="eightcol" 
                                      style="text-align: center;">

                                    <div class="twocol" style="
                                         font-size: 20px;">

                                        <span class="entypo vcard">
                                        </span>

                                    </div>

                                    <div class="ninecol last">
                                        <p>
                                            Codigo: 
                                        </p>
                                    </div>  

                                    <div class="ninecol">
                                        <p>
                                            ${codigo}
                                        </p>
                                    </div>  

                                </span>
                                <span class="twocol last"></span>
                            </div>
                            <!-- -->

                            <div class="twelvecol"
                                 style="
                                 margin: 10px auto;
                                 ">
                                <span class="twocol"></span>
                                <span class="eightcol" style="
                                      text-align: center;">

                                    <div class="twocol" style="
                                         font-size: 20px;">

                                        <span class="entypo user">
                                        </span>

                                    </div>

                                    <div class="ninecol last">
                                        <p>
                                            Nombre:
                                        </p>
                                    </div>  

                                    <div class="ninecol last">
                                        <p>
                                            ${nombre}
                                        </p>
                                    </div>  

                                </span>
                                <span class="twocol last"></span>
                            </div>
                            <!-- -->

                            <div class="twelvecol"
                                 style="
                                 margin: 10px auto;
                                 ">
                                <span class="twocol"></span>
                                <span class="eightcol" style="
                                      text-align: center;">

                                    <div class="twocol" style="
                                         font-size: 20px;">

                                        <span class="entypo graduation-cap">
                                        </span>

                                    </div>

                                    <div class="ninecol last">
                                        <p>
                                            Especialidad:
                                        </p>
                                    </div>  

                                    <div class="ninecol last">
                                        <p>
                                            ${especialidad}
                                        </p>
                                    </div>  

                                </span>
                                <span class="twocol last"></span>
                            </div>
                            <!-- -->

                            <div class="twelvecol"
                                 style="
                                 margin: 10px auto;
                                 ">
                                <span class="twocol"></span>
                                <span class="eightcol" style="
                                      text-align: center;">
                                    <a href="${baseURL}/votacion/presidentes/login/"
                                       class="btn success">
                                    Aceptar
                                    </a>
                                </span>
                                <span class="twocol last"></span>
                            </div>
                            <!-- -->
                            
                        </div>
                    </div>
                </div>
                <div class="twocol"></div>
            </div>
        </jsp:attribute>
    </t:votacionBody>

    <t:footer>
        <jsp:attribute name="foot">
        </jsp:attribute>
    </t:footer>       