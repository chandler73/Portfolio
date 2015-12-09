$(document).ready(function () {

    enableDatePicker();
    checkboxSetup();

    loadAnnouncements();
    loadBlogs();
    loadWebPages();
    loadCategories();
    loadUnsubmittedBlogs();
    loadUnapprovedBlogs();
    loadUnpublishedBlogs();
    loadAnnouncementLinks();
    loadWebPagesForEdits();

    $('#add-button').click(function (event) {
        event.preventDefault();

        $.ajax({
            type: "POST",
            url: "blogpost",
            data: JSON.stringify({
                userID: getUserId(),
                title: $("#add-title").val(),
                submitted: 0,
                creationDate: Date.now(),
                publishDate: "3000-01-02",
                expiryDate: "3000-01-02",
                content: "",
                categoryIDs: null
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            dataType: "json"
        }).success(function (data, status) {
            $("#grey-box").hide();
            $("#create-modal").hide();
            $("#add-title").val("");
            $('#blogpost-id').val(data.blogID);
            loadUnsubmittedBlogs();
            loadUnpublishedBlogs();
        }); //end success
    }); //end add-button

    $('#save-button').click(function (event) {
        event.preventDefault();
        var id = $('#blogpost-id').val();
        getBlogPostById(id, "save");
    });

    $('#announcement-save-button').click(function (event) {
        event.preventDefault();
        var id = $('#announcement-id').val();
        getAnnouncementById(id);
    });

    $('#delete-button').click(function (event) {
        event.preventDefault();
        var id = $('#blogpost-id').val();
        if (confirm("Delete?")) {
            $.ajax({
                type: "DELETE",
                url: "blogpost/" + id
            }).success(function () {
                $('#blogpost-id').val("");
                $('#grey-box').show();
                tinyMCE.activeEditor.setContent('');
                loadUnsubmittedBlogs();
            });
        }
    });

    $('#announcement-delete-button').click(function (event) {
        event.preventDefault();
        var id = $('#announcement-id').val();
        if (confirm("Delete?")) {
            $.ajax({
                type: "DELETE",
                url: "announcement/" + id
            }).success(function () {
                $('#announcement-id').val("");
                $('#grey-box').show();
                tinyMCE.activeEditor.setContent('');
                loadAnnouncementLinks();
                $('#publish-date').val("");
                $('#expiry-date').val("");
            });
        }
    });
    
    $('#page-delete-button').click(function (event) {
        event.preventDefault();
        var id = $('#page-id').val();
        if (confirm("Delete?")) {
            $.ajax({
                type: "DELETE",
                url: "page/" + id
            }).success(function (){
                tinyMCE.activeEditor.setContent('');
                loadWebPagesForEdits();
                $('#grey-box').show();
            })
        }
    })

    $("#submit-button").click(function (event) {
        event.preventDefault();
        var id = $("#blogpost-id").val();
        if (confirm("Submit????")) {
            getBlogPostById(id, "submit");
        }
    });
    
    $('#page-submit-button').click(function (event){
        event.preventDefault();
        var id = $('#page-id').val();
        getWebPageById(id);
    })

    $("#create-announcement-button").click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: "announcement",
            data: JSON.stringify({
                publishDate: "3000-01-02",
                expiryDate: "3000-01-02",
                content: ""
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            dataType: "json"
        }).success(function (data, status) {
            $("#grey-box").hide();
            $('#announcement-id').val(data.announcementID);
            tinyMCE.activeEditor.setContent("");
            $('#publish-date').val("");
            $('#expiry-date').val("");
            loadAnnouncementLinks();


        });

    });

    $('#add-page-button').click(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: "page",
            data: JSON.stringify({
                content: "",
                title: $("#add-title").val()
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            dataType: "json"
        }).success(function (data, status) {
            $('#grey-box').hide();
            $('#page-id').val(data.pageID);
            $('#add-title').val("");
            tinyMCE.activeEditor.setContent('');
            loadWebPagesForEdits();
            $("#create-modal").hide();
        });
    });
}); //end document ready

var categoryInts = [];


function loadAnnouncementLinks() {
    $("#announcement-links").empty();
    $("#announcement-links").append(
            $("<h3>").text("Announcements"));
    $.ajax({
        url: "announcements-all"
    }).success(function (data, status) {
        $.each(data, function (index, announcement) {
            var titleString = "";

            if (announcement.content === "") {
                titleString = "(No content)";
            } else {
                var tempArray = (announcement.content).split(" ");


                for (i = 0; i < tempArray.length; i++) {
                    titleString += tempArray[i] + " ";
                    console.log(i + ": " + tempArray[i]);
                    if (i === 2) {
                        break;
                    }
                }
            }

            $("#announcement-links").append($("<a>")
                    .attr({"class": "blog-link", "data-announcement-id": announcement.announcementID})
                    .text(titleString)
                    .on("click", {id: announcement.announcementID}, loadAnnouncementIntoTinyMCE));
        });
    });
}


// unsubmittedBlogs.append($('<a>')
//                    .attr({"class": "blog-link", "data-blog-id": blog.blogID})
//                    .text(blog.title)
//                    .on("click", {id: blog.blogID}, loadBlogIntoTinyMCE));


function enableDatePicker() {
    $(function () {
        $(".datepicker").datepicker({dateFormat: "yy-mm-dd"});
    });
}

function checkboxSetup() {
    $("#publish-date-checkbox").change(function () {
        var field = $("#publish-date");
        toggleInputField(field);
    });

    $("#expiry-date-checkbox").change(function () {
        var field = $("#expiry-date");
        toggleInputField(field);
    });
}

function toggleInputField(field) {
    $(field).prop("disabled", function (i, val) {
        return !val;
    });

    if ($(field).prop("disabled")) {
        $(field).val("");
    }
}

function loadAnnouncements() {
    var content = $("#announcements");

    $.ajax({
        url: "announcements"
    }).success(function (announcements, status) {
        $.each(announcements, function (index, ann) {
            content.append($('<div>')
                    .attr({"class": "announcement"})
                    .append($("<p>").text(ann.content)));
        });
    });
}

function clearBlogs() {
    $("#blog-content").empty();
}

function loadBlogs() {
    clearBlogs();
    var content = $("#blog-content");

    $.ajax({
        url: "blogposts"
    }).success(function (blogs, status) {
        $.each(blogs, function (index, blog) {
            content.append($('<div>')
                    .attr({'class': 'blog'})
                    .append($('<h3>').text(blog.publishDate))
                    .append($('<p>').text(blog.content))
                    .append($('<div>')
                            .attr({'class': 'blog-categories', 'id': blog.blogID})));
            printBlogCategories(blog);
        });
    });
}

function loadBlogsByCategory(event) {
    var catID = event.data.id;

    clearBlogs();
    var content = $("#blog-content");

    $.ajax({
        url: "blogposts/" + catID
    }).success(function (blogs, status) {
        $.each(blogs, function (index, blog) {
            content.append($('<div>')
                    .attr({'class': 'blog'})
                    .append($('<h3>').text(blog.publishDate))
                    .append($('<p>').text(blog.content))
                    .append($('<div>')
                            .attr({'class': 'blog-categories', 'id': blog.blogID})));
            printBlogCategories(blog);
        });
    });
}


function printBlogCategories(blog) {
    var selector = "#" + blog.blogID;
    var categoryDiv = $(selector);

    for (var i = 0; i < blog.categoryIDs.length; i++) {
        var categoryID = blog.categoryIDs[i];

        $.ajax({
            url: "category/" + categoryID
        }).success(function (category, status) {
            categoryDiv.append($('<a>')
                    .attr({"data-category-id": category.categoryID})
                    .text(category.category)
                    .on("click", {id: category.categoryID}, loadBlogsByCategory));
        });
    }
}

function loadWebPages() {
    var pageLinkDiv = $("#page-links");

    $.ajax({
        url: "webpages"
    }).success(function (pages, status) {
        $.each(pages, function (index, page) {
            pageLinkDiv.append($('<a>')
                    .attr({"class": "page-link",
                        "data-page-id": page.pageID,
                        "data-target": "#pages-modal",
                        "data-toggle": "modal"})
                    .text(page.title));
        });
    });
}

function loadWebPagesForEdits() {
    var pageLinkDiv = $("#webpage-links");
    pageLinkDiv.empty();
    pageLinkDiv.append($('<h3>').text('Web Pages'));

    $.ajax({
        url: "webpages"
    }).success(function (pages, status) {
        $.each(pages, function (index, page) {
            pageLinkDiv.append($('<a>')
                    .attr({"class": "blog-link", "data-page-id": page.pageID})
                    .text(page.title)
                    .on("click", {id: page.pageID}, loadPageIntoTinyMCE));
        });
    });
}

function loadCategories() {
    var categoryDiv = $("#category-links");

    $.ajax({
        url: "categories"
    }).success(function (categories, status) {
        $.each(categories, function (index, cat) {
            categoryDiv.append($('<a>')
                    .attr({"data-category-id": cat.categoryID})
                    .text(cat.category)
                    .on("click", {id: cat.categoryID}, loadBlogsByCategory));
        });
    });
}

function loadUnsubmittedBlogs() {
    var unsubmittedBlogs = $("#unsubmitted-blogs");
    unsubmittedBlogs.empty();
    unsubmittedBlogs.append($('<h3>').text("Unsubmitted Blogs"));
    $.ajax({
        url: "blogs"
    }).success(function (blogs, status) {
        $.each(blogs, function (index, blog) {
            unsubmittedBlogs.append($('<a>')
                    .attr({"class": "blog-link", "data-blog-id": blog.blogID})
                    .text(blog.title)
                    .on("click", {id: blog.blogID}, loadBlogIntoTinyMCE));
        });
    });
}

function loadUnapprovedBlogs() {
    var unsubmittedBlogs = $("#unapproved-blogs");
    unsubmittedBlogs.empty();
    unsubmittedBlogs.append($('<h3>').text("Unapproved Blogs"));
    $.ajax({
        url: "blogs-admin"
    }).success(function (blogs, status) {
        $.each(blogs, function (index, blog) {
            unsubmittedBlogs.append($('<a>')
                    .attr({"class": "blog-link", "data-blog-id": blog.blogID})
                    .text(blog.title)
                    .on("click", {id: blog.blogID}, loadBlogIntoTinyMCE));
        });
    });
}

function loadUnpublishedBlogs() {
    var unsubmittedBlogs = $("#unpublished-blogs");
    unsubmittedBlogs.empty();
    unsubmittedBlogs.append($('<h3>').text("Unpublished Blogs"));
    $.ajax({
        url: "blogs-unpublished"
    }).success(function (blogs, status) {
        $.each(blogs, function (index, blog) {
            unsubmittedBlogs.append($('<a>')
                    .attr({"class": "blog-link", "data-blog-id": blog.blogID})
                    .text(blog.title)
                    .on("click", {id: blog.blogID}, loadBlogIntoTinyMCE));
        });
    });
}

function loadBlogIntoTinyMCE(event) {
    var id = event.data.id;

    $.ajax({
        url: "blogpost/" + id
    }).success(function (blog, status) {
        $('#blogpost-id').val(blog.blogID);
        $('#publish-date').val(blog.publishDate);
        $('#expiry-date').val(blog.expiryDate);
        loadCategoriesIntoTinyMCE(blog.blogID);
        $("#grey-box").hide();
        tinyMCE.activeEditor.setContent(blog.content);
    });
}

function loadPageIntoTinyMCE(event) {
    var id = event.data.id;

    $.ajax({
        url: "webpage/" + id
    }).success(function (page, status) {
        $('#page-id').val(page.pageID);
        tinyMCE.activeEditor.setContent(page.content);
        $('#grey-box').hide();
    });
}

function loadAnnouncementIntoTinyMCE(event) {
    var id = event.data.id;

    $.ajax({
        url: "announcement/" + id
    }).success(function (announcement, status) {
        $("#announcement-id").val(announcement.announcementID);
        $("#publish-date").val(announcement.publishDate);
        $("#expiry-date").val(announcement.expiryDate);
        $("#grey-box").hide();
        tinyMCE.activeEditor.setContent(announcement.content);

    });
}

function loadCategoriesIntoTinyMCE(blogID) {

    $.ajax({
        url: "categories/" + blogID
    }).success(function (categories, status) {
        $.each(categories, function (index, cat) {
            $("#add-categories").val($("#add-categories").val() + ", " + cat.category);
        });
    });
}

function getBlogPostById(id, action) {

    $.ajax({
        url: "blogpost/" + id
    }).success(function (blog, status) {
        console.log("request worked");
        if (action === "save") {
            saveBlog(blog, id);
        } else if (action === "submit") {
            submitOrPublishBlog(blog, id);
        }

    });

}

function getAnnouncementById(id) {

    $.ajax({
        url: "announcement/" + id
    }).success(function (announcement, status) {

        saveAnnouncement(announcement, id);
    });
}

function getWebPageById(id) {
    
    $.ajax({
        url: "webpage/" + id
    }).success(function (webpage, status){
       
       saveWebPage(webpage, id);
    });
}

function saveBlog(blog, id) {
    if (getUserId() === 2) {
        splitCategories("", blog, id, "save");
    } else if (getUserId() === 1) {
        splitCategories($('#add-categories').val(), blog, id, "save");
    }
}

function saveAnnouncement(announcement, id) {
    if ($('#publish-date').val() === "") {
        var publishDate = "3000-01-02";
    } else {
        publishDate = $('#publish-date');
    }

    if ($('#expiry-date').val() === "") {
        var expiryDate = "3000-01-02";
    } else {
        expiryDate = $('#expiry-date');
    }

    $.ajax({
        type: "PUT",
        url: "announcement/" + id,
        data: JSON.stringify({
            publishDate: publishDate,
            expiryDate: expiryDate,
            content: tinyMCE.activeEditor.getContent({format: "raw"})
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        dataType: "json"
    }).success(function (data, status) {
        tinyMCE.activeEditor.setContent('');
        $("#announcement-id").val("");
        $("#publish-date").val("");
        $("#expiry-date").val("");
        loadAnnouncementLinks();

    });

}

function saveWebPage (webpage, id) {
    
    $.ajax({
        type: "PUT",
        url: "page/" + id,
        data: JSON.stringify({
            title: webpage.title,
            content: tinyMCE.activeEditor.getContent({format: "raw"})
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        dataType: "json"
    }).success(function (data, status){
        tinyMCE.activeEditor.setContent('');
        loadWebPagesForEdits();
        $('#grey-box').show();
    });
}

function saveBlog2(blog, id) {
    if (getUserId() === 2) {
        var pubDate = "3000-01-02";
        var exDate = null;
    } else if (getUserId() === 1) {
        if ($('#publish-date').val() === "") {
            pubDate = "3000-01-02";
        } else {
            pubDate = $('#publish-date').val();
        }
        exDate = $('#expiry-date').val();
    }

    $.ajax({
        type: "PUT",
        url: "blogpost/" + id,
        data: JSON.stringify({
            userID: blog.userID,
            title: blog.title,
            submitted: blog.submitted,
            creationDate: blog.creationDate,
            publishDate: pubDate,
            expiryDate: exDate,
            content: tinyMCE.activeEditor.getContent({format: "raw"}),
            categoryIDs: categoryInts
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        dataType: "json"
    }).success(function (data, status) {
        tinyMCE.activeEditor.setContent('');
        $('#blogpost-id').val("");
        $('#grey-box').show();
        $('#add-categories').val("");
    });
}

function getUserId() {
    if (window.location.pathname === "/DansDynamicBlog/blog") {
        return 2;
    } else if (window.location.pathname === "/DansDynamicBlog/blog-admin") {
        return 1;
    }
}

function submitOrPublishBlog(blog, id) {
    if (getUserId() === 2) {
        splitCategories("", blog, id, "submit");
    } else if (getUserId() === 1) {
        splitCategories($('#add-categories').val(), blog, id, "submit");
    }
}

function submitOrPublishBlog2(blog, id) {
    if (getUserId() === 2) {
        var sub = 1; //true because ghostie is submitting it
        var pubDate = "3000-01-02";
        var expDate = null;
    } else {
        sub = 0; //false when admin publishes it
        var pubDateField = $("#publish-date").val();
        if (pubDateField === "") {
            pubDate = Date.now();
        } else {
            pubDate = pubDateField;
        }
        var expDateField = $("#expiry-date").val();
        if (expDateField === "") {
            expDate = "3000-01-02";
        } else {
            expDate = expDateField;
        }
    }

    $.ajax({
        type: "PUT",
        url: "blogpost/" + id,
        data: JSON.stringify({
            userID: blog.userID,
            title: blog.title,
            submitted: sub,
            creationDate: blog.creationDate,
            publishDate: pubDate,
            expiryDate: expDate,
            content: tinyMCE.activeEditor.getContent({format: "raw"}),
            categoryIDs: categoryInts
        }),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        dataType: "json"
    }).success(function (data, status) {
        loadUnsubmittedBlogs();
        loadUnpublishedBlogs();
        loadUnapprovedBlogs();
        tinyMCE.activeEditor.setContent('');
        $('#blogpost-id').val("");
        $('#grey-box').show();
        sendMail(); //don't do when we publish
        $('#add-categories').val("");
    });
}

function sendMail() {
    $.ajax({
        type: 'POST',
        url: 'https://mandrillapp.com/api/1.0/messages/send.json',
        data: {
            'key': 'n0NeUTkI5KjrBuC7jIrV8g',
            'message': {
                'from_email': 'stravinsky42@gmail.com',
                'to': [
                    {
                        'email': 'chandler.molbert@gmail.com',
                        'name': 'Dan',
                        'type': 'to'
                    }
                ],
                'autotext': 'true',
                'subject': 'Blog Submitted For Review',
                'html': '<p>Your pal just submitted a blog.</p>'
            }
        }
    }).done(function (response) {
        console.log(response);
    });
}

function pushCategory(catid, catLength, blog, id, action) {
    categoryInts.push(catid);
    if (categoryInts.length === catLength) {
        if (action === "save") {
            saveBlog2(blog, id); //asynchronous stuff can bite me
        } else if (action === "submit") {
            submitOrPublishBlog2(blog, id);
        }
    }
}

function splitCategories(categories, blog, id, action) {
    var categoryArray = categories.split(",");
    var catLength = categoryArray.length;
    categoryInts = [];

    for (i = 0; i < categoryArray.length; i++) {
        categoryArray[i] = categoryArray[i].trim();

        (function (i) {
            $.ajax({
                url: "searchCategory/" + categoryArray[i],
                async: false
            }).success(function (category, status) {
                if ($.isEmptyObject(category)) {
                    $.ajax({
                        type: "POST",
                        url: "category",
                        data: JSON.stringify({
                            category: categoryArray[i]
                        }),
                        headers: {"Accept": "application/json",
                            "Content-Type": "application/json"},
                        dataType: "json"
                    }).success(function (category, status) {
                        console.log("success");
                        pushCategory(category.categoryID, catLength, blog, id, action);
                    });
                } else {
                    pushCategory(category.categoryID, catLength, blog, id, action);
                }
            });
        })(i);
    }



}

////// MODALS

$('#pages-modal').on('show.bs.modal', function (event) {
    var element = $(event.relatedTarget);
    var contactId = element.data('page-id');

    var modal = $(this);
    $.ajax({
        type: 'GET',
        url: 'webpage/' + contactId
    }).success(function (page) {
        modal.find('#pages-modal-label').text(page.title);
        modal.find("#page-content").text(page.content);
    });
});


