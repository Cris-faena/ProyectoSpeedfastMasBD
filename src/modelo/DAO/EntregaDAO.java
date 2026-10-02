package modelo.DAO;

import ConexionBD.ConexionBD;
import modelo.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto tipo "entrega" que representa el acceso a la información de la base de datos.
 * Utiliza sentencias para MYSQL.
 */
public class EntregaDAO
{
    // ========== AGREGAR ENTREGA ==========

    /**
     * Método que permite agregar una entrega a la base de datos
     * @param entrega objeto tipo "Entrega" que se quiere almacenar en la BD.
     * @return "True" si se agrega a la BD, "False" si no lo hace
     */
    public boolean agregar(Entrega entrega)
    {
        // Crea un String con una sentencia SQL que permite insertar entregas a la BD
        String sql = "INSERT INTO entrega (id_repartidor, id_pedido, fecha, hora) VALUES (?, ?, ?, ?)";

        // Se implementa la conexión a la base de datos
        try (Connection conn = ConexionBD.obtenerConexion();
             // Este objeto representa una declaración SQL pre-compilada.
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Pre-compila y almacena esta sentencia:
            ps.setInt(1, entrega.getId_repartidor());
            ps.setInt(2, entrega.getId_pedido());

            // fecha y hora pueden ser null
            if (entrega.getFecha() != null) {
                ps.setDate(3, java.sql.Date.valueOf(entrega.getFecha()));
            }
            else
            {
                System.out.println("La fecha no puede estar vacía");
            }

            if (entrega.getHora() != null) {
                ps.setTime(4, java.sql.Time.valueOf(entrega.getHora()));
            } else {
                ps.setNull(4, java.sql.Types.TIME);
            }
            // Ejecuta la sentencia pre-compilada. Puede ser INSERT, UPDATE o DELETE
            // Si afecta a lo menos una fila, devuelve true, si no se logra ejecutar, devuelve false
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar entrega: " + e.getMessage());
            return false;
        }
    }
    // ========== BORRAR ENTREGA ==========

    /**
     * Método que elimina una entrega por medio del id
     * @param idEntrega id de la entrega que se requiere eliminar
     * @return "true" si se elimina la entrega, "false" si no se logra.
     */
    public boolean eliminar(int idEntrega)
    {
        // Sentencia SQL para borrar un elemento de la tabla "entrega"
        String sql = "DELETE FROM entrega WHERE id = ?";

        // Se intenta establecer una conexión con la base de datos.
        try (Connection conn = ConexionBD.obtenerConexion();
             // Se establece una declaración SQL pre-compilada
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Convierte (int idEntrega) en atributo SQL de tipo integer
            ps.setInt(1, idEntrega);
            // Ejecuta la sentencia pre-compilada. Para este caso, ejecuta DELETE
            // Esto elimina la fila seleccionada de la base de datos.
            // Si afecta a lo menos una fila, devuelve true, si no se logra ejecutar, devuelve false
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar entrega: " + e.getMessage());
            return false;
        }
    }

    // ========== ENCONTRAR POR ID ==========

    /**
     * Método que permite buscar objetos "Entrega" en la base de datos, por el Id
     * @param idEntrega idEntrega que se requiere buscar en la BD
     * @return el objeto tipo "Entrega" si es que existe en la BD.
     */
    public Entrega buscarPorId(int idEntrega)
    {
        // un String que almacena una sentencia SQL para buscar el elemento por el ID
        String sql = "SELECT * FROM entrega WHERE id= ?";

        // Intenta conectar a la base de datos:
        try (Connection conn = ConexionBD.obtenerConexion();
             // Pre-compila la sentencia SQL señalada anteriormente en el String
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // En el primer parámetro del SQL, coloca el valor entero de: isEntrega, o sea a WHERE id=?
            ps.setInt(1, idEntrega);

            // Intenta devolver un conjunto de resultados, de la ejecución de la sentencia de consulta (ps.executequery)
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) // Si hay una fila después de la consulta, ejecuta mapearEntrega:
                {
                    return mapearEntrega(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar entrega: " + e.getMessage());
        }
        return null;
    }

    // ========== ENCONTRAR TODAS ==========

    /**
     * Método para obtener una lista de todos los objetos Entrega almacenados en la BD
     * @return lista de entregas
     */
    public List<Entrega> listarTodos()
    {
        // Se implementa un arraylist para almacenar los objetos Entrega encontrados:
        List<Entrega> lista = new ArrayList<>();
        // String que almacena una sentencia SQL para buscar todos los elementos de la Tabla entrega
        String sql = "SELECT * FROM entrega ORDER BY id";

        // Intenta realizar la conexión a la base de datos:
        try (Connection conn = ConexionBD.obtenerConexion();
             // Crea una objeto "rs" con una sentencia SQl pre-compilada, o sea (String sql).
             PreparedStatement ps = conn.prepareStatement(sql);
             // El conjunto de resultados "rs" es igual a la ejecución de la consulta "ps"
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) // Mientras haya una fila, añade a la lista
            {
                lista.add(mapearEntrega(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar entregas: " + e.getMessage());
        }
        return lista;
    }

    // ========== ENCONTRAR POR REPARTIDOR ==========

    /**
     * Método que encuentra una entrega por el ID del repartidor.
     * @param idRepartidor id del repartidor que se quiere asociar a una entrega.
     * @return una lista de entregas donde se encuentre el id del repartidor especificado.
     */
    public List<Entrega> buscarPorRepartidor(int idRepartidor)
    {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrega WHERE id_repartidor = ? ORDER BY fecha DESC, hora DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRepartidor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEntrega(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar entregas por repartidor: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Método que devuelve una lista de entregas que contenga el IdPedido Seleccionado.
     * @param idPedido id del repartidor que se quiere asociar a una entrega.
     * @return una lista de entregas donde se encuentre el id del repartidor especificado.
     */
    public List<Entrega> buscarPorPedidoLista(int idPedido)
    {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrega WHERE id_pedido = ? ORDER BY fecha DESC, hora DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEntrega(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar pedidos por repartidor: " + e.getMessage());
        }
        return lista;
    }

    // ========== ENCONTRAR POR PEDIDO ==========

    /**
     * Método para encontrar una entrega por medio del Id de un pedido
     * @param idPedido id del pedido que se utilizará para asociarlo a una entrega
     * @return una lista de entregas asociada a un id del pedido.
     */
    public Entrega buscarPorPedido(int idPedido) {
        String sql = "SELECT * FROM entrega WHERE id_pedido = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEntrega(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar entrega por pedido: " + e.getMessage());
        }
        return null;
    }

    // ========== Método auxiliar para mapear ResultSet → Entrega ==========

    /**
     * Método que transforma una fila de una tabla SQl en un objeto Java tipo Entrega
     * @param rs Conjunto de resultados obtenidos por medio de una consulta específica
     * @return un objeto tipo Entrega
     * @throws SQLException lanza una excepciónSQL en caso de fallas.
     */
    private Entrega mapearEntrega(ResultSet rs) throws SQLException
    {
        Entrega e = new Entrega(
                rs.getInt("id"),
                rs.getInt("id_repartidor"),
                rs.getInt("id_pedido")
        );

        // fecha y hora pueden ser null
        Date fechaSql = rs.getDate("fecha");
        Time horaSql = rs.getTime("hora");

        if (fechaSql != null) {
            e.setFecha(fechaSql.toLocalDate());
        }
        if (horaSql != null) {
            e.setHora(horaSql.toLocalTime());
        }

        return e;
    }

    public Boolean existePedidoEnEntrega(int idPedido)
    {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrega WHERE id_pedido = ? ORDER BY fecha DESC, hora DESC";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEntrega(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar pedidos por repartidor: " + e.getMessage());
            return false;
        }
        return true;
    }
}

