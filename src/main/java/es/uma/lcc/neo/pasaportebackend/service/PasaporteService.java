package es.uma.lcc.neo.pasaportebackend.service;

import es.uma.lcc.neo.pasaportebackend.entity.Pasaporte;
import es.uma.lcc.neo.pasaportebackend.entity.Seccion;
import es.uma.lcc.neo.pasaportebackend.exception.PasaporteInexistente;
import es.uma.lcc.neo.pasaportebackend.repository.PasaporteRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class PasaporteService {
    private PasaporteRepo pasaporteRepo;
    private Logger logger = Logger.getLogger(PasaporteService.class.getName());

    public PasaporteService(PasaporteRepo pasaporteRepo) {
        this.pasaporteRepo = pasaporteRepo;
    }

    public Optional<Pasaporte> obtenerPasaporte(Long id) {
        return pasaporteRepo.findById(id);
    }

    public List<Pasaporte> obtenerPasaportes() {
        return pasaporteRepo.findAll();
    }

    public Pasaporte crearPasaporte(Pasaporte pasaporte) {
        pasaporte.setId(null);
        if (pasaporte.getSecciones()!=null) {
            pasaporte.getSecciones().forEach(s -> s.setId(null));
        }
        return pasaporteRepo.save(pasaporte);
    }

    public Pasaporte modificarPasaporte(Pasaporte pasaporte) {
        if (pasaporteRepo.existsById(pasaporte.getId())) {
            return pasaporteRepo.save(pasaporte);
        } else {
            throw new PasaporteInexistente();
        }
    }

    public void eliminarPasaporte(Long id) {
        pasaporteRepo.deleteById(id);
    }

    public Seccion aniadirSeccionAPasaporte(Long idPasaporte, Seccion seccion) {
        logger.info("Añadiendo sección a pasaporte");
        seccion.setId(null);
        Optional<Pasaporte> pasaporte = pasaporteRepo.findById(idPasaporte);
        pasaporte.map(p -> p.getSecciones().add(seccion));
        logger.info("Terminado");
        return seccion;
    }
}
