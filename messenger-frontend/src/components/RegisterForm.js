import React, { useState } from 'react';

function RegisterForm({ onRegisterSuccess }) {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch('http://localhost:8080/api/v1/user/register', {

                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({ login, password }),
            });

            if (res.ok) {
                setMessage('Rejestracja udana 🎉');
                onRegisterSuccess(); // Przełącz na logowanie
            } else {
                const err = await res.text();
                setMessage(`Błąd: ${err}`);
            }
        } catch (err) {
            setMessage('Błąd połączenia z serwerem');
        }
    };

    return (
        <div>
            <h2>Rejestracja</h2>
            <form onSubmit={handleSubmit}>
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
                <button type="submit">Zarejestruj się</button>
            </form>
            <p>{message}</p>
        </div>
    );
}

export default RegisterForm;
