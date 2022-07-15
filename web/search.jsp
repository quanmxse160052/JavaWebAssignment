<%-- 
    Document   : search
    Created on : Jun 8, 2022, 7:44:41 AM
    Author     : Dell
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>

    </head>
    <body>

        <font color = "Red">
        Welcome, ${sessionScope.USER.fullName}               

        </font>
        <!--logout-->
        <form action="logoutAction" method="POST">
            <input type="submit" name="btAction" value="Logout"/>
        </form>


        <h1>Search page</h1>
        <a href="shoppingPageAction">Shopping</a>

        <form action="searchAction" method="POST">

            Search value <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" />
            <input type="submit" value="Search" name="btAction"/>
        </form>
        <!--get role of user has logged in-->    
        <c:set var="role" value="${sessionScope.USER.role}"/>
        <c:if test="${not empty param.txtSearchValue}" >
            <c:set var="searchResult" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty searchResult}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Admin</th>
                                <c:if test="${role}">
                                <th>Delete</th>
                                </c:if>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${searchResult}" varStatus="status">
                        <form action="updateAction" method="POST">
                            <tr>
                                <td>
                                    ${status.count}
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" value="${dto.username}" name="txtUsername" />
                                </td>

                                <td>
                                    <c:if test="${dto.username==sessionScope.USER.username}">
                                        <input type="text" value="${dto.password}" name="txtPassword" />
                                    </c:if>
                                    <c:if test="${dto.username!=sessionScope.USER.username}">
                                        ******
                                    </c:if>
                                </td>
                                <td>
                                    ${dto.fullName}
                                </td>
                                <td>
                                    <c:if test="${role}">
                                        <input type="checkbox" name="chkAdmin" value="ON"
                                               <c:if test="${dto.role}">
                                                   checked="checked" 
                                               </c:if>
                                               <c:if test="${dto.username==sessionScope.USER.username}">
                                                   disabled="disable" 
                                               </c:if>
                                               />
                                        <c:if test="${dto.username==sessionScope.USER.username}">
                                            <input type="hidden" name="chkAdmin" value="ON"/>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${role == false}">
                                        <input type="checkbox" name="chkAdmin" value="ON" disabled="disable"
                                               <c:if test="${dto.role}">
                                                   checked="checked" 
                                               </c:if>
                                               />
                                    </c:if>

                                </td>
                                <c:if test="${role }">
                                    <td>
                                        <c:url var="deleteLink" value="deleteAction">
                                            <c:param name="pk" value="${dto.username}"/>
                                            <c:param name="lastSearchValue" value="${param.txtSearchValue}"/>
                                        </c:url>
                                        <c:if test="${dto.username != sessionScope.USER.username && dto.role == false}">
                                            <a href="${deleteLink}">Delete</a>
                                        </c:if>
                                    </td>
                                </c:if>
                                <c:if test="${dto.username==sessionScope.USER.username || role == true}">
                                    <td>
                                        <input type="hidden" name="lastSearchValue" value="${param.txtSearchValue}" />
                                        <input type="submit" value="Update" name="btAction" />
                                    </td> 
                                </c:if>

                            </tr>
                        </form>

                    </c:forEach>
                </tbody>
            </table>        
        </c:if>
        <c:if test="${empty searchResult}">
            <h2>Do not have any record!</h2>
        </c:if>
    </c:if>

</body>
</html>
