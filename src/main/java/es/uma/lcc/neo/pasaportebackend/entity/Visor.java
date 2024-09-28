package es.uma.lcc.neo.pasaportebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public abstract class Visor {

    protected Visor(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;
}
