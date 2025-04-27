package modelos;

import enums.EstadoLibro;
import enums.EstadoPrestamo;

import java.time.LocalDate;
import java.util.Date;

public class Prestamo {
    private Libro libro;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private EstadoPrestamo estado;

    public Prestamo(Libro libro){
        this.libro = libro;
        this.fechaInicio = LocalDate.now();
        this.fechaInicio = fechaInicio.plusDays(7);
        this.estado = EstadoPrestamo.ACTIVO;
    }

    //getters
    public Libro getLibro() {
        return this.libro;
    }

    public LocalDate getFechaInicio(){
        return this.fechaInicio;
    }

    public LocalDate getFechaVencimiento(){
        return this.fechaVencimiento;
    }

    public EstadoPrestamo getEstado(){
        return this.estado;
    }

    //setters
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento){
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
