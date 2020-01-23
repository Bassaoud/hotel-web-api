package dev.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Hotel;
import dev.hotel.entite.Reservation;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.HotelRepository;
import dev.hotel.repository.ReservationRepository;

@Component

public class Startup {

	private static final Logger LOG = LoggerFactory.getLogger(Startup.class);

	private ClientRepository clientRepository;
	private HotelRepository hotelRepository;
	private ChambreRepository chambreRepository;
	private ReservationRepository reservationRepository;

	/**
	 * @param clientRepository
	 * @param hotelRepository
	 * @param chambreRepository
	 * @param reservationRepository
	 */
	public Startup(ClientRepository clientRepository, HotelRepository hotelRepository,
			ChambreRepository chambreRepository, ReservationRepository reservationRepository) {
		super();
		this.clientRepository = clientRepository;
		this.hotelRepository = hotelRepository;
		this.chambreRepository = chambreRepository;
		this.reservationRepository = reservationRepository;
	}

	@EventListener(ContextRefreshedEvent.class)

	public void init() {

		LOG.info("Démarrage de l'application");

		if (this.clientRepository.count() == 0) {

			List<Client> clients = new ArrayList<Client>();
			clients.add(new Client("Pierre", "Jean"));
			clients.add(new Client("Pierre", "Dimitri"));
			clients.add(new Client("Albert", "Dimitri"));
			clients.add(new Client("Durand", "Martin"));

			this.clientRepository.saveAll(clients);

		}
		if (this.hotelRepository.count() == 0) {
			List<Hotel> hotels = new ArrayList<>();
			hotels.add(new Hotel("Ritz", 4));
			hotels.add(new Hotel("Formule1", 1));
			this.hotelRepository.saveAll(hotels);
		}
		if (this.chambreRepository.count() == 0) {
			Hotel h1 = this.hotelRepository.findByNom("Ritz")
					.orElseThrow(() -> new EntityNotFoundException("hotel non trouvé"));
			Hotel h2 = this.hotelRepository.findByNom("Formule1")
					.orElseThrow(() -> new EntityNotFoundException("hotel non trouvé"));
			List<Chambre> chambres = new ArrayList<>();
			chambres.add(new Chambre("1", 20.0F, h1));
			chambres.add(new Chambre("2", 20.0F, h1));
			chambres.add(new Chambre("3A", 15.0F, h2));
			chambres.add(new Chambre("5A", 13.0F, h2));
			this.chambreRepository.saveAll(chambres);

		}

		if (this.reservationRepository.count() == 0) {
			Client c1 = this.clientRepository.findByPrenoms("jean")
					.orElseThrow(() -> new EntityNotFoundException("client non trouvé"));
			List<Chambre> chambres = this.chambreRepository.findAll();
			List<Reservation> reservations = new ArrayList<>();
			reservations.add(new Reservation(LocalDate.of(2019, 12, 15), LocalDate.of(2019, 12, 17), c1, chambres));
			this.reservationRepository.saveAll(reservations);
		}

	}
}
