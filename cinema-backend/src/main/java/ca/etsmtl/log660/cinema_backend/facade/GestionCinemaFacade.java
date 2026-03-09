package ca.etsmtl.log660.cinema_backend.facade;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ca.etsmtl.log660.cinema_backend.model.Client;
import ca.etsmtl.log660.cinema_backend.model.Copie;
import ca.etsmtl.log660.cinema_backend.model.Film;
import ca.etsmtl.log660.cinema_backend.model.Location;
import ca.etsmtl.log660.cinema_backend.util.HibernateUtil;

@Repository
public class GestionCinemaFacade {

    private final SessionFactory sessionFactory;

    public GestionCinemaFacade() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    // ------------------------
    // CLIENT
    // ------------------------

    public Client findClientByEmail(Session session, String email) {
        Query<Client> query = session.createQuery(
            "FROM Client c WHERE c.utilisateur.courriel = :email",
            Client.class
        );
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    // ------------------------
    // FILM
    // ------------------------

    public Film findFilmById(Session session, long filmId) {
        return session.get(Film.class, filmId);
    }

    // ------------------------
    // LOCATION
    // ------------------------

    public Long countActiveLocation(Session session, long clientId, long filmId) {
        Query<Long> query = session.createQuery("""
                SELECT COUNT(l.idLocation)
                FROM Location l
                WHERE l.client.idClient = :clientId
                AND l.copie.film.idFilm = :filmId
                AND l.dateRetourEffective IS NULL
                """, Long.class);

        query.setParameter("clientId", clientId);
        query.setParameter("filmId", filmId);

        return query.uniqueResult();
    }

    public void saveLocation(Session session, Location location) {
        session.persist(location);
    }

    // ------------------------
    // COPIE
    // ------------------------

    public Copie findAvailableCopy(Session session, long filmId) {
        Query<Copie> query = session.createQuery("""
                FROM Copie c
                WHERE c.film.idFilm = :filmId
                AND c.disponible = 'O'
                """, Copie.class);

        query.setParameter("filmId", filmId);
        query.setMaxResults(1);

        return query.uniqueResult();
    }

    public void updateCopie(Session session, Copie copie) {
        session.merge(copie);
    }
}