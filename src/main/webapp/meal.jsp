<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 23.10.2023
  Time: 1:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .normal {color:green;}
        .exceeded {color:red;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th>
                Дата и время
            </th>
            <th>
                Описание
            </th>
            <th>
                Калории
            </th>
        </tr>
    </thead>
    <c:forEach items="${listOfMeals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.exceed ? exceeded : normal}">
            <td>${meal.description}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            </tr>
    </c:forEach>
</table>
</body>
</html>
