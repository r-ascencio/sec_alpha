<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

            <c:choose>
                <c:when test="${entityName == 'Candidato'   || 
                                entityName == 'Presidente'  ||
                                entityName == 'Presidentes'  ||
                                entityName == 'Votantes'}">
                var fkEditor = function(container, options) {
                    if (options.field === "especialidad") {
                        $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                .appendTo(container)
                                .kendoComboBox({
                            autoBind: false,
                            dataSource: options.values,
                            enable: false,
                            text: "",
                            index: 0
                        });
                    } else {
                        $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                .appendTo(container)
                                .kendoComboBox({
                            autoBind: false,
                            dataSource: options.values,
                            enable: true,
                            text: "",
                            index: 0
                        });
                    }
                };

                </c:when>
                <c:when test="${entityName == 'Pregunta'}">
                    var fkEditor = function(container, options) {
                        if (options.field === "categoria") {
                            $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                    .appendTo(container)
                                    .kendoComboBox({
                                autoBind: false,
                                dataSource: options.values,
                                enable: true,
                                text: "-- Escoger Categoria --",
                                index: 1
                            });
                        } else {
                            $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                    .appendTo(container)
                                    .kendoComboBox({
                                autoBind: false,
                                dataSource: options.values,
                                enable: true,
                                text: "-- Escoger Categoria --",
                                index: 1
                            });
                        }
                    };
                </c:when>
                <c:when test="${entityName == 'Electo'}">
                    var fkEditor = function(container, options) {
                        if (options.field === "alumno") {
                            $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                    .appendTo(container)
                                    .kendoComboBox({
                                autoBind: false,
                                dataSource: options.values,
                                enable: false,
                                text: "",
                                index: 0
                            });
                        } else {
                            $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                    .appendTo(container)
                                    .kendoComboBox({
                                autoBind: false,
                                dataSource: options.values,
                                enable: true,
                                text: "",
                                index: 0
                            });
                        }
                    };

                </c:when>
                <c:otherwise>
                    var fkEditor = function(container, options) {
                        console.log(options);
                        $('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" />')
                                .appendTo(container)
                                .kendoComboBox({
                            autoBind: false,
                            dataSource: options.values,
                            enable: true,
                            text: "",
                            index: 0
                        });
                    };
                </c:otherwise>
            </c:choose>
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
                                && this.extension !== ".JPG"
                                && this.extension !== ".png"
                                && this.extension !== ".PNG") {
                            alert("La extensión y/o formato de la imagen, debe ser\n\
                                    archivo.jpg, archivo.JPG, archivo.png, archivo.PNG");
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

                var Entidad = "${entityName}",
                        Fields = ${fields},
                        Columns = ${columns},
                        ID = "${ID}";

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
        <c:if test="${entityName == 'Usuario'}">

            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="/admin/Usuario/nuevo" class="btn">
                        Agregar Usuario
                    </a>    
                </div>
                <div class="fourcol"></div>
            </div>
        </c:if>
        <c:if test="${entityName == 'Electo'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <c:if test="${electos == 'true'}">

                        <form id="act" 
                              action="${baseURL}/admin/Electo/setPresidente" 
                              method="POST">
                            <input type="submit" class="btn" 
                                   value="Actualizar Presidente"></input>
                        </form>

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

                    <c:if test="${electos == 'false'}">

                        <form id="act" action="${baseURL}/admin/Electo/set" 
                              method="POST">
                            <input type="submit" class="btn" 
                                   value="Actualizar Candidatos"></input>
                        </form>

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
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>

        <c:if test="${entityName == 'Pregunta'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/Categoria" class="btn">
                        Categoria de Preguntas
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>

        <c:if test="${entityName == 'Candidato'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/reportes/Candidatos" class="btn">
                        Reporte General Candidatos
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
            <hr/><br/>
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/reportes/CandidatosCategoria" class="btn">
                        Candidatos Estadisticas Categorias
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>

        <c:if test="${entityName == 'Presidentes'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/reportes/eleccion" class="btn">
                        Reporte Estadisticas Candidatos
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>

        <c:if test="${entityName == 'Categoria'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/Pregunta" class="btn">
                        Preguntas
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>

        <c:if test="${entityName == 'Votantes'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/reportes/Votantes" class="btn">
                        Reporte Votantes Faltantes
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>
        <c:if test="${entityName == 'Presidente'}">
            <div class="twelvecol">
                <div class="fourcol"></div>
                <div class="fourcol">
                    <a href="${baseURL}/admin/reportes/Presidente" class="btn">
                        Reporte Presidente Estadisticas
                    </a>
                </div>
                <div class="fourcol last"></div>
            </div>
        </c:if>

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
                                        alert("Archivo subido con exito.");
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
            <br/>
            <div class="twelvecol">
                <hr/>
                <div class="fourcol"></div>
                <div class="fourcol">
                    <c:if test="${cincoElecciones == false}">
                        <form 
                            method="POST"
                            action="${baseURL}/admin/reportes/AlumnosFaltantes">
                            <center>
                                <select name="especialidad">
                                    <c:forEach items="${especialidades}" 
                                               var="especialidad">
                                        <option value="${especialidad.codigo}">
                                            ${especialidad.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </center>
                            <br/>
                            <input 
                                type="submit" 
                                class="btn"
                                value="Reporte Faltantes Voto" />
                        </form>
                    </c:if>
                    <c:if test="${cincoElecciones == true}">
                        <form 
                            method="POST"
                            action="${baseURL}/admin/reportes/alumnos-eleccion-faltantes">
                            <center>
                                <select name="especialidad">
                                    <c:forEach items="${especialidades}" 
                                               var="especialidad">
                                        <option value="${especialidad.codigo}">
                                            ${especialidad.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </center>
                            <br/>
                            <input 
                                type="submit" 
                                class="btn"
                                value="Reporte Faltantes Voto" />
                        </form>
                    </c:if>
                </div>
                <div class="fourcol last"></div>
                <hr/>
            </div>
        </c:if>
        <div class="twelvecol" style="margin-bottom: 10px;"></div>
        <div class="twelvecol" style="margin-top: 10px;"></div>

        <script type="text/javascript">
            $(document).ready(function(){
                $('form:first').find('.elevencol').last().remove();
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="frm">
        ${grid}
    </jsp:attribute>

</t:adminbody>

<!-- TODO : IF IT'S LOGGED -->
<t:Egrid.js></t:Egrid.js>
<t:footer></t:footer>