package com.luis.intuneapp.basededatos;

public class ProductoVO {
    private int idProducto;
    private String descripcionProducto;
    private int cantidadProducto;
    private double precioProducto;
    private int estadoProducto;
    private int idMarca;

    public ProductoVO() {
    }

    public ProductoVO(String descripcionProducto, int cantidadProducto, double precioProducto,int idProducto) {
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
        this.idProducto = idProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(int estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }
}
