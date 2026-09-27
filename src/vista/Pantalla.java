package vista;

import controlador.*;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que implementa la interfaz gráfica
 */
public class Pantalla extends JFrame
{
    // Se cargan los respectivos componentes
    private JPanel PanelPrincipal;
    private JPanel Panel1;
    private JPanel panel2;
    private JTextField txtIdRepartidor;
    private JTextField txtNombreRepartidor;
    private JTextField txtIDPedido;
    private JTextField txtDireccionPed;
    private JTextField txtTipoPedido;
    private JTextField txtEstadoPedido;
    private JTable tblRepartidor;
    private JTable tblPedido;
    private JTable tblEntrega;
    private JPanel panel3;
    private JLabel lblRepartidor;
    private JLabel lblNombreRepartidor;
    private JLabel lblPedido;
    private JLabel lblDireccionPed;
    private JLabel lblTipoPedido;
    private JLabel lblEstadoPedido;
    private JPanel PanelBtnRepartidor;
    private JButton btnAgregarRep;
    private JButton btnEditarRepartidor;
    private JButton btnEliminarRepartidor;
    private JButton btnLimpiarRepartidor;
    private JPanel panelBotonesPedido;
    private JButton btnAgregarPedido;
    private JButton btnEditarPedido;
    private JButton btnEliminarPedido;
    private JButton btnLimpiarPedido;
    private JComboBox cboxTipo;
    private JComboBox cboxEstado;
    private JLabel lblEntregas;
    private JPanel PanelInferiorBtn;
    private JButton btnCrearEntrega;
    private JButton btnEliminarEntrega;
    private JButton btnLimpiarEntrega;

    // Se implementan 3 tablas "DefaultMOdel" para cada una de las entidades (ENTREGA - REPARTIDOR y PEDIDO)
    private DefaultTableModel modeloTablaEntrega;
    private DefaultTableModel modeloTablaRepartidor;
    private DefaultTableModel modeloTablaPedidos;
    // Se implementan 3 Controladores para cada una de las entidades
    private final ControladorEntrega controladorEntrega = new ControladorEntrega();
    private final ControladorRepartidor controladorRepartidor = new ControladorRepartidor();
    private final ControladorPedido controladorPedido = new ControladorPedido();

    // Se implementa un valor "int" que representará una posición de selección de fila
    private int idPedidoSeleccionado = -1;
    private int idRepartidorSeleccionado = -1;
    private int idEntregaSeleccionado = -1;

    // Se implementa el constructor de la clase pantalla
    public Pantalla()
    {
        setTitle("Speed Fast, la entrega de pedidos más rápida de Chile");
        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Esto inicializa la app maximizada.


        // Se añade un color al panel de pedidos.
        PanelPrincipal.setBackground(new Color(255, 235, 160));
        PanelPrincipal.setOpaque(true);

        // Se llama a los métodos que inicializan las tablas dentro de la GUI.
        inicializarTablaRepartidor();
        inicializarTablaPedidos();
        inicializarTablaEntrega();

        // Se llama a los métodos que permiten establecer los valores de cada JComboBox.
        cargarCategoriasTipoPedido();
        cargarCategoriasEstadoPedido();

        // Por cada fila en las tablas de la base de datos, se añaden filas a las tablas de la GUI.
        cargarTablaRepartidores();
        cargarTablaPedidos();
        cargarTablaEntregas();

        // Se ajusta el tamaño de los JTEXTFIELDS:
        txtDireccionPed.setPreferredSize(new Dimension(200, 20));
        txtNombreRepartidor.setPreferredSize(new Dimension(350, 20));

        // Se agregan las funcionalidades para los botones del panel de repartidores.
        btnAgregarRep.addActionListener(event -> {agregarRepartidor();});
        btnEditarRepartidor.addActionListener(event -> {editarRepartidor();});
        btnEliminarRepartidor.addActionListener(event -> {eliminarRepartidor();});
        btnLimpiarRepartidor.addActionListener(event -> {limpiarRepartidor();});

        // Se agregan las funcionalidades para los botones del panel de pedidos.
        btnAgregarPedido.addActionListener(event -> {agregarPedido();});
        btnEditarPedido.addActionListener(event -> {editarPedido();});
        btnEliminarPedido.addActionListener(event -> {eliminarPedido();});
        btnLimpiarPedido.addActionListener(event -> {limpiarPedido();});

        // Se agregan las funcionalidades para los botones del panel de entregas.
        btnCrearEntrega.addActionListener(ActionEvent -> {crearEntrega();});
        btnEliminarEntrega.addActionListener(ActionEvent -> {eliminarEntrega();});
        btnLimpiarEntrega.addActionListener(event -> {actualizarCampos();});

    }
    // Se implementa un método para cargar las categorías de los JComboBox:
    private void cargarCategoriasTipoPedido()
    {
        cboxTipo.removeAllItems();
        for (TipoPedido t : TipoPedido.values())
        {
            cboxTipo.addItem(t.toString());
        }
    }

    private void cargarCategoriasEstadoPedido()
    {
        cboxEstado.removeAllItems();
        for (EstadoPedido e : EstadoPedido.values())
        {
            cboxEstado.addItem(e.toString());
        }
    }


    // Se implementan los métodos que inicializarán lo valores en las columnas
    private void inicializarTablaPedidos()
    {
        String[] columnas = {"ID_pedido", "Dirección_pedido", "Tipo_pedido", "Estado_pedido"};
        modeloTablaPedidos = new DefaultTableModel(columnas, 0);
        tblPedido.setModel(modeloTablaPedidos);
        tblPedido.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPedido.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID_pedido
        tblPedido.getColumnModel().getColumn(1).setPreferredWidth(250);  // Dirección
        tblPedido.getColumnModel().getColumn(2).setPreferredWidth(200);  // Tipo
        tblPedido.getColumnModel().getColumn(3).setPreferredWidth(200);  // Estado

        tblPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblPedido.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int fila = tblPedido.getSelectedRow();
                if (fila >= 0) {
                    idPedidoSeleccionado = Integer.parseInt(modeloTablaPedidos.getValueAt(fila, 0).toString());
                    txtDireccionPed.setText(modeloTablaPedidos.getValueAt(fila, 1).toString());
                    String tipo = modeloTablaPedidos.getValueAt(fila, 2).toString();
                    cboxTipo.setSelectedItem(tipo);
                    String estado = modeloTablaPedidos.getValueAt(fila, 3).toString();
                    cboxEstado.setSelectedItem(estado);
                }
            }
        });
    }

    private void inicializarTablaRepartidor()
    {
        String[] columnas = {"id_repartidor", "nombre_repartidor"};
        modeloTablaRepartidor = new DefaultTableModel(columnas, 0);
        tblRepartidor.setModel(modeloTablaRepartidor);
        tblRepartidor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tblRepartidor.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblRepartidor.getColumnModel().getColumn(1).setPreferredWidth(392);
        tblRepartidor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblRepartidor.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e) {
                int fila = tblRepartidor.getSelectedRow();
                if (fila >= 0) {
                    idRepartidorSeleccionado = Integer.parseInt(modeloTablaRepartidor.getValueAt(fila, 0).toString());
                    txtNombreRepartidor.setText(modeloTablaRepartidor.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    private void inicializarTablaEntrega()
    {
        String[] columnas = {"ID_entrega", "ID_repartidor", "ID_pedido", "fecha_creación_entrega", "hora_creación_entrega"};
        modeloTablaEntrega = new DefaultTableModel(columnas, 0);
        tblEntrega.setModel(modeloTablaEntrega);
        tblEntrega.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblEntrega.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblEntrega.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int fila = tblEntrega.getSelectedRow();
                if (fila >= 0) {
                    idEntregaSeleccionado = Integer.parseInt(modeloTablaEntrega.getValueAt(fila, 0).toString());
                }
            }
        });
    }

    // ===================== CARGAR TABLA PEDIDOS DESDE BD =====================
    private void cargarTablaPedidos()
    {
        modeloTablaPedidos.setRowCount(0); // limpia la tabla

        for (Pedido p : controladorPedido.obtenerTodosLosPedidos())
        {
            modeloTablaPedidos.addRow(new Object[]{
                    p.getId_pedido(),
                    p.getDireccion_pedido(),
                    p.getTipo_pedido(),
                    p.getEstado_pedido()
            });
        }
    }

    // ===================== CARGAR TABLA REPARTIDORES DESDE BD =====================
    private void cargarTablaRepartidores()
    {
        modeloTablaRepartidor.setRowCount(0); // limpia la tabla

        for (Repartidor r : controladorRepartidor.obtenerTodosLosRepartidores())
        {
            modeloTablaRepartidor.addRow(new Object[]{
                    r.getId_repartidor(),
                    r.getNombre_repartidor()
            });
        }
    }

    // ===================== CARGAR TABLA ENTREGAS DESDE BD =====================
    private void cargarTablaEntregas()
    {
        modeloTablaEntrega.setRowCount(0); // limpia la tabla

        for (Entrega d : controladorEntrega.obtenerTodasLasEntregas())
        {
            modeloTablaEntrega.addRow(new Object[]{
                    d.getId_entrega(),
                    d.getId_repartidor(),
                    d.getId_pedido(),
                    d.getFecha(),
                    d.getHora()
            });
        }
        setVisible(true);
    }

    // Se implementan los métodos que agregarán funcionalidad a los botones para "repartidores"
    // ===================== AGREGAR REPARTIDOR A LA BD =====================
    private void agregarRepartidor()
    {
        String nombre_repartidor = txtNombreRepartidor.getText().trim();
        if (nombre_repartidor.isEmpty() || nombre_repartidor.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre de repartidor");
            return;
        }
        Repartidor r = new Repartidor();
        r.setNombre_repartidor(nombre_repartidor);

        if (controladorRepartidor.agregarRepartidor(r))
        {
            JOptionPane.showMessageDialog(this, "Repartidor agregado correctamente");
            cargarTablaRepartidores();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No se logró agregar al repartidor");
        }

    }
    // ===================== EDITAR REPARTIDOR A LA BD =====================
    private void editarRepartidor()
    {
        if (idRepartidorSeleccionado == -1)
        {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor para editar");
            return;
        }

        String nombre = txtNombreRepartidor.getText().trim();
        if (nombre.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "El nombre del repartidor no puede estar vacío");
        }

        Repartidor r = new Repartidor();
        r.setId_repartidor(idRepartidorSeleccionado);
        r.setNombre_repartidor(nombre);

        if (controladorRepartidor.editarRepartidor(r))
        {
            JOptionPane.showMessageDialog(this, "Repartidor editado correctamente");
            cargarTablaRepartidores();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al editar el repartidor");
        }
    }
    // ===================== ELIMINAR REPARTIDOR A LA BD =====================
    private void eliminarRepartidor()
    {
        if (idRepartidorSeleccionado == -1)
        {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor para eliminar");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este repartidor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION)
        {
            return;
        }

        if (controladorRepartidor.eliminarRepartidor(idRepartidorSeleccionado))
        {
            JOptionPane.showMessageDialog(this, "Repartidor eliminado correctamente");
            idRepartidorSeleccionado = -1;
            txtNombreRepartidor.setText("");
            cargarTablaRepartidores();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al eliminar el repartidor");
        }
    }
    // ===================== LIMPIAR CAMPOS REPARTIDOR A LA INTERFAZ =====================
    private void limpiarRepartidor()
    {
        txtNombreRepartidor.setText("");
        tblRepartidor.clearSelection();
        idRepartidorSeleccionado = -1;
        modeloTablaRepartidor.setRowCount(0);
    }

    // Se implementan los métodos que agregarán funcionalidad a los botones para "pedidos"
    // ===================== AGREGAR PEDIDO A LA BD =====================
    private void agregarPedido()
    {
        String direccion = txtDireccionPed.getText().trim();
        if  (direccion.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar una dirección para el pedido");
            return;
        }
        String tipo_pedido = cboxTipo.getSelectedItem().toString().trim();
        if  (tipo_pedido.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar una tipo de pedido para el pedido");
        }
        String estado_pedido = cboxEstado.getSelectedItem().toString().trim();
        if (estado_pedido.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar el estado del pedido");
        }
        Pedido p = new Pedido();
        p.setDireccion_pedido(direccion);
        p.setTipo_pedido(TipoPedido.valueOf(tipo_pedido));
        p.setEstado_pedido(EstadoPedido.valueOf(estado_pedido));

        if (controladorPedido.agregarPedido(p))
        {
            JOptionPane.showMessageDialog(this, "Pedido agregado correctamente");
            cargarTablaPedidos();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al agregar el pedido");
        }
    }

    // ===================== EDITAR PEDIDO EN LA BD =====================
    private void editarPedido()
    {
        if (idPedidoSeleccionado == -1)
        {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para editar");
            return;
        }
        String direccion = txtDireccionPed.getText().trim();
        if (direccion.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "la dirección del pedido no puede estar vacía");
        }
        String tipo_pedido = cboxTipo.getSelectedItem().toString().trim();
        if (tipo_pedido.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar una tipo de pedido para editar");
        }
        String estado_pedido = cboxEstado.getSelectedItem().toString().trim();
        if (estado_pedido.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar el estado del pedido para editar");
        }
        Pedido p = new Pedido();
        p.setId_pedido(idPedidoSeleccionado);
        p.setDireccion_pedido(direccion);
        p.setTipo_pedido(TipoPedido.valueOf(tipo_pedido));
        p.setEstado_pedido(EstadoPedido.valueOf(estado_pedido));

        if(controladorPedido.editarPedido(p))
        {
            JOptionPane.showMessageDialog(this, "Pedido editado correctamente");
            cargarTablaPedidos();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al editar el pedido");
        }
    }

    // ===================== ELIMINAR PEDIDO DE LA BD =====================
    private void eliminarPedido()
    {
        if (idPedidoSeleccionado == -1)
        {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este pedido?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION)
        {
            return;
        }

        if (controladorPedido.eliminarPedido(idPedidoSeleccionado))
        {
            JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente");
            idPedidoSeleccionado = -1;
            txtDireccionPed.setText("");
            cargarTablaPedidos();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al eliminar el pedido");
        }
    }

    // ===================== LIMPIAR PEDIDOS DE LA INTERFAZ =====================
    private void limpiarPedido()
    {
        txtDireccionPed.setText("");
        tblPedido.clearSelection();
        idPedidoSeleccionado = -1;
        modeloTablaPedidos.setRowCount(0);
    }

    // Se implementan los métodos que agregarán funcionalidad a los botones para "entregas"
    // ===================== AGREGAR ENTREGA A LA BD =====================
    private void crearEntrega()
    {
        Entrega e = new Entrega();
        e.setId_repartidor(idRepartidorSeleccionado);
        e.setId_pedido(idPedidoSeleccionado);
        e.setFecha(LocalDate.now());
        e.setHora(LocalTime.now());
        boolean esCorrecto = controladorEntrega.agregarEntrega(e);
        if (esCorrecto)
        {
            JOptionPane.showMessageDialog(this,
                    "Entrega agregado correctamente",
                    "éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No se pudo agregar la entrega", "fracaso", JOptionPane.ERROR_MESSAGE);
        }
        cargarTablaEntregas();

    }
    // ===================== ELIMINAR ENTREGA DE LA BD =====================
    private void eliminarEntrega()
    {
        if (idEntregaSeleccionado == -1)
        {
            JOptionPane.showMessageDialog(this, "Seleccione una entrega para eliminar");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar esta entrega?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION)
        {
            return;
        }

        if (controladorEntrega.eliminarEntrega(idEntregaSeleccionado))
        {
            JOptionPane.showMessageDialog(this, "Entrega eliminada correctamente");
            idEntregaSeleccionado = -1;
            cargarTablaEntregas();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al eliminar la entrega");
        }
    }

    // ===================== ACTUALIZAR CAMPOS DE ENTREGA DE LA BD =====================
    private void actualizarCampos()
    {

        tblEntrega.clearSelection();
        idEntregaSeleccionado = -1;
        modeloTablaEntrega.setRowCount(0);
    }

}
