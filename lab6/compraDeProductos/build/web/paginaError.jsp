<%-- 
    Document   : paginaError
    Created on : 4 jun. 2024, 7:12:03 a. m.
    Author     : JOSUE
--%>

<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>¡Error!</title>
</head>
<body>
    <h1>Ha ocurrido un error</h1>
    <p>Lo sentimos, ha ocurrido un error en la aplicación.</p>
    <%
        // Utiliza un nombre diferente para la variable de excepción
        Throwable errorException = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (errorException != null) {
            out.println("<h2>Detalles del error:</h2>");
            out.println("<p>" + errorException.getMessage() + "</p>");
            
            // Crear un PrintWriter a partir del JspWriter
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            errorException.printStackTrace(pw);
            out.println("<pre>" + sw.toString() + "</pre>");
        }
    %>
</body>
</html>