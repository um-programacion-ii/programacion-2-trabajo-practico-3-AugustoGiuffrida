package modelos;
import enums.EstadoLibro;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;

    public Libro(String isbn, String titulo, String autor, EstadoLibro estado){
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
        setEstado(estado);
    }

    //getters
    public String getIsbn(){
        return this.isbn;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getAutor(){
        return  this.autor;
    }

    public EstadoLibro getEstado(){
        return this.estado;
    }

    //setters
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public void setEstado(EstadoLibro estado){
        this.estado = estado;
    }

    @Override
    public String toString(){
        return "ISBN: " +isbn+ " | Titulo: " +titulo+ "| Autor: " +autor;
    }

}
