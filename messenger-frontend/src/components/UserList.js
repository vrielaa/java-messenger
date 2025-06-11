// src/components/UserList.js
import React, { useEffect, useState } from 'react';
import './UserList.css';

function UserList({ onUserSelect }) {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const res = await fetch('http://localhost:8080/api/v1/user/online', {
                    method: 'GET',
                    credentials: 'include'
                });

                if (res.ok) {
                    const data = await res.json();
                    setUsers(data);
                } else {
                    setError('Nie udało się pobrać listy użytkowników');
                }
            } catch (err) {
                setError('Błąd połączenia z serwerem');
            }
        };

        fetchUsers();
    }, []);

    return (
        <div className="userlist-container">
            <h3 className="userlist-title">Użytkownicy online</h3>
            {error && <p className="userlist-error">{error}</p>}
            <ul className="userlist-list">
                {users.map((user) => (
                    <li key={user.id} className="userlist-item">
                        <button className="userlist-button" onClick={() => onUserSelect(user)}>
                            {user.login}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default UserList;
