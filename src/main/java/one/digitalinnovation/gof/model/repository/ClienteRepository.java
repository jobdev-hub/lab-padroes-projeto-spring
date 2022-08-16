package one.digitalinnovation.gof.model.repository;

import one.digitalinnovation.gof.model.repository.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}