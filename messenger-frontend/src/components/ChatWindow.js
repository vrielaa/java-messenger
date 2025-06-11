import React, { useEffect, useRef, useState } from 'react';
import './ChatWindow.css'; // pamiętaj o stylach!

function ChatWindow({ user, currentUser }) {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    };

    useEffect(() => {
        const fetchMessages = async () => {
            const res = await fetch(`http://localhost:8080/api/v1/message/${user.id}`, {
                credentials: 'include'
            });
            if (res.ok) {
                const data = await res.json();
                setMessages(data);
                scrollToBottom();
            }
        };
        fetchMessages();
    }, [user]);

    const handleSend = async (e) => {
        e.preventDefault();
        if (!newMessage.trim()) return;

        const payload = {
            receiver: user.id,
            publicKeyIdToEncryptedContentMap: {
                plain: newMessage
            }
        };

        const res = await fetch('http://localhost:8080/api/v1/message', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            setMessages(prev => [
                ...prev,
                {
                    senderId: currentUser.id,
                    receiverId: user.id,
                    publicKeyIdToEncryptedContentMap: { plain: newMessage }
                }
            ]);
            setNewMessage('');
            scrollToBottom();
        }
    };

    return (
        <div>
            <h3 style={{ color: "#2a5298" }}>Czat z {user.login}</h3>
            <div className="chat-messages">
                {messages.map((msg, i) => (
                    <div
                        key={i}
                        className={`message ${msg.senderId === currentUser.id ? 'sender' : 'receiver'}`}
                    >
                        {msg.publicKeyIdToEncryptedContentMap?.plain || '[brak tekstu]'}
                    </div>
                ))}
                <div ref={messagesEndRef} />
            </div>
            <form onSubmit={handleSend} style={{ display: 'flex', gap: '0.5rem', marginTop: '1rem' }}>
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    placeholder="Napisz wiadomość..."
                    style={{ flex: 1, padding: '0.5rem' }}
                />
                <button type="submit">Wyślij</button>
            </form>
        </div>
    );
}

export default ChatWindow;
