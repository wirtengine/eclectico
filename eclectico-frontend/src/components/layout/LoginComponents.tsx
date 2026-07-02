import React from 'react';
import { ArrowRight } from 'lucide-react';
import logo from "@/assets/logo.jpeg";

// Componente de Estrella Retro SVG
const RetroStarSVG = ({ className, style }: { className?: string; style?: React.CSSProperties }) => (
    <svg
        viewBox="0 0 24 24"
        fill="currentColor"
        className={`w-7 h-7 ${className}`}
        style={style}
    >
        <path d="M12 0L14.6 9.4L24 12L14.6 14.6L12 24L9.4 14.6L0 12L9.4 9.4Z" />
    </svg>
);

// 1. Fondos Líquidos de Alta Intensidad Cromática (Cero Transparencias Pálidas)
export const BackgroundBlobs = () => (
    <div className="fixed inset-0 w-full h-full overflow-hidden pointer-events-none -z-10">
        {/* Celeste Intenso */}
        <div className="absolute top-[-5%] left-[-5%] w-[45vw] h-[45vw] rounded-full bg-eclectico-celeste/55 mix-blend-multiply filter blur-[75px] animate-vibrant-blob-1" />
        {/* Rosado Eléctrico */}
        <div className="absolute top-[10%] right-[-8%] w-[48vw] h-[48vw] rounded-full bg-eclectico-rosado/50 mix-blend-multiply filter blur-[85px] animate-vibrant-blob-2" />
        {/* Amarillo Oro Brillante */}
        <div className="absolute bottom-[-8%] left-[2%] w-[42vw] h-[42vw] rounded-full bg-eclectico-amarillo/60 mix-blend-multiply filter blur-[70px] animate-vibrant-blob-3" />
        {/* Naranja Encendido */}
        <div className="absolute bottom-[15%] right-[8%] w-[32vw] h-[32vw] rounded-full bg-eclectico-naranja/55 mix-blend-multiply filter blur-[75px] animate-vibrant-blob-1" style={{ animationDelay: '2s' }} />
        {/* Destello de Soporte */}
        <div className="absolute top-[35%] left-[15%] w-[28vw] h-[28vw] rounded-full bg-eclectico-rosado/35 mix-blend-multiply filter blur-[65px] animate-vibrant-blob-3" style={{ animationDelay: '4s' }} />
    </div>
);

// 2. Estrellas de Identidad con Brillo
export const FloatingStars = () => (
    <div className="absolute inset-0 pointer-events-none overflow-hidden z-0 select-none">
        <RetroStarSVG className="absolute top-[15%] left-[12%] text-eclectico-naranja animate-twinkle" />
        <RetroStarSVG className="absolute top-[35%] right-[18%] text-eclectico-rosado animate-twinkle" style={{ animationDelay: '0.8s' }} />
        <RetroStarSVG className="absolute bottom-[20%] left-[24%] text-eclectico-amarillo animate-twinkle" style={{ animationDelay: '1.6s' }} />
    </div>
);

// 3. Sección de Marca Ajustada Milimétricamente (TODO VISIBLE EN UNA SOLA MIRADA)
export const BrandSection = () => (
    <div className="hidden lg:flex flex-col justify-center items-center text-center w-full h-full z-10 px-6 xl:px-10 overflow-hidden">
        <div className="space-y-5 animate-float flex flex-col items-center w-full max-h-full py-2">

            <RetroStarSVG className="text-eclectico-naranja w-8 h-8 animate-twinkle" />

            {/* Rediseño de proporciones para asegurar que todo quepa perfectamente sin scroll */}
            <div className="w-[310px] xl:w-[410px] 2xl:w-[510px] transition-transform duration-500 ease-out hover:scale-[1.04] cursor-pointer select-none">
                <img
                    src={logo}
                    alt="Ecléctico Closet"
                    className="w-full h-auto shadow-[0_20px_50px_rgba(0,0,0,0.12)] rounded-[35px] border border-white/40"
                />
            </div>

            {/* Tipografía que no se esconde */}
            <div className="space-y-2 w-full">
                <h2 className="text-xl xl:text-2xl font-black tracking-[0.25em] uppercase text-eclectico-negro">
                    Boutique de moda
                </h2>
                <p className="text-sm xl:text-base tracking-[0.18em] text-eclectico-negro/90 font-bold">
                    Elegancia <span className="text-eclectico-naranja font-black">●</span> Estilo <span className="text-eclectico-rosado font-black">●</span> Exclusividad
                </p>
            </div>

            <div className="text-eclectico-amarillo text-xl animate-pulse">✨</div>
        </div>
    </div>
);

// 4. Inputs Premium Claros y Robustos
const LoginForm = ({ email, setEmail, password, setPassword, loading, onSubmit }: LoginCardProps) => (
    <form className="flex flex-col gap-6 w-full mt-8 relative z-10" onSubmit={onSubmit}>

        <div className="flex flex-col gap-1.5 w-full group">
            <label className="text-xs font-bold tracking-[0.15em] text-eclectico-negro/70 uppercase pl-0.5 group-focus-within:text-eclectico-naranja transition-colors duration-300">
                Correo electrónico
            </label>
            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full bg-transparent border-b-2 border-eclectico-negro/25 text-eclectico-negro font-bold py-2 px-0.5 outline-none focus:border-eclectico-naranja transition-colors duration-500 font-sans text-[15px]"
                required
            />
        </div>

        <div className="flex flex-col gap-1.5 w-full group">
            <label className="text-xs font-bold tracking-[0.15em] text-eclectico-negro/70 uppercase pl-0.5 group-focus-within:text-eclectico-naranja transition-colors duration-300">
                Contraseña
            </label>
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full bg-transparent border-b-2 border-eclectico-negro/25 text-eclectico-negro font-bold py-2 px-0.5 outline-none focus:border-eclectico-naranja transition-colors duration-500 font-sans text-[15px]"
                required
            />
        </div>

        <button
            type="submit"
            disabled={loading}
            className="mt-3 w-full flex items-center justify-center gap-2 bg-gradient-to-r from-eclectico-naranja to-eclectico-naranjaDark text-white font-bold text-base py-3.5 rounded-2xl shadow-[0_12px_28px_rgba(255,138,80,0.4)] hover:shadow-[0_18px_40px_rgba(255,138,80,0.6)] hover:scale-[1.02] active:scale-95 disabled:opacity-70 disabled:hover:scale-100 disabled:active:scale-100 transition-all duration-500 ease-in-out cursor-pointer"
        >
            {loading ? "Ingresando..." : "Ingresar"}
            {!loading && <ArrowRight size={18} className="animate-pulse" />}
        </button>
    </form>
);

interface LoginCardProps {
    email: string;
    setEmail: (val: string) => void;
    password: React.ComponentProps<'input'>['value'];
    setPassword: (val: string) => void;
    loading: boolean;
    onSubmit: (e: React.FormEvent) => void;
}

// 5. Tarjeta con Vidrio de Alto Contraste y Nitidez
export const LoginCard = (props: LoginCardProps) => (
    <div className="w-full max-w-[410px] mx-auto z-20 px-4 sm:px-0">

        {/* Logotipo para Móviles */}
        <div className="lg:hidden text-center mb-8 animate-float flex flex-col items-center gap-3">
            <img src={logo} alt="Ecléctico Closet" className="w-44 h-auto shadow-md rounded-3xl" />
            <h2 className="text-lg font-bold tracking-[0.2em] uppercase text-eclectico-negro">Boutique de moda</h2>
        </div>

        {/* Tarjeta con Borde de Vidrio Reforzado y Sombra Profunda */}
        <div className="bg-white/80 backdrop-blur-3xl ring-2 ring-white rounded-[38px] shadow-[0_40px_100px_rgba(0,0,0,0.18)] p-8 sm:p-10 w-full relative overflow-hidden">
            <div className="absolute top-0 right-0 w-32 h-32 bg-white/50 rounded-full blur-xl -translate-y-1/2 translate-x-1/2" />

            <div className="text-left space-y-1 relative z-10">
                <h2 className="text-2xl sm:text-3xl font-black tracking-tight text-eclectico-negro">
                    Bienvenida de nuevo
                </h2>
                <p className="text-[13px] text-eclectico-negro/70 font-semibold">
                    Inicia sesión para administrar tu tienda.
                </p>
            </div>
            <LoginForm {...props} />
        </div>
    </div>
);