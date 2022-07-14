<%-- 
    Document   : cart.jsp
    Created on : Jun 19, 2022, 9:46:25 PM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="quanmx.cart.CartProduct"%>
<%@page import="java.util.List"%>
<%@page import="quanmx.product.ProductDTO"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your cart</title>

    </head>
    <body>
               <h1>Your cart</h1>
        <c:if test="${not empty sessionScope.USER}">
            <form action="logoutAction" method="POST">
                <input type="submit" name="btAction" value="Logout"/>
            </form>
            <a href="searchPage">Search Page</a>
        </c:if>

        <c:if test="${empty sessionScope.USER}">
            <a href="loginPage">Login</a>
        </c:if>
        <c:set var="products" value="${requestScope.PRODUCTS}"/>
        <c:if test="${ not empty products}">
            <form action="removeCartProduct" method="POST">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Sku</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>    
                        <c:forEach var="pro" items="${products}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td>${pro.product.sku}</td>
                                <td>${pro.product.name}</td>                    
                                <td>${pro.product.description}</td>
                                <td>${pro.product.price}</td>
                                <td>${pro.quantity}</td>
                                <td>
                                    <input type="checkbox" name="chkitems" value="${pro.product.sku}" />
                                </td>
                            </tr>    
                        </c:forEach>


                    <td colspan="6">
                        <c:url var="addMoreProduct" value="shoppingPageAction">
                            <c:param name="btAction" value="shopping"/>
                        </c:url>
                        <a href="${addMoreProduct}">Add more product!</a>
                    </td>
                    <td>
                        <input type="submit" value="Remove product(s)" name="btAction"/>
                    </td>
                    </tbody>
                </table>
            </form>    
            <form action="checkOutAction" method="POST">
                <input type="submit" value="Check out" name="btAction" />
            </form>
        </c:if>

        <c:if test="${empty products}">
            <h3>You do not have any product in cart!</h3>
            <c:url var="addMoreProduct" value="shoppingPageAction">
                <c:param name="btAction" value="shopping"/>
            </c:url>
            <a href="${addMoreProduct}">Shopping now!</a>
        </c:if>

    </body>
</html>
