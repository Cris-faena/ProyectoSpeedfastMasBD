package modelo.DAO;

import ConexionBD.ConexionBD;
import modelo.Repartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un objeto tipo "Repartidor", que permite acceder a los elementos de la base de datos.
 * Utiliza sentencias SQL de MYSQL
 */
public class RepartidorDAO
{
    // ===================== AGREGAR REPARTIDOR =====================

    /**
     * Método que permite agregar un objeto tipo "repartidor" a la base de datos
     * @param repartidor objeto tipo repartidor para almacenar en la BD.
     * @return "true" si se almacenó en la base de datos, "false" so no se logra.
     */
    public boolean agregar(Repartidor repartidor)
    {
        // Almacena esta consulta SQl en un String, en donde agregues en la tabla "repartidor" el nombre del repartidor
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        // Intenta conectar a la base de datos, y almacena la consulta pre-compilada en un objeto "ps".
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            // agrega este valor en la columna 1 de la base de datos
            ps.setString(1, repartidor.getNombre_repartidor());
            // ejecuta la consulta
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Error al agregar repartidor: " + e.getMessage());
            return false;
        }
    }

    // ===================== EDITAR =====================

    /**
     * Método que permite editar un objeto tipo "Repartidor" a la base de datos.
     * @param repartidor objeto tipo "repartidor" para editar.
     * @return "true" si se logó editar, "false" si no se pudo.
     */
    public boolean editar(Repartidor repartidor)
    {
        String sql = "UPDATE repartidor SET nombre = ? WHERE id= ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            // Se cambió el valor de los índices
            ps.setString(1, repartidor.getNombre_repartidor());
            ps.setInt(2, repartidor.getId_repartidor());
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Error al editar repartidor: " + e.getMessage());
            return false;
        }
    }

    // ===================== ELIMINAR =====================

    /**
     * Método que permite eliminar un repartidor de la base de datos.
     * @param idRepartidor "id" del repartidor que se requiere eliminar.
     * @return "true" si se pudo eliminar, "false" si no se pudo.
     */
    public boolean eliminar(int idRepartidor)
    {
        String sql = "DELETE FROM repartidor WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, idRepartidor);
            return ps.executeUpdate() > 0;

        }
        catch (SQLException e)
        {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
            return false;
        }
    }

    // ===================== LISTAR TODOS =====================

    /**
     * Método que permite listar todos los repartidores almacenados en la BD.
     * @return una lista de objetos tipo "Repartidor" que se encuentran almacenados en la BD.
     */
    public List<Repartidor> listarTodos()
    {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidor ORDER BY id"; // Se cambió repartidores por repartidor. Y id_repartidor por id

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next())
            {
                Repartidor repartidor = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                lista.add(repartidor);
            }

        }
        catch (SQLException e)
        {
            System.out.println("Error al listar repartidores: " + e.getMessage());
        }

        return lista;
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Método que devuelve un repartidor almacenado en la BD por el "ID" ingresado
     * @param idRepartidor "ID" del repartidor que se requiere buscar.
     * @return un objeto tipo "Repartidor".
     */
    public Repartidor buscarPorId(int idRepartidor)
    {
        String sql = "SELECT * FROM repartidor WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, idRepartidor);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error al buscar repartidor: " + e.getMessage());
        }
        return null;
    }
}
