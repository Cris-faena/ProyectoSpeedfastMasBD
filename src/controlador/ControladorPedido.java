package controlador;

import modelo.Pedido;
import modelo.PedidoDAO;
import java.util.List;

/**
 * Clase que representa un controlador de operaciones para los objetos "Pedido"
 * Permite: AGREGAR - EDITAR - ELIMINAR - OBTENER Y BUSCAR pedidos en la base de datos
 */
public class ControladorPedido
{
    private final PedidoDAO daoP = new PedidoDAO(); // Se crea una instancia de la clase PedidoDAO.

    // ===================== AGREGAR PEDIDO =====================

    /**
     * Método que agrega pedidos a la base de datos
     * @param pedido objeto tipo "pedido" que se requiere agregar
     * @return "true" si se agrega el pedido, "false" si no se pudo agregar.
     */
    public boolean agregarPedido(Pedido pedido) {
        return daoP.agregar(pedido);
    }

    // ===================== EDITAR PEDIDO =====================

    /**
     * Método para editar un pedido almacenado en la base de datos.
     * @param pedido objeto tipo "pedido" que se requiere editar de la base de datos.
     * @return "true" si se edita el pedido, "false" si no se pudo editar.
     */
    public boolean editarPedido(Pedido pedido) {
        return daoP.editar(pedido);
    }

    // ===================== ELIMINAR PEDIDO=====================

    /**
     * Método para eliminar un pedido almacenado en la base de datos.
     * @param idPedido "id" del pedido que se requiere eliminar.
     * @return "true" si se elimina el pedido, "false" si no se pudo eliminar.
     */
    public boolean eliminarPedido(int idPedido) {
        return daoP.eliminar(idPedido);
    }

    // ===================== OBTENER TODOS =====================

    /**
     * Método para obtener los pedidos almacenados en la base de datos.
     * @return una lista con los pedidos almacenados en la base de datos.
     */
    public List<Pedido> obtenerTodosLosPedidos() {
        return daoP.listarTodos();
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Método para buscar un pedido en específico dentro de la base de datos.
     * @param idPedido "id" del pedido que se requiere buscar.
     * @return el objeto tipo "pedido".
     */
    public Pedido buscarPedidoPorId(int idPedido) {
        return daoP.buscarPorId(idPedido);
    }
}