<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:head pageTitle="principal">

    <jsp:attribute name="assets">
        <t:assets>
        </t:assets>
        <script type="text/javascript">
            var Entidad = "${entityName}";
            var Fields = ${fields};
            var Columns = ${columns};
        </script>
    </jsp:attribute>

</t:head>

<t:body adminTitle="${entityName}" adminDescp="${adminDesc}">

    <jsp:attribute name="sidemenu">
        <t:sidemenu></t:sidemenu>
    </jsp:attribute>

    <jsp:attribute name="body">
        <!-- here is supposed to be a fucking method to generate the
         fucking forms  -->
    </jsp:attribute>

    <jsp:attribute name="frm">
        ${grid}
    </jsp:attribute>
</t:body>

        <!-- TODO : IF IT'S LOGGED -->
<t:Egrid></t:Egrid>
<t:footer></t:footer>