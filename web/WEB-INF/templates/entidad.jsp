<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />

<t:head pageTitle="Administracion ${entityName}">
    <jsp:attribute name="preassets">
        <c:set var="foundation" value="/foo.css"/>
        <c:if test="${entityName == 'Nuevo usuario'}">
            <c:set var="foundation" value="/assets/css/foundationforms/css/foundation.css"/>
        </c:if>
        <!-- foundation -->
        <link rel="stylesheet" 
              href="${baseURL}${foundation}" />
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

                if (e.operation === "upload") {
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
        <c:if test="${entityName == 'Especialidad'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <form id="act" action="${baseURL}/admin/Especialidad/act" 
                          method="POST">
                        <input type="submit" class="btn" 
                               value="Actualizar Total Alumnos"></input>
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
                        $("#grid").data("kendoGrid").dataSource.read();
                    });
                    
                })
            </script>
        </c:if>
        <c:if test="${entityName == 'Alumno'}">
            <div class="twelvecol">
                <div id="instrucciones">
                    <div class="twelvecol">
                        <h2> Importar archivo de excel </h2>
                    </div>
                    <div class="twelvecol">
                        <div class="twocol"></div>
                        <div class="eightcol">
                            <form method="post" action="submit">
                                <div class="twelvecol">
                                    <fieldset>
                                        <div class="twelvecol">
                                            <input type="file" name="excelFile"
                                                   id="excelFile"/>
                                        </div>
                                    </fieldset>
                                </div>
                            </form>
                            <script type="text/javascript">
                                $("#excelFile").kendoUpload({
                                    multiple: false,
                                    showFileList: true,
                                    async: {
                                        saveUrl:
                                                '${baseURL}/admin/import/alumnos',
                                        autoUpload: true
                                    },
                                    success: function(e) {
                                        alert("Archivo subido con exito,\n\
                                Boton desactivado");
                                        $('#excelFile').data("kendoUpload").enable(false);
                                    },
                                    upload: function(e) {
                                        var files = e.files;
                                        $.each(files, function() {
                                            if (this.extension !== ".xlsx") {
                                                alert("El archivo debe ser un libro de excel");
                                                e.preventDefault();
                                            }
                                        });
                                    }
                                });

                                $(".k-upload-button").find("span")
                                        .text("Subir archivo de excel");
                                $(".k-upload-button").css("width", "100%");
                            </script>
                        </div>
                        <div class="twocol last"></div>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="twelvecol" style="margin-bottom: 10px;"></div>
        <div class="twelvecol" style="margin-top: 10px;"></div>
    </jsp:attribute>

    <jsp:attribute name="frm">
        ${grid}
    </jsp:attribute>
</t:adminbody>

<!-- TODO : IF IT'S LOGGED -->
<t:Egrid.js></t:Egrid.js>
<t:footer></t:footer>