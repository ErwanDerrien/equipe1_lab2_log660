import { Routes, Route } from "react-router";
import { useState } from "react";
import Login from "./pages/Login";
import RechercheFilm from "./pages/RechercheFilm";
import Inscription from "./pages/Inscription";
import Consultation from "./pages/Consultation";
import "./App.css";

function App() {
  const [sessionToken, setSessionToken] = useState("");
  const [selectedFilmId, setSelectedFilmId] = useState("");
  return (
    <Routes>
      <Route
        index
        element={
          <Login
            setSessionToken={setSessionToken}
            sessionToken={sessionToken}
          />
        }
      />
      <Route
        path="/Recherche"
        element={
          <RechercheFilm
            sessionToken={sessionToken}
            setSelectedFilmId={setSelectedFilmId}
          />
        }
      />
      <Route path="/Inscription" element={<Inscription />} />
      <Route
        path="/Consultation"
        element={
          <Consultation
            sessionToken={sessionToken}
            selectedFilmId={selectedFilmId}
          />
        }
      />
    </Routes>
  );
}

export default App;
