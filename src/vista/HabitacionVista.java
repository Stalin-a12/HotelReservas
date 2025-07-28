package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HabitacionVista extends JPanel {
    public JTextField txtNumero;
    public JTextField txtPrecio; // NUEVO
    public JComboBox<String> comboTipo;
    public JCheckBox chkDisponible;
    public JButton btnGuardar;
    public JTable tablaHabitaciones;

    public HabitacionVista() {
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        txtNumero = new JTextField();
        txtPrecio = new JTextField(); // NUEVO
        comboTipo = new JComboBox<>(new String[]{"INDIVIDUAL", "DOBLE", "SUITE"});
        chkDisponible = new JCheckBox("Disponible");
        btnGuardar = new JButton("Guardar");

        panelForm.add(new JLabel("Número:"));
        panelForm.add(txtNumero);
        panelForm.add(new JLabel("Tipo:"));
        panelForm.add(comboTipo);
        panelForm.add(new JLabel("Precio:")); // NUEVO
        panelForm.add(txtPrecio);             // NUEVO
        panelForm.add(new JLabel("Estado:"));
        panelForm.add(chkDisponible);
        panelForm.add(new JLabel(""));
        panelForm.add(btnGuardar);

        // Crear modelo con columna de precio
        String[] columnas = {"Número", "Tipo", "Precio", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaHabitaciones = new JTable(modelo);

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(tablaHabitaciones), BorderLayout.CENTER);
    }

    // Getter para el modelo de tabla si lo necesitas desde el controlador
    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaHabitaciones.getModel();
    }
}
