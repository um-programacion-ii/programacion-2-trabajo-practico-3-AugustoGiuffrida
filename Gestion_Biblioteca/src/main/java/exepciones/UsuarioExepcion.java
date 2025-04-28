package exepciones;

public class UsuarioExepcion extends RuntimeException{
    public UsuarioExepcion (String msg){
        super(msg);
    }
}
