<%--
  Created by IntelliJ IDEA.
  User: inter
  Date: 012, 5/12/2022
  Time: 11:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% if (request.getAttribute("objectName") == "Car" && rolesSet != null && (rolesSet.contains("admin") ||
                                                                           rolesSet.contains("driver"))) { %>

    <% if (request.getAttribute("delete_id") == null) { %>
        <form action="/cars" method="post">
            <div class="form-group">
                <label for="readyField">Ready?</label>
                <select name="ready" id="readyField" class="form-control" required>
                    <option value="true">True</option>
                    <option value="false">False</option>
                </select>
            </div>
            <div class="form-group">
                <label for="purposeField">Purpose</label>
                <input type="text" name="purpose" class="form-control" id="purposeField" placeholder="Enter purpose" required>
            </div>
            <input type="submit" name="btn_action" class="btn btn-primary" value="Add"/>
        </form>
    <% } %>


    <% if (request.getAttribute("delete_id") != null && rolesSet.contains("admin")) { %>
        <form action="/cars" method="post">
            <% session.setAttribute("delete_id", request.getAttribute("delete_id")); %>
            <input type="submit" name="btn_action" class="btn btn-danger" value="Delete"/>
        </form>
    <% } %>
<% } %>