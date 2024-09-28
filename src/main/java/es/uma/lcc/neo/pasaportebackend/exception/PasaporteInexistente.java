package es.uma.lcc.neo.pasaportebackend.exception;

public class PasaporteInexistente extends RuntimeException {
    public PasaporteInexistente(Long id) {
        super("El pasaporte con ID " +id + " no existe");
    }

    public PasaporteInexistente() {
        super();
    };
}
