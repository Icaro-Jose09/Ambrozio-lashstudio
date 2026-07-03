/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Nossa paleta premium inspirada em onça-pintada
        ambrozio: {
          beige: '#F5F5DC',
          brown: '#8B4513',
          dark: '#1A1A1A',
          gold: '#FFD700',
        }
      }
    },
  },
  plugins: [],
}