<%-- 
    Document   : redirect
    Created on : Nov 17, 2015, 3:10:19 PM
    Author     : apprentice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blogstyles.css" />
        <title>Dan's Dynamic Blog</title>
    </head>
    <body>
        
        <div id="header">
            <a href="${pageContext.request.contextPath}/home"><h1>Dan's Dynamic Blog</h1></a>
        </div>
        <div class='container'>
            <br/>
            <br/>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="${pageContext.request.contextPath}/blog-admin"><h2>To admin page</h2></a><br/>
            </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <a href="${pageContext.request.contextPath}/blog"><h2>To blogger page</h2></a><br/>
            </sec:authorize>
        </div>

        
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/blogJS.js"></script>
    <script>
        
        
        
        
    </script>
    </body>
    
</html>
