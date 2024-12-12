
package controller;

 //PRODUCTOS
public class Product {
  
    private int fk_id_proveedor;
    private String nombre;
    private double precio;
    
    //CONSTRUCTOR
    public Product(int fk_id_proveedor, String nombre, double precio) {
        this.fk_id_proveedor = fk_id_proveedor;
        this.nombre = nombre;
        this.precio = precio;
    }
   
    //GETTERS Y SETTERS

    public int getFk_id_proveedor() {
        return fk_id_proveedor;
    }

    public void setFk_id_proveedor(int fk_id_proveedor) {
        this.fk_id_proveedor = fk_id_proveedor;
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
}
