<%--
  Created by IntelliJ IDEA.
  User: dan.cazacu
  Date: 10/10/2018
  Time: 2:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title><%=request.getParameter("name")%></title>
</head>
<body>

<table>
    <%=
    request.getAttribute("database")
    %>
</table>

<%--<p>--%>
    <%--<%=--%>
        <%--request.getAttribute("database")--%>
    <%--%>--%>
<%--</p>--%>
</body>
</html>
