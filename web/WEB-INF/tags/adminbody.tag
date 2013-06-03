<%@tag description="required assets" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, 
                              pageContext.request.requestURI, 
                              pageContext.request.contextPath)}" />


<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!--  fragments  -->
<%@attribute name="sidemenu" fragment="true"%>
<%@attribute name="frm" fragment="true"%>
<%@attribute name="body" fragment="true"%>

<!--  parameters  -->
<%@attribute name="adminTitle" required="true"%>
<%@attribute name="adminDescp" required="true"%>

<body>
    <div class="container">
        <div class="row navbar" style="padding: 15px 0px;">
            <div class="fourcol">
                <div class="fivecol"></div>
                <div class="sixcol last">
                    ${userName}
                </div>
            </div>
            <div class="fourcol"></div>
            <div class="fourcol last">
                <div class="fivecol"></div>
                <div class="sixcol last">
                    <a href="${baseURL}/admin/logout/">
                        Cerrar Session.
                    </a>
                </div>
            </div>
        </div>
        <div class="row main">
            <jsp:invoke fragment="sidemenu"></jsp:invoke>
                <div id="base" class="tencol last">
                    <div class="header">
                        <div class="title">${adminTitle}</div>
                    <p>${adminDescp}</p>
                </div>
                <div class="body" id="body">
                    <jsp:invoke fragment="body"></jsp:invoke>
                        <div class="twelvecol">
                            <div id="grid"></div>
                        <jsp:invoke fragment="frm"></jsp:invoke>
                    </div>

                    <div class="twelvecol">
                        <div class="data-explorer-here"></div>
                        <div style="clear: both;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>