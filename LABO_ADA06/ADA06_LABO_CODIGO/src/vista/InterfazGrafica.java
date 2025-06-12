
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import modelo.*;
import java.util.List;

public class InterfazGrafica extends JFrame {
    private TablaHashLineal tablaLineal;
    private TablaHashABB tablaABB;
    private JTextField txtNombres, txtApellidos, txtTelefono, txtCorreo, txtDireccion, txtCodigoPostal;
    private JTextArea txtResultados;
    private int ultimoId = 0;

    public InterfazGrafica() {
       
        tablaLineal = new TablaHashLineal(100);
        tablaABB = new TablaHashABB(100);

        
        setTitle("Gestión de Clientes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel panelEntrada = new JPanel(new GridLayout(6, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelEntrada.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        panelEntrada.add(txtNombres);

        panelEntrada.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelEntrada.add(txtApellidos);

        panelEntrada.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelEntrada.add(txtTelefono);

        panelEntrada.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelEntrada.add(txtCorreo);

        panelEntrada.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelEntrada.add(txtDireccion);

        panelEntrada.add(new JLabel("Código Postal:"));
        txtCodigoPostal = new JTextField();
        panelEntrada.add(txtCodigoPostal);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton btnInsertar = new JButton("Insertar Cliente");
        btnInsertar.addActionListener(this::insertarCliente);
        panelBotones.add(btnInsertar);

        JButton btnBuscar = new JButton("Buscar por Nombre+Apellido");
        btnBuscar.addActionListener(this::buscarCliente);
        panelBotones.add(btnBuscar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e ->limpiarCampos() );
        panelBotones.add(btnLimpiar);

        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.addActionListener(this::mostrarTodosClientes);
        panelBotones.add(btnMostrarTodos);

        
        txtResultados = new JTextArea(10, 60); 
        txtResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelBotones, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);

        
        add(panelEntrada, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);


        cargarDatosIniciales();
    }


    private void cargarDatosIniciales() {
        List<Cliente> clientes = GestorCSV.cargarClientes();
        if (!clientes.isEmpty()) {
            ultimoId = clientes.get(clientes.size() - 1).getId();
            for (Cliente cliente : clientes) {
                tablaLineal.insertar(cliente);
                tablaABB.insertar(cliente);
            }
            txtResultados.append("Se cargaron " + clientes.size() + " clientes desde el archivo.\n");
        }
    }

    private void insertarCliente(ActionEvent e) {
        if (validarCampos()) {
            ultimoId++;
            String codigo = "CLI-" + String.format("%04d", ultimoId);
            Cliente cliente = new Cliente(
                ultimoId,
                codigo,
                txtNombres.getText(),
                txtApellidos.getText(),
                txtTelefono.getText(),
                txtCorreo.getText(),
                txtDireccion.getText(),
                txtCodigoPostal.getText()
            );
            
            
            tablaLineal.insertar(cliente);
            tablaABB.insertar(cliente);
            
           
            List<Cliente> todosClientes = obtenerTodosClientes();
            todosClientes.add(cliente);
            GestorCSV.guardarClientes(todosClientes);
            txtResultados.setText("");
            txtResultados.append("Cliente insertado: " + cliente + "\n");
            limpiarCampos();
        }
    }

    private List<Cliente> obtenerTodosClientes() {

        return GestorCSV.cargarClientes();
    }

    private void mostrarTodosClientes(ActionEvent e) {
        txtResultados.setText("");
        List<Cliente> clientes = GestorCSV.cargarClientes();
        txtResultados.append("\n--- Todos los Clientes ---\n");
        for (Cliente cliente : clientes) {
            txtResultados.append(cliente + "\n");
        }
        txtResultados.append("Total: " + clientes.size() + " clientes\n\n");
    }

    private void buscarCliente(ActionEvent e) {
        if (txtNombres.getText().isEmpty() || txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese nombres y apellidos para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        txtResultados.setText("");
        String clave = txtNombres.getText() + txtApellidos.getText();
        
        long inicio = System.nanoTime();
        Cliente encontradoLineal = tablaLineal.buscar(clave);
        long finLineal = System.nanoTime();
        Cliente encontradoABB = tablaABB.buscar(clave);
        long finABB = System.nanoTime();
        
        txtResultados.append("\n--- Búsqueda ---\n");
        txtResultados.append("Clave: " + clave + "\n");
        txtResultados.append("Reasignación Lineal: " + (encontradoLineal != null ? encontradoLineal : "No encontrado") + "\n");
        txtResultados.append("Tiempo búsqueda (Lineal): " + (finLineal - inicio) + " ns\n");
        txtResultados.append("Encadenamiento ABB: " + (encontradoABB != null ? encontradoABB : "No encontrado") + "\n");
        txtResultados.append("Tiempo búsqueda (ABB): " + (finABB - finLineal) + " ns\n\n");
    }

    private boolean validarCampos() {
        if (txtNombres.getText().isEmpty() || 
            txtApellidos.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos obligatorios son: Código, Nombres, Apellidos y Teléfono", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtCodigoPostal.setText("");
        txtResultados.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazGrafica gui = new InterfazGrafica();
            gui.setVisible(true);
        });
    }
}