package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static Connection conexion;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                // Registrar el driver (no necesario desde JDBC 4.0, pero lo dejamos por compatibilidad)
                Class.forName("org.sqlite.JDBC");
                
                // Establecer conexión (la base de datos se creará automáticamente si no existe)
                conexion = DriverManager.getConnection("jdbc:sqlite:db/hotel.db");
                
                // Crear tablas si no existen
                crearTablas();
                
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Error al conectar a la base de datos:");
                e.printStackTrace();
                throw new RuntimeException("No se pudo conectar a la base de datos", e);
            }
        }
        return conexion;
    }

    private static void crearTablas() throws SQLException {
        try (Statement stmt = conexion.createStatement()) {
            // Tabla habitaciones
            stmt.execute("CREATE TABLE IF NOT EXISTS habitaciones (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "numero TEXT UNIQUE NOT NULL," +
                        "tipo TEXT NOT NULL," +
                        "precio REAL NOT NULL," +
                        "disponible INTEGER NOT NULL)");
            
            // Tabla clientes
            stmt.execute("CREATE TABLE IF NOT EXISTS clientes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "dni TEXT UNIQUE NOT NULL," +
                        "nombre TEXT NOT NULL," +
                        "apellido TEXT NOT NULL," +
                        "telefono TEXT," +
                        "email TEXT)");
            
            // Tabla reservas
            stmt.execute("CREATE TABLE IF NOT EXISTS reservas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cliente_id INTEGER NOT NULL," +
                        "habitacion_id INTEGER NOT NULL," +
                        "fecha_inicio TEXT NOT NULL," +
                        "fecha_fin TEXT NOT NULL," +
                        "FOREIGN KEY (cliente_id) REFERENCES clientes(id)," +
                        "FOREIGN KEY (habitacion_id) REFERENCES habitaciones(id))");
        }
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión:");
                e.printStackTrace();
            }
        }
    }
}
