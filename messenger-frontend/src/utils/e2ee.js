export async function encryptWithDerKey(base64PubKey, message) {
    // Convert base64 to ArrayBuffer (DER)
    function base64ToArrayBuffer(base64) {
        const binary = atob(base64);
        const bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) {
            bytes[i] = binary.charCodeAt(i);
        }
        return bytes.buffer;
    }

    // Import public key (SPKI format)
    const publicKey = await crypto.subtle.importKey(
        "spki",
        base64ToArrayBuffer(base64PubKey),
        {
            name: "RSA-OAEP",
            hash: "SHA-256"
        },
        false,
        ["encrypt"]
    );

    // Encode message to Uint8
    const encoder = new TextEncoder();
    const encodedMsg = encoder.encode(message);

    // Encrypt message
    const encrypted = await crypto.subtle.encrypt(
        { name: "RSA-OAEP" },
        publicKey,
        encodedMsg
    );

    // Return encrypted message as base64 string
    const encryptedBase64 = btoa(String.fromCharCode(...new Uint8Array(encrypted)));

    return encryptedBase64;
}
export async function decryptWithDerKey(base64PrivKey, base64EncryptedMsg) {
    // Utility to convert base64 to ArrayBuffer
    function base64ToArrayBuffer(base64) {
        const binary = atob(base64);
        const bytes = new Uint8Array(binary.length);
        for (let i = 0; i < binary.length; i++) {
            bytes[i] = binary.charCodeAt(i);
        }
        return bytes.buffer;
    }

    // Import private key (PKCS#8 DER)
    const privateKey = await crypto.subtle.importKey(
        "pkcs8",
        base64ToArrayBuffer(base64PrivKey),
        {
            name: "RSA-OAEP",
            hash: "SHA-256"
        },
        false,
        ["decrypt"]
    );

    // Decode encrypted message from base64
    const encryptedBytes = base64ToArrayBuffer(base64EncryptedMsg);

    // Decrypt
    const decryptedBuffer = await crypto.subtle.decrypt(
        { name: "RSA-OAEP" },
        privateKey,
        encryptedBytes
    );

    // Convert ArrayBuffer to string
    const decoder = new TextDecoder();
    return decoder.decode(decryptedBuffer);
}
export const getE2eeKeysForUser = user => {
    if (localStorage.getItem("e2ee") === null) {
        localStorage.setItem("e2ee", "{}");
    }
    const keys = JSON.parse(localStorage.getItem("e2ee"))[user.id];
    return keys === undefined ? {} : keys;
}
const addKeyForUser = (user, keyObj) => {
    const keys = JSON.parse(localStorage.getItem("e2ee"));
    if (keys[user.id] === undefined) {
        keys[user.id] = {};
    }
    keys[user.id][keyObj.id] = keyObj;
    localStorage.setItem("e2ee", JSON.stringify(keys));
}

async function createNewKeyObj(user) {
    // Generate RSA keypair
    const keyPair = await window.crypto.subtle.generateKey(
        {
            name: "RSA-OAEP",
            modulusLength: 2048,
            publicExponent: new Uint8Array([1, 0, 1]),
            hash: "SHA-256"
        },
        true, // extractable
        ["encrypt", "decrypt"]
    );

    // Export keys as DER (raw ArrayBuffer)
    const publicKeyDer = await window.crypto.subtle.exportKey("spki", keyPair.publicKey);
    const privateKeyDer = await window.crypto.subtle.exportKey("pkcs8", keyPair.privateKey);

    // Convert DER ArrayBuffers to Base64
    function toBase64(buffer) {
        const bytes = new Uint8Array(buffer);
        let binary = '';
        for (let b of bytes) binary += String.fromCharCode(b);
        return btoa(binary);
    }

    const keypairJson = {
        pub: toBase64(publicKeyDer),
        priv: toBase64(privateKeyDer)
    };
    return keypairJson;
}

const sendKeyToBackend = async (keyObj) => {
    console.log(keyObj.pub.length);
    const response = await fetch('http://localhost:8080/api/v1/public-key', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({"base64Der": keyObj.pub}),
    });
    if (response.ok) {
        return (await response.json()).id;
    }
    return undefined;
};
const enrollNewKeyForUser = async (user) => {
    const keyObj = await createNewKeyObj(user);
    const id = await sendKeyToBackend(keyObj);
    if (id !== undefined) {
        keyObj.id = id;
        addKeyForUser(user, keyObj);
    } else {
        alert("Something's gone wrong, perhaps try relogging again");
    }

};

export async function ensureE2eeKeysGeneratedAndOnServer(user) {
    if (Object.keys(getE2eeKeysForUser(user)).length === 0) {
        await enrollNewKeyForUser(user);
    }
}