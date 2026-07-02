/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            fontFamily: {
                sans: ['Outfit', 'Poppins', 'sans-serif'],
            },
            colors: {
                eclectico: {
                    crema: '#FDF6ED',
                    naranja: '#F2A56B',
                    naranjaDark: '#ED9550',
                    celeste: '#9AEAF6',
                    rosado: '#E89AD5',
                    amarillo: '#F7E86A',
                    negro: '#2F2F2F',
                }
            },
            animation: {
                blob: 'blob 10s infinite',
                float: 'float 6s ease-in-out infinite',
            },
            keyframes: {
                blob: {
                    '0%': { transform: 'translate(0px, 0px) scale(1)' },
                    '33%': { transform: 'translate(40px, -60px) scale(1.1)' },
                    '66%': { transform: 'translate(-30px, 30px) scale(0.9)' },
                    '100%': { transform: 'translate(0px, 0px) scale(1)' },
                },
                float: {
                    '0%, 100%': { transform: 'translateY(0)' },
                    '50%': { transform: 'translateY(-15px)' },
                }
            }
        },
    },
    plugins: [],
}