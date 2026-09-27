package modelo;

/**
 * Clase ENUM que representa el estado actual del pedido realizado.
 */
public enum EstadoPedido
{
    PENDIENTE,      // El pedido no se ha asignado a nadie.
    EN_REPARTO,     // El pedido ha sido asignado a un repartidor y se encuentra en camino.
    ENTREGADO       // El pedido fue estregado.
}
