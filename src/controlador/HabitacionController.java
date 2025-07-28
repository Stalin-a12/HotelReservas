package controlador;

import modelo.ConexionDB;
import modelo.Habitacion;
import modelo.TipoHabitacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

@SuppressWarnings("unused")
public class HabitacionController {
    private Connection conn;

    public HabitacionController() {
        this.conn = ConexionDB.getConexion();
    }

    /**
     * Registra una nueva habitación en el sistema
     * @param habitacion Objeto Habitacion a registrar
     * @return true si se registró correctamente, false si hubo error
     */
    public boolean registrarHabitacion(Habitacion habitacion) {
        // Validación de entrada
        if (habitacion == null) {
            showError("La habitación no puede ser nula");
            return false;
        }

        if (habitacion.getNumero() == null || habitacion.getNumero().isEmpty()) {
            showError("El número de habitación es obligatorio");
            return false;
        }

        try {
            // Verificar si ya existe
            if (existeHabitacion(habitacion.getNumero())) {
                showError("Ya existe una habitación con el número: " + habitacion.getNumero());
                return false;
            }

            // Registrar la habitación
            String sql = "INSERT INTO habitaciones(numero, tipo, precio, disponible) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, habitacion.getNumero());
                ps.setString(2, habitacion.getTipo().name());
                ps.setDouble(3, habitacion.getPrecio());
                ps.setBoolean(4, habitacion.isDisponible());
                
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 1) {
                    showSuccess("Habitación registrada exitosamente");
                    return true;
                }
            }
        } catch (SQLException e) {
            showError("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Verifica si una habitación ya existe en la base de datos
     * @param numero Número de habitación a verificar
     * @return true si existe, false si no existe o hay error
     */
    private boolean existeHabitacion(String numero) throws SQLException {
        String sql = "SELECT 1 FROM habitaciones WHERE numero = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Muestra un mensaje de error
     * @param message Mensaje a mostrar
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito
     * @param message Mensaje a mostrar
     */
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void llenarTablaHabitaciones(JTable tabla) {
        String[] columnas = {"Número", "Tipo", "Precio", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        String sql = "SELECT * FROM habitaciones";

        try (PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String numero = rs.getString("numero");
                String tipo = rs.getString("tipo");
                double precio = rs.getDouble("precio");
                boolean disponible = rs.getBoolean("disponible");

                Object[] fila = {numero, tipo, precio, disponible ? "Sí" : "No"};
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            showError("Error al cargar habitaciones: " + e.getMessage());
        }
    }


}