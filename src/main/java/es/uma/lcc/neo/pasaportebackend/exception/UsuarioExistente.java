package es.uma.lcc.neo.pasaportebackend.exception;

import es.uma.lcc.neo.pasaportebackend.entity.Usuario;

public class UsuarioExistente extends RuntimeException {
    public UsuarioExistente(Usuario usuario) {
        super("El usuario con email " + usuario.getEmail() + " ya existe");
    }

    public UsuarioExistente() {
        super();
    };
}
