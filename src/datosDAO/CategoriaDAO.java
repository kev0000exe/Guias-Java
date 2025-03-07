/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datosDAO;

import datos.interfaces.CRUDGeneralInterface;
import entidades.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDAO implements CRUDGeneralInterface<Categoria> {

    private final Conexion conectar;
    private boolean resp;

    public CategoriaDAO() {
        conectar = Conexion.getInstance();
    }

    public List<Categoria> getAll(String nombre) {
        List<Categoria> registros = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE nombre LIKE ?";

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    registros.add(new Categoria(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4)
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener categorías: " + e.getMessage());
        }
        return registros;
    }

    @Override
    public boolean insert(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion, estado) VALUES (?, ?, ?)";
        resp = false;

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isEstado());

            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar categoría: " + e.getMessage());
        }
        return resp;
    }

    public boolean update(Categoria categoria) {
        resp = false;
        String sql = "UPDATE categoria SET nombre=?, descripcion=? WHERE id=?";

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setInt(3, categoria.getId());

            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar categoría: " + e.getMessage());
        }
        return resp;
    }

    public boolean offVariable(int id) {
        resp = false;
        String sql = "UPDATE categoria SET estado=0 WHERE id=?";

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar categoría: " + e.getMessage());
        }
        return resp;
    }

    @Override
    public boolean onVariable(int id) {
        resp = false;
        String sql = "UPDATE categoria SET estado=1 WHERE id=?";

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar categoría: " + e.getMessage());
        }
        return resp;
    }

    public int total() {
        int totalRegistro = 0;
        String sql = "SELECT COUNT(id) FROM categoria";

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalRegistro = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar categorías: " + e.getMessage());
        }
        return totalRegistro;
    }

    public boolean exist(String nombre) {
        resp = false;
        String sql = "SELECT nombre FROM categoria WHERE nombre = ?";

        try (Connection conn = conectar.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resp = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar existencia de categoría: " + e.getMessage());
        }
        return resp;
    }
}
