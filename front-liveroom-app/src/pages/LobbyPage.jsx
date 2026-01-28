import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createRoom } from '../services/api';

export const LobbyPage = () => {
    const navigate = useNavigate();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [title, setTitle] = useState('');
    const [loading, setLoading] = useState(false);

    const handleCreateRoom = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            // "VIDEO" is default
            await createRoom(title, 'VIDEO');
            // For now, simplistically navigate to room page or refresh list
            // In real app, we might get the room ID and go to /room/id
            navigate('/room');
        } catch (error) {
            console.error("Failed to create room", error);
            alert("Error creating room");
        } finally {
            setLoading(false);
            setIsModalOpen(false);
        }
    };

    return (
        <div className="min-h-screen bg-brand-dark text-white font-sans selection:bg-brand-primary selection:text-white">
            {/* Navbar simplified */}
            <nav className="p-6 flex justify-between items-center max-w-7xl mx-auto">
                <div className="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-brand-primary to-brand-secondary">
                    SoLive
                </div>
                <div className="flex gap-4">
                    <button
                        onClick={() => navigate('/login')}
                        className="px-4 py-2 text-sm text-gray-400 hover:text-white transition"
                    >
                        Log Out
                    </button>
                    <div className="w-10 h-10 rounded-full bg-brand-card flex items-center justify-center border border-gray-800">
                        👾
                    </div>
                </div>
            </nav>

            {/* Hero Section */}
            <main className="max-w-7xl mx-auto px-6 py-12 flex flex-col items-center text-center">
                <div className="inline-block px-4 py-1 rounded-full bg-brand-card border border-brand-secondary/30 text-brand-secondary text-sm font-medium mb-6">
                    🚀 Start Streaming Now
                </div>
                <h1 className="text-5xl md:text-7xl font-bold tracking-tight mb-6">
                    Connect. Stream. <br />
                    <span className="text-transparent bg-clip-text bg-gradient-to-r from-brand-primary to-brand-accent">
                        Go Viral.
                    </span>
                </h1>
                <p className="text-gray-400 text-lg md:text-xl max-w-2xl mb-10">
                    Join the next generation of live streaming. High quality, low latency, and built for community interaction.
                </p>

                <div className="flex gap-4">
                    <button
                        onClick={() => setIsModalOpen(true)}
                        className="px-8 py-4 bg-brand-primary hover:bg-pink-600 rounded-full font-bold text-lg shadow-lg shadow-brand-primary/25 transition transform hover:scale-105"
                    >
                        Create Room
                    </button>
                    <button
                        onClick={() => navigate('/room')}
                        className="px-8 py-4 bg-brand-card hover:bg-gray-800 border border-gray-700 rounded-full font-bold text-lg transition"
                    >
                        Join Random
                    </button>
                </div>
            </main>

            {/* Modal */}
            {isModalOpen && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">
                    <div className="bg-brand-card border border-gray-700 p-8 rounded-2xl w-full max-w-md shadow-2xl relative">
                        <button
                            onClick={() => setIsModalOpen(false)}
                            className="absolute top-4 right-4 text-gray-400 hover:text-white"
                        >
                            ✕
                        </button>
                        <h2 className="text-2xl font-bold mb-6">New Stream</h2>
                        <form onSubmit={handleCreateRoom} className="space-y-4">
                            <div>
                                <label className="block text-sm text-gray-400 mb-1">Room Title</label>
                                <input
                                    type="text"
                                    value={title}
                                    onChange={(e) => setTitle(e.target.value)}
                                    className="w-full bg-brand-dark border border-gray-700 rounded-xl px-4 py-3 focus:outline-none focus:border-brand-primary transition"
                                    placeholder="Ex: Chill Vibez 🎵"
                                    required
                                />
                            </div>
                            <button
                                type="submit"
                                disabled={loading}
                                className="w-full py-3 bg-gradient-to-r from-brand-secondary to-brand-primary rounded-xl font-bold text-lg hover:opacity-90 transition disabled:opacity-50"
                            >
                                {loading ? 'Creating...' : 'Go Live 🔴'}
                            </button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};
