package dev.hotel.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dev.hotel.entite.Chambre;

import dev.hotel.repository.ChambreRepository;

@RestController
public class ChambreController {
	private static final Logger LOG = LoggerFactory.getLogger(ChambreController.class);
	
	private ChambreRepository chambreRepository;
	
	public ChambreController(ChambreRepository chambreRepository) {
		this.chambreRepository = chambreRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "chambres")
	public List<Chambre> listChambre(){
		LOG.info("Liste de chambres");
		
	return chambreRepository.findAll();
	}
}
