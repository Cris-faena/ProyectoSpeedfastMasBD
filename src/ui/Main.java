package ui;

import ConexionBD.ConexionBD;
import modelo.*;
import vista.Pantalla;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException {
        ConexionBD.obtenerConexion();

        javax.swing.SwingUtilities.invokeLater(() -> {
            Pantalla pantalla = new Pantalla();
            pantalla.setVisible(true);
        });
    }
}
