<%@tag description="headtemplate" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="pageTitle" required="true"%>
<%@attribute name="assets" fragment="true"%>
<%@attribute name="preassets" fragment="true"%>

<!DOCTYPE HTML>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>${pageTitle}</title>
        <jsp:invoke fragment="preassets" />
        <t:assets>
        </t:assets>
        <jsp:invoke fragment="assets" />
    </head>