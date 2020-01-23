package dev.hotel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.repository.ClientRepository;
import dev.hotel.entite.*;

@RestController
@RequestMapping(value = "clients")
public class ClientController {

	private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
	ClientRepository clientRepository;

	public ClientController(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void init() {
		LOG.info("Démarrage de l'application");
		
	
//	if(this.clientRepository.count() == 0) {
//		Client client = new Client();
//		client.setNom("BENOIT");
//		client.setPrenoms("Dimitri");
//		
//		this.clientRepository.save(client);
		
	}


	@RequestMapping(method = RequestMethod.GET, path = "/clients")
	public List<Client> retourneListeClients() {
		return this.clientRepository.findAll();

	}

	@RequestMapping(method = RequestMethod.GET, params = "nom")
	public List<Client> listClientNom(@RequestParam("nom") String nom) {

		LOG.info("Affiche la liste de clients");
		List<Client> liste = this.clientRepository.findByNom(nom);

		return liste;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/clients")
    public ResponseEntity<String> postClient(@RequestBody() Client client) {

        

        if (clientRepository.findByNomAndPrenoms(client.getNom(),client.getPrenoms()).isEmpty()) {

            LOG.info("Client saved");
            clientRepository.save(new Client(client.getNom(),client.getPrenoms()));
            return ResponseEntity.status(HttpStatus.CREATED).body("Client saved");

          

        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Un client avec les mêmes nom & prenoms existe déja");

        }

    }

}