package modelos;

import enums.EstadoLibro;
import exepciones.LibroExepcion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Catalogo {
    private Map<String, Libro>libros = new HashMap<>();
    public Catalogo(){}

    public void agregarLibro(Libro libro) throws LibroExepcion {
        if(libro == null){
            throw new LibroExepcion("El libro no puede estar vacio");
        } else if (libros.containsKey(libro.getIsbn())){
            throw new LibroExepcion("El libro ya se encuentra dentro del catalogo");
        }
        libros.put(libro.getIsbn(),libro);
    }

    public void eliminarLibro(Libro libro) throws LibroExepcion{
        if (libro == null){
            throw new LibroExepcion("No se encontro libro con este ISBN");
        } else if (!libros.containsKey(libro.getIsbn())) {
            throw new LibroExepcion("El libro no se encuentra dentro del catalogo");
        }
        libros.remove(libro.getIsbn());
    }

    public Libro buscarPorISBN(String isbn) throws LibroExepcion {
        if(!libros.containsKey(isbn)){
            throw new LibroExepcion("No se encontro libro con este ISBN");
        }
        return libros.get(isbn);
    }

    public Libro buscarPorTitulo(String titulo) throws LibroExepcion{
        return libros.values().stream()
                .filter(libro -> libro.getTitulo().equals(titulo))
                .findFirst().orElseThrow(() -> new LibroExepcion("No se encontraron libros con este titulo"));

    }

    public Libro buscarPorAutor(String autor) throws LibroExepcion{
        return libros.values().stream()
                .filter(libro -> libro.getAutor().equals(autor))
                .findFirst().orElseThrow(() -> new LibroExepcion("No se encontraron libros con este autor"));

    }

    public List<Libro> obtenerDisponibles(){
        return libros.values().stream()
                .filter(libro -> libro.getEstado().equals(EstadoLibro.DISPONIBLE))
                .collect(Collectors.toList());
    }
}
