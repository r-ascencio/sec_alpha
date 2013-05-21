<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<t:head pageTitle="Eleccion de presidente">
    <jsp:attribute name="assets">       
        <link rel="stylesheet" 
              href="${baseURL}/assets/css/foundationforms/css/foundation.min.css" />
        <link rel="stylesheet" href="${baseURL}/assets/css/main.css" />
    </jsp:attribute>
</t:head>

<t:votacionBody>
    <jsp:attribute name="listView">
        <div id="listView"></div>
    </jsp:attribute>
</t:votacionBody>

<t:footer>
    <jsp:attribute name="foot">
        <script type="text/x-kendo-tmpl" id="template">
        <div class="electo fivecol">
            <img src="#:imagen_src#" alt="#:nombre# image" />
            <h3>#:nombre#</h3>
        </div>
        </script>
        <script type="text/javascript">
            $(document).ready(function() {
                var dataSource = new kendo.data.DataSource({
                    transport: {
                        read: {
                            url: "${baseURL}/r/presidente", //test
                            dataType: "json"
                        }
                    }
                });

                $("#listView").kendoListView({
                    dataSource: dataSource,
                    selectable: "single",
                    dataBound: onDataBound,
                    change: onChange,
                    template: kendo.template($("#template").html())
                });

                function onDataBound() {
                    console.log("ListView data bound");
                }

                function onChange() {
                    var data = dataSource.view(),
                            selected = $.map(this.select(), function(item) {
                        return data[$(item).index()].alumno;
                    });

                    console.log("Selected: " + selected.length + " item(s), [" + selected.join(", ") + "]");
                }
            });
        </script>
    </jsp:attribute>
</t:footer>
