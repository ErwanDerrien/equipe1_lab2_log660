import axios from "axios";
import { useState, useEffect } from "react";
import { Link } from "react-router";
import "./RechercheFilm.css";
import { useNavigate } from "react-router";
import DatePicker from "react-date-picker";
import "react-date-picker/dist/DatePicker.css";
import "react-calendar/dist/Calendar.css";

function FilmComponents({ films, setFilms, setSelectedFilmId }) {
  setFilms([
    {
      titre: "Star Wars",
      urlAffiche:
        "https://static.wikia.nocookie.net/lucasfilm/images/8/87/StarWarsMoviePoster1977.jpg/revision/latest/scale-to-width-down/1000?cb=20150203191803",
      nomRealisateur: "George Lucas",
      id: "1",
    },
    {
      titre: "The Bullet Train",
      urlAffiche:
        "https://image.tmdb.org/t/p/original/eXaBBPqJRkW4SGqkbiLHNzDbzIf.jpg",
      nomRealisateur: "Junya Satou",
      id: "2",
    },
  ]);

  return films.map((film) => {
    return (
      <Film
        titre={film.titre}
        urlAffiche={film.urlAffiche}
        nomRealisateur={film.nomRealisateur}
        id={film.id}
        setSelectedFilmId={setSelectedFilmId}
      />
    );
  });
}

function RechercheFilm({ setSelectedFilmId }) {
  const [films, setFilms] = useState([]);
  const [inputTitre, setInputTitre] = useState("");
  const [inputRealisateur, setInputRealisateur] = useState("");
  const [inputGenre, setInputGenre] = useState("");
  const [inputLangue, setInputLangue] = useState("");
  const [inputPays, setInputPays] = useState("");
  const [inputAnneeInferieure, setInputAnneeInferieure] = useState("");
  const [inputAnneeSuperieure, setInputAnneeSuperieure] = useState("");

  function Recherche({
    setFilms,
    inputTitre,
    inputRealisateur,
    inputGenre,
    inputLangue,
    inputPays,
    inputAnneeInferieure,
    inputAnneeSuperieure,
  }) {
    axios
      .get("http://localhost:3000/api/recherche", {
        params: {
          titre: inputTitre,
          realisateur: inputRealisateur,
          genre: inputGenre,
          langue: inputLangue,
          pays: inputPays,
          dateSortieDebut: inputAnneeInferieure,
          dateSortieFin: inputAnneeSuperieure,
        },
      })
      .then((response) => {
        setFilms(response.data);
      });
  }

  function handleTitre(event) {
    setInputTitre(event.target.value);
  }
  function handleRealisateur(event) {
    setInputRealisateur(event.target.value);
  }
  function handleGenre(event) {
    setInputGenre(event.target.value);
  }
  function handleLangue(event) {
    setInputLangue(event.target.value);
  }
  function handlePays(event) {
    setInputPays(event.target.value);
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
          <input onChange={handleTitre} />
        </div>
        <div className="RechercheBase">
          <p>Réalisateur:</p>
          <input onChange={handleRealisateur} />
        </div>
        <div className="RechercheBase">
          <p>Genre:</p>
          <input onChange={handleGenre} />
        </div>
        <div className="RechercheBase">
          <p>Langue:</p>
          <input onChange={handleLangue} />
        </div>
        <div className="RechercheBase">
          <p>Pays:</p>
          <input onChange={handlePays} />
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
        <button
          onClick={Recherche(
            setFilms,
            inputTitre,
            inputRealisateur,
            inputGenre,
            inputLangue,
            inputPays,
            inputAnneeInferieure,
            inputAnneeSuperieure,
          )}
        >
          Chercher Film
        </button>
      </div>
      <div>
        <FilmComponents
          films={films}
          setFilms={setFilms}
          setSelectedFilmId={setSelectedFilmId}
        />
      </div>
    </>
  );
}

function Film({
  titreFilm,
  urlAffiche,
  nomRealisateur,
  id,
  setSelectedFilmId,
}) {
  return (
    <div onClick={FilmClicked(id, setSelectedFilmId)}>
      <h2>{titreFilm}</h2>
      <img src={urlAffiche} width="100" />
      <h3>Réalisateur: {nomRealisateur}</h3>
    </div>
  );
}

function FilmClicked({ id, setSelectedFilmId }) {
  const navigate = useNavigate();
  useEffect(() => {
    if (id) {
      setSelectedFilmId(id);
      navigate("/Consultation");
    }
  }, [id, navigate, setSelectedFilmId]);
}

export default RechercheFilm;

/*
SELECT 
                f.idFilm,
                f.titre,
                f.urlAffiche,
                r.nom,
                f.anneeSortie,
                f.langueOriginale,
                f.dureeMinutes,
                f.resume,
                COUNT(c.idCopie)

                long id,
        String titre,
        String urlAffiche,
        String nomRealisateur
*/
