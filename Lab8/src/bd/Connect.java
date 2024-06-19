package bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private Connection conexion;
    private final String url = "jdbc:mysql://localhost:3306/lab8";
    private final String usuario = "root";
    private final String contrasena = "123456";

    public Connect() throws SQLException, ClassNotFoundException {
        conectar();
    }

    private void conectar() throws SQLException, ClassNotFoundException {
        if (conexion == null || conexion.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión exitosa a la base de datos");
        }
    }

    public void desconectar() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
            System.out.println("Conexión cerrada");
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}
