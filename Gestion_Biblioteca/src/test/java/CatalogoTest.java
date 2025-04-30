import enums.EstadoLibro;
import exepciones.LibroExepcion;
import modelos.Catalogo;
import modelos.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogoTest {
    Catalogo catalogo;
    Libro libro1;
    Libro libro2;

    @BeforeEach void setUp() {
        catalogo = new Catalogo();
        libro1 = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin", EstadoLibro.DISPONIBLE);
        libro2 = new Libro("123-4-56-789101-1", "Clean Architecture", "John Doe", EstadoLibro.PRESTADO);
        catalogo.agregarLibro(libro1);
    }

    @Test
    public void testAgregarLibroCorrectamente() {
        catalogo.agregarLibro(libro2);
        assertEquals(libro2,catalogo.buscarPorISBN(libro2.getIsbn()));
    }

    @Test
    public void testAgregarLibroFallo(){
        assertThrows(LibroExepcion.class,()-> catalogo.agregarLibro(libro1));
    }

    @Test
    public void testCantidadLibros(){
        assertEquals(1,catalogo.obtenerCantidad());
    }

    @Test
    public void testElminarLibroCorrectamente(){
        catalogo.eliminarLibro(libro1);
        assertThrows(LibroExepcion.class,()-> catalogo.buscarPorISBN(libro1.getIsbn()));
        assertEquals(0,catalogo.obtenerCantidad());
    }

    @Test
    public void testBuscarPorIsbnCorrecto(){
        Libro libroBuscado = catalogo.buscarPorISBN(libro1.getIsbn());
        assertEquals(libro1, libroBuscado);
    }

    @Test
    public void testBuscarPorIsbnFallido(){
        assertThrows(LibroExepcion.class,()-> catalogo.buscarPorISBN(libro2.getIsbn()));
    }

    @Test
    public void testBuscarPorTitulo(){
        Libro libroBuscado = catalogo.buscarPorTitulo(libro1.getTitulo());
        assertEquals(libro1, libroBuscado);
    }

    @Test
    public void testBuscarPorTituloFallido(){
        assertThrows(LibroExepcion.class,()-> catalogo.buscarPorTitulo(libro2.getTitulo()));
    }

    @Test
    public void testBuscarPorAutor(){
        Libro libroBuscado = catalogo.buscarPorAutor(libro1.getAutor());
        assertEquals(libro1, libroBuscado);
    }

    @Test
    public void testBuscarPorAutorFallido(){
        assertThrows(LibroExepcion.class,()-> catalogo.buscarPorAutor(libro2.getAutor()));
    }

    @Test
    public void testObtenerDisponibles(){
        catalogo.agregarLibro(libro2);
       List<Libro> CantDisponibles = catalogo.obtenerDisponibles();
       assertEquals(1,CantDisponibles.size());
       assertTrue(CantDisponibles.contains(libro1));
    }

}
