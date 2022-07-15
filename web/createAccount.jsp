<%-- 
    Document   : createAccount
    Created on : Jul 6, 2022, 9:47:49 AM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create New Account</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <body>
        <h1>Create New Account</h1>
        <a href="loginPage">Login Page</a> 
        <a href="shoppingPageAction">Shopping</a>
        <br/>

        <c:set var="errors" value="${requestScope.CREATE_ERROR}"/>
        <form action="createNewAccountAction" method="POST" >
            Username* <input type="text" name="txtUsername" value="${param.txtUsername}" /> e.g 6-12 chars <br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                ${errors.usernameLengthErr}
                </font>
                <br/>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red">
                ${errors.usernameIsExisted}
                </font>
                <br/>
            </c:if>
            Password* <input type="password" name="txtPassword" value="" /> e.g 6-30 chars <br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}
                </font>
                <br/>
            </c:if>
            Confirm* <input type="password" name="txtConfirm" value="" /><br/>
            <c:if test="${not empty errors.confirmNotMatchErr}">
                <font color="red">
                ${errors.confirmNotMatchErr}
                </font>
                <br/>
            </c:if>
            Full name* <input type="text" name="txtFullName" value="${param.txtFullName}" /> e.g 2-50 chars<br/>
            <c:if test="${not empty errors.fullNameLengthError}">
                <font color="red">
                ${errors.fullNameLengthError}
                </font>
                <br/>
            </c:if>
            <input type="reset" value="Reset" />
            <input type="submit" value="Create New Account" name="btAction"/>
        </form>
    </body>
</html>

