<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
    <h1>Java Web</h1>
    <h2>JSP</h2>

<%--    <p> Java Server Pages - динамические страници с помощью серверной активности </p>--%>

<%--    <p><code>&lt;%= 2+3 %&gt; = <%= 2 + 3 %></code></p>--%>
<%--    <p><code>&lt;% int x = 10; %&gt; <% int x = 10; %></code></p>--%>
<%--    <p><code>&lt;%= x %&gt; = <%= x %></code></p>--%>

<%--    <pre>--%>
<%--    &lt;% if(Умова) { %&gt;--%>
<%--    HTML-якщо-true--%>
<%--    &lt;% } else { %&gt;--%>
<%--    HTML-якщо-false--%>
<%--    &lt;% } %&gt;--%>
<%--    </pre>--%>

<%--    <p>--%>
<%--      <% if( x % 2 == 0 ) { %>--%>
<%--      <b>x - парне число</b>--%>
<%--      <% } else { %>--%>
<%--      <i>х - непарне число</i>--%>
<%--      <% } %>--%>
<%--    </p>--%>

<%--  <h3>Циклы</h3>--%>
<%--    <pre>--%>
<%--  <% for (int i = 0; i < 10; i++) { %>--%>
<%--   <p>HTML <%= i %></p>--%>
<%--      <%}%>--%>
<%--    </pre>--%>

<%--  <% String[] arr = {"Prod 1 ", "Prod 2 ","Prod 3 ","Prod 4 "};  %>--%>

<%--  <ul>--%>
<%--    <% for (String str : arr) {%> <li><%= str %></li> <%} %>--%>
<%--  </ul>--%>
<%--    &lt;jsp:include page="fragment.jsp"/&gt;--%>
<%--    <jsp:include page="fragment.jsp"/>--%>

    <table border="1">
      <tr>
        <th>Номер</th>
        <th>Продукт</th>
        <th>Цена</th>
      </tr>
      <%
        for (int i = 0; i < 10; i++) {
      %>
      <tr>
        <td><%= i + 1 %></td>
        <td><%= "Products " + (i + 1) %></td>
        <td><%= 10 * (i + 1) %></td>
      </tr>
      <%
        }
      %>
    </table>

  </body>
</html>
