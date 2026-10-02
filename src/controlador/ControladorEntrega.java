package controlador;

import modelo.*;
import modelo.DAO.EntregaDAO;
import modelo.DAO.PedidoDAO;
import modelo.DAO.RepartidorDAO;

import java.util.List;

/**
 * Clase que controla las operaciones tipo "ENTREGA".
 * Permite: AGREGAR - ELIMINAR - OBTENER y BUSCAR entregas en la base de datos.
 */
public class ControladorEntrega
{
    private final EntregaDAO daoE = new EntregaDAO();       // crea un objeto "EntregaDAO".
    private final RepartidorDAO daoR = new RepartidorDAO(); // crea un objeto "RepartidorDAO".
    private final PedidoDAO daoP = new PedidoDAO();         // crea un objeto "PedidoDAO".

    // ===================== AGREGAR =====================

    /**
     * Método para agregar una entrega a la base de datos
     * @param entrega objeto tipo "entrega"
     * @return "true" si se almacena, "false" si no se almacena
     */
    public boolean agregarEntrega(Entrega entrega)
    {
        // 1. Validar que el repartidor exista
        if (daoR.buscarPorId(entrega.getId_repartidor()) == null) {
            System.out.println("Error: El repartidor no existe.");
            return false;
        }

        // 2. Validar que el pedido exista
        if (daoP.buscarPorId(entrega.getId_pedido()) == null) {
            System.out.println("Error: El pedido no existe.");
            return false;
        }

        // 3. Validar que el pedido no tenga ya una entrega asociada
        Entrega existente = daoE.buscarPorPedido(entrega.getId_pedido());
        if (existente != null) {
            System.out.println("Error: Este pedido ya tiene una entrega registrada.");
            return false;
        }

        // 4. Validar fecha y hora
        if (entrega.getFecha() == null || entrega.getHora() == null) {
            System.out.println("Error: La entrega debe tener fecha y hora.");
            return false;
        }

        // 5. Si todo está OK → llamar al DAO
        return daoE.agregar(entrega);
    }

    // ===================== ELIMINAR =====================

    /**
     * Método que elimina una entrega de la base de datos.
     * @param idEntrega id de la entrega que se desea eliminar
     * @return "true" si se elimina, "false" si no se elimina
     */
    public boolean eliminarEntrega(int idEntrega) {
        return daoE.eliminar(idEntrega);
    }

    // ===================== OBTENER TODOS =====================

    /**
     * Método para obtener una lista de todos las entregas almacenadas en la base de datos
     * @return una lista con las entregas almacenadas
     */
    public List<Entrega> obtenerTodasLasEntregas() {
        return daoE.listarTodos();
    }

    // ===================== BUSCAR POR ID =====================

    /**
     * Método para buscar un elemento específico en la base de datos.
     * @param idPedido "id" del elemento que desea buscar.
     * @return "Pedido" solicitada
     */
    public Entrega buscarEntregaPorPedido(int idPedido)
    {
        return daoE.buscarPorId(idPedido);
    }

    /**
     * Método que devuelve una lista de objetos "Entrega" filtrada por el idRepartidor ingresado.
     * @param idRepartidor identificador único de un repartidor almacenado previamente en la BD.
     * @return una lista de objetos "Entrega" filtrada por el "id" del repartidor.
     */
    public List<Entrega> buscarEntregaPorRepartidor(int idRepartidor)
    {
        return daoE.buscarPorRepartidor(idRepartidor);
    }

    /**
     * Método que devuelve una lista de objetos "Pedido" filtrada por el idPedido ingresado
     * @param idPedido identificador único de un repartidor almacenado previamente en la BD.
     * @return una lista de objetos "Entrega" filtrada por el "id" del pedido.
     */
    public List<Entrega> buscarEntregaPorPedidoLista(int idPedido)
    {
        return daoE.buscarPorPedidoLista(idPedido);
    }

    /**
     * Método que permite confirmar si un pedido existe en la tabla "Entrega" de la BD.
     * @param idPedido "id" del pedido que se requiere saber si ya está almacenado en la BD.
     * @return "true" si existe en la BD de la tabla entrega, "false" si no existe.
     */
    public Boolean existeEstePedido(int idPedido)
    {
        return daoE.existePedidoEnEntrega(idPedido);
    }

    /**
     * Método que permite confirmar si un repartidor existe en la tabla "Entrega" de la BD.
     * @param idRepartidor "id" del repartidor que se requiere saber si ya está almacenado en la BD.
     * @return "true" si existe en la BD de la tabla entrega, "false" si no existe.
     */
    public Boolean existeEsteRepartidor(int idRepartidor)
    {
        return daoE.existeRepartidorEnEntrega(idRepartidor);
    }
}
