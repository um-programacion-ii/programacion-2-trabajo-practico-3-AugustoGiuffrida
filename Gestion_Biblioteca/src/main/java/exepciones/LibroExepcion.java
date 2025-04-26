package exepciones;

import modelos.Libro;

public class LibroExepcion extends  Exception {
    public LibroExepcion(String msg){
        super(msg);
    }
}
