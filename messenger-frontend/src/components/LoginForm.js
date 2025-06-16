import React, {useState} from 'react';
import './register-login.css';
import {ensureE2eeKeysGeneratedAndOnServer} from "../utils/e2ee";

function LoginForm({ onLoginSuccess, onSwitchToRegister }) {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch('http://localhost:8080/api/v1/user/login', {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ login, password }),
            });

            if (response.ok) {
                const user = await response.json();
                console.log('Zalogowano jako:', user.login);
                await ensureE2eeKeysGeneratedAndOnServer(user);
                onLoginSuccess(user);
            } else {
                const err = await response.text();
                console.error('Błąd logowania:', err);
                setError('Nieprawidłowy login lub hasło');
            }
        } catch (err) {
            console.error('Błąd połączenia z serwerem:', err);
            setError('Błąd połączenia z serwerem');
        }
    };

    return (
        <div className="login-register-container">
            <form className="login-register-form" onSubmit={handleSubmit}>
                <h2>Zaloguj się</h2>
                <input
                    type="text"
                    placeholder="Login"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Hasło"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Zaloguj</button>
                {error && <p className="error">{error}</p>}
                <div style={{textAlign: 'center', marginTop: '1rem'}}>
                    <p style={{margin: 0}}>
                        Nie masz konta?
                    </p>
                    <button type="button" onClick={onSwitchToRegister} style={{
                        background: 'none',
                        border: 'none',
                        color: '#007bff',
                        cursor: 'pointer',
                        textDecoration: 'underline',
                        fontSize: '0.9rem'
                    }}>
                        Zarejestruj się
                    </button>
                </div>

            </form>
        </div>
    );
}

export default LoginForm;
