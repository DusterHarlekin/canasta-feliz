
package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inventory {
    private int idProducto;
    private double pesoInicial;
    private double pesoActual;
    private String fechaEntrada;

    public Inventory(int idProducto, double pesoInicial, double pesoActual) {
        this.idProducto = idProducto;
        this.pesoInicial = pesoInicial;
        this.pesoActual = pesoActual;
        this.fechaEntrada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(double pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(double pesoActual) {
        this.pesoActual = pesoActual;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    
}

