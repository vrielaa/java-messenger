import React, {useEffect, useRef, useState} from 'react';
import './ChatWindow.css';
import {Client} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {decryptWithDerKey, encryptWithDerKey, getE2eeKeysForUser} from "../utils/e2ee";

function ChatWindow({ user, currentUser }) {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const [keys, setKeys] = useState({});
    const messagesEndRef = useRef(null);
    const stompClientRef = useRef(null);
    const msgsRef = useRef(null);

    useEffect(() => {
        if (messagesEndRef.current) {
            messagesEndRef.current.scrollTop = messagesEndRef.current.scrollHeight;
        }
    }, [messages]);

    useEffect(() => {
        msgsRef.current = messages;
    }, [messages]);

    const decodeMessage = async messageEntity => {
        const localKeys = getE2eeKeysForUser(currentUser);
        const remoteKeys = keys[currentUser.id];
        const key = (() => {
            for (const remoteKeyId in messageEntity.publicKeyIdToEncryptedContentMap) {
                for (const localKeyId in localKeys) {
                    if (remoteKeyId === localKeyId) {
                        return localKeys[localKeyId];
                    }
                }
            }
            return undefined;
        })();
        if (key === undefined) {
            messageEntity.decoded = "can't decode";
            return messageEntity;
        }
        let decoded = messageEntity.publicKeyIdToEncryptedContentMap[key.id];
        messageEntity.decoded = await decryptWithDerKey(key.priv, decoded);
        return messageEntity;
    };

    const addMessage = async messageEntity => {
        setMessages([...msgsRef.current, await decodeMessage(messageEntity)]);
    };
    const encrypt = async (message) => {
        return await Promise.all([currentUser.id, user.id]
            .flatMap(id => keys[id])
            .map(async key => {
                console.log(key);
                const encrypted = await encryptWithDerKey(key.publicKeyBase64Der, message);
                return [key.id, encrypted];
            })).then(x => {
            let fromEntries = Object.fromEntries(x);
            console.log(fromEntries)
            return fromEntries;
        });
    };


    useEffect(() => {
        const fetchForOne = user  => {
            fetch("http://localhost:8080/api/v1/public-key/" + user.id, {
                method: "GET",
                credentials: "include"
            })
                .then(result => {
                    result.json().then(json => {
                        const newKeys = keys;
                        newKeys[user.id] = json;
                        setKeys(newKeys);
                    })
                })
        };
        setKeys([]);
        fetchForOne(user);
        fetchForOne(currentUser);

    }, [currentUser, user]);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
        let list = document.getElementsByClassName("chat-messages");
        list.scrollTop = list.scrollHeight;
    };

    useEffect(() => {
        const fetchMessages = async () => {
            const res = await fetch(`http://localhost:8080/api/v1/message/${user.id}`, {
                credentials: 'include'
            });
            if (res.ok) {
                const data = await res.json();
                Promise.all(data.map(msg => decodeMessage(msg)))
                    .then(setMessages);
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
            publicKeyIdToEncryptedContentMap: await encrypt(newMessage)
        };

        const res = await fetch('http://localhost:8080/api/v1/message', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            setNewMessage('');
        }
    };

    useEffect(() => {
        const stompClient = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            debug: (str) => {
                console.log("[STOMP DEBUG]", str);
            },

            onConnect: (frame) => {
                console.log(frame);
                // Subscribe to user-specific topic
                stompClient.subscribe(`/topic/messages/${currentUser.id}`, (message) => {
                    decodeMessage(JSON.parse(message.body))
                        .then(msg => {
                            setMessages([...msgsRef.current, msg]);
                        });
                });
                scrollToBottom();
            },

            onDisconnect: (frame) => {
                // frame: STOMP frame object of disconnect
            },

            onStompError: (frame) => {
                // frame: Error frame returned from broker
            },

            onWebSocketClose: (event) => {
                // event: WebSocket close event
            },

            onWebSocketError: (event) => {
                // event: WebSocket error event
            },
        });

        stompClient.activate();
        stompClientRef.current = stompClient;

        return () => {
            if (stompClientRef.current) {
                stompClientRef.current.deactivate();
            }
        };
    }, [currentUser]); // Re-run effect if userId changes


    return (
        <div>
            <h3 style={{color: "#12782b"}}>Czat z {user.login}</h3>
            <div className="chat-messages" ref={messagesEndRef}>
                {messages.map((msg, i) => (
                    <div
                        key={i}
                        className={`message ${msg.senderId === currentUser.id ? 'sender' : 'receiver'}`}
                    >
                        {msg?.decoded || '[brak tekstu]'}
                    </div>
                ))}
            </div>
            <form className="chat-form" onSubmit={handleSend}>
                <input
                    className="chat-input"
                    type="text"
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    placeholder="Napisz wiadomość..."
                />
                <button className="chat-send-btn" type="submit">Wyślij</button>
            </form>
        </div>
    );
}

export default ChatWindow;
