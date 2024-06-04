<%-- 
    Document   : comprar
    Created on : 4 jun. 2024, 7:48:09 a. m.
    Author     : JOSUE
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="compra.productos.compraproductos" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resultado de la Compra</title>
</head>
<body>
    <h1>Resultado de la Compra</h1>
    <%
        int cantidadPan = Integer.parseInt(request.getParameter("CantidadPan"));
        int cantidadQueso = Integer.parseInt(request.getParameter("CantidadQueso"));
        int cantidadPlatanos = Integer.parseInt(request.getParameter("CantidadPlatanos"));
        int cantidadNaranjas = Integer.parseInt(request.getParameter("CantidadNaranjas"));

        // Consumir el servicio web
        compraproductos service = new compraproductos();

        String resultado = service.comprasProductos(cantidadPan, cantidadQueso, cantidadPlatanos, cantidadNaranjas);
        out.println(resultado);
    %>
</body>
</html>
