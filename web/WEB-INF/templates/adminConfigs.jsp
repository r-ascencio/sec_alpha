
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
            <span class="alert twelvecol hidden" style="text-align: center" id="message"></span>
            <!-- //TODO: Agregar mas opciones -->
            <div class="fourcol"></div>
            <div class="fourcol">
                <form id="act" action="${baseURL}/admin/configs/restart" 
                      method="POST">
                    <input type="submit" class="btn" 
                           value="Reiniciar"></input>
                </form>
            </div>
            <div class="fourcol last"></div>
        </div>

        <script type="text/javascript">
            $("#act").submit(function(e)
            {
                e.preventDefault();
                var $this = $(this),
                        url = $this.attr('action');

                var posting = $.post(url);

                posting.done(function() {
                    $("#message").removeClass('hidden');
                    $("#message").text("Reinicio completo");
                });

            })
        </script>
    </jsp:attribute>
</t:adminbody>
<t:footer>
    <jsp:attribute name="foot">
        <!-- i should write a script for init and shit -->
    </jsp:attribute>
</t:footer>