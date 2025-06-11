import React, { useState } from 'react';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import UserList from './components/UserList';

function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    const [isRegistering, setIsRegistering] = useState(false);

    const handleUserSelect = (user) => {
        console.log('Wybrano użytkownika:', user);
        // Tutaj można przejść do okna czatu
    };

    return (
        <div className="App">
            {loggedIn ? (
                <>
                    <p>Zalogowano! 🟢</p>
                    <UserList onUserSelect={handleUserSelect} />
                </>
            ) : isRegistering ? (
                <>
                    <RegisterForm onRegisterSuccess={() => setIsRegistering(false)} />
                    <p>
                        Masz już konto?{' '}
                        <button onClick={() => setIsRegistering(false)}>Zaloguj się</button>
                    </p>
                </>
            ) : (
                <>
                    <LoginForm onLoginSuccess={() => setLoggedIn(true)} />
                    <p>
                        Nie masz konta?{' '}
                        <button onClick={() => setIsRegistering(true)}>Zarejestruj się</button>
                    </p>
                </>
            )}
        </div>
    );
}

export default App;
