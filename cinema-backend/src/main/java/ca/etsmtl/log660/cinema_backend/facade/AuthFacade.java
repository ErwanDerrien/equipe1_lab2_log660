package ca.etsmtl.log660.cinema_backend.facade;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import ca.etsmtl.log660.cinema_backend.model.Utilisateur;
import ca.etsmtl.log660.cinema_backend.util.HibernateUtil;

@Component
public class AuthFacade {

    private final SessionFactory sessionFactory;

    public AuthFacade() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Utilisateur findByCourriel(String courriel) {
        try (Session session = sessionFactory.openSession()) {
            Query<Utilisateur> query = session.createQuery(
                    "FROM Utilisateur u WHERE u.courriel = :courriel",
                    Utilisateur.class);

            query.setParameter("courriel", courriel == null ? "" : courriel);
            return query.uniqueResult();
        }
    }

    public void saveUtilisateur(Utilisateur utilisateur) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(utilisateur);
            tx.commit();
        }
    }
}