/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DataBase;


public class pruebaConexion {
    public static void main(String[] args){
        Conexion con = Conexion.getInstance();
        con.conectar();
        
        // Verificar si la conexión es válida
        if(con.getConnection() != null){
            System.out.println("Conectado");
        }else{
            System.out.println("Sin conexión");
        }
    }
 
}
