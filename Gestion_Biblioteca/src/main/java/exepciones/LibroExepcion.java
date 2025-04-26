package exepciones;

import modelos.Libro;

public class LibroExepcion extends  RuntimeException {
    public LibroExepcion(String msg){
        super(msg);
    }
}
