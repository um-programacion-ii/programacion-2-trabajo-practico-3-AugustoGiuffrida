import enums.EstadoLibro;
import modelos.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibroTest {
    Libro libro;

    @BeforeEach
    public void preparacion(){
        this.libro = new Libro("978-3-16-148410-0", "Clean Code", "Robert C. Martin", EstadoLibro.DISPONIBLE);
    }

    @Test
    public void testCrearLibro(){
        assertEquals("978-3-16-148410-0", libro.getIsbn());
        assertEquals("Clean Code",libro.getTitulo());
        assertEquals("Robert C. Martin", libro.getAutor());
        assertEquals(EstadoLibro.DISPONIBLE, libro.getEstado());
    }

    @Test
    public void testSetters(){
        libro.setIsbn("123-4-56-789101-1");
        libro.setTitulo("Refactoring");
        libro.setAutor("Pedro");
        libro.setEstado(EstadoLibro.PRESTADO);

        assertEquals("123-4-56-789101-1", libro.getIsbn());
        assertEquals("Refactoring", libro.getTitulo());
        assertEquals("Pedro", libro.getAutor());
        assertEquals(EstadoLibro.PRESTADO, libro.getEstado());
    }

    @Test
    public void testToString(){
        assertEquals("ISBN: 978-3-16-148410-0 | Titulo: Clean Code| Autor: Robert C. Martin", libro.toString());
    }
}
