package controlador;

import modelo.*;

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

    /** Método para buscar un elemento específico en la base de datos.
     * @param idEntrega "id" del elemento que desea buscar.
     * @return "entrega" solicitada
     */
    public Entrega buscarEntregaPorId(int idEntrega) {
        return daoE.buscarPorId(idEntrega);
    }
}
