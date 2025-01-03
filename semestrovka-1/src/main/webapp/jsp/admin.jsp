<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Панель администратора</title>
    <link rel="stylesheet" href="/css/admin.css">
</head>
<body>
<h1>Панель администратора</h1>
<div class="model-links">
    <a href="/admin/accounts">Accounts</a>
    <a href="/admin/appointments">Appointments</a>
    <a href="/admin/clients">Clients</a>
    <a href="/admin/employees">Employees</a>
    <a href="/admin/persons">Persons</a>
    <a href="/admin/procedures">Procedures</a>
    <a href="/admin/users">Users</a>
    <a href="/admin/employee_procedures">Employee_procedures</a>
</div>

<table>
    <thead>
    <tr>
       <c:forEach items="${columns}" var="column">
           <td>
               ${column}
           </td>
       </c:forEach>
    </tr>
    </thead>

    <tbody>

        <c:forEach items="${rows}" var="row">
        <tr>
                <c:forEach items="${row}" var="column">
                    <td>
                        ${column}
                    </td>
                </c:forEach>
        </tr>
        </c:forEach>

    </tbody>
</table>
</body>
</html>
