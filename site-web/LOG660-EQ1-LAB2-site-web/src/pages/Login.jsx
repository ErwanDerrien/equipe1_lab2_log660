import { Link } from "react-router";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios";
import "./Login.css";

function Login({ setSessionToken, sessionToken }) {
  const [inputCourriel, setInputCourriel] = useState("");
  const [inputMotDePasse, setInputMotDePasse] = useState("");

  function saveCourriel(event) {
    setInputCourriel(event.target.value);
  }

  function saveMotDePasse(event) {
    setInputMotDePasse(event.target.value);
  }

  return (
    <>
      <title>Login</title>
      <header>Connexion</header>
      <div className="LoginDetails">
        <input onChange={saveCourriel} placeholder="courriel" />
        <input onChange={saveMotDePasse} placeholder="mot de passe" />
        <div className="Buttons">
          <button
            onClick={Connecter(
              inputCourriel,
              setInputCourriel,
              inputMotDePasse,
              setInputMotDePasse,
              setSessionToken,
              sessionToken,
            )}
          >
            Connexion
          </button>
          <Link to="/Inscription">
            <button>Inscription</button>
          </Link>
        </div>
      </div>
    </>
  );
}

function Connecter(
  inputCourriel,
  setInputCourriel,
  inputMotDePasse,
  setInputMotDePasse,
  setSessionToken,
  sessionToken,
) {
  const navigate = useNavigate();
  axios
    .get("http://localhost:3000/api/login", {
      params: {
        courriel: inputCourriel,
        password: inputMotDePasse,
      },
    })
    .then((response) => {
      setSessionToken(response.data);
      setInputCourriel("");
      setInputMotDePasse("");
    })
    .catch(function (error) {
      console.log(error);
    });

  useEffect(() => {
    if (sessionToken) {
      navigate("/Recherche");
    }
  }, [sessionToken, navigate]);
}

export default Login;
