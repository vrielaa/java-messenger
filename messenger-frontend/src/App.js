import React, {useEffect, useState} from 'react';
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

    const logout = () => {
        console.log("callback called");
        console.trace()
        fetch(`http://localhost:8080/logout`, {
            "credentials": "include"
        }).then(() => {
            setCurrentUser(null);
            setSelectedUser(null);
            setLoggedIn(false);
            setIsRegistering(false);
        });
    };

    useEffect(() => {
        const fetchStatus = async () => {
            const res = await fetch(`http://localhost:8080/api/v1/user/me`, {
                "credentials": "include"
            });
            console.log(res);
            if (res.ok) {
                handleLoginSuccess(await res.json());
            }
        };
        fetchStatus();
    }, []);

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
                <UserList onUserSelect={setSelectedUser} currentUser={currentUser} logoutCallback={logout} />
            </div>
            <div className="chat-area">
                {selectedUser ? (
                    <ChatWindow user={selectedUser} currentUser={currentUser} />
                ) : (
                    <p style={{ padding: "1rem", color: "#12782b" }}>
                        Wybierz użytkownika z listy, aby rozpocząć czat.
                    </p>
                )}
            </div>
        </div>
    );
}

export default App;
