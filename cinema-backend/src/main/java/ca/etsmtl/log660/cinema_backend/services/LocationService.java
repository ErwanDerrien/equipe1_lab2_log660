package ca.etsmtl.log660.cinema_backend.services;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import ca.etsmtl.log660.cinema_backend.facade.GestionCinemaFacade;
import ca.etsmtl.log660.cinema_backend.model.Client;
import ca.etsmtl.log660.cinema_backend.model.Copie;
import ca.etsmtl.log660.cinema_backend.model.Film;
import ca.etsmtl.log660.cinema_backend.model.Location;
import ca.etsmtl.log660.cinema_backend.util.ErrorEnum;

@Service
public class LocationService {

    private final GestionCinemaFacade facade;

    public LocationService(GestionCinemaFacade facade) {
        this.facade = facade;
    }

    public Optional<ErrorEnum> location(long filmId, String email) {

        try (Session session = facade.getSession()) {

            Transaction tx = session.beginTransaction();

            Client client = facade.findClientByEmail(session, email);

            if (client == null) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Film film = facade.findFilmById(session, filmId);

            if (film == null) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Long already = facade.countActiveLocation(
                    session,
                    client.getIdClient(),
                    filmId);

            if (already != null && already > 0) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_USER_ALREADY_HAS_COPY);
            }

            Copie copie = facade.findAvailableCopy(session, filmId);

            if (copie == null) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_NOT_ENOUGH_COPIES);
            }

            Location location = new Location();
            location.setClient(client);
            location.setCopie(copie);

            facade.saveLocation(session, location);

            copie.setDisponible("N");
            facade.updateCopie(session, copie);

            tx.commit();

            return Optional.empty();
        }
    }
}