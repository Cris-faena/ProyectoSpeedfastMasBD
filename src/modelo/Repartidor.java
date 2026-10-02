package modelo;

/**
 * Clase que representa un repartidor que trabaja para SpeedFast.
 */
public class Repartidor
{
    private int id_repartidor;          // Atributo que representa el identificador único del repartidor.
    private String nombre_repartidor;   // Atributo que representa el nombre del repartidor.

    // Constructor sin parámetros
    public Repartidor()
    {}

    // Constructor con parámetros
    public Repartidor(int id_repartidor, String nombre_repartidor)
    {
        this.id_repartidor = id_repartidor;
        this.nombre_repartidor = nombre_repartidor;
    }

    // Se implementan los getters:

    /**
     * Método que devuelve el id del repartidor.
     * @return id del repartidor.
     */
    public int getId_repartidor() {return id_repartidor;}

    /**
     * Método que devuelve el nombre del repartidor.
     * @return nombre del repartidor.
     */
    public String getNombre_repartidor() {return nombre_repartidor;}

    // Se implementan los setters:

    /**
     * Método para modificar el id del repartidor.
     * @param id_repartidor nuevo id que se requiere asignar al repartidor.
     */
    public void setId_repartidor(int id_repartidor) {this.id_repartidor = id_repartidor;}

    /**
     * Método para modificar el nombre del repartidor.
     * @param nombre_repartidor nuevo nombre que se requiere asignar al repartidor.
     */
    public void setNombre_repartidor(String nombre_repartidor) {this.nombre_repartidor = nombre_repartidor;}

    // Se implementa un método toString:

    /**
     * Método que devuelve una cadena de texto con la información del objeto "Repartidor"
     * @return "idRepartidor" + "nombreRepartidor".
     */
    public String toString()
    {
        return "ID: " + id_repartidor + " " + "-" + " " + nombre_repartidor;
    }
}
