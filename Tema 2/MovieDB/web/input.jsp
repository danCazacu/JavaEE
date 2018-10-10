<%--
  Created by IntelliJ IDEA.
  User: dan.cazacu
  Date: 10/10/2018
  Time: 9:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MovieDB</title>
</head>
<body>
<p style="font-family:courier;color:tomato"><i>Add or update movie in database</i></p>

<form method="POST" action="InputController">

    <p style="color:darkorchid;">Movie name:</p>
    <input type="text" name="name" size="20" value=""/><br/>

    <p style="color:blue;">Movie description:</p>
    <input type="text" name="description" size="20" value=""/> <br/>

    <p style="color:green;">Movie genre:</p>
    <select name="genre">
        <%=request.getAttribute("select")%>
    </select>

    <br/>
    <br>
    <input type="submit" name="submit" value="Submit">
</form>
</body>
</html>
