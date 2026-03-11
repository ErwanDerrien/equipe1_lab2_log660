import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router";
import "./RechercheFilm.css";
import DatePicker from "react-date-picker";
import "react-date-picker/dist/DatePicker.css";
import "react-calendar/dist/Calendar.css";

function FilmComponents({ films }) {
  if (!films || films.length === 0) {
    return <p>Aucun film trouvé.</p>;
  }

  return films.map((film) => (
    <Film
      key={film.id}
      titre={film.titre}
      urlAffiche={film.urlAffiche}
      nomRealisateur={film.nomRealisateur}
      id={film.id}
    />
  ));
}

function RechercheFilm() {
  const [films, setFilms] = useState([]);
  const [inputTitre, setInputTitre] = useState("");
  const [inputRealisateur, setInputRealisateur] = useState("");
  const [inputActeur, setInputActeur] = useState("");
  const [inputGenre, setInputGenre] = useState("");
  const [inputLangue, setInputLangue] = useState("");
  const [inputPays, setInputPays] = useState("");
  const [inputAnneeInferieure, setInputAnneeInferieure] = useState(null);
  const [inputAnneeSuperieure, setInputAnneeSuperieure] = useState(null);

  async function rechercher() {
    try {
      const token = localStorage.getItem("token");

      const params = {
        titre: inputTitre || null,
        realisateur: inputRealisateur || null,
        acteur: inputActeur || null,
        genre: inputGenre || null,
        langue: inputLangue || null,
        pays: inputPays || null,
        dateSortieDebut: inputAnneeInferieure
          ? inputAnneeInferieure.toISOString().split("T")[0]
          : null,
        dateSortieFin: inputAnneeSuperieure
          ? inputAnneeSuperieure.toISOString().split("T")[0]
          : null,
      };

      const response = await axios.get("http://localhost:8080/api/recherche", {
        params,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setFilms(response.data);

      const url = new URL(window.location.href);
      Object.entries(params).forEach(([key, value]) => {
        if (value) {
          url.searchParams.set(key, value);
        } else {
          url.searchParams.delete(key);
        }
      });
      history.pushState({}, "", url);
    } catch (error) {
      console.log(error);
      if (error.response?.status === 404) {
        setFilms([]);
        return;
      }
      setFilms([]);
      alert("Erreur lors de la recherche de films.");
    }
  }

  return (
    <>
      <title>Recherche de film</title>
      <header className="RechercheHeader">Recherche de Films</header>

      <div className="RechercheHeadingButtons">
        <Link to="/">
          <button>Connexion</button>
        </Link>

        <Link to="/Inscription">
          <button>Inscription</button>
        </Link>
      </div>

      <div className="RechercheInfo">
        <div className="RechercheBase">
          <p>Titre du film:</p>
          <input
            value={inputTitre}
            onChange={(e) => setInputTitre(e.target.value)}
          />
        </div>

        <div className="RechercheBase">
          <p>Réalisateur:</p>
          <input
            value={inputRealisateur}
            onChange={(e) => setInputRealisateur(e.target.value)}
          />
        </div>

        <div className="RechercheBase">
          <p>Acteur:</p>
          <input
            value={inputActeur}
            onChange={(e) => setInputActeur(e.target.value)}
          />
        </div>

        <div className="RechercheBase">
          <p>Genre:</p>
          <input
            value={inputGenre}
            onChange={(e) => setInputGenre(e.target.value)}
          />
        </div>

        <div className="RechercheBase">
          <p>Langue:</p>
          <input
            value={inputLangue}
            onChange={(e) => setInputLangue(e.target.value)}
          />
        </div>

        <div className="RechercheBase">
          <p>Pays:</p>
          <input
            value={inputPays}
            onChange={(e) => setInputPays(e.target.value)}
          />
        </div>

        <div className="RechercheBase">
          <p>Année de sortie entre:</p>
          <div className="RechercheAnnee">
            <DatePicker
              onChange={setInputAnneeInferieure}
              value={inputAnneeInferieure}
            />
            <p>et</p>
            <DatePicker
              onChange={setInputAnneeSuperieure}
              value={inputAnneeSuperieure}
            />
          </div>
        </div>

        <button onClick={rechercher}>Chercher Film</button>
      </div>

      <div>
        <FilmComponents films={films} />
      </div>
    </>
  );
}

function Film({ titre, urlAffiche, nomRealisateur, id }) {
  const navigate = useNavigate();

  function filmClicked() {
    navigate(`/Consultation/${id}`);
  }

  const safeAffiche = urlAffiche ? urlAffiche.replace("http", "https") : "";

  return (
    <div onClick={filmClicked} style={{ cursor: "pointer" }}>
      <h2>{titre}</h2>
      {safeAffiche && <img src={safeAffiche} width="100" alt={titre} />}
      <h3>Réalisateur: {nomRealisateur}</h3>
    </div>
  );
}

export default RechercheFilm;