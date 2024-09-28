package es.uma.lcc.neo.pasaportebackend.repository;


import es.uma.lcc.neo.pasaportebackend.entity.PasswordReset;
import es.uma.lcc.neo.pasaportebackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordReset, String> {

    @Query("SELECT p FROM PasswordReset p WHERE p.usuario = :usuario")
    Optional<PasswordReset> findByUsuario(Usuario usuario);

    @Query("SELECT p FROM PasswordReset p WHERE p.usuario.id = :id")
    Optional<PasswordReset> findByUsuario(Long id);

}
