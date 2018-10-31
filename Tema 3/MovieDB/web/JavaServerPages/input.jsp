<%--
  Created by IntelliJ IDEA.
  User: dan.cazacu
  Date: 10/10/2018
  Time: 9:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  
<style type="text/css">

    body{
        background-image: url("images/background.jpg");
        margin-left: 2%;
        margin-top: 1%;
    }
    input[type=text] {
        padding: 12px 16px;
        box-sizing: border-box;
        border: blue;
        border-style: outset;
        border-radius: 25px;
        border-color: #7dacbc;
        color: black;
        font-family: "Comic Sans MS";
    }

    input[type=submit] {
        padding: 12px 26px;
        box-sizing: border-box;
        border: darkslategrey;
        border-radius: 25px;
        border-style: outset;
        border-color: #656369;
        background-color: grey;
        color: black;
        margin: 20px 35px;
    }

    input[type=radio]{

        padding-right: 200px;
        margin-left: 3%
    }

    select{

        padding: 12px 20px;
        width:200px;
        border-radius: 25px;
        border: blue;
        border-style: outset;
        border-radius: 25px;
        border-color: #7dacbc;
        margin-left: 1%;
    }

    img {
        border-radius: 25%;
    }

</style>
<head>
    <title>MovieDB</title>
</head>

<div class="white-text landing-page" id="landing-page">
<body>
<p style="font-family:courier;color:black"><i>Add or update movie in database</i></p>

<form method="POST" action="/InputController">

    <pre style="font-family:courier;color:red"><%=request.getSession().getAttribute("error")%></pre>
    <input type="radio" name="operationType" value="create"> CREATE <br>
    <input type="radio" name="operationType" value="update"> UPDATE<br>
    <input type="radio" name="operationType" value="get"> GET <br>

    <p style="color:blue;margin-left: 3%">Movie name:</p>
    <input type="text" name="name" size="20" value="<%=request.getSession().getAttribute("moviename")%>"/><br/>

    <p style="color:#3762af;margin-left: 2%">Movie description:</p>
    <input type="text" name="description" size="20" value="<%=request.getSession().getAttribute("moviedesc")%>"/> <br/>

    <p style="color:green;margin-left: 3.5%">Movie genre:</p>

    <select name="genre">
        <option value=""></option>
        <%=request.getSession().getAttribute("select")%>
    </select>
    <br>

    <p style="color:blue;">Enter text:<img src="/CAPTCHAProviderServlet"/></p>
    <input type="text" name="captcha" size="20" value=""/> <br>
    <input type="submit" name="submit" value="Submit">


</form>
</body>
</div>
</html>
