<%--
  Created by IntelliJ IDEA.
  User: Vitaliy
  Date: 05.07.2019
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>
    <h2> My Products:</h2>
    <c:out value="${info}"/>
    <c:out value="${sessionScope.Box}"/>
    <table border="1" align="center">
        <tr>
            <th> Name</th>
            <th> description</th>
            <th> Price</th>
            <th> Actions</th>
            <c:forEach var="currentProduct" items="${allProductList}">
        <tr>
            <td> ${currentProduct.getName()}</td>
            <td> ${currentProduct.getDescription()}</td>
            <td> ${currentProduct.getPrice()}</td>
            <td>
                <form action="/product/basket/add" method="get">
                    <button name="addInBox" type="submit" value="${currentProduct.getId()}">Add in Box</button>
                </form>
                <c:if test="${user.getRole() eq 'admin'}">
                <form action="/product/delete" method="post">
                    <button name="delete" type="submit" value="${currentProduct.getId()}">Delete</button>
                </form>
                <form action="/product/edit" method="get">
                    <button name="edit" type="submit" value="${currentProduct.getId()}">Edit</button>
                </form>
            </td>
            </c:if>

        </tr>
        </c:forEach>
    </table>
    <form action="/confirmation/userOrder">
        <button type="submit"> Confirm order</button>
    </form>
    <c:if test="${user.getRole() eq 'admin'}">
    <center>
        <form action="/product/create" method="get">
            <a href="/product/create"> NewProduct </a><br>

            <form action="/user/all" method="post">
                <a href="/user/all"> All Users </a><br>
            </form>
            </c:if>
</body>
</html>
