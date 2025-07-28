package vista;

import javax.swing.*;
import java.awt.*;

public class ClienteVista extends JPanel {
    public JTextField txtNombre, txtApellido, txtDni;
    public JButton btnRegistrar;
    public JTable tablaClientes;

    public ClienteVista() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(4, 2, 5, 5));
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDni = new JTextField();
        btnRegistrar = new JButton("Registrar Cliente");

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Apellido:"));
        formulario.add(txtApellido);
        formulario.add(new JLabel("DNI:"));
        formulario.add(txtDni);
        formulario.add(new JLabel(""));
        formulario.add(btnRegistrar);

        tablaClientes = new JTable();  // Se llenará desde el controlador

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }
}

