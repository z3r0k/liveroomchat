export const ChatBox = ({ roomId, currentUser }) => {
    return (
        <div className="bg-black/50 p-4 rounded-lg text-white">
            <h3 className="font-bold mb-2">Chat Room: {roomId}</h3>
            <div className="h-48 overflow-y-auto mb-2 bg-black/30 p-2 rounded">
                <p className="text-gray-400 italic">Welcome to the chat!</p>
            </div>
            <input
                type="text"
                placeholder="Type a message..."
                className="w-full bg-transparent border-b border-white outline-none px-2 py-1"
            />
        </div>
    );
};
