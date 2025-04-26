package modelos;

import enums.EstadoLibro;
import enums.EstadoPrestamo;

import java.util.Date;

public class Prestamo {
    private Libro libro;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private EstadoPrestamo estado;

    public Prestamo(Libro libro, Date fechaInicio, Date fechaVencimiento, EstadoPrestamo estado){
        this.libro = libro;
        setFechaInicio(fechaInicio);
        setFechaVencimiento(fechaVencimiento);
        setEstado(estado);
    }

    //getters
    public Libro getLibro() {
        return this.libro;
    }

    public Date getFechaInicio(){
        return this.fechaInicio;
    }

    public Date getFechaVencimiento(){
        return this.fechaVencimiento;
    }

    public EstadoPrestamo getEstado(){
        return this.estado;
    }

    //setters
    public void setFechaInicio(Date fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public void setFechaVencimiento(Date fechaVencimiento){
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setEstado(EstadoPrestamo estado){
        this.estado = estado;
    }

    @Override
    public String toString(){
        return "Libro: "+libro.getTitulo()+" | Fecha inicio: " +fechaInicio+ " | Fecha vencimiento: " +fechaVencimiento+ "| Estado: " +estado;
    }
}
