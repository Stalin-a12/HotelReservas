package vista;

import javax.swing.*;

import modelo.Habitacion;

import java.awt.*;

public class ReservaVista extends JPanel {
    public JTextField txtDni, txtFechaInicio, txtFechaFin;
    public JButton btnReservar;
    public JTable tablaReservas;
    public JComboBox<Habitacion> comboHabitaciones;
    public JComboBox<String> comboMetodoPago;
    public JLabel lblTotal;

    public ReservaVista() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        txtDni = new JTextField();
        txtFechaInicio = new JTextField("YYYY-MM-DD");
        txtFechaFin = new JTextField("YYYY-MM-DD");
        comboHabitaciones = new JComboBox<>();
        btnReservar = new JButton("Reservar");

        comboMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Descuento"});
        lblTotal = new JLabel("Total: $0.00");

        formPanel.add(new JLabel("DNI del Cliente:"));
        formPanel.add(txtDni);
        formPanel.add(new JLabel("Fecha Inicio:"));
        formPanel.add(txtFechaInicio);
        formPanel.add(new JLabel("Fecha Fin:"));
        formPanel.add(txtFechaFin);
        formPanel.add(new JLabel("Habitacion:"));
        formPanel.add(comboHabitaciones);
        formPanel.add(new JLabel("Método de Pago:"));
        formPanel.add(comboMetodoPago);
        formPanel.add(new JLabel(""));
        formPanel.add(lblTotal);
        formPanel.add(new JLabel(""));
        formPanel.add(btnReservar);

        tablaReservas = new JTable();

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(tablaReservas), BorderLayout.CENTER);
    }
}
