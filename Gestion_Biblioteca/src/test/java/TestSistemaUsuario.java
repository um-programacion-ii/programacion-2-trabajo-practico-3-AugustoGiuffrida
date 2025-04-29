import enums.EstadoLibro;
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
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registrarPrestamoCorrecto(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario("usuario");

        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);
        doNothing().when(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");

        sistemaUsuarios.agregarUsuario(usuario);
        sistemaUsuarios.solicitarPrestamo("usuario","123-4-56-789101-1");

        assertEquals(1,usuario.getPrestamos().size());
        verify(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");

    }

    @Test
    public void registrarPrestamoSinUsuario(){
        Libro libro = new Libro("123-4-56-789101-1", "Refactoring", "Pedro", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario("usuario");

        when(catalogo.buscarPorISBN("123-4-56-789101-1")).thenReturn(libro);
        doNothing().when(sistemaPrestamos).registrarPrestamo("123-4-56-789101-1");


        assertThrows(UsuarioExepcion.class, () -> sistemaUsuarios.solicitarPrestamo("usuario","123-4-56-789101-1"));
        verifyNoInteractions(sistemaPrestamos);
    }




}
