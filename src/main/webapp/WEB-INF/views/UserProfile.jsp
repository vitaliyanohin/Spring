<%--
  Created by IntelliJ IDEA.
  User: Vitaliy
  Date: 11.07.2019
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center> <h2> <c:out value="${inform}"/> </h2> </center>
<center>
    <h2> Your Profile:</h2>
    <a href="/product/admin/create"> New Product </a><br>
    <a href="/product/all"> All Products </a> <br>
    <a href="myOrders"> My Orders </a> <br>
        <c:if  test="${user.getRole() eq 'ROLE_ADMIN'}" >
    <h3> Add NEW USER:</h3>
    <form action="/register" method="post">
        Email <input name="email" type="email"/><br>
        Password <input name="pass" type="password"><br>
        Repeat password <input name="repeatPassword" type="password"><br>
        <input type="radio" name="role" value="ROLE_USER">Role: user<Br>
        <input type="radio" name="role" value="ROLE_ADMIN"> Role: admin<Br>
        <button type="submit"> Submit </button><br>
        <button type="submit" formaction="/user/admin/all" formmethod="get">All Users</button><br>
    </form>
        </c:if>

    <a href='<spring:url value="/signout"/>'>Logout</a>
</body>
</html>
