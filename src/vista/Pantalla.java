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
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz gráfica
 */
public class Pantalla extends JFrame
{
    // Se cargan los respectivos componentes
    private JPanel PanelPrincipal;
    private JPanel PanelNombreRepartidor;
    private JPanel PanelTablaEntregas;
    private JTextField txtIdRepartidor;
    private JTextField txtNombreRepartidor;
    private JTextField txtIDPedido;
    private JTextField txtDireccionPed;
    private JTextField txtTipoPedido;
    private JTextField txtEstadoPedido;
    private JTable tblRepartidor;
    private JTable tblPedido;
    private JTable tblEntrega;
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
    private JPanel PanelFiltradoPedidos;
    private JComboBox jcbTipoPedido;
    private JComboBox jcbEstadoPedido;
    private JLabel lblFiltrarPedidoPor;
    private JLabel lblTipo_Pedido2;
    private JLabel lblEstado_Pedido2;
    private JButton btnFiltrarPedido;
    private JButton btnListarTodos;
    private JPanel PanelFiltroRepartidor;
    private JLabel lblFiltrarRepartidor;
    private JTextField txtIdRepartidorFiltro;
    private JButton btnFiltrarRepartidor;
    private JButton btnListarRepartidor;
    private JLabel lblIdRepartidorFiltro;
    private JLabel lblFiltrarEntregas;
    private JTextField txtFiltrarTipoEntrega;
    private JLabel lblFiltrarPedidoEstado;
    private JLabel lblFiltrartipo;
    private JTextField txtFiltrarEstadoEntrega;
    private JButton btnListarEntrega;
    private JTextField txtFiltrarIdRepartidorEntrega;
    private JTextField txtFiltrarIdEntrega;
    private JButton btnAplicarFlitro;
    private JTextField txtFiltrarPedidoEntrega;
    private JPanel PanelCreacionEntrega;
    private JLabel lblSeleccionEntrega;
    private JComboBox jcb_id_repartidor;
    private JComboBox jcb_ID_PedidO;
    private JLabel txt_ID_REPARTIDOR;
    private JLabel txt_ID_PEDIDO;
    private JLabel lblTitulo;
    private JPanel PanelTitulo;
    private JScrollPane jspTablaRepartidor;
    private JScrollPane jspTablaPedido;
    private JScrollPane jspTablaEntrega;
    private JPanel PanelTxtPedidos;

    // Se implementan 3 tablas "DefaultMOdel" para cada una de las entidades (ENTREGA - REPARTIDOR y PEDIDO)
    private DefaultTableModel modeloTablaEntrega;
    private DefaultTableModel modeloTablaRepartidor;
    private DefaultTableModel modeloTablaPedidos;

    // Se implementan 3 Controladores para cada una de las entidades
    private final ControladorEntrega controladorEntrega = new ControladorEntrega();
    private final ControladorRepartidor controladorRepartidor = new ControladorRepartidor();
    private final ControladorPedido controladorPedido = new ControladorPedido();

    // Se implementa un valor "int" que representará una posición de selección de fila.
    // El valor -1 representa que no se ha marcado ningúna fila.
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
        cargarCategoriasJComboBoxRepartidor();
        cargarCategoriasJComboBoxPedido();

        // Por cada fila en las tablas de la base de datos, se añaden filas a las tablas de la GUI.
        cargarTablaRepartidores();
        cargarTablaPedidos();
        cargarTablaEntregas();

        // Se cambia el color de los JPanel que albergan los filtros:
        PanelFiltroRepartidor.setBackground(new Color(173, 216, 230));
        PanelFiltradoPedidos.setBackground(new Color(173, 216, 230));
        PanelInferiorBtn.setBackground(new Color(173, 216, 230));

        // Se agrega cada tabla al JScrollPane:
        jspTablaRepartidor.setViewportView(tblRepartidor);
        jspTablaRepartidor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jspTablaRepartidor.setPreferredSize(new Dimension(350, 150));
        jspTablaPedido.setViewportView(tblPedido);
        jspTablaPedido.setPreferredSize(new Dimension(350, 150));
        jspTablaEntrega.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jspTablaEntrega.setViewportView(tblEntrega);
        jspTablaEntrega.setPreferredSize(new Dimension(350, 150));
        jspTablaPedido.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Se cambia el color para las JTables:
        tblRepartidor.setBackground(new Color(255, 245, 230));
        tblRepartidor.getTableHeader().setBackground(new Color(240, 220, 200));
        tblPedido.getTableHeader().setBackground(new Color(240, 220, 200));
        tblPedido.setBackground(new Color(255, 245, 230));
        tblEntrega.setBackground(new Color(255, 245, 230));
        tblEntrega.getTableHeader().setBackground(new Color(240, 220, 200));

        // Se ajusta el tamaño de los JTextFields:
        txtDireccionPed.setPreferredSize(new Dimension(170, 20));
        txtNombreRepartidor.setPreferredSize(new Dimension(350, 20));
        txtNombreRepartidor.setMinimumSize(new Dimension(350, 20));
        txtNombreRepartidor.setMaximumSize(new Dimension(350, 20));
        txtIdRepartidorFiltro.setPreferredSize(new Dimension(120, 20));
        txtFiltrarIdRepartidorEntrega.setPreferredSize(new Dimension(100, 20));
        txtFiltrarPedidoEntrega.setPreferredSize(new Dimension(100, 20));
        txtFiltrarPedidoEntrega.setMinimumSize(new Dimension(100, 20));
        txtFiltrarPedidoEntrega.setMaximumSize(new Dimension(100, 20));

        //Se ajustan los tamaños de los JComboBox:
        jcb_ID_PedidO.setPreferredSize(new Dimension(200, 20));
        jcb_ID_PedidO.setMinimumSize(new Dimension(200, 20));
        jcb_ID_PedidO.setMaximumSize(new Dimension(200, 20));
        jcb_id_repartidor.setPreferredSize(new Dimension(200, 20));
        jcb_id_repartidor.setMinimumSize(new Dimension(200, 20));
        jcb_id_repartidor.setMaximumSize(new Dimension(200, 20));

        // Se agregan las funcionalidades para los botones del panel de repartidores.
        btnAgregarRep.addActionListener(event -> {agregarRepartidor();});
        btnEditarRepartidor.addActionListener(event -> {editarRepartidor();});
        btnEliminarRepartidor.addActionListener(event -> {eliminarRepartidor();});
        btnLimpiarRepartidor.addActionListener(event -> {limpiarRepartidor();});
        btnFiltrarRepartidor.addActionListener(event -> {filtrarRepartidorId();});
        btnListarRepartidor.addActionListener(event -> {listarTodosLosRepartidores();});

        // Se agregan las funcionalidades para los botones del panel de pedidos.
        btnAgregarPedido.addActionListener(event -> {agregarPedido();});
        btnEditarPedido.addActionListener(event -> {editarPedido();});
        btnEliminarPedido.addActionListener(event -> {eliminarPedido();});
        btnLimpiarPedido.addActionListener(event -> {limpiarPedido();});
        btnFiltrarPedido.addActionListener(event -> {filtrarPedidos();});
        btnListarTodos.addActionListener(event -> {listarTodosLosPedidos();});

        // Se agregan las funcionalidades para los botones del panel de entregas.
        btnCrearEntrega.addActionListener(ActionEvent -> {crearEntrega();});
        btnEliminarEntrega.addActionListener(ActionEvent -> {eliminarEntrega();});
        btnLimpiarEntrega.addActionListener(event -> {actualizarCampos();});
        btnAplicarFlitro.addActionListener(event -> {aplicarFlitro();});
        btnListarEntrega.addActionListener(event -> {listarTodas();});


    }
    // Se implementa un método para cargar las categorías de los JComboBox:

    /**
     * Método que carga las categorías que desplegarán las JComboBox relacionadas con "tipoPedido".
     * Cabe señalar, que se agregan como tipo "String" para poder utilizalos en la BD.
     */
    private void cargarCategoriasTipoPedido()
    {
        cboxTipo.removeAllItems();
        jcbTipoPedido.removeAllItems();
        for (TipoPedido t : TipoPedido.values())
        {
            cboxTipo.addItem(t.toString());
            jcbTipoPedido.addItem(t.toString());
        }
    }

    /**
     * Método que carga las categorías que desplegarán las JComboBox relacionadas con "estadoPedido".
     * Cabe señalar, que se agregan como tipo "String" para poder utilizalos en la BD.
     */
    private void cargarCategoriasEstadoPedido()
    {
        cboxEstado.removeAllItems();
        jcbEstadoPedido.removeAllItems();
        for (EstadoPedido e : EstadoPedido.values())
        {
            cboxEstado.addItem(e.toString());
            jcbEstadoPedido.addItem(e.toString());
        }
    }

    /**
     * Método que carga las categorías del JComboBox de la tabla "Entrega" (id_repartidor).
     * Añade los "IDs" registrados en la tabla "repartidores".
     * Estas se usarán para crear nuevas entregas.
     */
    private void cargarCategoriasJComboBoxRepartidor()
    {
        jcb_id_repartidor.removeAllItems();
        for (Repartidor r : controladorRepartidor.obtenerTodosLosRepartidores())
        {
            jcb_id_repartidor.addItem(r);
        }

    }

    /**
     * Método que carga las categorías del JComboBox de la tabla "Entrega" (id_pedido).
     * Añade los "IDs" registrados en la tabla "pedidos".
     * Estas se usarán para crear nuevas entregas.
     */
    private void cargarCategoriasJComboBoxPedido()
    {
        jcb_ID_PedidO.removeAllItems();
        for (Pedido p : controladorPedido.obtenerTodosLosPedidos())
        {
            jcb_ID_PedidO.addItem(p);
        }
    }

    // Se implementan los métodos que inicializarán lo valores en las tablas

    /**
     * Método que inicializa la tabla "Pedidos" con sus respectivas columnas.
     * Se definen los tamaños que tendrá cada columna.
     */
    private void inicializarTablaPedidos()
    {
        String[] columnas = {"ID_pedido", "Dirección_pedido", "Tipo_pedido", "Estado_pedido"};
        modeloTablaPedidos = new DefaultTableModel(columnas, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        tblPedido.setModel(modeloTablaPedidos);
        tblPedido.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPedido.getColumnModel().getColumn(0).setPreferredWidth(95);   // ID_pedido
        tblPedido.getColumnModel().getColumn(1).setPreferredWidth(220);  // Dirección
        tblPedido.getColumnModel().getColumn(2).setPreferredWidth(193);  // Tipo
        tblPedido.getColumnModel().getColumn(3).setPreferredWidth(193);  // Estado

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

    /**
     * Método que inicializa la tabla "Repartidores" con sus respectivas columnas.
     * Se definen los tamaños que tendrá cada columna.
     */
    private void inicializarTablaRepartidor()
    {
        String[] columnas = {"id_repartidor", "nombre_repartidor"};
        modeloTablaRepartidor = new DefaultTableModel(columnas, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        tblRepartidor.setModel(modeloTablaRepartidor);
        tblRepartidor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tblRepartidor.getColumnModel().getColumn(0).setPreferredWidth(112);
        tblRepartidor.getColumnModel().getColumn(1).setPreferredWidth(400);
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

    /**
     * Método que inicializa la tabla "Entregas" con sus respectivas columnas.
     */
    private void inicializarTablaEntrega()
    {
        String[] columnas = {"ID_entrega", "ID_repartidor", "ID_pedido", "fecha_creación_entrega", "hora_creación_entrega"};
        modeloTablaEntrega = new DefaultTableModel(columnas, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
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

    /**
     * Método que carga los pedidos existentes en la BD en la JTable "Pedidos"
     */
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

    /**
     * Método que carga la JTable de pedidos, utilizando la lista de pedidos almacenados y filtrados.
     * @param listaPedidos lista de objetos tipo "Pedido".
     */
    private void cargarTablaPedidosFiltrados(List<Pedido> listaPedidos)
    {
        modeloTablaPedidos.setRowCount(0); // limpia la tabla

        for (Pedido p : listaPedidos)
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

    /**
     * Método que carga la JTable de repartidores con toda la información de repartidores almacenada en la BD.
     */
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

    // ===================== CARGAR TABLA REPARTIDORES DESDE BD =====================

    /**
     * Método que permite cargar la JTable de repartidores filtrada por un objeto tipo Repartidor
     * @param repartidorFiltrado objeto tipo "Repartidor" que se quiere mostrar en la JTable de repartidores
     */
    private void cargarTablaRepartidoresFiltrada(Repartidor repartidorFiltrado)
    {
        modeloTablaRepartidor.setRowCount(0); // limpia la tabla

        List<Repartidor> repartidoresFiltrados = new ArrayList<Repartidor>();
        repartidoresFiltrados.add(repartidorFiltrado);

        for (Repartidor r : repartidoresFiltrados)
        {
            modeloTablaRepartidor.addRow(new Object[]{
                    r.getId_repartidor(),
                    r.getNombre_repartidor()
            });
        }
    }

    /**
     * Método que devuelve la lista de todos los repartidores almacenados en la BD.
     */
    private void listarTodosLosRepartidores()
    {
        cargarTablaRepartidores();
    }

    // ===================== CARGAR TABLA ENTREGAS DESDE BD =====================

    /**
     * Método que añade los valores existentes en la BD a la JTable de entregas.
     */
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

    /**
     * Método que añade valores filtrados a la JTable de entrega, a partir de una lista de objetos "Entrega" filtrados
     * @param listaEntregas lista de objetos "Entrega", los cuales se encuentran filtrados por repartidor o pedido.
     */
    private void cargarTablaEntregasFiltradaPorRepartidorOPedido(List<Entrega> listaEntregas)
    {
        modeloTablaEntrega.setRowCount(0); // limpia la tabla
        for (Entrega d : listaEntregas)
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

    /**
     * Método que agrega repartidores a la BD a partir de los campos ingresados por el usuario.
     */
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
            cargarCategoriasJComboBoxRepartidor(); // se llama a este método para actualizar los "JComboBox" de la parte "Entrega" de la GUI.
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No se logró agregar al repartidor");
        }

    }
    // ===================== EDITAR REPARTIDOR A LA BD =====================

    /**
     * Método que permite editar un repartidor almacenado en la BD.
     * Se debe marcar un repartidor de la JTable, ingresar el nuevo nombre y presionar el botón editar.
     */
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
            cargarCategoriasJComboBoxRepartidor();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al editar el repartidor");
        }
    }
    // ===================== ELIMINAR REPARTIDOR DE LA BD =====================

    /**
     * Método para eliminar un repartidor de la BD.
     * Requiere confirmar la eliminación en el cuadro emergente.
     */
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
            cargarCategoriasJComboBoxRepartidor();
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

    // ===================== FILTRAR REPARTIDOR POR ID =====================

    /**
     * Método que filtra un repartidor de la JTable por el ID ingresado en el campo.
     */
    private void filtrarRepartidorId()
    {
        idRepartidorSeleccionado = -1;
        // si el campo para filtrar el repartidor está vacío, lanza este cuadro emergente:
        if (txtIdRepartidorFiltro.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID en el campo");
            return;
        }
        // la variable "int idRepartidor" es igual al valor ingresado en el campo para filtrar el repartidor.
        int idRepartidor = Integer.parseInt(txtIdRepartidorFiltro.getText().trim());
        // El objeto tipo "Repartidor" llamado "repartidorFiltrado" es igual al resultado de la consulta "buscarRepartidor"
        // Se pasa como parámetro el valor obtenido del campo filtrar ingresado por el usuario
        Repartidor repartidorFiltrado = controladorRepartidor.buscarRepartidorPorId(idRepartidor);
        // Si el ID ingresado por el usuario no existe, lanza este cuadro emergente:
        if (repartidorFiltrado == null)
        {
            JOptionPane.showMessageDialog(this, "Repartidor no encontrado");
            return;
        }
        // si el ID existe, llama al método "repartidorFiltrado" y pásale como argumento el ID ingresado por el usuario
        // Este método devolverá la tabla "repartidores" filtrada con la fila que cumple con el ID del usuario.
        cargarTablaRepartidoresFiltrada(repartidorFiltrado);
    }

    // Se implementan los métodos que agregarán funcionalidad a los botones para "pedidos"
    // ===================== AGREGAR PEDIDO A LA BD =====================

    /**
     * Método para agregar un pedido a la BD.
     */
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
            cargarCategoriasJComboBoxPedido();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al agregar el pedido");
        }
    }

    // ===================== EDITAR PEDIDO EN LA BD =====================

    /**
     * Método para editar un pedido almacenado en la BD.
     * Actualiza la JTable de pedidos simultáneamente.
     */
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
            cargarCategoriasJComboBoxPedido();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al editar el pedido");
        }
    }

    // ===================== ELIMINAR PEDIDO DE LA BD =====================

    /**
     * Método para eliminar un pedido almacenado en la BD.
     * Requiere confirmación por medio de un cuadro emergente.
     */
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
            cargarCategoriasJComboBoxPedido();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error al eliminar el pedido");
        }
    }

    // ===================== LIMPIAR PEDIDOS DE LA INTERFAZ =====================

    /**
     * Método para limpiar los campos de la JTable de pedidos.
     */
    private void limpiarPedido()
    {
        txtDireccionPed.setText("");
        tblPedido.clearSelection();
        idPedidoSeleccionado = -1;
        modeloTablaPedidos.setRowCount(0);
    }

    // ===================== FILTRAR PEDIDOS DE LA INTERFAZ =====================

    /**
     * Método que permite filtrar los pedidos de la Jtable de pedidos
     * Requiere seleccionar los valores requeridos en cada jComboBox
     */
    private void filtrarPedidos()
    {
        List<Pedido> pedidoFiltrado;
        String tipoString = (String) jcbTipoPedido.getSelectedItem();
        if (tipoString.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de pedido para poder filtrar");
            return;
        }
        TipoPedido tipo = null;
        if (tipoString != null)
        {
            tipo = TipoPedido.valueOf(tipoString);
        }
        String estadoString = (String) jcbEstadoPedido.getSelectedItem();
        if (estadoString.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estado del pedido para poder filtrar");
            return;
        }

        EstadoPedido estado = null;
        if (estadoString != null)
        {
            estado = EstadoPedido.valueOf(estadoString);
        }
        pedidoFiltrado = controladorPedido.filtrarPorTipoOEstado(tipo, estado);
        cargarTablaPedidosFiltrados(pedidoFiltrado);
    }

    // ===================== LISTAR PEDIDOS DE LA INTERFAZ =====================

    /**
     * Método que permite listar todos los pedidos almacenados en la BD.
     */
    private void listarTodosLosPedidos()
    {
        List<Pedido> todosLosPedido = controladorPedido.obtenerTodosLosPedidos();
        if (todosLosPedido.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No se encontraron pedidos almacenados en la BD");
            return;
        }
        cargarTablaPedidos();
    }

    // Se implementan los métodos que agregarán funcionalidad a los botones para "entregas"
    // ===================== AGREGAR ENTREGA A LA BD =====================

    /**
     * Método que permite crear nuevas entregas.
     * Se requiere que existan repartidores y pedidos almacenados previamente.
     * Si un pedido ya fue asignado a un repartidor, no se puede asignar a otro.
     */
    private void crearEntrega()
    {
        Repartidor r = (Repartidor) jcb_id_repartidor.getSelectedItem();
        Pedido p = (Pedido) jcb_ID_PedidO.getSelectedItem();

        int id_repartidor = r.getId_repartidor();
        int id_pedido = p.getId_pedido();

        if (r == null || p == null)
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Repartidor y un Pedido");
            return;
        }

        if (jcb_ID_PedidO.getSelectedIndex() == -1 || jcb_id_repartidor.getSelectedIndex() == -1)
        {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un pedido y un repartidor para guardar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
                    return;
        }

        boolean existePedido = controladorEntrega.existeEstePedido(id_pedido);
        if (!existePedido)
        {
            JOptionPane.showMessageDialog(this, "Este pedido no existe");
            return;
        }

        Entrega e = new Entrega();
        e.setId_repartidor(id_repartidor);
        e.setId_pedido(id_pedido);
        e.setFecha(LocalDate.now());
        e.setHora(LocalTime.now());

        boolean esCorrecto = controladorEntrega.agregarEntrega(e);
        if (esCorrecto)
        {
            JOptionPane.showMessageDialog(this,
                    "Entrega agregada correctamente",
                    "éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                    "No se pudo agregar la entrega. Revise si el pedido ya fue asignado a un repartidor",
                    "fracaso",
                    JOptionPane.ERROR_MESSAGE);
        }
        cargarTablaEntregas();
    }
    // ===================== ELIMINAR ENTREGA DE LA BD =====================

    /**
     * Método que permite eliminar una entrega registrada en la BD.
     * Requiere confirmación en el cuadro emergente para completar la eliminación.
     */
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

    // ===================== LIMPIAR CAMPOS DE ENTREGA DE LA BD =====================

    /**
     * Método que limpia los campos de la JTable "Entrega".
     * No elimina registros, sólo los oculta.
     */
    private void actualizarCampos()
    {

        tblEntrega.clearSelection();
        idEntregaSeleccionado = -1;
        modeloTablaEntrega.setRowCount(0);
    }

    /**
     * Método que permite filtrar una búsqueda en la tabla "entregas".
     * Selecciona un id_pedido para buscar por pedidos.
     * Selecciona un id_repartidor para buscar por repartidor.
     */
    private void aplicarFlitro()
    {
        idPedidoSeleccionado = -1;
        String idRepartidorTxt = txtFiltrarIdRepartidorEntrega.getText().trim();
        String idPedidoTxt = txtFiltrarPedidoEntrega.getText().trim();

        if (!idPedidoTxt.isEmpty() && !idRepartidorTxt.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Sólo se puede agregar un filtro");
        }

        if (idPedidoTxt.isEmpty() && idRepartidorTxt.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Debe agregar, a lo menos, un filtro");
        }

        try
        {
            if (!idRepartidorTxt.isEmpty())
            {
                List<Entrega> listaEntregaRepartidor;
                int idRepartidor = Integer.parseInt(idRepartidorTxt);
                listaEntregaRepartidor = controladorEntrega.buscarEntregaPorRepartidor(idRepartidor);
                cargarTablaEntregasFiltradaPorRepartidorOPedido(listaEntregaRepartidor);
                return;
            }
            if (!idPedidoTxt.isEmpty())
            {
                List<Entrega> listaEntregaPedido;
                int idPedido = Integer.parseInt(idPedidoTxt);
                listaEntregaPedido = controladorEntrega.buscarEntregaPorPedidoLista(idPedido);
                cargarTablaEntregasFiltradaPorRepartidorOPedido(listaEntregaPedido);
                return;
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar un Id válido");
        }
    }

    /**
     * Método que permite volver a visualizar todos las entregas creadas
     */
    private void listarTodas()
    {
        cargarTablaEntregas();
    }
}
