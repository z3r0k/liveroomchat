import { useState, useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const SOCKET_URL = 'http://localhost:8080/ws'; // Tu endpoint en Spring Boot

export const useChat = (roomId) => {
    const [messages, setMessages] = useState([]);
    const [isConnected, setIsConnected] = useState(false);
    const clientRef = useRef(null);

    useEffect(() => {
        // 1. Configuración del Cliente STOMP
        const client = new Client({
            webSocketFactory: () => new SockJS(SOCKET_URL),
            onConnect: () => {
                console.log('Conectado al WebSocket!');
                setIsConnected(true);

                // 2. Suscribirse a la sala específica
                // Cuando llegue un mensaje del servidor, se ejecuta el callback
                client.subscribe(`/topic/room/${roomId}`, (message) => {
                    const newMessage = JSON.parse(message.body);
                    setMessages((prev) => [...prev, newMessage]);
                });
            },
            onDisconnect: () => {
                setIsConnected(false);
                console.log('Desconectado');
            },
            // Auto-reconexión en caso de caída (Vital para live streaming)
            reconnectDelay: 5000,
        });

        // 3. Activar la conexión
        client.activate();
        clientRef.current = client;

        // Cleanup al desmontar el componente (salir de la sala)
        return () => {
            client.deactivate();
        };
    }, [roomId]);

    // Función para enviar mensajes (Texto o Regalos)
    const sendMessage = (user, content, type = 'CHAT') => {
        if (clientRef.current && isConnected) {
            const payload = {
                sender: user,
                content: content,
                type: type, // 'CHAT', 'GIFT', 'JOIN', etc.
                roomId: roomId
            };

            // Enviamos al destino que Spring Boot está escuchando (@MessageMapping)
            clientRef.current.publish({
                destination: "/app/chat",
                body: JSON.stringify(payload)
            });
        }
    };

    return { messages, sendMessage, isConnected };
};