import { useState, useEffect } from "react";
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
  const [inputDate, setInputDate] = useState("");
  const [inputMotDePasse, setInputMotDePasse] = useState("");

  function saveNom(event) {
    setInputNom(event.target.value);
  }
  function savePrenom(event) {
    setInputPrenom(event.target.value);
  }
  function saveCourriel(event) {
    setInputCourriel(event.target.value);
  }
  function saveTelephone(event) {
    setInputTelephone(event.target.value);
  }
  function saveAdresse(event) {
    setInputAdresse(event.target.value);
  }
  function saveRue(event) {
    setInputRue(event.target.value);
  }
  function saveVille(event) {
    setInputVille(event.target.value);
  }
  function saveProvince(event) {
    setInputProvince(event.target.value);
  }
  function saveCodePostal(event) {
    setInputCodePostal(event.target.value);
  }
  function saveMotDePasse(event) {
    setInputMotDePasse(event.target.value);
  }

  return (
    <>
      <title>Inscription</title>
      <header>Inscription</header>
      <div className="InscriptionInfo">
        <div className="InscriptionNomComplet">
          <div className="InscriptionBase">
            <p>Nom:</p>
            <input onChange={saveNom} />
          </div>
          <div className="InscriptionBase">
            <p>Prenom:</p>
            <input onChange={savePrenom} />
          </div>
        </div>
        <div className="InscriptionBase">
          <p>Adresse Courriel:</p>
          <input onChange={saveCourriel} />
        </div>
        <div className="InscriptionBase">
          <p>Numéro de Téléphone:</p>
          <input onChange={saveTelephone} />
        </div>
        <div className="InscriptionLocalisation">
          <div className="InscriptionAdresse">
            <div className="InscriptionBase">
              <p>Numéro d'Adresse Civique:</p>
              <input onChange={saveAdresse} />
            </div>
            <div className="InscriptionBase">
              <p>Rue:</p>
              <input onChange={saveRue} />
            </div>
          </div>
          <div className="InscriptionRegion">
            <div className="InscriptionBase">
              <p>Ville:</p>
              <input onChange={saveVille} />
            </div>
            <div className="InscriptionBase">
              <p>Province:</p>
              <input onChange={saveProvince} />
            </div>
          </div>
          <div className="InscriptionBase">
            <p>Code Postal:</p>
            <input onChange={saveCodePostal} />
          </div>
        </div>
        <div className="InscriptionBase">
          <p>Date de Naissance:</p>
          <DatePicker onChange={setInputDate} value={inputDate} />
        </div>
        <div className="InscriptionBase">
          <p>Mot de Passe:</p>
          <input onChange={saveMotDePasse} />
        </div>
      </div>
      <button
        onClick={Inscrire(
          inputNom,
          inputPrenom,
          inputCourriel,
          inputTelephone,
          inputAdresse,
          inputRue,
          inputVille,
          inputProvince,
          inputCodePostal,
          inputDate,
          inputMotDePasse,
        )}
      >
        Inscription
      </button>
    </>
  );
}

function Inscrire(
  inputNom,
  inputPrenom,
  inputCourriel,
  inputTelephone,
  inputAdresse,
  inputRue,
  inputVille,
  inputProvince,
  inputCodePostal,
  inputDate,
  inputMotDePasse,
) {
  const navigate = useNavigate();
  let registerOk = "";

  axios
    .post("http://localhost:3000/api/register", {
      params: {
        nom: inputNom,
        prenom: inputPrenom,
        courriel: inputCourriel,
        telephone: inputTelephone,
        adresseCivique: inputAdresse,
        rue: inputRue,
        ville: inputVille,
        province: inputProvince,
        codePostal: inputCodePostal,
        dateNaissance: inputDate,
        motDePasse: inputMotDePasse,
      },
    })
    .then((response) => {
      registerOk = response.data;
      console.log(response.data);
    })
    .catch(function (error) {
      console.log(error);
    });

  useEffect(() => {
    if (registerOk) {
      navigate("/");
    }
  }, [registerOk, navigate]);
}

export default Inscription;

/*
    String nom,
    String prenom,
    String courriel,
    String telephone,
    String adresseCivique,
    String rue,
    String ville,
    String province,
    String codePostal,
    LocalDate dateNaissance,
    String motDePasse) {}
*/
