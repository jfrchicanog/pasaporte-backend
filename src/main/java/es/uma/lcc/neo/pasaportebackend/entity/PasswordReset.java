package es.uma.lcc.neo.pasaportebackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@Builder
public class PasswordReset {
    @Id
    @GeneratedValue
    private String token;
    private Timestamp tokenCreation;
    @ManyToOne
    private Usuario usuario;
}
