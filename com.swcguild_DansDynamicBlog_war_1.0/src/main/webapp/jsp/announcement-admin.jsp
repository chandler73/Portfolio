<%-- 
    Document   : blog-admin
    Created on : Nov 5, 2015, 11:14:35 AM
    Author     : Suzanne Ludwig
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blogstyles.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.structure.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.theme.min.css" />

        <title>Dan's Dynamic Blog - Admin Announcement</title>
    </head>
    <body>
        <div id="header">
            <a href="${pageContext.request.contextPath}/home"><h1>Dan's Dynamic Blog</h1></a>
            <div id="getdown">
                <div class="dropdown">
                    <button class="btn btn-default custom-width dropdown-toggle" type="button" data-toggle="dropdown">Announcements
                        <span class="caret"></span></button>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="${pageContext.request.contextPath}/blog-admin">Blogs</a></li>
                        <li><a href="${pageContext.request.contextPath}/page-admin">Webpages</a></li>
                    </ul>
                </div>
            </div>
            <button id="logout-button" class='btn btn-default'>
                Log Out
            </button>
        </div>

        <div class="container-replacer">

            <div class="col-md-3 grey" id="left-links">
                <div class="unpublished-blog-links" id="announcement-links">
                    <h3>Announcements</h3>
                </div>
            </div>

            <div class="col-md-offset-3 col-md-9 grey" id="editor-content">
                <div class="col-md-9">
                    <div style = "position: relative"> 
                        <div id="grey-box"
                             style = "z-index: 1;
                             position: absolute; 
                             background-color: grey; 
                             opacity: 0.8;
                             width: 100%;
                             height: 600px;
                             background: #ddd;
                             text-align: center;
                             padding-top: 200px;">
                            Click "Create" to make an entry <br>
                            Or select an announcement to edit.
                        </div>
                        <textarea id="announcement_entry" style = "position: absolute;
                                  width: 100%;
                                  height: 250px;">
                        </textarea>
                    </div>
                    <br>
                    <form class="form-horizontal" role="form">
                        <div class="form-group">

                        </div>
                        <div class="checkbox" >
                            <label><input type="checkbox" id="publish-date-checkbox"> <strong>Publish Date</strong></label>
                            <input type="text" class="datepicker" id="publish-date" disabled />
                        </div>
                        <div class="checkbox" >
                            <label><input type="checkbox" id="expiry-date-checkbox"> <strong>Expiry Date</strong></label>
                            <input type="text" class="datepicker" id="expiry-date" disabled />
                        </div>

                    </form>
                    <div id="editor-buttons">
                        <button id="announcement-save-button" type="submit" class='btn btn-success'>Save/Publish</button>
                        <button id="announcement-delete-button" class='btn btn-default'>Delete</button>
                        <input id="announcement-id" type="text" style="display:none">
                    </div>
                </div>
                <div class="col-md-3">
                    <button id="create-announcement-button"
                            class='btn btn-success'>Create</button>
                </div>
            </div>

        </div>



        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/blogJS.js"></script>
        <script type="text/javascript">
            tinymce.init({
                selector: "#announcement_entry"
            });
        </script>
    </body>
</html>
