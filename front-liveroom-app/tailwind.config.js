/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                brand: {
                    dark: '#FF0080',      // Main background (User requested Pink)
                    card: '#151A23',      // Card background
                    primary: '#FF0080',   // Neon Pink
                    secondary: '#7928CA', // Violet/Purple
                    accent: '#06b6d4',    // Cyan/Blue
                }
            }
        },
    },
    plugins: [],
}
