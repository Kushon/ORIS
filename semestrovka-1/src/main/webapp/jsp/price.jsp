<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Прайс-лист</title>
    <link rel="stylesheet" href="css/price.css">
</head>
<body>
<nav>
    <a href="/home">Главная</a>
    <a href="/profile">Профиль</a>
</nav>
<h1>Прайс-лист</h1>
<c:forEach items="${procedures}" var="procedure">
    <div class="service">
        <h3>${procedure.name}</h3>
        <p class="description"><strong>Описание:</strong> ${procedure.description}</p>
        <p class="price"><strong>Стоимость:</strong> ${procedure.price} руб.</p>
        <%-- <button class="book-button" onclick="bookService(${procedure.id})">Записаться</button> --%>
        <a class="button" href="/appointment?id=${procedure.id}">Записаться</a>
    </div>
</c:forEach>

</body>
</html>
