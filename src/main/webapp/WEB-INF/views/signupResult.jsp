<%@ page contentType="text/html;charset=UTF-8" %>

<h1>Submitted Registration Data</h1>

<ul>
    <li><strong>Name:</strong> <%= request.getAttribute("user-name") %></li>
    <li><strong>Phone:</strong> <%= request.getAttribute("user-phone") %></li>
    <li><strong>Email:</strong> <%= request.getAttribute("user-email") %></li>
    <li><strong>Password:</strong> <%= request.getAttribute("user-password") %></li>
    <li><strong>Password Repeat:</strong> <%= request.getAttribute("user-repeat") %></li>

    <% if (request.getAttribute("fileName") != null) { %>
    <li><strong>Avatar Name:</strong> <%= request.getAttribute("fileName") %></li>
    <li><strong>Avatar Size:</strong> <%= request.getAttribute("fileSize") %> bytes</li>
    <% } %>
</ul>
