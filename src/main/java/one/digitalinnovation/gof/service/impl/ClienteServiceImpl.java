package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import one.digitalinnovation.gof.model.repository.PlanetaDaSorteRepository;
import one.digitalinnovation.gof.model.repository.entity.PlanetaDaSorte;
import one.digitalinnovation.gof.service.SwapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.repository.entity.Cliente;
import one.digitalinnovation.gof.model.repository.ClienteRepository;
import one.digitalinnovation.gof.model.repository.entity.Endereco;
import one.digitalinnovation.gof.model.repository.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PlanetaDaSorteRepository planetaDaSorteRepository;
    private final ViaCepService viaCepService;
    private final SwapiService swapiService;


    public ClienteServiceImpl(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, PlanetaDaSorteRepository planetaDaSorteRepository, ViaCepService viaCepService, SwapiService swapiService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.planetaDaSorteRepository = planetaDaSorteRepository;
        this.viaCepService = viaCepService;
        this.swapiService = swapiService;
    }

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Cliente> buscarTodos() {
		// Buscar todos os Clientes.
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar Cliente por ID.
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

    @Override
    public void inserir(Cliente cliente) {
        salvarCliente(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // Buscar Cliente por ID, caso exista:
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarCliente(cliente);
        }
    }

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);
	}

    private void salvarCliente(Cliente cliente) {
        cliente.setEndereco(atualizarEndereco(cliente));
        cliente.setPlanetaDaSorte(sorteiaPlaneta(cliente));
        clienteRepository.save(cliente);
    }

    private Endereco atualizarEndereco(Cliente cliente) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        return endereco;
    }

    private PlanetaDaSorte sorteiaPlaneta(Cliente cliente) {
        // Consultar o Swapi para identificar quantidade de planetas e sortear um.
        Integer min = 1;
        Integer max = swapiService.getPlanets().getCount();
        int sorteio = min + (int) (Math.random() * ((max - min) + 1));
        // Consultar o Swapi para obter o planeta sorteado.
        PlanetaDaSorte planeta = swapiService.getPlanetById(sorteio);
        // Verificar se planeta já existe no BD.
        Optional<PlanetaDaSorte> planetaBd = planetaDaSorteRepository.findById(planeta.getName());
        if (!planetaBd.isPresent()) {
            // Caso não exista, persistir.
            planetaDaSorteRepository.save(planeta);
        }
        // Caso exista, retornar o planeta do BD.
        return planeta;
    }

}
