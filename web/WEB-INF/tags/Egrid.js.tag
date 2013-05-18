<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" 
          uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="baseURL" 
       value="${fn:replace(pageContext.request.requestURL, 
                pageContext.request.requestURI, 
                pageContext.request.contextPath)}" />

<script  type="text/javascript">
    var BaseUrl = "${baseURL}";
</script>
<script type="text/javascript" src="${baseURL}/assets/js/Egrid.js"></script>
</script>