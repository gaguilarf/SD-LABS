<%-- 
    Document   : welcome
    Created on : 4 jun. 2024, 7:20:44 a. m.
    Author     : JOSUE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <h1>Bienvenido</h1>
    <p>Has iniciado sesión correctamente.</p>
    <h1>Ingrese las cantidades de productos</h1>
    <form action="comprar.jsp" method="post">
        <label for="CantidadPan">Cantidad de Pan:</label>
        <input type="number" id="CantidadPan" name="CantidadPan"><br>
        <label for="CantidadQueso">Cantidad de Queso:</label>
        <input type="number" id="CantidadQueso" name="CantidadQueso"><br>
        <label for="CantidadPlatanos">Cantidad de Plátanos:</label>
        <input type="number" id="CantidadPlatanos" name="CantidadPlatanos"><br>
        <label for="CantidadNaranjas">Cantidad de Naranjas:</label>
        <input type="number" id="CantidadNaranjas" name="CantidadNaranjas"><br>
        <input type="submit" value="Comprar">
    </form>
</body>
</html>
</body>
</html>