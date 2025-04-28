import enums.EstadoLibro;
import enums.EstadoPrestamo;
import exepciones.LibroExepcion;
import exepciones.PrestamoExepcion;
import modelos.Catalogo;
import modelos.Libro;
import modelos.Prestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sistemas.SistemaPrestamos;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestSistemaPrestamo {
    @Mock
    Catalogo catalogo;

    @InjectMocks
    SistemaPrestamos sistemaPrestamos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestRegistroPrestamoCorrecto(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.DISPONIBLE);
        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);

        sistemaPrestamos.registrarPrestamo(libro.getIsbn());
        Prestamo prestamo = sistemaPrestamos.buscarPrestamoPorIsbn(libro.getIsbn());

        assertNotNull(prestamo);
        assertEquals(EstadoLibro.PRESTADO, libro.getEstado());
        assertEquals(EstadoPrestamo.ACTIVO,prestamo.getEstado());
        verify(catalogo).buscarPorISBN("123-4-56-789101-1");
    }

    @Test
    public void TestRegistrarPrestamoLibroInexistente() {
        when(catalogo.buscarPorISBN("999-9-99-999999-9")).thenReturn(null);

        assertThrows(LibroExepcion.class, () -> sistemaPrestamos.registrarPrestamo("999-9-99-999999-9"));

        verify(catalogo).buscarPorISBN("999-9-99-999999-9");
    }

    @Test
    public void TestRegistroPrestamoLibroNoDisponible(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.PRESTADO);
        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);

        assertThrows(PrestamoExepcion.class, ()->sistemaPrestamos.registrarPrestamo(libro.getIsbn()));
        assertEquals(EstadoLibro.PRESTADO, libro.getEstado());
        verify(catalogo).buscarPorISBN("123-4-56-789101-1");
    }

    @Test
    public void TestDevolverLibroPrestado(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.DISPONIBLE);
        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);

        sistemaPrestamos.registrarPrestamo(libro.getIsbn());
        sistemaPrestamos.devolverLibro(libro.getIsbn());
        Prestamo prestamo = sistemaPrestamos.buscarPrestamoPorIsbn(libro.getIsbn());

        assertEquals(EstadoLibro.DISPONIBLE, libro.getEstado());
        assertEquals(EstadoPrestamo.DEVUELTO,prestamo.getEstado());
        verify(catalogo,times(2)).buscarPorISBN("123-4-56-789101-1");
    }

    @Test
    public void TestDevolverLibroNoPrestado(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.PRESTADO);
        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);

        assertThrows(PrestamoExepcion.class,()-> sistemaPrestamos.devolverLibro(libro.getIsbn()));

        verify(catalogo).buscarPorISBN("123-4-56-789101-1");
    }

    @Test
    public void TestBuscarPrestamoNoExistente(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.PRESTADO);
        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);
        assertThrows(PrestamoExepcion.class, ()->sistemaPrestamos.buscarPrestamoPorIsbn(libro.getIsbn()));
    }



}
