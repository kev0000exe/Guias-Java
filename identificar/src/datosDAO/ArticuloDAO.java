/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datosDAO;

import datos.interfaces.CRUDGeneralInterface;
import java.util.List;
import database.Conexion;
import datos.interfaces.CrudPaginadoInterface;
import entidades.Articulo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;




/**
 *
 * @author offic
 */

public class ArticuloDAO implements CrudPaginadoInterface<Articulo> {
    
    private final Conexion conectar;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    
    public ArticuloDAO(){
        conectar = Conexion.getInstance();
    }

    @Override
    public List<Articulo> getAll(String list, int totalPorPagina, int numPagina) {
       List<Articulo> registros = new ArrayList();
        try {
            ps = conectar.conectar().prepareStatement(
             "SELECT "+
              "a.idArticulo,"+
              "a.categoria_id,"+
              "c.nombre as categoria_nombre,"+ 
              "a.codigo," +
              "a.nombre,"+
              "a.precio_venta,"+
              "a.stock,"+
              "a.descripcion,"+
              "a.imagen,"+
              "a.estado"+     
             "FROM  articulo a" +
             "inner join categoria c" +
             "ON a.categoria_id = c.id"+
             "Where a.nombre Like ?" +
             "Order by a.idArticulo ASC" +
             "Limit ?, ?"
            
            );
            ps.setString(1, "%" + list + "%");
            ps.setInt(2, (numPagina-1) * totalPorPagina );
            ps.setInt(2, totalPorPagina );
            
            rs = ps.executeQuery();
            while(rs.next()){
                registros.add(new Articulo 
                       (rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getBoolean(9)));
            }
            ps.close();
            rs.close(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            rs = null; 
            conectar.desconectar();
        }
        return  registros ;
    }

    @Override
    public boolean insert(Articulo object) {
    resp = false;
    try {
        ps = conectar.conectar().prepareStatement
        ("INSERT INTO categoria "
                + "(categoria_id,"
                + "codigo,"
                + "nombre,"
                + "precio_venta,"
                + "stock,"
                + "descripcion"
                + "imagen"
                + "estado) "
                + "VALUES"
                + "(?,?,?,?,?,?,?,1)");
            ps.setInt(1, object.getCategoria_id());
            ps.setString(2, object.getCodigo());
            ps.setString(3, object.getNombre());
            ps.setDouble(4, object.getPrecio_venta());
            ps.setInt(5, object.getStock());
            ps.setString(6, object.getDescripcion());
             ps.setString(7, object.getImagen());
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Tenemos un problema al insertar el dato " + e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
}




    @Override
    public boolean update(Articulo object) {
    resp = false;
    try {
        ps = conectar.conectar().prepareStatement
        ("Update categoria SET "
                + "categoria_id=?, "
                + "codigo=?"
                + "nombre=?"
                + "precio_venta=?"
                + "stock=?"
                + "desscripocion=?"
                + "imagen=?"
                + "where id= ?");
            ps.setInt(1, object.getCategoria_id());
            ps.setString(2, object.getCodigo());
            ps.setString(3, object.getNombre());
            ps.setDouble(4, object.getPrecio_venta());
            ps.setInt(5, object.getStock());
            ps.setString(6, object.getDescripcion());
            ps.setString(7, object.getImagen());
            ps.setInt(8, object.getIdArticulo());
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
}
    @Override
    public boolean onnVariable(int id) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update articulo SET estado=1 where id= ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;

    }

    @Override
    public boolean offVariable(int id) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("Update articulo SET estado=0 where id= ?");
            ps.setInt(1, id);
            if(ps.executeUpdate() > 0){
                resp = true;
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
            ps = null;
            conectar.desconectar();
        }
        return resp;
    }

    @Override
    public boolean exist(String text) {
        resp = false;
        try {
            ps = conectar.conectar().prepareStatement
        ("select nombre from articulo where id = ?");
            ps.setString(1, text);
            rs = ps.executeQuery();
            rs.last();
            
            if(rs.getRow()> 0){
                resp = true;
            }
            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Creando el objeto");
        }finally{
             ps = null;
             rs = null;
             conectar.desconectar();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistro = 0;
        try {
            ps = conectar.conectar().prepareStatement
        ("select  count(id) from articulo");
            rs = ps.executeQuery();
            while(rs.next()){
                totalRegistro = rs.getInt("count(id)");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally{
             ps = null;
             rs = null;
             conectar.desconectar();
        }
        return totalRegistro;
        
    }
    
     
}
