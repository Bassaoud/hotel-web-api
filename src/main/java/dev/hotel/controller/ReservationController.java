package dev.hotel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.entite.ReservationRequete;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.ReservationRepository;

@RestController
public class ReservationController {

	public static final Logger LOG = LoggerFactory.getLogger(ReservationController.class);

	private ReservationRepository reservationRepository;
	private ClientRepository clientRepository;
	private ChambreRepository chambreRepository;

	public ReservationController(ReservationRepository reservationRepository, ClientRepository clientRepository,
			ChambreRepository chambreRepository) {
		super();
		this.reservationRepository = reservationRepository;
		this.clientRepository = clientRepository;
		this.chambreRepository = chambreRepository;
	}

	/**
	 * { "dateDebut" : "2019-10-01", "dateFin" : "2019-10-10", "clientId" :
	 * "UUID_CLIENT", "chambres" : [ "UUID_CHAMBRE_X", "UUID_CHAMBRE_Y",
	 * "UUID_CHAMBRE_Z" ] }
	 */

	@RequestMapping(method = RequestMethod.GET, path = "reservations")
	public List<Reservation> listeReservations() {
		LOG.info("Voici les reservations");
		return this.reservationRepository.findAll();
	}
	

	@RequestMapping(method = RequestMethod.POST, path = "reservations")
	public ResponseEntity<String> creerReservation(@RequestBody ReservationRequete reservation) {
		Reservation resa = new Reservation();
		ResponseEntity<String> reponse;
		if (clientRepository.findById(reservation.getClientId()).isPresent()) {
			Client client = new Client();
			client = (clientRepository.findById(reservation.getClientId()).get());
			reponse = ResponseEntity.status(HttpStatus.CREATED).body("Client trouvé");
		} else {
			reponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("LOL");
		}

		List<Chambre> chambres = new ArrayList<>();
		for (UUID c : reservation.getChambres()) {
			// ResponseEntity<String> repChambre = null;
			if (chambreRepository.findById(c).isPresent()) {
				Chambre chambre = new Chambre();
				chambre = this.chambreRepository.findById(c).get();
				chambres.add(chambre);
				resa.setChambres(chambres);
				resa.setDateDebut(reservation.getDateDebut());
				resa.setDateFin(reservation.getDateFin());
			} else {
				throw new EntityNotFoundException("la chambre " + c + "n’existe pas");
			}
		}
		this.reservationRepository.save(resa);
		return reponse;

	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<String> resaException(EntityNotFoundException exc) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exc.getMessage());
	}

}
