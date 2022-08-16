package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.dto.PlanetaCountDto;
import one.digitalinnovation.gof.model.repository.entity.PlanetaDaSorte;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client HTTP, criado via <b>OpenFeign</b>, para o consumo da API do
 * <b>SWAPI</b>.
 *
 * @author jobdev-hub
 * @see <a href="https://spring.io/projects/spring-cloud-openfeign">Spring Cloud OpenFeign</a>
 * @see <a href="https://swapi.dev/">SWAPI</a>
 */
@FeignClient(name = "swapi", url = "https://swapi.dev/api/")
public interface SwapiService {

    @GetMapping("planets")
    PlanetaCountDto getPlanets();

    @GetMapping("planets/{id}")
    PlanetaDaSorte getPlanetById(@PathVariable int id);

}
