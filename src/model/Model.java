package model;

import com.mysql.jdbc.Connection;
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
            System.out.println("LA CONEXIÓN FUE UN ÉXITO");
            
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
        String query = "select productos.nombre, productos.precio, proveedores.nombre, proveedores.apellido"
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
    
    public ResultSet getCuts() throws SQLException{
        String query = "select cortes.nombre, cortes.merma_promedio, productos.nombre"
                + " from cortes inner join productos on cortes.fk_id_producto=productos.id_producto";
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
    
}
