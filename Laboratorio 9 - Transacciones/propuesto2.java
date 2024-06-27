package sdlab9;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class prueba {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;

        try {
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba", "root", "josuemathias2910");

            // Deshabilitar el auto-commit para manejar la transacción manualmente
            connection.setAutoCommit(false);

            // Primer comando SQL
            String sql1 = "INSERT INTO accounts (account_id, balance) VALUES (?, ?)";
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, 101);
            preparedStatement1.setDouble(2, 1000.00);
            preparedStatement1.executeUpdate();

            // Segundo comando SQL
            String sql2 = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setDouble(1, 500.00);
            preparedStatement2.setInt(2, 101);
            preparedStatement2.executeUpdate();

            // Confirmar la transacción
            connection.commit();
            System.out.println("Transacción completada exitosamente.");

        } catch (SQLException e) {
            // Si hay un error, hacer rollback para deshacer los cambios
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transacción revertida debido a un error: " + e.getMessage());
                } catch (SQLException rollbackException) {
                    System.err.println("Error al hacer rollback: " + rollbackException.getMessage());
                }
            }
        } finally {
            // Cerrar los recursos
            try {
                if (preparedStatement1 != null) preparedStatement1.close();
                if (preparedStatement2 != null) preparedStatement2.close();
                if (connection != null) connection.close();
            } catch (SQLException closeException) {
                System.err.println("Error al cerrar recursos: " + closeException.getMessage());
            }
        }
    }
}
