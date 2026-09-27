package modelo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una entrega de pedidos, con su repartidor encargado.
 */
public class Entrega
{
    private int id_entrega;     // Atributo que representa el identificador único de la entrega.
    private int id_repartidor;  // Atributo que representa el identificador único del repartidor.
    private int id_pedido;      // Atributo que representa el identificador único del pedido.
    private LocalDate fecha;    // Atributo que representa  la fecha de creación de la entrega.
    private LocalTime hora;     // Atributo que representa la hora de creación de la entrega.

    // Se implementa un constructor sin parámetros.
    public Entrega(){}

    // Se implementa un constructor con parámetros.
    public Entrega(int id_entrega, int id_repartidor, int id_pedido)
    {
        this.id_entrega = id_entrega;
        this.id_repartidor = id_repartidor;
        this.id_pedido = id_pedido;
    }

    // Se implementan los getters:

    /**
     * Método que devuelve el "id" de una entrega.
     * @return id de la entrega.
     */
    public int getId_entrega() {return id_entrega;}

    /**
     * Método que devuelve el "id" de un repartidor.
     * @return id del repartidor.
     */
    public int getId_repartidor() {return id_repartidor;}

    /**
     * Método que devuelve el "id" de un pedido.
     * @return id del pedido.
     */
    public int getId_pedido() {return id_pedido;}

    /**
     * Método que devuelve la fecha de creación de una entrega
     * @return fecha de creación
     */
    public LocalDate getFecha() {return fecha;}

    /**
     * Método que devuelve la hora de creación de una entrega
     * @return hora de creación
     */
    public LocalTime getHora() {return hora;}

    // Se implementan los setters:

    /**
     * Método para modificar el "id" de un pedido.
     * @param id_pedido nuevo "id" que se quiere asignar a un pedido.
     */
    public void setId_pedido(int id_pedido) {this.id_pedido = id_pedido;}

    /**
     * Método para modificar el "id" de un repartidor.
     * @param id_repartidor nuevo "id" que se quiere asignar a un repartidor.
     */
    public void setId_repartidor(int id_repartidor) {this.id_repartidor = id_repartidor;}

    /**
     * Método para modificar el "id" de una entrega.
     * @param id_entrega nuevo "id" que se quiere asignar a una entrega
     */
    public void setId_entrega(int id_entrega) {this.id_entrega = id_entrega;}

    /**
     * Método para modificar la fecha de creación de la entrega.
     * @param fecha nueva fecha que se desea asignar.
     */
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    /**
     * Método para modificar la hora de creación de la entrega.
     * @param hora nueva hora que se desea asignar.
     */
    public void setHora(LocalTime hora) {this.hora = hora;}

    // Se implementa un método toString

    /**
     * Método que devuelve una cadena de texto con la información del objeto "entrega"
     * @return idEntrega + idRepartidor + idPedido + fechaCreación + horaCreación
     */
    public String toString()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
        return String.format("El ID de la entrega es: %d.%n" +
                "El ID del repartidor es: %d.%n" +
                "El ID del pedido es: %d.%n" +
                "La fecha de creación: %s.%n" +
                "La hora de creación: %s.%n", id_entrega, id_repartidor, id_pedido, fecha, hora.format(dtf));
    }
}
