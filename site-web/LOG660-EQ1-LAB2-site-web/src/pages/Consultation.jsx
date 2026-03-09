import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router";

function Consultation() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [titre, setTitre] = useState("");
  const [urlAffiche, setUrlAffiche] = useState("");
  const [nomRealisateur, setNomRealisateur] = useState("");
  const [anneeSortie, setAnneeSortie] = useState("");
  const [langueOriginale, setLangueOriginale] = useState("");
  const [dureeMinutes, setDureeMinutes] = useState("");
  const [resume, setResume] = useState("");
  const [nbCopies, setNbCopies] = useState(0);

  useEffect(() => {
    async function chargerConsultation() {
      if (!id) return;

      try {
        const token = localStorage.getItem("token");

        const response = await axios.get(
          `http://localhost:8080/api/consultation/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setTitre(response.data.titre);
        setUrlAffiche(response.data.urlAffiche);
        setNomRealisateur(response.data.nomRealisateur);
        setAnneeSortie(response.data.anneeSortie);
        setLangueOriginale(response.data.langueOriginale);
        setDureeMinutes(response.data.dureeMinutes);
        setResume(response.data.resume);
        setNbCopies(response.data.nbCopies);
      } catch (error) {
        console.log(error);
        alert("Erreur lors du chargement du film.");
      }
    }

    chargerConsultation();
  }, [id]);

  async function louerFilm() {
    try {
      const token = localStorage.getItem("token");

      await axios.post(
        "http://localhost:8080/api/location",
        { id: Number(id) },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Location effectuée avec succès.");
      navigate("/Recherche");
    } catch (error) {
      console.log(error);
      alert("Erreur lors de la location.");
    }
  }

  return (
    <>
      <title>Consultation</title>

      <h1>{titre}</h1>

      {urlAffiche && <img src={urlAffiche} alt={titre} width="200" />}

      <h3>Par: {nomRealisateur}</h3>
      <h3>Année: {anneeSortie}</h3>
      <h3>Langue: {langueOriginale}</h3>
      <h3>Durée: {dureeMinutes} minutes</h3>
      <p>{resume}</p>
      <h3>Copies disponibles: {nbCopies}</h3>

      <button onClick={louerFilm} disabled={nbCopies <= 0}>
        Louer
      </button>

      {nbCopies <= 0 && <p>Aucune copie disponible pour le moment.</p>}
    </>
  );
}

export default Consultation;