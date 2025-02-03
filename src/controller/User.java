/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author rogerguedez
 */
public class User {
    private String usuario;
    private String clave;
    private String nombre;
    private String apellido;
    private String cedula;
    private int rol;
    private final String rolText;

    public User(String usuario, String clave, String nombre, String apellido, String cedula, int rol) {
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.rol = rol;
        
        switch (rol){
            case 1:
                this.rolText = "Técnico";
                break;
            case 2:
                this.rolText = "Supervisor";
                break;
            default:
                this.rolText = "Cocinero";
        }
        
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getRolText() {
        return rolText;
    }
    
    
    
    
}
