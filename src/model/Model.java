package model;

import com.mysql.jdbc.Connection;
import controller.Inventory;
import controller.Product;
import controller.Provider;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;

public class Model {
    //PARÁMETROS PARA LA CONEXIÓN A LA BD
    
    private static final String url = "jdbc:mysql://localhost:3306/canastafeliz";
    private static final String user = "root";
    private static final String password = "";
    private static final String driver = "com.mysql.jdbc.Driver";
    private static PreparedStatement ps;
    private static Connection con;

    //CONSTRUCTOR
    public Model() {
    }
    
    //CONEXIÓN A LA BD
    public Connection connect(){
        
        try {
            
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection (url, user, password);
            
        } catch (ClassNotFoundException | SQLException ex) {
            
            System.out.println("NO SE PUDO CONECTAR A LA BASE DE DATOS");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return con;
    }
    
    //DESCONEXIÓN A LA BD
    public void disconnect (){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet getProviders() throws SQLException{
        String query = "select * from proveedores";
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public ResultSet getProducts() throws SQLException{
        String query = "select productos.id_producto, productos.nombre, productos.precio, productos.merma_promedio, proveedores.nombre"
                + " from productos inner join proveedores on productos.fk_id_proveedor=proveedores.id_proveedor";
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public ResultSet getProduct(int id) throws SQLException{
        String query = "select * from productos where id_producto="+id;
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public ResultSet getInvProductDates(int productId) throws SQLException{
        String query = "select fecha_entrada, id_inventario from inventario where fk_id_producto="+productId;
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public ResultSet getInvProduct(int inventoryId) throws SQLException{
        String query = "select * from inventario where id_inventario="+inventoryId;
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public ResultSet login(String user, String pass) throws SQLException{
        String query = "select nombre, apellido, cedula, rol from usuarios where usuario=? and clave=?";
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public ResultSet getInvProducts() throws SQLException{
        String query = "select inventario.fk_id_producto, productos.nombre, inventario.peso_inicial, inventario.peso_actual, inventario.fecha_entrada"
                + " from inventario inner join productos on inventario.fk_id_producto=productos.id_producto";
        ResultSet rs = null;
 
        try {
            con = connect();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
         
        } catch (SQLException e) {
            System.out.println("ERROR. " + e);
        } finally {
            return rs;
        }
    }
    
    public void postProvider(Provider p) throws SQLException, Exception {
        String query = "insert into proveedores (rif, nombre, telefono, correo, direccion) values (?,?,?,?,?)";
        try {
            con = connect();
            
            ps = con.prepareStatement(query);
            ps.setString(1, p.getRif());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getDireccion());

            ps.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("ERROR. " + e.getMessage());
            if(e.getErrorCode() == 1062){
                showMessageDialog(null, "ERROR. Ya existe un proveedor con este número de RIF/Cédula");
                throw new Exception();
            }
        }
    }
   
       public void postProduct(Product p) throws SQLException, Exception {
        String query = "insert into productos (nombre, precio, merma_promedio, fk_id_proveedor) values (?,?,?,?)";
        try {
            con = connect();
            
            ps = con.prepareStatement(query);
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setDouble(3, p.getMermaPromedio());
            ps.setDouble(4, p.getIdProveedor());

            ps.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("ERROR. " + e.getMessage());
        }
    }
       
       public void postInvProduct(Inventory p) throws SQLException, Exception {
        String query = "insert into inventario (fk_id_producto, peso_inicial, peso_actual, fecha_entrada) values (?,?,?,?)";
        try {
            con = connect();
            
            ps = con.prepareStatement(query);
            ps.setInt(1, p.getIdProducto());
            ps.setDouble(2, p.getPesoInicial());
            ps.setDouble(3, p.getPesoActual());
            ps.setString(4, p.getFechaEntrada());

            ps.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("ERROR. " + e.getMessage());
        }
    }
       
       public void updateInvProduct(double newCurrentWeight, int id) throws SQLException, Exception {
        String query = "update inventario set peso_actual = ? where id_inventario = ?";
        try {
            con = connect();
            
            ps = con.prepareStatement(query);
            
            ps.setDouble(1, newCurrentWeight);
            ps.setInt(2, id);

            ps.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("ERROR. " + e.getMessage());
        }
    }
    
    
}
