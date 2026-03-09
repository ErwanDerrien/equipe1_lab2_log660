import { Routes, Route } from "react-router";
import { useState } from "react";
import Login from "./pages/Login";
import RechercheFilm from "./pages/RechercheFilm";
import Inscription from "./pages/Inscription";
import Consultation from "./pages/Consultation";
import "./App.css";

function App() {
  const [sessionToken, setSessionToken] = useState(
    localStorage.getItem("token") || ""
  );

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
        element={<RechercheFilm sessionToken={sessionToken} />}
      />

      <Route path="/Inscription" element={<Inscription />} />

      <Route
        path="/Consultation/:id"
        element={<Consultation sessionToken={sessionToken} />}
      />
    </Routes>
  );
}

export default App;