import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // Tu backend Spring

// Interceptor to add correct token
axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, error => Promise.reject(error));

export const loginUser = async (email, password) => {
    // Asumiendo que el endpoint es /auth/login
    // Ajusta según tu backend real
    const response = await axios.post(`${API_URL}/auth/login`, { email, password });
    return response.data;
};

export const fetchAgoraToken = async (channelName, userId) => {
    // El token ahora se inyecta automáticamente por el interceptor
    const response = await axios.get(`${API_URL}/streaming/token`, {
        params: { channelName, userId }
    });
    return response.data.token;
};

export const createRoom = async (title, type = 'VIDEO') => {
    const response = await axios.post(`${API_URL}/rooms`, { title, type });
    return response.data;
};