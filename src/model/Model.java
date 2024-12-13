package model;

import com.mysql.jdbc.Connection;
import controller.Product;
import controller.Provider;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String query = "select productos.nombre, productos.precio, productos.merma_promedio, proveedores.nombre, proveedores.apellido"
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
    
    public void postProvider(Provider p) throws SQLException, Exception {
        String query = "insert into proveedores (rif, nombre, apellido, telefono, correo, direccion) values (?,?,?,?,?,?)";
        try {
            con = connect();
            
            ps = con.prepareStatement(query);
            ps.setString(1, p.getRif());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getTelefono());
            ps.setString(6, p.getDireccion());
            ps.setString(5, p.getCorreo());

            ps.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("ERROR. " + e.getMessage());
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
    
    
}
