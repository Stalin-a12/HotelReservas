package controlador;

import modelo.Cliente;
import modelo.ConexionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {

    private Connection conn;

    public ClienteController() {
        conn = ConexionDB.getConexion();
    }

    public boolean registrarCliente(Cliente cliente) {
        try {
            PreparedStatement buscar = conn.prepareStatement("SELECT * FROM clientes WHERE dni = ?");
            buscar.setString(1, cliente.getDni());
            ResultSet rs = buscar.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "DNI ya existe.");
                return false;
            }

            PreparedStatement insertar = conn.prepareStatement("INSERT INTO clientes(nombre, apellido, dni) VALUES (?, ?, ?)");
            insertar.setString(1, cliente.getNombre());
            insertar.setString(2, cliente.getApellido());
            insertar.setString(3, cliente.getDni());
            insertar.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar cliente.");
            return false;
        }
    }

    //  Método para obtener lista de clientes
    public List<Cliente> listarClientesDesdeBD() {
        List<Cliente> lista = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni")
                );
                c.setId(rs.getInt("id"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //  Método para llenar la tabla directamente
    public void llenarTablaClientes(JTable tabla) {
        String[] columnas = {"ID", "Nombre", "Apellido", "DNI"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Cliente c : listarClientesDesdeBD()) {
            Object[] fila = {c.getId(), c.getNombre(), c.getApellido(), c.getDni()};
            modelo.addRow(fila);
        }

        tabla.setModel(modelo);
    }
}

