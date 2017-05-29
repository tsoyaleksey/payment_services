
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 style="text-align: center" class="alert-danger">${requestScope['javax.servlet.error.status_code']}</h1>
<h3 style="text-align: center" class="alert-warning">OOPS, SORRY WE CAN'T FIND THAT PAGE!</h3>