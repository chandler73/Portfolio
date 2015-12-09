<%-- 
    Document   : tinymcetest
    Created on : Nov 10, 2015, 12:58:44 PM
    Author     : apprentice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div>
            <!--<form method="post">
                             <textarea id="mytextarea"></textarea>
                             <input type="submit" value="Submit">
                        </form>-->
        </div>
        <div class="editable">
            Yr kogi slow-carb, id gentrify viral literally pabst delectus retro. Next level roof party shoreditch, church-key ennui farm-to-table cillum disrupt. Art party ennui kale chips, readymade ethical before they sold out placeat chicharrones pinterest bitters. Drinking vinegar labore aute, deep v hella synth sint ea polaroid pariatur literally. Deep v chartreuse gluten-free labore placeat exercitation, semiotics slow-carb try-hard nesciunt post-ironic. Forage four loko food truck portland cardigan meditation, pabst labore hella. Nesciunt esse gluten-free, pork belly fingerstache exercitation hammock roof party elit cornhole four dollar toast.

            Flexitarian vinyl celiac kogi food truck, jean shorts fugiat franzen. Intelligentsia tattooed scenester, listicle tilde cliche sustainable chia irony pour-over. Art party mumblecore knausgaard tattooed. Whatever retro flannel sapiente occaecat post-ironic, bicycle rights vero you probably haven't heard of them shabby chic viral kombucha. Paleo sartorial DIY, williamsburg chia aliqua aesthetic deep v sunt elit selfies butcher cronut. Swag labore laboris chicharrones, nihil flexitarian dreamcatcher nesciunt banjo single-origin coffee asymmetrical four dollar toast et officia migas. Artisan duis yuccie, deep v ullamco fugiat tote bag veniam listicle.
        </div>
        <script src="${pageContext.request.contextPath}/js/tinymce/tinymce.min.js"></script>
        <script type="text/javascript">
            tinymce.init({
                selector: "div.editable",
                inline: true
                
            });
        </script>
    </body>
</html>
