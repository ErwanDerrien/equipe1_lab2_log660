package ca.etsmtl.log660.cinema_backend.facade;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ca.etsmtl.log660.cinema_backend.dtos.ConsultationActeurDTO;
import ca.etsmtl.log660.cinema_backend.dtos.ConsultationResultDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheResultDTO;
import ca.etsmtl.log660.cinema_backend.model.Personne;
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

    public List<RechercheResultDTO> rechercheFilms(Session session, RechercheDTO dto) {

        StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT
                f.ID_FILM,
                f.TITRE,
                f.URL_AFFICHE,
                p.NOM
            FROM FILM f
            JOIN PERSONNE p ON f.ID_REALISATEUR = p.ID_PERSONNE
            WHERE 1 = 1
        """);

        if (dto.titre() != null && !dto.titre().isBlank()) {
            sql.append(" AND LOWER(f.TITRE) LIKE LOWER(:titre)");
        }

        if (dto.realisateur() != null && !dto.realisateur().isBlank()) {
            sql.append(" AND LOWER(p.NOM) LIKE LOWER(:realisateur)");
        }

        if (dto.acteur() != null && !dto.acteur().isBlank()) {
            sql.append("""
                 AND EXISTS (
                     SELECT 1
                     FROM FILMACTEUR fa
                     JOIN PERSONNE pa ON pa.ID_PERSONNE = fa.ID_ACTEUR
                     WHERE fa.ID_FILM = f.ID_FILM
                     AND (
                         LOWER(pa.NOM) LIKE LOWER(:acteur)
                         OR LOWER(pa.PRENOM) LIKE LOWER(:acteur)
                         OR LOWER(pa.PRENOM || ' ' || pa.NOM) LIKE LOWER(:acteur)
                         OR LOWER(pa.NOM || ' ' || pa.PRENOM) LIKE LOWER(:acteur)
                     )
                 )
            """);
        }

        if (dto.langue() != null && !dto.langue().isBlank()) {
            sql.append(" AND LOWER(f.LANGUE_ORIGINALE) = LOWER(:langue)");
        }

        if (dto.dateSortieDebut() != null) {
            sql.append(" AND f.ANNEE_SORTIE >= :anneeDebut");
        }

        if (dto.dateSortieFin() != null) {
            sql.append(" AND f.ANNEE_SORTIE <= :anneeFin");
        }

        if (dto.genre() != null && !dto.genre().isBlank()) {
            sql.append("""
                 AND EXISTS (
                     SELECT 1
                     FROM FILMGENRE fg
                     JOIN GENRE g ON g.ID_GENRE = fg.ID_GENRE
                     WHERE fg.ID_FILM = f.ID_FILM
                     AND LOWER(g.NOM) LIKE LOWER(:genre)
                 )
            """);
        }

        if (dto.pays() != null && !dto.pays().isBlank()) {
            sql.append("""
                 AND EXISTS (
                     SELECT 1
                     FROM FILMPAYS fp
                     JOIN PAYS pa ON pa.ID_PAYS = fp.ID_PAYS
                     WHERE fp.ID_FILM = f.ID_FILM
                     AND LOWER(pa.NOM) LIKE LOWER(:pays)
                 )
            """);
        }

        Query<?> nativeQuery = session.createNativeQuery(sql.toString());

        if (dto.titre() != null && !dto.titre().isBlank()) {
            nativeQuery.setParameter("titre", "%" + dto.titre().trim() + "%");
        }

        if (dto.realisateur() != null && !dto.realisateur().isBlank()) {
            nativeQuery.setParameter("realisateur", "%" + dto.realisateur().trim() + "%");
        }

        if (dto.acteur() != null && !dto.acteur().isBlank()) {
            nativeQuery.setParameter("acteur", "%" + dto.acteur().trim() + "%");
        }

        if (dto.langue() != null && !dto.langue().isBlank()) {
            nativeQuery.setParameter("langue", dto.langue().trim());
        }

        if (dto.dateSortieDebut() != null) {
            nativeQuery.setParameter("anneeDebut", dto.dateSortieDebut().getYear());
        }

        if (dto.dateSortieFin() != null) {
            nativeQuery.setParameter("anneeFin", dto.dateSortieFin().getYear());
        }

        if (dto.genre() != null && !dto.genre().isBlank()) {
            nativeQuery.setParameter("genre", "%" + dto.genre().trim() + "%");
        }

        if (dto.pays() != null && !dto.pays().isBlank()) {
            nativeQuery.setParameter("pays", "%" + dto.pays().trim() + "%");
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = (List<Object[]>) nativeQuery.list();

        return rows.stream()
                .map(row -> new RechercheResultDTO(
                        toLong(row[0]),
                        row[1] == null ? null : row[1].toString(),
                        row[2] == null ? null : row[2].toString(),
                        row[3] == null ? null : row[3].toString()))
                .toList();
    }

    public ConsultationResultDTO getConsultationResult(Session session, long id) {

        String sql = """
            SELECT
                f.ID_FILM,
                f.TITRE,
                f.URL_AFFICHE,
                f.ID_REALISATEUR,
                p.NOM,
                f.ANNEE_SORTIE,
                f.LANGUE_ORIGINALE,
                f.DUREE_MINUTES,
                f.RESUME,
                COUNT(c.ID_COPIE)
            FROM FILM f
            JOIN PERSONNE p ON f.ID_REALISATEUR = p.ID_PERSONNE
            LEFT JOIN COPIE c
                ON c.ID_FILM = f.ID_FILM
                AND c.DISPONIBLE = 'O'
            WHERE f.ID_FILM = :id
            GROUP BY
                f.ID_FILM,
                f.TITRE,
                f.URL_AFFICHE,
                f.ID_REALISATEUR,
                p.NOM,
                f.ANNEE_SORTIE,
                f.LANGUE_ORIGINALE,
                f.DUREE_MINUTES,
                f.RESUME
        """;

        Query<?> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("id", id);

        Object[] result = (Object[]) nativeQuery.uniqueResult();

        if (result == null) {
            return null;
        }

        String sqlActeurs = """
            SELECT
                pa.ID_PERSONNE,
                pa.PRENOM,
                pa.NOM,
                fa.NOM_PERSONNAGE
            FROM FILMACTEUR fa
            JOIN PERSONNE pa ON pa.ID_PERSONNE = fa.ID_ACTEUR
            WHERE fa.ID_FILM = :id
            ORDER BY pa.NOM, pa.PRENOM
        """;

        Query<?> acteursQuery = session.createNativeQuery(sqlActeurs);
        acteursQuery.setParameter("id", id);

        @SuppressWarnings("unchecked")
        List<Object[]> acteurRows = (List<Object[]>) acteursQuery.list();

        List<ConsultationActeurDTO> acteurs = acteurRows.stream()
                .map(row -> new ConsultationActeurDTO(
                        toLong(row[0]),
                        row[1] == null ? null : row[1].toString(),
                        row[2] == null ? null : row[2].toString(),
                        row[3] == null ? null : row[3].toString()))
                .toList();

        return new ConsultationResultDTO(
                toLong(result[0]),
                result[1] == null ? null : result[1].toString(),
                result[2] == null ? null : result[2].toString(),
                toLong(result[3]),
                result[4] == null ? null : result[4].toString(),
                toInteger(result[5]),
                result[6] == null ? null : result[6].toString(),
                toInteger(result[7]),
                result[8] == null ? null : result[8].toString(),
                toInteger(result[9]),
                acteurs);
    }

    public Personne getPersonne(Session session, long id) {
        return session.get(Personne.class, id);
    }

    private long toLong(Object value) {
        if (value == null) {
            return 0L;
        }
        if (value instanceof Long longValue) {
            return longValue;
        }
        if (value instanceof Integer integerValue) {
            return integerValue.longValue();
        }
        if (value instanceof BigDecimal bigDecimalValue) {
            return bigDecimalValue.longValue();
        }
        if (value instanceof Number numberValue) {
            return numberValue.longValue();
        }
        return Long.parseLong(value.toString());
    }

    private Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer integerValue) {
            return integerValue;
        }
        if (value instanceof Long longValue) {
            return longValue.intValue();
        }
        if (value instanceof BigDecimal bigDecimalValue) {
            return bigDecimalValue.intValue();
        }
        if (value instanceof Number numberValue) {
            return numberValue.intValue();
        }
        return Integer.valueOf(value.toString());
    }
}