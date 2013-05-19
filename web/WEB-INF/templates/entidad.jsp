<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="principal">
    <jsp:attribute name="preassets">
        <!-- foundation -->
        <link rel="stylesheet" 
              href="${baseURL}/assets/css/foundationforms/css/foundation.css" />
        <!-- /foundation -->
    </jsp:attribute>
    <jsp:attribute name="assets">
        <script type="text/javascript">
            var fkEditor = function(container, options) {
                console.log(options);
                $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                        .appendTo(container)
                        .kendoComboBox({
                    autoBind: false,
                    dataSource: options.values
                });
            };
            var imgEditorCandidato = function(container, options) {
                if (options.model.imagen_src) {
                    $('<img src="' + options.model.imagen_src + '" />').appendTo(container);
                }

                $('<input name="files" type="file" />').appendTo(container).kendoUpload({
                    success: function(e) {
                        if (e.operation == "upload") {
                            container.html('<img src="' + e.response.imagen_src + '" />');
                            options.model.set(options.field, e.response.imagen_src);
                        }
                    },
                    upload: onUpload,
                    error: onError,
                    multiple: false,
                    showFileList: false,
                    async: {
                        saveUrl: '${baseURL}/uploads',
                        autoUpload: true
                    }
                });
            };
            function onUpload(e) {
                var files = e.files;
                $.each(files, function() {
                    if (this.extension !== ".jpg"
                            && this.extension !== ".png") {
                        alert("Solo .jpg y .png");
                        e.preventDefault();
                    }
                });
            }
            function onError(e) {
                // Array with information about the uploaded files
                var files = e.files;

                if (e.operation == "upload") {
                    alert("Failed to uploaded " + files.length + " files");
                    alert(e.XMLHttpRequest.responseText + " : TEXT");
                    alert(e.XMLHttpRequest.statusText + " : STATUS");
                }
            }

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