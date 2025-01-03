<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
    <link rel="stylesheet" href="css/profile.css">


</head>
<body>
<nav>
    <a href="/home">Главная</a>
    <a href="/price">Прайс-лист</a>
</nav>
<div class="container">
    <h1>Профиль</h1>
    <p><strong>Имя:</strong> ${name}</p>
    <p><strong>Email:</strong> ${email}</p>

    <h2>Активные записи</h2>
    <table>
        <thead>
        <tr>
            <th>Дата</th>
            <th>Услуга</th>
            <th>Мастер</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${activeAppointments}" var="appointment">
            <tr>
                <td>${appointment.date}</td>
                <td>${appointment.service}</td>
                <td>${appointment.master}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>История записей</h2>
    <table>
        <thead>
        <tr>
            <th>Дата</th>
            <th>Услуга</th>
            <th>Мастер</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointmentHistory}" var="appointment">
            <tr>
                <td>${appointment.date}</td>
                <td>${appointment.service}</td>
                <td>${appointment.master}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="/price" class="button">Записаться</a>

    <form action="/logout" method="POST">
        <button type="submit">Выйти</button>
    </form>
</div>

</body>
</html>
