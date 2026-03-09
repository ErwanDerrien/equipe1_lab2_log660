import axios from "axios";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router";

function Consultation({ sessionToken, selectedFilmId }) {
  const [titre, setTitre] = useState("");
  const [urlAffiche, setUrlAffiche] = useState("");
  const [nomRealisateur, setNomRealisateur] = useState("");
  const [anneeSortie, setAnneeSortie] = useState("");
  const [langueOriginale, setLangueOriginale] = useState("");
  const [dureeMinutes, setDureeMinutes] = useState("");
  const [resume, setResume] = useState("");
  const [nbCopies, setNbCopies] = useState("");

  useEffect(() => {
    axios
      .get(`http://localhost:3000/api/consultation/${selectedFilmId}`)
      .then((response) => {
        setTitre(response.titre);
        setUrlAffiche(response.urlAffiche);
        setNomRealisateur(response.nomRealisateur);
        setAnneeSortie(response.anneeSortie);
        setLangueOriginale(response.langueOriginale);
        setDureeMinutes(response.dureeMinutes);
        setResume(response.resume);
        setNbCopies(response.nbCopies);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [selectedFilmId]);

  return (
    <>
      <title>Consultation</title>
      <h1>{titre}</h1>
      <img src={urlAffiche} />
      <h3>Par: {nomRealisateur}</h3>
      <h3>{anneeSortie}</h3>
      <h3>{langueOriginale}</h3>
      <h3>Durée: {dureeMinutes} minutes</h3>
      <p>{resume}</p>
      {nbCopies > 0 && (
        <button onClick={LouerFilm(sessionToken, selectedFilmId)}>Louer</button>
      )}
    </>
  );
}

function LouerFilm({ sesstionToken, selectedFilmId }) {
  const navigate = useNavigate();
  axios
    .post("http://localhost:3000/api/location", {
      params: {
        id: selectedFilmId,
      },
    })
    .then(() => {
      navigate("/Recherche");
    })
    .catch(function (error) {
      console.log(error);
    });
}

export default Consultation;

/*
long id,
    String titre,
    String urlAffiche,
    String nomRealisateur,
    Integer anneeSortie,
    String langueOriginale,
    Integer dureeMinutes,
    String resume,
    int nbCopies) {}
*/
