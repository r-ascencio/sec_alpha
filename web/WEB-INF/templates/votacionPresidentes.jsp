<!-- servlet : controllers.votacion.PresidenteVotacionServlet -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<t:head pageTitle="Eleccion de presidente">
    <jsp:attribute name="assets">
    </jsp:attribute>
</t:head>

<t:votacionBody>
    <jsp:attribute name="listView">

        <div class="twelvecol">
            <div class="twelvecol header" style="padding: 0px;">
                <h1 style="padding: 20px 10px 10px 15px;"> Instrucciones </h1>
                <p style="padding: 2px 10px 20px 15px;">
                    Para <span class="alert warning">votar</span>
                    por el Presidente realize <span class="alert success">doble click</span> sobre el candidato.
                </p>
            </div>
        </div>

        <style type="text/css">
            #listView-container {
                padding-top:    20px;
                padding-bottom: 30px;
            }
            #cC { display: none; }
        </style>

        <div class="twelvecol" id="listView-container">
            <div id="cC" data-uid="#" class="twelvecol">
                <div class="twelvecol">
                    <div class="onecol"></div>
                    <div class="elevencol hero" style="padding: 20px;">                       
                        <div id="cImage" class="fivecol">
                            <img alt="#" src="#" />
                        </div>

                        <h3 class="sevencol last">
                            <div class="twelvecol">
                                <div class="twocol">Nombre: </div>
                                <div class="ninecol" id="cNombre"></div>
                            </div>
                            <div class="twelvecol">
                                <div class="twocol">Codigo: </div>
                                <div class="ninecol" id="cAlumno"></div>
                            </div>
                        </h3>

                        <div class="fivecol"></div>
                        <div class="sevencol last">
                            <div class="sixcol">
                                <form id="tothechosenone" method="POST"
                                      action="${baseURL}/votacion/presidentes/voto">

                                    <input type="hidden"
                                           id="cName"
                                           name="cName"
                                           value="${fn:escapeXml(htmlCode)}" />

                                    <input type="hidden" 
                                           id="cAlumnus"
                                           name="cAlumnus"
                                           value="${fn:escapeXml(htmlCode)}" />

                                    <input type="hidden" 
                                           id="cImg"
                                           name="cImg"
                                           value="${fn:escapeXml(htmlCode)}" />

                                    <input type="hidden" 
                                           id="cUID"
                                           name="cUID"
                                           value="${fn:escapeXml(htmlCode)}" />

                                    <input type="submit" class="btn success"
                                           id="votar" value="Votar">
                                    </input>

                                </form>
                            </div>
                            <div class="sixcol last">
                                <span id="cancel" class="btn critical">
                                    Cancelar
                                </span>

                            </div>
                        </div>
                    </div>
                    <div class="onecol last"></div>
                </div>
            </div>

            <div id="listView">
                <!-- HERE I LOAD THE ELECTED ONES -->
            </div>
        </div>

    </jsp:attribute>
</t:votacionBody>

<t:footer>
    <jsp:attribute name="foot">
        <script type="text/x-kendo-tmpl" id="template">

            <div class="fourcol hero candidato" id="#:alumno#"
            style="margin: 10px 0px;">
            <div class="twelvecol">
            <div class="threecol"></div>
            <div class="sixcol">
            <img style="height: 150px; width: 150px;" src="#:imagen_src#" alt="#:nombre# image" />
            </div>
            <div class="threecol last"></div>
            </div>
            <div class="twelvecol">
            <div class="threecol"></div>
            <div class="sixcol">
            <p>#:nombre#</p>
            </div>
            <div class="threecol last"></div>
            </div>
            </div>

            <style type="text/css"> 
            .k-widget { border: none; background: none;} 
            </style>
        </script>

        <script type="text/javascript" src="${baseURL}/assets/js/vpApps.js">
        </script>
    </jsp:attribute>
</t:footer>
