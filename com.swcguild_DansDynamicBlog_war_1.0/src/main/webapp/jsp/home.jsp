<%-- 
    Document   : home
    Created on : Nov 5, 2015, 11:13:04 AM
    Author     : Suzanne Ludwig
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <a href="${pageContext.request.contextPath}/redirect"><button id="login-button" class='btn btn-default'>
                Log In
            </button></a>
        </div>
        <div class='container'>

            <div class="col-md-4" id="left-links">
                <div id="page-links"></div>
                <div id="category-links"></div>
            </div>
            <div class="col-md-offset-4 col-md-8" id="announcements"></div>
            <div class="col-md-offset-4 col-md-8" id="blog-content"></div>
        </div>

        <div class="modal fade" id="pages-modal" tabindex="-1" role="dialog"
             aria-labelledby="pages-modal-label" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span>
                            <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title" id="pages-modal-label"></h4>
                    </div>
                    <div class="modal-body">
                        <div id="page-content"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            Close
                        </button>
                    </div>
                </div>     
            </div>
        </div>
        
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/blogJS.js"></script>
    </body>
    
</html>
