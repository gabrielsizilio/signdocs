import './App.css';
import ProtectedLayout from './components/ProtectedLayout';
import { AuthProvider } from './context/AuthProvider';

import { BrowserRouter, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route
            path="/me"
            element={
              <ProtectedLayout>
                {/* TODO: Criar página protegida */}
                <h2>Hello World!</h2>
              </ProtectedLayout>
            }
          />
          <Route
            path="/login"
            element={<h2>Login</h2>}
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
