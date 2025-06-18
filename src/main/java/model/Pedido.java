package model;

import java.time.LocalDate;

public class Pedido {
    private int id;
    private int usuarioId;
    private String descripcion;
    private LocalDate fecha;

    public Pedido(int id, int usuarioId, String descripcion, LocalDate fecha) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

