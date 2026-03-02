package ca.etsmtl.log660.cinema_backend.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import ca.etsmtl.log660.cinema_backend.model.Client;
import ca.etsmtl.log660.cinema_backend.model.Copie;
import ca.etsmtl.log660.cinema_backend.model.Film;
import ca.etsmtl.log660.cinema_backend.model.Location;
import ca.etsmtl.log660.cinema_backend.util.ErrorEnum;
import ca.etsmtl.log660.cinema_backend.util.HibernateUtil;

@Service
public class LocationService {

    private final SessionFactory sessionFactory;

    public LocationService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Optional<ErrorEnum> location(long filmId, String email) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Query<Client> clientQuery = session.createQuery(
                    "FROM Client c WHERE c.courriel = :email",
                    Client.class);
            clientQuery.setParameter("email", email);

            Client client = clientQuery.uniqueResult();

            if (client == null) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Film film = session.get(Film.class, filmId);
            if (film == null) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_INVALID_ID);
            }

            Query<Long> alreadyQuery = session.createQuery("""
                        SELECT COUNT(l.idLocation)
                        FROM Location l
                        WHERE l.client.idClient = :clientId
                        AND l.copie.film.idFilm = :filmId
                        AND l.dateRetourEffective IS NULL
                    """, Long.class);

            alreadyQuery.setParameter("clientId", client.getIdClient());
            alreadyQuery.setParameter("filmId", filmId);

            Long alreadyCount = alreadyQuery.uniqueResult();

            if (alreadyCount != null && alreadyCount > 0) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_USER_ALREADY_HAS_COPY);
            }

            Query<Copie> copieQuery = session.createQuery("""
                        FROM Copie c
                        WHERE c.film.idFilm = :filmId
                        AND c.disponible = 'O'
                    """, Copie.class);

            copieQuery.setParameter("filmId", filmId);
            copieQuery.setMaxResults(1);

            Copie copieDisponible = copieQuery.uniqueResult();

            if (copieDisponible == null) {
                return Optional.of(ErrorEnum.LOCATION_ERROR_NOT_ENOUGH_COPIES);
            }

            Location location = new Location();
            location.setClient(client);
            location.setCopie(copieDisponible);
            location.setDateLocation(Date.valueOf(LocalDate.now()));

            Date retourPrevu = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
            location.setDateRetourPrevue(retourPrevu);

            session.persist(location);

            copieDisponible.setDisponible("N");
            session.merge(copieDisponible);

            tx.commit();

            return Optional.empty();
        }
    }
}
