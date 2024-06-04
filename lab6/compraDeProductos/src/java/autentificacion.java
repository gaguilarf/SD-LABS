/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/autentificacion")
public class autentificacion extends HttpServlet {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                response.sendRedirect("welcome.jsp");
            } else {
                throw new Exception("Usuario o contraseña incorrecta.");
            }
        } catch (Exception e) {
            // Redirigir a la página de error con la excepción
            request.setAttribute("javax.servlet.error.exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}