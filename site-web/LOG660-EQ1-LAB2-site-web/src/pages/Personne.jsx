import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import "./Personne.css";

function Personne() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [prenom, setPrenom] = useState("");
  const [nom, setNom] = useState("");
  const [dateNaissance, setDateNaissance] = useState("");
  const [lieuNaissance, setLieuNaissance] = useState("");
  const [urlPhoto, setUrlPhoto] = useState("");
  const [biographie, setBiographie] = useState("");

  useEffect(() => {
    async function chargerPersonne() {
      if (!id) return;

      try {
        const token = localStorage.getItem("token");

        const response = await axios.get(
          `http://localhost:8080/api/personne/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setPrenom(response.data.prenom || "");
        setNom(response.data.nom || "");
        setDateNaissance(response.data.dateNaissance || "");
        setLieuNaissance(response.data.lieuNaissance || "");
        setUrlPhoto(
          response.data.urlPhoto
            ? response.data.urlPhoto.replace("http", "https")
            : ""
        );
        setBiographie(response.data.biographie || "");
      } catch (error) {
        console.log(error);
        alert("Erreur lors du chargement de la fiche personne.");
      }
    }

    chargerPersonne();
  }, [id]);

  return (
    <div className="PersonnePage">
      <title>Fiche personne</title>

      <div className="PersonneRetour">
        <button onClick={() => navigate(-1)}>Retour</button>
      </div>

      <h1 className="PersonneTitre">
        {prenom} {nom}
      </h1>

      <div className="PersonneContenu">
        {urlPhoto && (
          <img
            className="PersonnePhoto"
            src={urlPhoto}
            alt={`${prenom} ${nom}`}
          />
        )}

        <div className="PersonneInfos">
          <h3>Date de naissance: {dateNaissance || "Non disponible"}</h3>
          <h3>Lieu de naissance: {lieuNaissance || "Non disponible"}</h3>

          <p className="PersonneBio">
            {biographie || "Biographie non disponible."}
          </p>
        </div>
      </div>
    </div>
  );
}

export default Personne;