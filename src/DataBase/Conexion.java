/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Clase para manejar la conexión a la base de datos MySQL con el patrón Singleton.
 */
public class Conexion {
    private static Conexion singleInstance;
    private Connection connection;

    // Datos de conexión
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB = "puntoventa";
    private static final String USER = "root";
    private static final String PASSWORD = "secreto123";

    /**
     * Constructor privado para evitar múltiples instancias.
     */
    private Conexion() {
        this.connection = null;
    }

    /**
     * Método para obtener una única instancia de la clase (Singleton).
     * @return Conexion - instancia única de la clase.
     */
    public synchronized static Conexion getInstance() {
        if (singleInstance == null) {
            singleInstance = new Conexion();
        }
        return singleInstance;
    }

    /**
     * Método para establecer la conexión con la base de datos.
     * @return Connection - conexión establecida o null si hay un error.
     */
    public Connection conectar() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                // Cargar el driver de MySQL
                Class.forName(DB_DRIVER);
                // Establecer la conexión
                this.connection = DriverManager.getConnection(URL + DB, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            return null;
        }
        return this.connection;
    }

    /**
     * Método para obtener la conexión actual.
     * @return Connection - conexión activa o null.
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Método para cerrar la conexión.
     */
    public void cerrarConexion() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
