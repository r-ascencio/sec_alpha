
<!-- servlet: views.admin.AdminDashboardServlet.java -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="Administracion ${entityName}">

    <jsp:attribute name="assets">

        <script type="text/javascript"
                src="${baseURL}/assets/js/jquery-ui/js/jquery-ui-1.10.3.custom.min.js">
        </script>

        <link rel="stylesheet" 
              href="${baseURL}/assets/js/jquery-ui/css/smoothness/jquery-ui-1.10.3.custom.min.css" />

        <style type="text/css">

            .progressbar-container {
                margin: 10px auto;
            }

        </style>

    </jsp:attribute>

</t:head>

<t:adminbody adminTitle="Administracion" adminDescp="Informacion basica del sistema">
    <jsp:attribute name="sidemenu">
        <t:adminsidemenu>
        </t:adminsidemenu>
    </jsp:attribute>

    <jsp:attribute name="body">

        <div class="twelvecol">            

            <div id="candidatoPie" class="k-content sevencol">
                <div class="candidatoPie-wrapper">
                    <div id="candidatoGrafica" style="background: center no-repeat url(${baseURL}/assets/img/bg.png">

                    </div>
                </div>
            </div>

            <div class="fivecol last" style="text-align: center">

                <div class="twelvecol">
                    <h3> Proceso de votacion </h3>
                </div>

                <div  class="twelvecol">
                    <div class="fourcol"> <h4>Especialidad</h4> </div>
                    <div class="twocol"> <h4>%</h4> </div>
                    <div class="sixcol last"></div>
                </div>

                <div class="twelvecol" id="progress-cont">
                    <!-- here you will see the processs. -->
                </div>

            </div>



        </jsp:attribute>
    </t:adminbody>
    <t:footer>
        <jsp:attribute name="foot">
            <!-- i should write a script for init and shit -->

            <script>
                var data = ${dataCandidatos};
                var dataVotacion = ${dataVotacion};
            </script>

            <script src="${baseURL}/assets/js/charts.js">
            </script>

        </jsp:attribute>
    </t:footer>