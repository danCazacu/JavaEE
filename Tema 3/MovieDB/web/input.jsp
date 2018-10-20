<%--
  Created by IntelliJ IDEA.
  User: dan.cazacu
  Date: 10/10/2018
  Time: 9:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  
<style>
    input[type=text] {
        padding: 12px 16px;
        box-sizing: border-box;
        border: blue;
        border-style: dot-dot-dash;
        border-radius: 25px;
        background-color: #7dacbc;
        color: white;
    }

    input[type=submit] {
        padding: 12px 26px;
        box-sizing: border-box;
        border: darkslategrey;
        border-radius: 25px;
        background-color: grey;
        color: black;
        margin: 20px 35px;
    }

    input[type=radio]{

        padding-right: 200px;
    }

    select{

        padding: 12px 20px;
    }

</style>
<head>
    <title>MovieDB</title>
</head>
<body>
<p style="font-family:courier;color:black"><i>Add or update movie in database</i></p>

<form method="POST" action="InputController">

    <pre style="font-family:courier;color:red"><%=request.getSession().getAttribute("error")%></pre>
    <input type="radio" name="operationType" value="create"> CREATE <br>
    <input type="radio" name="operationType" value="update"> UPDATE<br>
    <input type="radio" name="operationType" value="get"> GET <br>

    <p style="color:blue;">Movie name:</p>
    <input type="text" name="name" size="20" value="<%=request.getSession().getAttribute("moviename")%>"/><br/>

    <p style="color:blue;">Movie description:</p>
    <input type="text" name="description" size="20" value="<%=request.getSession().getAttribute("moviedesc")%>"/> <br/>

    <p style="color:green;">Movie genre:</p>
    <select name="genre">
        <%=request.getSession().getAttribute("select")%>
    </select>

    <br>

    <p style="color:blue;">Enter text:<img src="CAPTCHAProviderServlet"/></p>
    <input type="text" name="captcha" size="20" value=""/> <br>
    <input type="submit" name="submit" value="Submit">


</form>
</body>
</html>
