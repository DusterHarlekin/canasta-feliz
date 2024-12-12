
package controller;

//CORTES
public class Cortes {
    private int fk_id_producto;
    private String nombre;
    private String merma_promedio;
    
    //CONSTRUCTOR

    public Cortes(int fk_id_producto, String nombre, String merma_promedio) {
        this.fk_id_producto = fk_id_producto;
        this.nombre = nombre;
        this.merma_promedio = merma_promedio;
    }
    
  //GETTERS Y SETTER

    public int getFk_id_producto() {
        return fk_id_producto;
    }

    public void setFk_id_producto(int fk_id_producto) {
        this.fk_id_producto = fk_id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMerma_promedio() {
        return merma_promedio;
    }

    public void setMerma_promedio(String merma_promedio) {
        this.merma_promedio = merma_promedio;
    }
    
}
