import React, { useState } from 'react';
import { useChat } from '../hooks/useChat';

export const ChatBox = ({ roomId, currentUser }) => {
    const { messages, sendMessage, isConnected } = useChat(roomId);
    const [inputText, setInputText] = useState("");

    const handleSend = () => {
        if (inputText.trim()) {
            sendMessage(currentUser, inputText, 'CHAT');
            setInputText("");
        }
    };

    const handleSendGift = () => {
        // Enviar un regalo es lo mismo que un mensaje, pero con type='GIFT'
        sendMessage(currentUser, "Ha enviado un 🦁 León!", 'GIFT');
    };

    return (
        <div className="fixed bottom-4 left-4 w-80 h-64 bg-black/60 rounded-lg p-2 flex flex-col text-white">
            {/* Header */}
            <div className="text-xs text-green-400 mb-2">
                Estado: {isConnected ? '🟢 En vivo' : '🔴 Desconectado'}
            </div>

            {/* Lista de Mensajes */}
            <div className="flex-1 overflow-y-auto mb-2 space-y-1">
                {messages.map((msg, index) => (
                    <div key={index} className={`text-sm ${msg.type === 'GIFT' ? 'text-yellow-400 font-bold' : ''}`}>
                        <span className="opacity-70">{msg.sender}: </span>
                        <span>{msg.content}</span>
                    </div>
                ))}
            </div>

            {/* Input y Botones */}
            <div className="flex gap-2">
                <input
                    className="flex-1 bg-gray-800 rounded px-2 text-sm"
                    value={inputText}
                    onChange={(e) => setInputText(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && handleSend()}
                    placeholder="Di algo..."
                />
                <button onClick={handleSend} className="bg-blue-600 px-3 rounded text-sm">Enviar</button>
                <button onClick={handleSendGift} className="bg-purple-600 px-3 rounded text-sm">🎁</button>
            </div>
        </div>
    );
};