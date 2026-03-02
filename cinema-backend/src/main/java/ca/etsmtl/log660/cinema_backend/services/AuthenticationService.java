package ca.etsmtl.log660.cinema_backend.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ca.etsmtl.log660.cinema_backend.dtos.RegisterDTO;
import ca.etsmtl.log660.cinema_backend.model.Utilisateur;
import ca.etsmtl.log660.cinema_backend.util.HibernateUtil;

import java.sql.Date;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    private final SessionFactory sessionFactory;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.passwordEncoder = passwordEncoder;
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

    public boolean register(RegisterDTO dto) {

        if (findByCourriel(dto.courriel()) != null) {
            throw new RuntimeException("Email already registered");
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setNom(dto.nom());
        utilisateur.setPrenom(dto.prenom());
        utilisateur.setCourriel(dto.courriel());
        utilisateur.setTelephone(dto.telephone());
        utilisateur.setAdresseCivique(dto.adresseCivique());
        utilisateur.setRue(dto.rue());
        utilisateur.setVille(dto.ville());
        utilisateur.setProvince(dto.province());
        utilisateur.setCodePostal(dto.codePostal());
        utilisateur.setDateNaissance(Date.valueOf(dto.dateNaissance()));
        utilisateur.setMotDePasse(passwordEncoder.encode(dto.motDePasse()));

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(utilisateur);
            tx.commit();
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Utilisateur utilisateur = findByCourriel(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                utilisateur.getCourriel(),
                utilisateur.getMotDePasse(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}