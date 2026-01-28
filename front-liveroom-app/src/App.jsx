import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { LoginPage } from './pages/LoginPage';
import { RoomPage } from './pages/RoomPage';
import { LobbyPage } from './pages/LobbyPage';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          {/* Ruta principal: http://localhost:5173/ */}
          <Route path="/" element={<LobbyPage />} />

          {/* Ruta de Login: http://localhost:5173/login */}
          <Route path="/login" element={<LoginPage />} />

          {/* Ruta de la sala: http://localhost:5173/room */}
          <Route path="/room" element={<RoomPage />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}



export default App;