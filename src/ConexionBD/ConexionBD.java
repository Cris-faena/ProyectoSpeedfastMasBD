package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que permite establecer una conexión con una base de datos.
 * Necesitas especificar: URL, USUARIO, CONTRASEÑA
 */
public class ConexionBD
{

    private static final String URL = "jdbc:mysql://localhost:3306/bd_speedfast";       // URL de la base de datos que quieres conectar.
    private static final String USUARIO = "cristian_faundez";                           // USUARIO registrado en la base de datos.
    private static final String CONTRASENA = System.getenv("MYSQL_PASSWORD");     // CONTRASEÑA para acceder a la base de datos. Obtiene el valor de una variable de entorno almacenada.

    /**
     * Método para obtener la conexión con la base de datos
     * @return conexión entre la base de datos y el IDE
     * @throws SQLException la excepción correspondiente en caso de falla.
     */
    public static Connection obtenerConexion() throws SQLException
    {
        Connection conexion = null; // Sesión específica con la base de datos, llamada "conexión" es nula
        // Si la contraseña es nula o vacía, lanza esta excepción SQL:
        if (CONTRASENA == null || CONTRASENA.isEmpty())
        {
                throw new SQLException("No se encontró la variable de entorno 'MYSQL_PASSWORD'");
        }
        // En caso de que todo esté correcto, imprime este mensaje:
        System.out.println("Se ha conectado con éxito a la base de datos.....");
        // y devuelve los drivers que permitirán la conexión JDBM.
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}