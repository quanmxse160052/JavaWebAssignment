<%-- 
    Document   : shopping
    Created on : Jun 19, 2022, 8:12:27 PM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="quanmx.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>

    </head>
    <body>
        <h1> QUAN DEP TRAI STORE</h1>
        <c:if test="${not empty sessionScope.USER}">
            <form action="logoutAction" method="POST">
                <input type="submit" name="btAction" value="Logout"/>
            </form>
            <a href="searchPage">Search Page</a>
        </c:if>

        <a href="viewCartAction">View cart</a>


        <c:if test="${empty sessionScope.USER}">
            <a href="loginPage">Login</a>
        </c:if>
        <c:set var="products" value="${requestScope.PRODUCTS}"/>
        <c:if test="${not empty products}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Sku</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Add to cart</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${products}" >
                    <form action="addToCartAction" method="POST">

                        <tr>

                            <td>${dto.sku}
                                <input name="productID" value="${dto.sku}" type="hidden"/>
                            </td>
                            <td>${dto.name}</td>
                            <td>${dto.description}</td>
                            <td>${dto.price}</td>
                            <td> <input type="submit" value="Add to cart" name="btAction"/></td>
                        </tr>
                    </form>        
                </c:forEach>

            </tbody>
        </table>
    </c:if>


    <c:if test="${empty products}">
        <h3>Do not have any product to buy now!</h3>
    </c:if>
</body>

</html>
