/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos.interfaces;
import java.util.List;
/**
 *
 * @author offic
 */
public interface CRUDGeneralInterface <T> {
    public List<T> getAll(String list);
    public boolean insert(T object);
    public boolean update(T object);
    public boolean onnVariable(int id);
    public boolean offVariable(int id);
    public boolean exist(String text);
    public int total();
    
    
   
    
    
}
