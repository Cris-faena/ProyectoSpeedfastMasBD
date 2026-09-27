package modelo;

/**
 * Clase que representa un pedido, encargado a SpeedFast para su entrega
 */
public class Pedido
{
    private int id_pedido;                  // Atributo que representa un id del pedido.
    private String direccion_pedido;        // Atributo que representa una dirección de entrega de pedido.
    private TipoPedido tipo_pedido;         // Atributo que representa el tipo de pedido realizado. Puede ser COMIDA, ENCOMIENDA o EXPRESS.
    private EstadoPedido estado_pedido;     // Atributo que representa el estado del pedido. Puede ser PENDIENTE, EN_REPARTO o ENTREGADO.

    // Constructor sin parámetros
    public Pedido(){}

    // Constructor con parámetros
    public Pedido (int id_pedido,  String direccion_pedido, TipoPedido tipo_pedido, EstadoPedido estado_pedido)
    {
        if (tipo_pedido == null)
        {
            throw new IllegalArgumentException("Tipo de pedido no puede ser nulo");
        }
        if (estado_pedido == null)
        {
            throw new IllegalArgumentException("Estado de pedido no puede ser nulo");
        }
        this.id_pedido = id_pedido;
        this.direccion_pedido = direccion_pedido;
        this.tipo_pedido = tipo_pedido;
        this.estado_pedido = estado_pedido;
    }

    // Se implementan los getters:

    /**
     * Método que devuelve el id del pedido.
     * @return id del pedido
     */
    public int getId_pedido() {return id_pedido;}

    /**
     * Método que devuelve la dirección de entrega del pedido.
     * @return dirección de entrega del pedido.
     */
    public String getDireccion_pedido() {return direccion_pedido;}

    /**
     * Método que devuelve el tipo de pedido
     * @return tipo de pedido (COMIDA - ENCOMIENDA - EXPRESS)
     */
    public TipoPedido getTipo_pedido() {return tipo_pedido;}

    /**
     * Método que devuelve el estado del pedido
     * @return estado del pedido (PENDIENTE - EN_REPARTO - ENTREGADO)
     */
    public EstadoPedido getEstado_pedido() {return estado_pedido;}

    // Se implementan los setters:

    /**
     * Método para modificar el "id" de un pedido.
     * @param id_pedido nuevo "id" que se quiere asignar a un pedido.
     */
    public void setId_pedido(int id_pedido) {this.id_pedido = id_pedido;}

    /**
     * Método para modificar la dirección de entrega de un pedido.
     * @param direccion_pedido nueva dirección que se requiere asignar
     */
    public void setDireccion_pedido(String direccion_pedido) {this.direccion_pedido = direccion_pedido;}

    /**
     * Método para modificar el tipo de pedido.
     * @param tipo_pedido nuevo tipo que se quiere asignar al pedido.
     */
    public void setTipo_pedido(TipoPedido tipo_pedido) {this.tipo_pedido = tipo_pedido;}

    /**
     * Método para modificar el estado del pedido.
     * @param estado_pedido nuevo estado del pedido que se requiere asignar.
     */
    public void setEstado_pedido(EstadoPedido estado_pedido) {this.estado_pedido = estado_pedido;}

    // Se implementa el método toString:

    /**
     * Método que devuelve una cadena de texto con la información del objeto "Pedido"
     * @return "IdPedido" + "Dirección" + "Tipo" + "Estado" del pedido.
     */
    public String toString()
    {
        return String.format("El ID del pedido es: %s.%n" +
                "La dirección del pedido es: %s.%n" +
                "El tipo de pedido es: %s.%n" +
                "El estado del pedido es: %s.%n", id_pedido, direccion_pedido, tipo_pedido, estado_pedido);
    }
}
