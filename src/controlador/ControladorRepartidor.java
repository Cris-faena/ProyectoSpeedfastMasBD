package controlador;

import modelo.Repartidor;
import modelo.DAO.RepartidorDAO;
import java.util.List;

/**
 * Clase que representa un controlador de operaciones para los objetos "Repartidor"
 */
public class ControladorRepartidor
{
    private final RepartidorDAO daoR = new RepartidorDAO(); // Se crea una instancia de la clase RepartidorDAO.

    // ===================== AGREGAR REPARTIDOR=====================

    /**
     * Método que agrega repartidores a la base de datos.
     * @param repartidor objeto tipo "repartidor" que se requiere agregar.
     * @return "true" si se agrega el repartidor, "false" si no se pudo agregar.
     */
    public boolean agregarRepartidor(Repartidor repartidor) {
        return daoR.agregar(repartidor);
    }

    // ===================== EDITAR REPARTIDOR =====================

    /**
     * Método para editar un repartidor almacenado en la base de datos.
     * @param repartidor objeto tipo "repartidor" que se requiere editar de la base de datos.
     * @return "true" si se edita el repartidor, "false" si no se pudo editar.
     */
    public boolean editarRepartidor(Repartidor repartidor) {
        return daoR.editar(repartidor);
    }

    // ===================== ELIMINAR REPARTIDOR=====================

    /**
     * Método para eliminar un repartidor almacenado en la base de datos.
     * @param idRepartidor "id" del repartidor que se requiere eliminar.
     * @return "true" si se elimina el repartidor, "false" si no se pudo eliminar.
     */
    public boolean eliminarRepartidor(int idRepartidor) {return daoR.eliminar(idRepartidor);}

    // ===================== OBTENER REPARTIDORES =====================

    /**
     * Método para obtener los repartidores almacenados en la base de datos.
     * @return una lista con los repartidores almacenados en la base de datos.
     */
    public List<Repartidor> obtenerTodosLosRepartidores() {return daoR.listarTodos();}

    // ===================== BUSCAR POR ID =====================

    /**
     * Método para buscar un repartidor en específico dentro de la base de datos.
     * @param idRepartidor "id" del repartidor que se requiere buscar.
     * @return el objeto tipo "repartidor".
     */
    public Repartidor buscarRepartidorPorId(int idRepartidor) {
        return daoR.buscarPorId(idRepartidor);
    }
}