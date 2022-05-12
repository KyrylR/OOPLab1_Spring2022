<%--
  Created by IntelliJ IDEA.
  User: inter
  Date: 012, 5/12/2022
  Time: 11:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% if (request.getAttribute("objectName") == "Driver" && rolesSet != null && rolesSet.contains("admin")) {%>

    <% if (request.getAttribute("delete_id") == null) { %>
        <form action="/drivers" method="post">
            <div class="form-group">
                <label for="nameField">Name</label>
                <input type="text" name="name" class="form-control" id="nameField" placeholder="Enter name" required>
            </div>
            <input type="submit" name="btn_action" class="btn btn-primary" value="Add"/>
        </form>
    <% } %>

    <% if (request.getAttribute("delete_id") != null) { %>
        <form action="/drivers" method="post">
            <% session.setAttribute("delete_id", request.getAttribute("delete_id")); %>
            <input type="submit" name="btn_action" class="btn btn-danger" value="Delete"/>
        </form>
    <% } %>
<% } %>
