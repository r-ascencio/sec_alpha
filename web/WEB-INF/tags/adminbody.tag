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
<%@attribute name="iwantnav" required="false"%>

<body>
    <div class="container">
        <c:if test="${iwantnav == null}">
            <div class="row navbar" style="padding: 15px 0px; background: #676F73">
                <div class="sevencol">
                    <div class="onecol"></div>
                    <div class="sevencol" style="
                         line-height: 20px;">
                        ${adminTitle}
                    </div>
                    <div class="fivecol last">
                    </div>
                </div>
                <div class="onecol"></div>

                <c:if test="${admin == 1}">

                    <div class="fourcol last">
                        <div class="fivecol"></div>
                        <div class="fivecol">
                            <span class="rgt" style="line-height: 20px;">
                                ${userName}
                            </span>
                        </div>
                        <div class="onecol last">
                            <div class="btn icon" style="padding: 0px 8px 8px 8px;">
                                <div class="entypo cog" style="color: #676F73"></div>
                            </div>
                        </div>
                    </div>

                </c:if>

            </div>
        </c:if>
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