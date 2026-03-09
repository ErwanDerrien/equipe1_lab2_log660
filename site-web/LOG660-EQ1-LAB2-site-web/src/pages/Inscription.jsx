import { useState } from "react";
import "./Inscription.css";
import { useNavigate } from "react-router";
import axios from "axios";
import DatePicker from "react-date-picker";
import "react-date-picker/dist/DatePicker.css";
import "react-calendar/dist/Calendar.css";

function Inscription() {
  const [inputNom, setInputNom] = useState("");
  const [inputPrenom, setInputPrenom] = useState("");
  const [inputCourriel, setInputCourriel] = useState("");
  const [inputTelephone, setInputTelephone] = useState("");
  const [inputAdresse, setInputAdresse] = useState("");
  const [inputRue, setInputRue] = useState("");
  const [inputVille, setInputVille] = useState("");
  const [inputProvince, setInputProvince] = useState("");
  const [inputCodePostal, setInputCodePostal] = useState("");
  const [inputDate, setInputDate] = useState(null);
  const [inputMotDePasse, setInputMotDePasse] = useState("");

  const passwordRegex = /^[A-Za-z0-9]{5,}$/;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const navigate = useNavigate();

  async function inscrire() {
    if (!inputNom.trim()) {
      alert("Le nom est obligatoire.");
      return;
    }

    if (!inputPrenom.trim()) {
      alert("Le prénom est obligatoire.");
      return;
    }

    if (!inputCourriel.trim()) {
      alert("Le courriel est obligatoire.");
      return;
    }

    if (!emailRegex.test(inputCourriel)) {
      alert("Veuillez entrer une adresse courriel valide.");
      return;
    }

    if (!inputTelephone.trim()) {
      alert("Le numéro de téléphone est obligatoire.");
      return;
    }

    if (!inputAdresse.trim()) {
      alert("Le numéro d'adresse civique est obligatoire.");
      return;
    }

    if (!inputRue.trim()) {
      alert("La rue est obligatoire.");
      return;
    }

    if (!inputVille.trim()) {
      alert("La ville est obligatoire.");
      return;
    }

    if (!inputProvince.trim()) {
      alert("La province est obligatoire.");
      return;
    }

    if (!inputCodePostal.trim()) {
      alert("Le code postal est obligatoire.");
      return;
    }

    if (!inputDate) {
      alert("La date de naissance est obligatoire.");
      return;
    }

    if (!inputMotDePasse.trim()) {
      alert("Le mot de passe est obligatoire.");
      return;
    }

    if (!passwordRegex.test(inputMotDePasse)) {
      alert(
        "Le mot de passe doit contenir au moins 5 caractères et seulement des lettres et des chiffres."
      );
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/auth/register", {
        nom: inputNom,
        prenom: inputPrenom,
        courriel: inputCourriel,
        telephone: inputTelephone,
        adresseCivique: inputAdresse,
        rue: inputRue,
        ville: inputVille,
        province: inputProvince,
        codePostal: inputCodePostal,
        dateNaissance: inputDate.toISOString().split("T")[0],
        motDePasse: inputMotDePasse,
      });

      console.log(response.data);
      alert("Inscription réussie.");
      navigate("/");
    } catch (error) {
      console.log(error);
      alert("Erreur lors de l'inscription.");
    }
  }

  return (
    <>
      <title>Inscription</title>
      <header>Inscription</header>

      <div className="InscriptionInfo">
        <div className="InscriptionNomComplet">
          <div className="InscriptionBase">
            <p>Nom:</p>
            <input
              value={inputNom}
              onChange={(e) => setInputNom(e.target.value)}
            />
          </div>

          <div className="InscriptionBase">
            <p>Prenom:</p>
            <input
              value={inputPrenom}
              onChange={(e) => setInputPrenom(e.target.value)}
            />
          </div>
        </div>

        <div className="InscriptionBase">
          <p>Adresse Courriel:</p>
          <input
            value={inputCourriel}
            onChange={(e) => setInputCourriel(e.target.value)}
          />
        </div>

        <div className="InscriptionBase">
          <p>Numéro de Téléphone:</p>
          <input
            value={inputTelephone}
            onChange={(e) => setInputTelephone(e.target.value)}
          />
        </div>

        <div className="InscriptionLocalisation">
          <div className="InscriptionAdresse">
            <div className="InscriptionBase">
              <p>Numéro d'Adresse Civique:</p>
              <input
                value={inputAdresse}
                onChange={(e) => setInputAdresse(e.target.value)}
              />
            </div>

            <div className="InscriptionBase">
              <p>Rue:</p>
              <input
                value={inputRue}
                onChange={(e) => setInputRue(e.target.value)}
              />
            </div>
          </div>

          <div className="InscriptionRegion">
            <div className="InscriptionBase">
              <p>Ville:</p>
              <input
                value={inputVille}
                onChange={(e) => setInputVille(e.target.value)}
              />
            </div>

            <div className="InscriptionBase">
              <p>Province:</p>
              <input
                value={inputProvince}
                onChange={(e) => setInputProvince(e.target.value)}
              />
            </div>
          </div>

          <div className="InscriptionBase">
            <p>Code Postal:</p>
            <input
              value={inputCodePostal}
              onChange={(e) => setInputCodePostal(e.target.value)}
            />
          </div>
        </div>

        <div className="InscriptionBase">
          <p>Date de Naissance:</p>
          <DatePicker onChange={setInputDate} value={inputDate} />
        </div>

        <div className="InscriptionBase">
          <p>Mot de Passe:</p>
          <input
            type="password"
            value={inputMotDePasse}
            onChange={(e) => setInputMotDePasse(e.target.value)}
          />
        </div>
      </div>

      <button onClick={inscrire}>Inscription</button>
    </>
  );
}

export default Inscription;