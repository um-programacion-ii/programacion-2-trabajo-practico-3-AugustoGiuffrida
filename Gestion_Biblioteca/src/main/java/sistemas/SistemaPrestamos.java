package sistemas;

import enums.EstadoLibro;
import enums.EstadoPrestamo;
import exepciones.LibroExepcion;
import exepciones.PrestamoExepcion;
import modelos.Catalogo;
import modelos.Libro;
import modelos.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class SistemaPrestamos {
        private Catalogo catalogo;
        private List<Prestamo> prestamos = new ArrayList<>();

        public SistemaPrestamos(Catalogo catalogo){
            this.catalogo = catalogo;
        }

        public void registrarPrestamo(String isbn){
            Libro libro = catalogo.buscarPorISBN(isbn);
            if(libro == null){
                throw new LibroExepcion("El libro no existe");
            } else if (libro.getEstado() == EstadoLibro.PRESTADO){
                throw new PrestamoExepcion("El libro ya se encuentra prestado");
            }
            libro.setEstado(EstadoLibro.PRESTADO);
            Prestamo prestamo = new Prestamo(libro);
            prestamos.add(prestamo);

        }

        public void devolverLibro(String isbn){
            Libro libro = catalogo.buscarPorISBN(isbn);
            Prestamo prestamo = prestamos.stream()
                    .filter(p -> p.getLibro().getIsbn().equals(libro.getIsbn()))
                    .findFirst()
                    .orElseThrow(() -> new PrestamoExepcion("No se encontró un préstamo para este libro"));

            libro.setEstado(EstadoLibro.DISPONIBLE);
            prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        }


        public Prestamo buscarPrestamoPorIsbn(String isbn){
            return prestamos.stream()
                    .filter(p -> p.getLibro().getIsbn().equals(isbn))
                    .findFirst().orElseThrow(()->new PrestamoExepcion("No se encontró un préstamo para este libro"));
        }

        public List<Prestamo> getPrestamos(){
            return new ArrayList<>(prestamos);
        }
}
