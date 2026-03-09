package ca.etsmtl.log660.cinema_backend.facade;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ca.etsmtl.log660.cinema_backend.dtos.ConsultationResultDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheResultDTO;
import ca.etsmtl.log660.cinema_backend.util.HibernateUtil;

@Repository
public class RechercheFacade {

    private final SessionFactory sessionFactory;

    public RechercheFacade() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    // --------------------------------
    // RECHERCHE FILMS
    // --------------------------------

    public List<RechercheResultDTO> rechercheFilms(Session session, RechercheDTO dto) {

        StringBuilder hql = new StringBuilder("""
            SELECT f.idFilm, f.titre, f.urlAffiche, p.nom
            FROM Film f
            JOIN Personne p ON f.idRealisateur = p.idPersonne
            WHERE 1=1
        """);

        if (dto.titre() != null && !dto.titre().isBlank()) {
            hql.append(" AND LOWER(f.titre) LIKE LOWER(:titre)");
        }

        if (dto.realisateur() != null && !dto.realisateur().isBlank()) {
            hql.append(" AND LOWER(r.nom) LIKE LOWER(:realisateur)");
        }

        if (dto.langue() != null && !dto.langue().isBlank()) {
            hql.append(" AND f.langueOriginale = :langue");
        }

        if (dto.dateSortieDebut() != null) {
            hql.append(" AND f.anneeSortie >= :anneeDebut");
        }

        if (dto.dateSortieFin() != null) {
            hql.append(" AND f.anneeSortie <= :anneeFin");
        }

        Query<Object[]> query = session.createQuery(hql.toString(), Object[].class);

        if (dto.titre() != null && !dto.titre().isBlank()) {
            query.setParameter("titre", "%" + dto.titre() + "%");
        }

        if (dto.realisateur() != null && !dto.realisateur().isBlank()) {
            query.setParameter("realisateur", "%" + dto.realisateur() + "%");
        }

        if (dto.langue() != null && !dto.langue().isBlank()) {
            query.setParameter("langue", dto.langue());
        }

        if (dto.dateSortieDebut() != null) {
            query.setParameter("anneeDebut", dto.dateSortieDebut().getYear());
        }

        if (dto.dateSortieFin() != null) {
            query.setParameter("anneeFin", dto.dateSortieFin().getYear());
        }

        return query.list()
                .stream()
                .map(row -> new RechercheResultDTO(
                        (Long) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }

    // --------------------------------
    // CONSULTATION FILM
    // --------------------------------

    public ConsultationResultDTO getConsultationResult(Session session, long id) {

        String hql = """
            SELECT 
                f.idFilm,
                f.titre,
                f.urlAffiche,
                p.nom,
                f.anneeSortie,
                f.langueOriginale,
                f.dureeMinutes,
                f.resume,
                COUNT(c.idCopie)
            FROM Film f
            JOIN Personne p ON f.idRealisateur = p.idPersonne
            LEFT JOIN Copie c ON c.film.idFilm = f.idFilm
            WHERE f.idFilm = :id
            GROUP BY 
                f.idFilm,
                f.titre,
                f.urlAffiche,
                p.nom,
                f.anneeSortie,
                f.langueOriginale,
                f.dureeMinutes,
                f.resume
        """;

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("id", id);

        Object[] result = query.uniqueResult();

        if (result == null) {
            return null;
        }

        return new ConsultationResultDTO(
                (Long) result[0],
                (String) result[1],
                (String) result[2],
                (String) result[3],
                (Integer) result[4],
                (String) result[5],
                (Integer) result[6],
                (String) result[7],
                ((Long) result[8]).intValue()
        );
    }
}