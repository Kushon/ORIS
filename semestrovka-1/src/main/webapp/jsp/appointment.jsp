<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Запись на услугу</title>
<link rel="stylesheet" href="css/appointment.css">
</head>
<body>
<nav>
    <a href="/profile">Профиль</a>
    <a href="/price">Прайс-лист</a>
</nav>
<div class="container">
    <h2>Запись на услугу: ${procedure.name}</h2>
    <form action="/appointment" method="post">
        <input type="hidden" name="procedureId" value="${procedure.id}">

        <label for="employeeId">Выберите сотрудника:</label>
        <select name="employeeId" id="employeeId" required>
            <c:forEach items="${employees}" var="employee">
                <option value="${employee.id}">${employee.name}</option>
            </c:forEach>
        </select>

        <label for="appointmentDate">Выберите дату и время:</label>
        <input type="datetime-local" id="appointmentDate" name="appointmentDate" required>

        <input type="submit" value="OK">
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const now = new Date();
        const offset = now.getTimezoneOffset() * 60000;
        const localISOTime = new Date(now - offset).toISOString().slice(0, 16);

        const appointmentDateField = document.getElementById('appointmentDate');
        appointmentDateField.setAttribute('min', localISOTime);


        const maxDate = new Date(now);
        maxDate.setMonth(maxDate.getMonth() + 2);
        const maxISOTime = new Date(maxDate - offset).toISOString().slice(0, 16);
        appointmentDateField.setAttribute('max', maxISOTime);
    });

    document.querySelector('form').addEventListener('submit', function (e) {
        e.preventDefault();

        const appointmentDate = document.getElementById('appointmentDate').value;

        if (!appointmentDate) {
            alert('Пожалуйста, выберите дату и время.');
            return;
        }

        const selectedDate = new Date(appointmentDate);
        const currentDate = new Date();
        const maxAllowedDate = new Date();
        maxAllowedDate.setMonth(currentDate.getMonth() + 2);

        if (selectedDate < currentDate) {
            alert('Дата и время не могут быть в прошлом.');
            return;
        }

        if (selectedDate > maxAllowedDate) {
            alert('Вы не можете записаться более чем на 2 месяца вперед.');
            return;
        }

        this.submit();
    });
</script>


</body>
</html>
