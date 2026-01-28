import {
    LocalUser,
    RemoteUser,
    useJoin,
    useLocalMicrophoneTrack,
    useLocalCameraTrack,
    usePublish,
    useRemoteUsers,
    AgoraRTCProvider
} from "agora-rtc-react";
import AgoraRTC from "agora-rtc-sdk-ng";
import { useState, useEffect, useMemo } from "react";
import { fetchAgoraToken } from "../services/api";
import { ChatBox } from "../components/ChatBox";

export const RoomPage = () => {
    // In a real app, these would come from URL params or Context
    const appId = "tu_app_id_publico";
    const channel = "SalaDePrueba";
    const userId = "2";
    const [token, setToken] = useState("");

    const client = useMemo(() => AgoraRTC.createClient({ mode: "rtc", codec: "vp8" }), []);

    useEffect(() => {
        fetchAgoraToken(channel, userId)
            .then(t => setToken(t))
            .catch(err => {
                console.error("Error fetching Agora token:", err);
            });
    }, []);

    if (!token) return (
        <div className="flex h-screen items-center justify-center bg-brand-dark space-y-4 flex-col">
            <div className="w-12 h-12 border-4 border-brand-primary border-t-transparent rounded-full animate-spin"></div>
            <p className="text-gray-400 animate-pulse">Connecting to studio...</p>
        </div>
    );

    return (
        <div className="relative w-full h-screen bg-brand-dark overflow-hidden">
            {/* VIDEO LAYER */}
            <div className="absolute inset-0 z-0">
                <AgoraRTCProvider client={client}>
                    <AgoraManager appId={appId} channel={channel} token={token} uid={userId} />
                </AgoraRTCProvider>
            </div>

            {/* UI LAYER - Gradient Overlay at bottom for readable chat */}
            <div className="absolute inset-x-0 bottom-0 h-1/2 bg-gradient-to-t from-black/80 to-transparent z-10 pointer-events-none"></div>

            {/* INTERACTIVE UI */}
            <div className="absolute inset-0 z-20 pointer-events-none flex flex-col justify-end p-4 md:p-6">
                <div className="pointer-events-auto w-full max-w-sm">
                    <ChatBox roomId={channel} currentUser="Julio" />
                </div>
            </div>
        </div>
    );
};

// --- Agora Component ---
const AgoraManager = ({ appId, channel, token, uid }) => {
    useJoin({ appid: appId, channel: channel, token: token, uid: uid }, true);

    const { localMicrophoneTrack } = useLocalMicrophoneTrack(true);
    const { localCameraTrack } = useLocalCameraTrack(true);
    usePublish([localMicrophoneTrack, localCameraTrack]);
    const remoteUsers = useRemoteUsers();

    return (
        <div className="w-full h-full bg-brand-dark">
            {remoteUsers.length > 0 ? (
                <div className="w-full h-full relative">
                    {/* Main Remote View */}
                    <RemoteUser
                        user={remoteUsers[0]}
                        style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                    />

                    {/* Floating Local View (PIP) */}
                    <div className="absolute top-6 right-6 w-32 h-48 md:w-40 md:h-60 rounded-2xl overflow-hidden shadow-2xl border-2 border-white/20 z-30 transform hover:scale-105 transition duration-300">
                        <LocalUser
                            cameraTrack={localCameraTrack}
                            cameraOn={true}
                            micOn={true}
                            videoTrack={localCameraTrack}
                            style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                        />
                    </div>
                </div>
            ) : (
                // Fullscreen Self View
                <LocalUser
                    cameraTrack={localCameraTrack}
                    cameraOn={true}
                    micOn={true}
                    videoTrack={localCameraTrack}
                    style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                />
            )}
        </div>
    );
};