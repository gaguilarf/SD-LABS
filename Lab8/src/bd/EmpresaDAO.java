package bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
    private final Connect connect;

    public EmpresaDAO() throws SQLException, ClassNotFoundException {
        connect = new Connect();
    }

    // Método para insertar un nuevo departamento
    public void insertarDepartamento(int id, String nombre, String telefono, String fax) throws SQLException {
        String query = "INSERT INTO Departamento (IDDpto, Nombre, Telefono, Fax) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, telefono);
            stmt.setString(4, fax);
            stmt.executeUpdate();
        }
    }

    // Método para actualizar un departamento
    public void actualizarDepartamento(int id, String nombre, String telefono, String fax) throws SQLException {
        String query = "UPDATE Departamento SET Nombre = ?, Telefono = ?, Fax = ? WHERE IDDpto = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, fax);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un departamento
    public void eliminarDepartamento(int id) throws SQLException {
        String query = "DELETE FROM Departamento WHERE IDDpto = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para insertar un nuevo proyecto
    public void insertarProyecto(int id, String nombre, Date fechaInicio, Date fechaTermino, int idDpto) throws SQLException {
        String query = "INSERT INTO Proyecto (IDProy, Nombre, Fec_Inicio, Fec_Termino, IDDpto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setDate(3, fechaInicio);
            stmt.setDate(4, fechaTermino);
            stmt.setInt(5, idDpto);
            stmt.executeUpdate();
        }
    }

    // Método para actualizar un proyecto
    public void actualizarProyecto(int id, String nombre, Date fechaInicio, Date fechaTermino, int idDpto) throws SQLException {
        String query = "UPDATE Proyecto SET Nombre = ?, Fec_Inicio = ?, Fec_Termino = ?, IDDpto = ? WHERE IDProy = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setDate(2, fechaInicio);
            stmt.setDate(3, fechaTermino);
            stmt.setInt(4, idDpto);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un proyecto
    public void eliminarProyecto(int id) throws SQLException {
        String query = "DELETE FROM Proyecto WHERE IDProy = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para insertar un nuevo ingeniero
    public void insertarIngeniero(int id, String nombre, String especialidad, String cargo) throws SQLException {
        String query = "INSERT INTO Ingeniero (IDIng, Nombre, Especialidad, Cargo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, especialidad);
            stmt.setString(4, cargo);
            stmt.executeUpdate();
        }
    }

    // Método para actualizar un ingeniero
    public void actualizarIngeniero(int id, String nombre, String especialidad, String cargo) throws SQLException {
        String query = "UPDATE Ingeniero SET Nombre = ?, Especialidad = ?, Cargo = ? WHERE IDIng = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, especialidad);
            stmt.setString(3, cargo);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un ingeniero
    public void eliminarIngeniero(int id) throws SQLException {
        String query = "DELETE FROM Ingeniero WHERE IDIng = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para insertar un ingeniero en un proyecto
    public void insertarIngenieroEnProyecto(int idProy, int idIng) throws SQLException {
        String query = "INSERT INTO Proyecto_Ingeniero (IDProy, IDIng) VALUES (?, ?)";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, idProy);
            stmt.setInt(2, idIng);
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un ingeniero de un proyecto
    public void eliminarIngenieroDeProyecto(int idProy, int idIng) throws SQLException {
        String query = "DELETE FROM Proyecto_Ingeniero WHERE IDProy = ? AND IDIng = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, idProy);
            stmt.setInt(2, idIng);
            stmt.executeUpdate();
        }
    }

    // Método para consultar todos los proyectos de un determinado departamento
    public List<String> obtenerProyectosDeDepartamento(int idDpto) throws SQLException {
        List<String> proyectos = new ArrayList<>();
        String query = "SELECT Nombre FROM Proyecto WHERE IDDpto = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, idDpto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    proyectos.add(rs.getString("Nombre"));
                }
            }
        }
        return proyectos;
    }

    // Método para consultar todos los ingenieros que participan en un determinado proyecto
    public List<String> obtenerIngenierosDeProyecto(int idProy) throws SQLException {
        List<String> ingenieros = new ArrayList<>();
        String query = "SELECT i.Nombre FROM Ingeniero i JOIN Proyecto_Ingeniero pi ON i.IDIng = pi.IDIng WHERE pi.IDProy = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, idProy);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ingenieros.add(rs.getString("Nombre"));
                }
            }
        }
        return ingenieros;
    }
    
    public List<Object[]> obtenerTodosLosDepartamentos() throws SQLException {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM Departamento";
        try (Statement stmt = connect.getConexion().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                data.add(new Object[]{rs.getInt("IDDpto"), rs.getString("Nombre"), rs.getString("Telefono"), rs.getString("Fax")});
            }
        }
        return data;
    }

    public List<Object[]> obtenerTodosLosProyectos() throws SQLException {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM Proyecto";
        try (Statement stmt = connect.getConexion().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                data.add(new Object[]{rs.getInt("IDProy"), rs.getString("Nombre"), rs.getDate("Fec_Inicio"), rs.getDate("Fec_Termino"), rs.getInt("IDDpto")});
            }
        }
        return data;
    }

    public List<Object[]> obtenerTodosLosIngenieros() throws SQLException {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM Ingeniero";
        try (Statement stmt = connect.getConexion().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                data.add(new Object[]{rs.getInt("IDIng"), rs.getString("Nombre"), rs.getString("Especialidad"), rs.getString("Cargo")});
            }
        }
        return data;
    }

    public List<Object[]> obtenerTodosLosProyectosIngenieros() throws SQLException {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM Proyecto_Ingeniero";
        try (Statement stmt = connect.getConexion().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                data.add(new Object[]{rs.getInt("IDProy"), rs.getInt("IDIng")});
            }
        }
        return data;
    }

    public List<Object[]> obtenerProyectosPorDepartamento(int idDpto) throws SQLException {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT p.IDProy, p.Nombre, p.Fec_Inicio, p.Fec_Termino, d.IDDpto, d.Nombre AS NombreDpto " +
                       "FROM Proyecto p JOIN Departamento d ON p.IDDpto = d.IDDpto WHERE p.IDDpto = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, idDpto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    data.add(new Object[]{rs.getInt("IDProy"), rs.getString("Nombre"), rs.getDate("Fec_Inicio"), rs.getDate("Fec_Termino"), rs.getInt("IDDpto"), rs.getString("NombreDpto")});
                }
            }
        }
        return data;
    }

    public List<Object[]> obtenerIngenierosPorProyecto(int idProy) throws SQLException {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT i.IDIng, i.Nombre, i.Especialidad, i.Cargo, p.IDProy, p.Nombre AS NombreProy " +
                       "FROM Ingeniero i JOIN Proyecto_Ingeniero pi ON i.IDIng = pi.IDIng " +
                       "JOIN Proyecto p ON pi.IDProy = p.IDProy WHERE p.IDProy = ?";
        try (PreparedStatement stmt = connect.getConexion().prepareStatement(query)) {
            stmt.setInt(1, idProy);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    data.add(new Object[]{rs.getInt("IDIng"), rs.getString("Nombre"), rs.getString("Especialidad"), rs.getString("Cargo"), rs.getInt("IDProy"), rs.getString("NombreProy")});
                }
            }
        }
        return data;
    }
}
