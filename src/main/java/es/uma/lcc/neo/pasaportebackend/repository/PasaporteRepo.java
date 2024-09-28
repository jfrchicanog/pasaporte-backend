package es.uma.lcc.neo.pasaportebackend.repository;

import es.uma.lcc.neo.pasaportebackend.entity.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasaporteRepo extends JpaRepository<Pasaporte, Long> {
}
