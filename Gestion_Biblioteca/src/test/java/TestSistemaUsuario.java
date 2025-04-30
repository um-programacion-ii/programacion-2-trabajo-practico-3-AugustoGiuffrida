import enums.EstadoLibro;
import exepciones.LibroExepcion;
import exepciones.PrestamoExepcion;
import exepciones.UsuarioExepcion;
import modelos.Catalogo;
import modelos.Libro;
import modelos.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sistemas.SistemaPrestamos;
import sistemas.SistemaUsuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestSistemaUsuario {
    @Mock
    Catalogo catalogo;

    @Mock
    SistemaPrestamos sistemaPrestamos;

    @InjectMocks
    SistemaUsuarios sistemaUsuarios;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestSolicitarPrestamoCorrecto() {
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario("usuario");

        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);
        doNothing().when(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");

        sistemaUsuarios.agregarUsuario(usuario);
        sistemaUsuarios.solicitarPrestamo("usuario", "123-4-56-789101-1");

        assertEquals(1, usuario.getPrestamos().size());
        verify(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");
    }

    @Test
    public void TestSolicitarPrestamoSinUsuario() {
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.DISPONIBLE);

        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);
        doNothing().when(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");

        UsuarioExepcion exepcion = assertThrows(UsuarioExepcion.class, () -> sistemaUsuarios.solicitarPrestamo("usuario", "123-4-56-789101-1"));

        assertEquals("No se encontro el usuario", exepcion.getMessage());
        verifyNoInteractions(sistemaPrestamos);
    }

    @Test
    public void TestSolicitarPrestamoSinLibro() {
        Usuario usuario = new Usuario("usuario");
        sistemaUsuarios.agregarUsuario(usuario);

        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenThrow(new LibroExepcion("No se encontro libro con este ISBN"));

        UsuarioExepcion exepcion = assertThrows(UsuarioExepcion.class, () -> sistemaUsuarios.solicitarPrestamo("usuario", "123-4-56-789101-1"));

        assertEquals("Error al registrar prestamo: No se encontro libro con este ISBN", exepcion.getMessage());
        verifyNoInteractions(sistemaPrestamos);
    }

    @Test
    public void TestSolicitarPrestamoLibroNoDisponible() {
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.PRESTADO);
        Usuario usuario = new Usuario("usuario");

        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);
        doThrow(new PrestamoExepcion("El libro ya se encuentra prestado")).when(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");

        sistemaUsuarios.agregarUsuario(usuario);

        UsuarioExepcion exepcion = assertThrows(UsuarioExepcion.class, () -> sistemaUsuarios.solicitarPrestamo("usuario", "123-4-56-789101-1"));

        assertEquals("Error al registrar prestamo: El libro ya se encuentra prestado", exepcion.getMessage());
        verify(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");
    }

    @Test
    public void TestAgregarUsuario(){
        Usuario usuario = new Usuario("usuario");

        sistemaUsuarios.agregarUsuario(usuario);
        Usuario usuarioBuscado = sistemaUsuarios.buscarUsuario("usuario");

        assertEquals(usuario, usuarioBuscado);

    }

    @Test
    public void TestBuscarUsuarioInexistente(){
        UsuarioExepcion exepcion = assertThrows(UsuarioExepcion.class, () -> sistemaUsuarios.buscarUsuario("usuario"));
        assertEquals("No se encontro el usuario", exepcion.getMessage());
    }


    @Test
    public void TesteliminarUsuario() {
        Usuario usuario = new Usuario("usuario");

        sistemaUsuarios.agregarUsuario(usuario);
        sistemaUsuarios.eliminarUsuario("usuario");

        UsuarioExepcion excepcion = assertThrows(
                UsuarioExepcion.class,
                () -> sistemaUsuarios.buscarUsuario("usuario")
        );

        assertEquals("No se encontro el usuario", excepcion.getMessage());
    }

}


