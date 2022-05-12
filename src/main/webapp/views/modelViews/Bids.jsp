<%--
  Created by IntelliJ IDEA.
  User: inter
  Date: 012, 5/12/2022
  Time: 11:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="ua.univ.models.Driver" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% if (request.getAttribute("objectName") == "Bid" && rolesSet != null && (rolesSet.contains("admin") ||
                                                                           rolesSet.contains("manager"))) { %>

    <% if (request.getAttribute("delete_id") == null) { %>
        <form action="/bids" method="post">
            <div class="form-group">
                <label for="workPurpose">Work Purpose</label>
                <input type="text" name="name" class="form-control" id="workPurpose" placeholder="Enter work purpose for car" required>
            </div>
            <div class="form-group">
                <label for="finishedField">Finished</label>
                <select name="finished" id="finishedField" class="form-control" required>
                    <option value="true">True</option>
                    <option value="false">False</option>
                </select>
            </div>
            <div class="form-group">
                <label for="driverField">Driver</label>
                <select name="driver_id" id="driverField" class="form-control" required>
                    <% for (Driver driver : (List<Driver>) request.getAttribute("driverList")) { %>
                        <option value="<%=driver.getId()%>"><%=driver.getName().toString()%></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <label for="driverFeedbackField">Review</label>
                <textarea style="resize:none" type="text" rows="5" cols="40" name="driverFeedback" class="form-control" id="driverFeedbackField" placeholder="Enter Feedback"></textarea>
            </div>
            <input type="submit" name="btn_action" class="btn btn-primary" value="Add"/>
        </form>
    <% } %>


    <% if (request.getAttribute("delete_id") != null) { %>
        <form action="/bids" method="post">
            <% session.setAttribute("delete_id", request.getAttribute("delete_id")); %>
            <input type="submit" name="btn_action" class="btn btn-danger" value="Delete" />
        </form>
    <% } %>

    <% if (request.getAttribute("delete_id") != null && rolesSet != null && (rolesSet.contains("admin") || rolesSet.contains("driver"))) { %>
        <form action="/bids" method="post">
            <% session.setAttribute("update_id", request.getAttribute("delete_id")); %>
            <div class="form-group">
                <label for="finishedSelect">Finished</label>
                <select name="finished" id="finishedSelect" class="form-control" required>
                    <option value="true">True</option>
                    <option value="false">False</option>
                </select>
            </div>
            <div class="form-group">
                <label for="feedbackField">Driver feedback</label>
                <textarea type="text" rows="5" cols="40" name="feedback" class="form-control" id="feedbackField"
                          placeholder="Enter feedback" required><%= request.getAttribute("feedback") %></textarea>
            </div>
            <input type="submit" name="btn_action" class="btn btn-success" value="Update"/>
        </form>
    <% } %>

<% } %>