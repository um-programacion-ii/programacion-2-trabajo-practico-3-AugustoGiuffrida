package modelos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    String nombre;
    List<Prestamo> prestamos;

    public Usuario(String nombre){
        this.nombre = nombre;
        this.prestamos = new ArrayList<>();;
    }

    //getters
    public String getNombre(){
        return this.nombre;
    }

    public List<Prestamo> getPrestamos(){
        return this.prestamos;
    }

    //setters
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void registrarPrestamos(Prestamo prestamo){
        prestamos.add(prestamo);
    }
}
