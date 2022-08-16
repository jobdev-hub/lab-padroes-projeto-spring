package one.digitalinnovation.gof.model.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@Entity
public class PlanetaDaSorte {

    @Id
    private String name;

    private String climate;

    private String gravity;

    private String terrain;

}
