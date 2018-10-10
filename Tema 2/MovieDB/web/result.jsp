<%--
  Created by IntelliJ IDEA.
  User: dan.cazacu
  Date: 10/10/2018
  Time: 2:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=request.getParameter("name")%></title>
</head>
<body>
<p>
    <%=
        request.getAttribute("database")
    %>
</p>
</body>
</html>
