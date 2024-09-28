package es.uma.lcc.neo.pasaportebackend.exception;

public class SinPermiso extends RuntimeException {
    public SinPermiso() {
        super("No tiene permiso para realizar esta acción");
    }
}
