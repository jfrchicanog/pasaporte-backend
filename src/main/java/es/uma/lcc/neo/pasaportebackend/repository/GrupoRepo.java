package es.uma.lcc.neo.pasaportebackend.repository;

import es.uma.lcc.neo.pasaportebackend.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepo extends JpaRepository<Grupo, Long> {
}
