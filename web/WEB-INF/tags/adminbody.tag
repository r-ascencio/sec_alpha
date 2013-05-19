<%@tag description="required assets" pageEncoding="UTF-8"%>


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
        <div class="row navbar"></div>
        <div class="row main">
            <jsp:invoke fragment="sidemenu"></jsp:invoke>
                <div id="base" class="ninecol">
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