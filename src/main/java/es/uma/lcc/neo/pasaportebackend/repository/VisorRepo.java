package es.uma.lcc.neo.pasaportebackend.repository;

import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import es.uma.lcc.neo.pasaportebackend.entity.Visor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisorRepo<T extends Visor> extends JpaRepository<T, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> usuarioByEmail(String email);
}
