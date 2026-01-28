import { useState } from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const { login } = useAuth();
    const navigate = useNavigate();
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await login(email, password);
            navigate("/"); // Ir al lobby
        } catch (err) {
            setError("Credenciales inválidas");
        }
    };

    return (
        <div className="flex h-screen items-center justify-center bg-brand-dark relative overflow-hidden font-sans">
            {/* Background Accents */}
            <div className="absolute top-10 left-10 w-72 h-72 bg-brand-primary/20 rounded-full blur-3xl animate-pulse"></div>
            <div className="absolute bottom-10 right-10 w-96 h-96 bg-brand-secondary/20 rounded-full blur-3xl animate-pulse delay-1000"></div>

            <div className="bg-brand-card/60 backdrop-blur-xl border border-gray-700 p-10 rounded-3xl shadow-2xl w-full max-w-sm relative z-10 transition-all hover:border-brand-primary/50">
                <h2 className="text-3xl font-bold text-center mb-8 bg-clip-text text-transparent bg-gradient-to-r from-brand-primary to-brand-secondary">
                    Welcome Back
                </h2>

                {error && (
                    <div className="bg-red-500/20 border border-red-500 text-red-200 p-3 rounded-lg mb-4 text-sm text-center">
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-6">
                    <div>
                        <label className="block text-gray-400 text-sm font-medium mb-1">Email</label>
                        <input
                            type="email"
                            className="w-full bg-brand-dark/50 border border-gray-600 rounded-xl px-4 py-3 text-white focus:outline-none focus:border-brand-primary focus:ring-1 focus:ring-brand-primary transition placeholder-gray-600"
                            placeholder="you@example.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-gray-400 text-sm font-medium mb-1">Password</label>
                        <input
                            type="password"
                            className="w-full bg-brand-dark/50 border border-gray-600 rounded-xl px-4 py-3 text-white focus:outline-none focus:border-brand-primary focus:ring-1 focus:ring-brand-primary transition placeholder-gray-600"
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-gradient-to-r from-brand-secondary to-brand-primary text-white font-bold py-3 rounded-xl hover:opacity-90 transition transform active:scale-95 shadow-lg shadow-brand-primary/25"
                    >
                        Sign In
                    </button>
                </form>

                <p className="mt-6 text-center text-gray-500 text-sm">
                    Don't have an account?{" "}
                    <button onClick={() => navigate('/register')} className="text-brand-accent hover:underline">
                        Sign Up
                    </button>
                </p>
            </div>
        </div>
    );
};
