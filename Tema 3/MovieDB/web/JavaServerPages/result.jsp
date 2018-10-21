<%--
  Created by IntelliJ IDEA.
  User: dan.cazacu
  Date: 10/10/2018
  Time: 2:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="" uri = "/WEB-INF/custom.tld" %>
<html>
<style>
    table {
        font-family: arial, sans-serif;
        width: 100%;
        border: 1px solid black;
    }

    td, th {
        border: 1px solid black;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }
</style>
<head>
</head>
<body>
<button type="button"><a href="/">Back</a></button>
<table>
    <%=
    request.getSession().getAttribute("database")
    %>
</table>

<%--<p>--%>
    <%--<%=--%>
        <%--request.getAttribute("database")--%>
    <%--%>--%>
<%--</p>--%>
<p><:record key="Scary Movie"></:record></p>

<%--<fmt:bundle basename="out.artifacts.MovieDB_war_exploded.Resources.database">--%>
<%--<table>--%>
    <%--<tr>--%>
        <%--<td><fmt:message key="movie.1.name"></fmt:message></td>--%>
        <%--<td><input type=”text”></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td><fmt:message key="movie.1.description"></fmt:message></td>--%>
        <%--<td><input type=”password”></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td> <input type=”submit” value=”Login”/> </td>--%>
    <%--</tr>--%>
<%--</table>--%>
<%--</fmt:bundle>--%>
</body>
</html>
