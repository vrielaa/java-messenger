import React, { useEffect, useState } from 'react';

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
        <div>
            <h3>Użytkownicy online:</h3>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {users.map((user) => (
                    <li key={user.id}>
                        <button onClick={() => onUserSelect(user)}>{user.login}</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default UserList;
