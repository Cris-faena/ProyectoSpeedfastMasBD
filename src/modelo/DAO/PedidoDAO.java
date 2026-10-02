package modelo.DAO;

import ConexionBD.ConexionBD;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un objeto tipo pedido para manipular en la base de datos.
 */
public class PedidoDAO
{
    // ===================== AGREGAR PEDIDO =====================

    /**
     * Método que permite agregar pedidos a la base de datos.
     * @param pedido objeto tipo pedido que se requiere almacenar en la BD.
     * @return "true" si se agregó el pedido correctamente, "false" si no se logra.
     */
    public boolean agregar(Pedido pedido)
    {
        // Se almacena una consulta SQL en un String, insertando 3 valores en la tabla "pedido" (dirección, tipo, estado)
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        // Intenta conectar a la base de datos y crea un objeto "ps" que almacene la consulta SQL:
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            // El valor del primer elemento, insértalo en este orden en cada la columna de la tabla "pedido"en la BD
            ps.setString(1, pedido.getDireccion_pedido());
            ps.setString(2, pedido.getTipo_pedido().name());      // Guarda el nombre del enum
            ps.setString(3, pedido.getEstado_pedido().name());
            // Ejecuta la consulta en la base de datos:
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Error al agregar pedido: " + e.getMessage());
            return false;
        }
    }

    // ===================== EDITAR PEDIDO =====================

    /**
     * Método que permite editar un pedido almacenado en la base de datos.
     * @param pedido pedido que se requiere editar al interior de la BD.
     * @return "true" si se editó el pedido correctamente, "false" si no se logra.
     */
    public boolean editar(Pedido pedido)
    {
        // Almacena esta consulta SQL en un String, en donde quiero que actualices los valores de la tabla "pedido" (dirección,tipo, estado)
        String sql = "UPDATE pedido SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        // trata de conectar a la base de datos y almacena la consulta SQl pre-compilada en el objeto "ps"
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // asigna estos valores del objeto pedido en la tabla "pedido" de la BD.
            ps.setString(1, pedido.getDireccion_pedido());
            ps.setString(2, pedido.getTipo_pedido().name());
            ps.setString(3, pedido.getEstado_pedido().name());
            ps.setInt(4, pedido.getId_pedido());
            // Ejecuta la consulta en la base de datos
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Error al editar pedido: " + e.getMessage());
            return false;
        }
    }

    // ===================== ELIMINAR PEDIDO=====================

    /**
     * Método que permite eliminar un pedido de la base de datos.
     * @param idPedido id del pedido que se requiere eliminar de la BD.
     * @return "true" si se eliminó el pedido correctamente, "false" si no se logra.
     */
    public boolean eliminar(int idPedido)
    {
        // Se almacena una consulta SQL en un String, que indica: "borra de la tabla pedido, en donde el Id del pedido es:
        String sql = "DELETE FROM pedido WHERE id = ?";

        // Intenta conectar a la base de datos y pre-compila esta consulta en un objeto "ps".
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // el valor "id" del objeto pedido pre-compilado, debes asignarlo a la tabla "pedido", en la columna 1
            ps.setInt(1, idPedido);
            // Ejecuta la consulta en la base de datos:
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }

    // ===================== LISTAR TODOS =====================

    /**
     * Método que lista todos los pedidos de la base de datos.
     * @return una lista con todos los pedidos de la BD.
     */
    public List<Pedido> listarTodos()
    {
        // Se crea una lista para almacenar los pedidos de la base de datos
        List<Pedido> lista = new ArrayList<>();
        // Almacena esta consulta SQL en un String, en donde se seleccione todas las columnas de la tabla "pedido", ordenada por "id".
        String sql = "SELECT * FROM pedido ORDER BY id";

        // Intenta conectar a la base de datos, y crea una consulta estática SQL. Además crea un objeto "rs" que almacene los resultados de la consulta en memoria
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            // mientras no haya otra línea, crea un objeto pedido con los elementos existentes en la base de datos
            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );
                // añade estos elementos a la lista "pedido"
                lista.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return lista;
    }

    // ===================== BUSCAR PEDIDOS POR ID =====================

    /**
     * Método que devuelve un pedido de la base de datos, dependiendo del "idPedido" ingresado como parámetro.
     * @param idPedido id del pedido que se requiere obtener
     * @return un objeto tipo "pedido" desde la BD
     */
    public Pedido buscarPorId(int idPedido)
    {
        // Almacena esta consulta SQL en un String, en donde se busquen todos los pedidos de la tabla pedidos, en donde el "id" sea el parámetro ingresado
        String sql = "SELECT * FROM pedido WHERE id= ?";
        // Intenta conectar a la base de datos y almacena la consulta SQL en un objeto "ps" pre-compilado
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            // busca en la columna 1 de la base de datos el id ingresado en el objeto "ps".
            ps.setInt(1, idPedido);
            // Ejecuta la consulta SQL en la base de datos
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }

        return null;
    }

    /**
     * Método que permite filtrar los pedidos por 'Tipo' o 'Estado'
     * @param tipo Enum de tipo 'pedido' (COMIDA - ENCOMIENDA - EXPRESS)
     * @param estado Enum de tipo 'estado' (PENDIENTE - EN_REPARTO - ENTREGADO)
     * @return una lista tipo 'pedidos' filtrados según parámetros
     */
    public List<Pedido> filtrarPorTipoOEstado(TipoPedido tipo, EstadoPedido estado)
    {
        // Se implementa una lista 'Pedido' vacía, de tipo ARRAYLIST para almacenar los valores:
        List<Pedido> lista = new ArrayList<>();

        // Se implementa un StringBuilder que contiene la sentencia SQl.
        // Implementa un WHERE 1=1 que permitirá agregar filtros opcionales
        StringBuilder sql = new StringBuilder("SELECT * FROM pedido WHERE 1=1");

        // Se implementa una lista de objetos vacía
        List<Object> parametros = new ArrayList<>();

        // Si el tipo ingresado es distinto de "NULL":
        if (tipo != null)
        {
            sql.append(" AND tipo = ?"); // agrega esto a la sentencia almacenada.
            parametros.add(tipo.name()); // agrega el nombre del tipo a la lista de parámetros.
        }

        // Si el estado ingresado es distinto de "NULL":
        if (estado != null)
        {
            sql.append(" AND estado = ?"); // agrega esto a la sentencia almacenada.
            parametros.add(estado.name()); // agrega el nombre del estado a la lista de parámetros.
        }

        sql.append(" ORDER BY id"); // finalmente, agrega 'ordenado por: id'
        // EJEMPLO DE SENTENCIA SQL: SELECT * FROM pedido WHERE 1=1 AND tipo=? AND estado =? ORDER BY id;

        // Intenta conectar a la base de datos y almacena la sentencia SQL en un objeto tipo "Prepared Statement".
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Asigna parámetros dinámicos:
            // Parametros.size = obtiene cuantos valores hay en la lista (o sea, cuantos '?' se agregaron).
            // Parametros.get = obtiene el valor de la posición "i" de la lista.
            for (int i = 0; i < parametros.size(); i++)
            {
                // asigna ese valor de la lista, en la posición i + 1.
                ps.setObject(i + 1, parametros.get(i));
            }

            // guarda el resultado de la consulta 'ps.executeQuery' en el objeto ResultSet 'rs'.
            ResultSet rs = ps.executeQuery();

            // recorre los resultados de la consulta y ve creando objetos pedidos hasta que no haya otro valor:
            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );
                // Agrégalo a la lista
                lista.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al filtrar pedidos: " + e.getMessage());
        }
        // finalmente, devuelve la lista con los resultados
        return lista;
    }
}
