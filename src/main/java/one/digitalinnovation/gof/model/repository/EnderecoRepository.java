package one.digitalinnovation.gof.model.repository;

import one.digitalinnovation.gof.model.repository.entity.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, String> {

}