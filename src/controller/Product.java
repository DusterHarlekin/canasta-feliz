
package controller;

 //PRODUCTOS
public class Product {
  
    private int idProveedor;
    private String nombre;
    private double precio;
    private double mermaPromedio;
    
    //CONSTRUCTOR

    public Product(int idProveedor, String nombre, double precio, double mermaPromedio) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.precio = precio;
        this.mermaPromedio = mermaPromedio;
    }
    
   
    //GETTERS Y SETTERS

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getMermaPromedio() {
        return mermaPromedio;
    }

    public void setMermaPromedio(double mermaPromedio) {
        this.mermaPromedio = mermaPromedio;
    }

    
    
}
