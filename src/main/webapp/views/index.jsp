<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: inter
  Date: 012, 5/12/2022
  Time: 11:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Faculty Application</title>
</head>

<style>
    body {
        --nav-width: 200px;
        margin: 0 0 0 var(--nav-width);
        font-family: 'Quicksand', sans-serif;
        font-size: 18px;
    }

    .nav {
        position: fixed;
        top: 0;
        left: 0;
        width: var(--nav-width);
        height: 100vh;
        background: #222222;
    }

    .nav__link {
        display: block;
        padding: 12px 18px;
        text-decoration: none;
        color: #eeeeee;
        font-weight: 500;
    }

    .nav__link:hover {
        background: rgba(255, 255, 255, 0.05);
    }

    #app {
        margin: 2em;
        line-height: 1.5;
        font-weight: 500;
    }

    a {
        color: #009579;
    }

    .models-table {
        cursor: pointer;
    }
</style>

<body>
<nav class="nav flex-column">
    <a href="/" class="nav__link" data-link>Main page</a>

    <a href="/cars" class="nav__link" data-link>Cars</a>
    <a href="/drivers" class="nav__link" data-link>Drivers</a>
    <a href="/carDriver" class="nav__link" data-link>Car-Drivers</a>
    <a href="/bids" class="nav__link" data-link>Bids</a>
    <a href="/logout" class="nav__link" data-link>Logout</a>

    <a href="/Sky" class="nav__link" data-link>To the moooooon(err)!</a>

</nav>

<div id="app">
    <%! Set<String> rolesSet = null; %>

    <%
        if (request.getAttribute("roles") != null) {
            rolesSet = (Set<String>) request.getAttribute("roles");
        }
    %>

    <% if (request.getAttribute("info") != null) { %>
    <%= request.getAttribute("info") %>

    <%@include file="modelViews/Bids.jsp" %>
    <%@include file="modelViews/Car-Driver.jsp" %>
    <%@include file="modelViews/Cars.jsp" %>
    <%@include file="modelViews/Drivers.jsp" %>
    <% } %>
</div>

</body>
</html>
