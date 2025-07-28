package controlador;

import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaController {

    private Connection conn;

    public ReservaController() {
        conn = ConexionDB.getConexion();
    }

    public boolean crearReserva(Reserva reserva) {
        try {
            PreparedStatement verificar = conn.prepareStatement(
                "SELECT * FROM reservas WHERE habitacion_id = ? AND (" +
                "(fecha_inicio <= ? AND fecha_fin >= ?) OR " +
                "(fecha_inicio <= ? AND fecha_fin >= ?))"
            );
            verificar.setInt(1, reserva.getHabitacion().getId());
            verificar.setString(2, reserva.getFechaFin().toString());
            verificar.setString(3, reserva.getFechaFin().toString());
            verificar.setString(4, reserva.getFechaInicio().toString());
            verificar.setString(5, reserva.getFechaInicio().toString());

            ResultSet rs = verificar.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "La habitación ya está reservada en ese periodo.");
                return false;
            }

            PreparedStatement insertar = conn.prepareStatement(
                "INSERT INTO reservas(cliente_id, habitacion_id, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)"
            );
            insertar.setInt(1, reserva.getCliente().getId());
            insertar.setInt(2, reserva.getHabitacion().getId());
            insertar.setString(3, reserva.getFechaInicio().toString());
            insertar.setString(4, reserva.getFechaFin().toString());
            insertar.executeUpdate();

            JOptionPane.showMessageDialog(null, "Reserva creada con éxito.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al crear reserva.");
            return false;
        }
    }

    public Cliente buscarClientePorDni(String dni) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM clientes WHERE dni = ?");
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni")
                );
                cliente.setId(rs.getInt("id"));
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Habitacion> ObtenerHabitacionDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        try {
            String sql = "SELECT * FROM habitaciones WHERE disponible = true";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Habitacion h = new Habitacion(
                        rs.getString("numero"),
                        TipoHabitacion.valueOf(rs.getString("tipo")),
                        rs.getBoolean("disponible")
                );
                h.setId(rs.getInt("id"));
                disponibles.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disponibles;
    }

    public void cargarReservasEnTabla(JTable tabla) {
        try {
            String[] columnas = { "Cliente", "Habitación", "Inicio", "Fin" };
            DefaultTableModel modelo = new DefaultTableModel(null, columnas);

            PreparedStatement ps = conn.prepareStatement(
                "SELECT c.nombre || ' ' || c.apellido AS cliente, " +
                "h.numero AS habitacion, r.fecha_inicio, r.fecha_fin " +
                "FROM reservas r " +
                "JOIN clientes c ON r.cliente_id = c.id " +
                "JOIN habitaciones h ON r.habitacion_id = h.id"
            );

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("cliente"),
                    rs.getString("habitacion"),
                    rs.getString("fecha_inicio"),
                    rs.getString("fecha_fin")
                };
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar reservas.");
        }
    }

}
