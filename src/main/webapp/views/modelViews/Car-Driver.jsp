<%--
  Created by IntelliJ IDEA.
  User: inter
  Date: 012, 5/12/2022
  Time: 11:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="ua.univ.models.Car" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    if (request.getAttribute("objectName") == "CarDriver" && rolesSet != null && (rolesSet.contains("admin") ||
                                                                                  rolesSet.contains("manager") ||
                                                                                  rolesSet.contains("driver"))) {
%>
    <% if (request.getAttribute("delete_id") == null) { %>
        <form action="/carDriver" method="post">
            <div class="form-group">
                <label for="carField">Select car</label>
                <select name="car_id" id="carField" class="form-control" required>
                    <% for (Car car : (List<Car>) request.getAttribute("carList")) { %>
                        <option value="<%=car.getId()%>">For <%=car.getPurpose().toString()%></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group">
                <label for="driverField">Select driver</label>
                <select name="driver_id" id="driverField" class="form-control" required>
                    <% for (Driver driver : (List<Driver>) request.getAttribute("driverList")) { %>
                        <option value="<%=driver.getId()%>"><%=driver.getName().toString()%></option>
                    <% } %>
                </select>
            </div>
            <input type="submit" name="btn_action" class="btn btn-primary" value="Add"/>
        </form>
    <% } %>

    <% if (request.getAttribute("delete_id") != null && rolesSet.contains("admin")) { %>
        <form action="/carDriver" method="post">
            <% session.setAttribute("delete_id", request.getAttribute("delete_id")); %>
            <input type="submit" name="btn_action" class="btn btn-danger" value="Delete"/>
        </form>
    <% } %>

<% } %>