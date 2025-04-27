package sistemas;

import enums.EstadoLibro;
import enums.EstadoPrestamo;
import exepciones.LibroExepcion;
import exepciones.PrestamoExepcion;
import modelos.Libro;
import modelos.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class SistemaPrestamos {
    private List<Prestamo> prestamos = new ArrayList<>();

    public SistemaPrestamos(){}

    public void registrarPrestamo(Libro libro){
        if(libro == null){
            throw new LibroExepcion("El libro no existe");
        } else if (libro.getEstado() == EstadoLibro.PRESTADO){
            throw new PrestamoExepcion("El libro ya se encuentra prestado");
        }
        libro.setEstado(EstadoLibro.PRESTADO);
        Prestamo prestamo = new Prestamo(libro);
        prestamos.add(prestamo);
    }

    public void devolverLibro(Libro libro){
        Prestamo prestamo = prestamos.stream()
                .filter(p ->p.getLibro().getIsbn().equals(libro.getIsbn()))
                .findFirst().orElseThrow(()->new PrestamoExepcion("No se encontró un préstamo para este libro"));
        if(!libro.getEstado().equals(EstadoLibro.PRESTADO)) {
            throw new LibroExepcion("No se puede devolver el libro, actualmente esta disponible");
        } else if (!prestamo.getEstado().equals(EstadoPrestamo.ACTIVO)) {
            throw new PrestamoExepcion("El prestamo no esta activo");
        }
        libro.setEstado(EstadoLibro.DISPONIBLE);
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
    }

    public Prestamo buscarPorIsbn(String isbn){
        return prestamos.stream()
                .filter(p -> p.getLibro().getIsbn().equals(isbn))
                .findFirst().orElseThrow(()->new PrestamoExepcion("No se encontró un préstamo para este libro"));
    }

    public List<Prestamo> getPrestamos(){
        return new ArrayList<>(prestamos);
    }

}
