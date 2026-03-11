package ca.etsmtl.log660.cinema_backend.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import ca.etsmtl.log660.cinema_backend.facade.GestionCinemaFacade;
import ca.etsmtl.log660.cinema_backend.model.Client;
import ca.etsmtl.log660.cinema_backend.model.Copie;
import ca.etsmtl.log660.cinema_backend.model.Film;
import ca.etsmtl.log660.cinema_backend.model.Forfait;
import ca.etsmtl.log660.cinema_backend.model.Location;
import ca.etsmtl.log660.cinema_backend.util.ErrorEnum;

@Service
public class LocationService {

    private final GestionCinemaFacade facade;

    public LocationService(GestionCinemaFacade facade) {
        this.facade = facade;
    }

    public Optional<ErrorEnum> location(long filmId, String email) {
        Transaction tx = null;

        try (Session session = facade.getSession()) {
            tx = session.beginTransaction();

            Client client = facade.findClientByEmail(session, email);
            if (client == null) {
                tx.rollback();
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Film film = facade.findFilmById(session, filmId);
            if (film == null) {
                tx.rollback();
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Forfait forfait = facade.findForfaitByCode(session, client.getTypeForfait());
            if (forfait == null) {
                tx.rollback();
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Long already = facade.countActiveLocation(
                    session,
                    client.getIdClient(),
                    filmId);

            if (already != null && already > 0) {
                tx.rollback();
                return Optional.of(ErrorEnum.LOCATION_ERROR_USER_ALREADY_HAS_COPY);
            }

            Long nbLocationsActives = facade.countActiveLocationsForClient(session, client.getIdClient());
            if (nbLocationsActives != null
                    && forfait.getLocationsMax() != null
                    && nbLocationsActives >= forfait.getLocationsMax()) {
                tx.rollback();
                return Optional.of(ErrorEnum.LOCATION_ERROR_FORFAIT_LIMIT);
            }

            Copie copie = facade.findAvailableCopy(session, filmId);
            if (copie == null) {
                tx.rollback();
                return Optional.of(ErrorEnum.LOCATION_ERROR_NOT_ENOUGH_COPIES);
            }

            LocalDate aujourdHui = LocalDate.now();
            int dureeMaxJours = forfait.getDureeMaxJours() == null ? 7 : forfait.getDureeMaxJours();

            Location location = new Location();
            location.setClient(client);
            location.setCopie(copie);
            location.setDateLocation(Date.valueOf(aujourdHui));
            location.setDateRetourPrevue(Date.valueOf(aujourdHui.plusDays(dureeMaxJours)));
            location.setDateRetourEffective(null);

            facade.saveLocation(session, location);

            copie.setDisponible("N");
            facade.updateCopie(session, copie);

            tx.commit();
            return Optional.empty();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
        }
    }
}