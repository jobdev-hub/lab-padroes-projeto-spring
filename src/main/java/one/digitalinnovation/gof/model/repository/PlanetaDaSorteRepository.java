package one.digitalinnovation.gof.model.repository;

import one.digitalinnovation.gof.model.repository.entity.PlanetaDaSorte;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetaDaSorteRepository extends CrudRepository<PlanetaDaSorte, String> {

}