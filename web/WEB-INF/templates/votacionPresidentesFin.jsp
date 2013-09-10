<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" 
       value="${fn:replace(pageContext.request.requestURL, 
                pageContext.request.requestURI, 
                pageContext.request.contextPath)}" />


<t:head pageTitle="Eleccion de presidente">
    <jsp:attribute name="assets">
    </jsp:attribute>
</t:head>

<t:votacionBody>
    <jsp:attribute name="preguntas">
        <div style="margin: 20px;"></div>
        <div class="twelvecol">
            <div class="onecol"></div>
            <div class="tencol hero">

                <div class="twelvecol">
                    <div class="twelvecol">
                        <div class="fourcol"></div>
                        <div class="fourcol">
                            <h1 style="text-align: center">
                                Voto registrado.
                            </h1>
                        </div>
                        <div class="fourcol last"></div>
                    </div>

                    <div class="twelvecol">
                        <div class="twocol"></div>
                        <div class="eightcol">
                            <img src="${cImg}" alt="imagen candidato"/>
                        </div>
                        <div class="twocol last"></div>
                    </div>



                    <div class="twelvecol">

                        <div style="margin: 20px;"></div>

                        <div class="twocol"></div>
                        <div class="eightcol" style="text-align: center">
                            ${cCodigo}
                        </div>
                        <div class="twocol last"></div>
                    </div>
                    <div class="twelvecol">
                        <div class="twocol"></div>
                        <div class="eightcol" style="text-align: center">
                            ${cNombre}
                        </div>
                        <div class="twocol last"></div>
                    </div>


                </div>


                <div class="twelvecol">
                    <div style="margin: 20px;"></div>
                    <a class="btn success" href="">
                        Aceptar
                    </a>
                </div>

            </div>
            <div class="onecol last"></div>
        </div>
    </jsp:attribute>
</t:votacionBody>

<t:footer>
    <jsp:attribute name="foot">
    </jsp:attribute>
</t:footer>