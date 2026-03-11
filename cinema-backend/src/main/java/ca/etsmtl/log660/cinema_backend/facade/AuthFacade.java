package ca.etsmtl.log660.cinema_backend.facade;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import ca.etsmtl.log660.cinema_backend.model.Client;
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

            query.setParameter("courriel", courriel == null ? "" : courriel.trim());
            return query.uniqueResult();
        }
    }

    public void saveUtilisateurEtClient(Utilisateur utilisateur, String typeForfait) {
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            session.persist(utilisateur);
            session.flush();

            Client client = new Client();
            client.setTypeForfait(typeForfait);
            client.setUtilisateur(utilisateur);

            session.persist(client);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}