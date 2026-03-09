import { Link, useNavigate } from "react-router";
import { useState } from "react";
import axios from "axios";
import "./Login.css";

function Login({ setSessionToken }) {
  const [inputCourriel, setInputCourriel] = useState("");
  const [inputMotDePasse, setInputMotDePasse] = useState("");
  const navigate = useNavigate();

  async function connecter() {
    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        courriel: inputCourriel,
        password: inputMotDePasse,
      });

      const token = response.data.token ?? response.data;

      setSessionToken(token);
      localStorage.setItem("token", token);

      setInputCourriel("");
      setInputMotDePasse("");
      navigate("/Recherche");
    } catch (error) {
      console.log(error);
      alert("Échec de la connexion.");
    }
  }

  return (
    <>
      <title>Login</title>
      <header>Connexion</header>

      <div className="LoginDetails">
        <input
          value={inputCourriel}
          onChange={(e) => setInputCourriel(e.target.value)}
          placeholder="courriel"
        />

        <input
          type="password"
          value={inputMotDePasse}
          onChange={(e) => setInputMotDePasse(e.target.value)}
          placeholder="mot de passe"
        />

        <div className="Buttons">
          <button onClick={connecter}>Connexion</button>

          <Link to="/Inscription">
            <button>Inscription</button>
          </Link>
        </div>
      </div>
    </>
  );
}

export default Login;