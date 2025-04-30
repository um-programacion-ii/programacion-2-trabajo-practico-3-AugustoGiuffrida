package sistemas;

import exepciones.LibroExepcion;
import exepciones.PrestamoExepcion;
import exepciones.UsuarioExepcion;
import modelos.Catalogo;
import modelos.Libro;
import modelos.Prestamo;
import modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SistemaUsuarios {
    private List<Usuario> usuarios = new ArrayList<>();
    private Catalogo catalogo;
    private SistemaPrestamos sistemaPrestamos;

    public SistemaUsuarios(Catalogo catalogo, SistemaPrestamos sistemaPrestamos){
        this.catalogo = catalogo;
        this.sistemaPrestamos = sistemaPrestamos;
    }

    public void agregarUsuario(Usuario usuario){
        if(usuario == null){
            throw new UsuarioExepcion("Usuario esta vacio");
        } else if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new UsuarioExepcion("El nombre del usuario no puede ser nulo o vacío");
        } else if (usuarios.stream().anyMatch(u -> u.getNombre().equals(usuario.getNombre()))) {
            throw new UsuarioExepcion("El usuario ya existe");
        }
        usuarios.add(usuario);
    }

    public void eliminarUsuario(String nombre){
        Usuario usuario = buscarUsuario(nombre);
        usuarios.remove(usuario);
    }

    public Usuario buscarUsuario(String nombre){
        Usuario usuario = usuarios.stream()
                .filter(u ->u.getNombre().equals(nombre))
                .findFirst().orElseThrow(()-> new UsuarioExepcion("No se encontro el usuario"));
        return  usuario;
    }

    public void solicitarPrestamo(String nombre, String isbn){
        try {
            Usuario usuario = buscarUsuario(nombre);
            Libro libro = catalogo.buscarPorISBN(isbn);
            sistemaPrestamos.registrarPrestamo(isbn);
            Prestamo prestamo = sistemaPrestamos.buscarPrestamoPorIsbn(isbn);
            usuario.agregarPrestamos(prestamo);
        } catch (LibroExepcion | PrestamoExepcion e){
            throw new UsuarioExepcion("Error al registrar prestamo: "+e.getMessage());
        }
    }



}
