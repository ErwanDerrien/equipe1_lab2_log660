package ca.etsmtl.log660.cinema_backend.services;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import ca.etsmtl.log660.cinema_backend.dtos.ConsultationResultDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheResultDTO;
import ca.etsmtl.log660.cinema_backend.facade.RechercheFacade;
import ca.etsmtl.log660.cinema_backend.model.Personne;

@Service
public class RechercheService {

    private final RechercheFacade facade;

    public RechercheService(RechercheFacade facade) {
        this.facade = facade;
    }

    public List<RechercheResultDTO> recherche(RechercheDTO dto) {
        try (Session session = facade.getSession()) {
            return facade.rechercheFilms(session, dto);
        }
    }

    public ConsultationResultDTO getConsultationResult(long id) {
        try (Session session = facade.getSession()) {
            return facade.getConsultationResult(session, id);
        }
    }

    public Personne getPersonne(long id) {
        try (Session session = facade.getSession()) {
            return facade.getPersonne(session, id);
        }
    }
}