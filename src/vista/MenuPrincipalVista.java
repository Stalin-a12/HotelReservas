package vista;
import controlador.ClienteController;
import controlador.HabitacionController;
import controlador.ReservaController;
import modelo.Administrador;
import modelo.Cliente;
import modelo.Habitacion;
import modelo.Recepcionista;
import modelo.Reserva;
import modelo.TipoHabitacion;
import modelo.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class MenuPrincipalVista extends JFrame {
    public JTabbedPane pestañas;

    public MenuPrincipalVista(Usuario usuario) {
        setTitle("Menú Principal - Hotel");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pestañas = new JTabbedPane();

        // Mostrar pestañas según el tipo de usuario
        if (usuario instanceof Administrador) {
            agregarPestañaClientes();
            agregarPestañaHabitaciones();
            agregarPestañaReservas();
        } else if (usuario instanceof Recepcionista) {
            agregarPestañaReservas();
        }

        add(pestañas);
        setVisible(true);

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra esta ventana
            new LoginVista(); // Regresa al login
        });

        // Lo agregamos en la parte inferior
        add(btnCerrarSesion, java.awt.BorderLayout.SOUTH); // O usa un JPanel si tienes otro layout

        setVisible(true);
    }

    private void agregarPestañaClientes() {
        ClienteVista clienteVista = new ClienteVista();
        ClienteController clienteController = new ClienteController();

        clienteController.llenarTablaClientes(clienteVista.tablaClientes);

        clienteVista.btnRegistrar.addActionListener(e -> {
            String nombre = clienteVista.txtNombre.getText();
            String apellido = clienteVista.txtApellido.getText();
            String dni = clienteVista.txtDni.getText();

            if (!nombre.isBlank() && !apellido.isBlank() && !dni.isBlank()) {
                Cliente cliente = new Cliente(nombre, apellido, dni);
                if (clienteController.registrarCliente(cliente)) {
                    clienteController.llenarTablaClientes(clienteVista.tablaClientes);
                    clienteVista.txtNombre.setText("");
                    clienteVista.txtApellido.setText("");
                    clienteVista.txtDni.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            }
        });

        pestañas.add("Clientes", clienteVista);
    }

    private void agregarPestañaHabitaciones() {
        HabitacionVista habitacionVista = new HabitacionVista();
        HabitacionController habitacionController = new HabitacionController();

        habitacionController.llenarTablaHabitaciones(habitacionVista.tablaHabitaciones);

        habitacionVista.btnGuardar.addActionListener(e -> {
            String numero = habitacionVista.txtNumero.getText();
            String tipoStr = (String) habitacionVista.comboTipo.getSelectedItem();
            boolean disponible = habitacionVista.chkDisponible.isSelected();

            if (!numero.isBlank()) {
                Habitacion hab = new Habitacion(numero, TipoHabitacion.valueOf(tipoStr), disponible);
                if (habitacionController.registrarHabitacion(hab)) {
                    habitacionController.llenarTablaHabitaciones(habitacionVista.tablaHabitaciones);
                    habitacionVista.txtNumero.setText("");
                    habitacionVista.chkDisponible.setSelected(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Número de habitación obligatorio.");
            }
        });

        pestañas.add("Habitaciones", habitacionVista);
    }

    private void agregarPestañaReservas() {
        ReservaVista reservaVista = new ReservaVista();
        ReservaController reservaController = new ReservaController();

        List<Habitacion> disponibles = reservaController.ObtenerHabitacionDisponibles();
        for (Habitacion h : disponibles) {
            reservaVista.comboHabitaciones.addItem(h);
        }
        reservaController.cargarReservasEnTabla(reservaVista.tablaReservas);

        reservaVista.btnReservar.addActionListener(e -> {
            try {
                String dni = reservaVista.txtDni.getText();
                String inicio = reservaVista.txtFechaInicio.getText();
                String fin = reservaVista.txtFechaFin.getText();
                String metodoPago = (String) reservaVista.comboMetodoPago.getSelectedItem();

                if (dni.isBlank() || inicio.isBlank() || fin.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son requeridos.");
                    return;
                }

                Cliente cliente = reservaController.buscarClientePorDni(dni);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                    return;
                }

                Habitacion habSeleccionada = (Habitacion) reservaVista.comboHabitaciones.getSelectedItem();

                Reserva reserva = new Reserva(
                        cliente,
                        habSeleccionada,
                        LocalDate.parse(inicio),
                        LocalDate.parse(fin)
                );
                double precioBase = habSeleccionada.getPrecio();
                double total = precioBase;

                if ("Descuento".equals(metodoPago)) {
                    total = precioBase * 0.9;
                }
                reservaVista.lblTotal.setText(String.format("Total: $%.2f", total));

                if (reservaController.crearReserva(reserva)) {
                    JOptionPane.showMessageDialog(this, "Reserva registrada exitosamente.");
                    reservaController.cargarReservasEnTabla(reservaVista.tablaReservas);
                    reservaVista.txtDni.setText("");
                    reservaVista.txtFechaInicio.setText("YYYY-MM-DD");
                    reservaVista.txtFechaFin.setText("YYYY-MM-DD");
                    reservaVista.lblTotal.setText("Total: $0.00");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido o error general.");
            }
        });

        pestañas.add("Reservas", reservaVista);
    }
}


