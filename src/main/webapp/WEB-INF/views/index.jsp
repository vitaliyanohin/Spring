<%--
  Created by IntelliJ IDEA.
  User: Vitaliy
  Date: 03.07.2019
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<center>
    <h2> PLS SIGN IN!</h2>
    <c:out value="${info}"/>
    <form action='<spring:url value="/signin"/>' method="post">
        Email <input name="username" type="email" value="${email}"/><br>
        Password <input name="password" id="password" type="password"><br>
        Repeat password <input name="repeatPassword" id="repeatPassword" type="password"><br>
        <button type="submit"> Sing in</button><br>
        <button type="submit" formaction="/register" formmethod="post">Sing up</button><br>
    </form>

    <button type="submit" formaction="/user/all" formmethod="post"> All Users</button><br>
    <a href="register"> Registration </a><br>
    <a href="/product"> New Product </a><br>
    <a href="/user/admin/all"> All Users </a><br>
    <a href="/product/all"> All Products </a> <br>
</center>
</body>
</html>
