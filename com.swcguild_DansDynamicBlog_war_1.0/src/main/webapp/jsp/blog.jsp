<%-- 
    Document   : blog
    Created on : Nov 5, 2015, 11:14:25 AM
    Author     : Suzanne Ludwig, Dessa Brewington
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <style type="text/css">

            .blog_entry{
                width: 100%;
                height: 400px;
                background: #ddd;
            }

        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blogstyles.css" />
        <title>Dan's Dynamic Blog - Admin</title>
    </head>

    <body>
        <div id="header">
            <a href="${pageContext.request.contextPath}/home"><h1>Dan's Dynamic Blog</h1></a>
            <button id="logout-button" class='btn btn-default'>
                Log Out
            </button>
        </div>

        <div class="container-replacer">

            <div class="col-md-3 grey" id="left-links">
                <div id="unsubmitted-blogs" class="unpublished-blog-links">
                    <h3>Unsubmitted Blogs</h3>
                </div>
            </div>

            <div class="col-md-offset-3 col-md-9 grey" id="editor-content">
                <div class="col-md-9">
                    <form>
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
                                Or select a blog to edit.
                            </div>
                            <textarea id="blog_entry" 
                                      style = "position: absolute;
                                      width: 100%;
                                      height: 400px;">
                            </textarea>
                        </div>

                        <br>

                        <div id="editor-buttons">
                            <button id="submit-button" type="submit" class='btn btn-success'>Submit For Approval</button>
                            <button id="delete-button" class='btn btn-default'>Delete</button>
                            <button id="save-button" class='btn btn-default'>Save</button>
                            <input id="blogpost-id" type="text" style="display:none">
                        </div>
                    </form>
                </div>

                <div class="col-md-3">
                    <button id="create-button"
                            data-target="#create-modal"
                            data-toggle="modal"
                            class='btn btn-success'>Create
                    </button>
                </div>
            </div>
        </div>

        <div class="modal fade" id="create-modal" tabindex="-1" role="dialog"
             aria-labelledby="pages-modal-label" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span>
                            <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title" id="pages-modal-label">Create New Blog</h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="add-title" class="col-md-4 control-label">
                                    Title:
                                </label>

                                <div class="col-md-8">
                                    <input type="text"
                                           class="form-control"
                                           id="add-title"
                                           placeholder="Title" />
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <button type="submit"
                                            id="add-button"
                                            class="btn btn-default">
                                        Create Blog
                                    </button> 

                                    <button id="cancel-button"
                                            class="btn btn-default">
                                        Cancel
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>     
            </div>
        </div>


        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/temp.js"></script>
        <script src="${pageContext.request.contextPath}/js/blogJS.js"></script>
        <script type="text/javascript">
            tinymce.init({
                selector: "#blog_entry"
            });
        </script>
    </body>
</html>
