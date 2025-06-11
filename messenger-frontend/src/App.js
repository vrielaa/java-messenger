import React, { useState } from 'react';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import UserList from './components/UserList';
import ChatWindow from './components/ChatWindow';
import './App.css';

function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    const [isRegistering, setIsRegistering] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const [currentUser, setCurrentUser] = useState(null); // 👈 dodajemy

    const handleLoginSuccess = (user) => {
        setCurrentUser(user); // 👈 zapisz użytkownika
        setLoggedIn(true);
    };

    const handleRegisterSuccess = () => setIsRegistering(false);

    if (!loggedIn) {
        return (
            <div className="auth-container">
                {isRegistering ? (
                    <RegisterForm
                        onRegisterSuccess={handleRegisterSuccess}
                        onSwitchToLogin={() => setIsRegistering(false)}
                    />
                ) : (
                    <LoginForm
                        onLoginSuccess={handleLoginSuccess}
                        onSwitchToRegister={() => setIsRegistering(true)}
                    />
                )}
            </div>
        );
    }

    return (
        <div className="messenger-layout">
            <div className="userlist-area">
                <UserList onUserSelect={setSelectedUser} />
            </div>
            <div className="chat-area">
                {selectedUser ? (
                    <ChatWindow user={selectedUser} currentUser={currentUser} />
                ) : (
                    <p style={{ padding: "1rem", color: "#555" }}>
                        Wybierz użytkownika z listy, aby rozpocząć czat.
                    </p>
                )}
            </div>
        </div>
    );
}

export default App;
