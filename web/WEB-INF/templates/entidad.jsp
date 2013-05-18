<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="principal">

    <jsp:attribute name="assets">
        <script type="text/javascript">
            var imgEditorCandidato = function(container, options) {
                if (options.model.imagen_src) {
                    $('<img src="' + options.model.imagen_src + '" />').appendTo(container);
                }

                $('<input name="files" type="file" />').appendTo(container).kendoUpload({
                    multiple: false,
                    showFileList: false,
                    async: {
                        saveUrl: '${baseURL}/uploads',
                        autoUpload: true,
                    },
                    sucess: function() {
                        console.log("fooo");
                    }
                });
            };
            var Entidad = "${entityName}";
            var Fields = ${fields};
            var Columns = ${columns};
            var ID = "${ID}";
        </script>
    </jsp:attribute>

</t:head>

<t:adminbody adminTitle="${entityName}" adminDescp="${adminDesc}">

    <jsp:attribute name="sidemenu">
        <t:adminsidemenu>
        </t:adminsidemenu>
    </jsp:attribute>

    <jsp:attribute name="body">
        <!-- here is supposed to be a fucking method to generate the
         fucking forms  -->
    </jsp:attribute>

    <jsp:attribute name="frm">
        ${grid}
    </jsp:attribute>
</t:adminbody>

<!-- TODO : IF IT'S LOGGED -->
<t:Egrid.js></t:Egrid.js>
<t:footer></t:footer>