import React from 'react';
import { ArrowRight } from 'lucide-react';
import logo from "@/assets/logo.jpeg";

const RetroStarSVG = ({ className, style }: { className?: string; style?: React.CSSProperties }) => (
    <svg viewBox="0 0 24 24" fill="currentColor" className={`w-7 h-7 ${className}`} style={style}>
        <path d="M12 0L14.6 9.4L24 12L14.6 14.6L12 24L9.4 14.6L0 12L9.4 9.4Z" />
    </svg>
);

// 1. Fondos Acuarela con tus colores exactos
export const BackgroundBlobs = () => (
    <div className="fixed inset-0 w-full h-full overflow-hidden pointer-events-none -z-10 bg-eclectico-crema">
        <div className="absolute top-[-10%] left-[-5%] w-[55vw] h-[55vw] rounded-full bg-eclectico-celeste/70 mix-blend-multiply filter blur-[90px] animate-vibrant-blob-1" />
        <div className="absolute top-[10%] right-[-10%] w-[50vw] h-[50vw] rounded-full bg-eclectico-rosado/60 mix-blend-multiply filter blur-[100px] animate-vibrant-blob-2" />
        <div className="absolute bottom-[-10%] left-[5%] w-[45vw] h-[45vw] rounded-full bg-eclectico-amarillo/60 mix-blend-multiply filter blur-[80px] animate-vibrant-blob-3" />
        <div className="absolute bottom-[10%] right-[5%] w-[40vw] h-[40vw] rounded-full bg-eclectico-naranja/40 mix-blend-multiply filter blur-[90px] animate-vibrant-blob-1" style={{ animationDelay: '2s' }} />
        <div className="absolute top-[40%] left-[25%] w-[35vw] h-[35vw] rounded-full bg-eclectico-coral/50 mix-blend-multiply filter blur-[85px] animate-vibrant-blob-2" style={{ animationDelay: '4s' }} />
    </div>
);

export const FloatingStars = () => (
    <div className="absolute inset-0 pointer-events-none overflow-hidden z-0 select-none">
        <RetroStarSVG className="absolute top-[18%] left-[14%] text-eclectico-naranja animate-twinkle" />
        <RetroStarSVG className="absolute top-[32%] right-[16%] text-eclectico-celeste animate-twinkle" style={{ animationDelay: '1s' }} />
        <RetroStarSVG className="absolute bottom-[22%] left-[22%] text-eclectico-rosado animate-twinkle" style={{ animationDelay: '2s' }} />
    </div>
);

export const BrandSection = () => (
    <div className="hidden lg:flex flex-col justify-center items-center text-center w-full h-full z-10 px-6 xl:px-10 overflow-hidden">
        <div className="space-y-6 animate-float flex flex-col items-center w-full">
            <RetroStarSVG className="text-eclectico-coral w-8 h-8 animate-twinkle" />

            <div className="w-[320px] xl:w-[420px] transition-transform duration-500 hover:scale-[1.03] cursor-pointer">
                <img src={logo} alt="Ecléctico Closet" className="w-full h-auto shadow-[0_15px_40px_rgba(59,56,56,0.08)] rounded-[35px]" />
            </div>

            <div className="space-y-2">
                <h2 className="text-xl xl:text-2xl font-black tracking-[0.25em] uppercase text-eclectico-negro">
                    Boutique de moda
                </h2>
                <p className="text-sm xl:text-base tracking-[0.18em] text-eclectico-negro/80 font-bold">
                    Elegancia <span className="text-eclectico-coral font-black">●</span> Estilo <span className="text-eclectico-celeste font-black">●</span> Exclusividad
                </p>
            </div>
            <div className="text-eclectico-amarillo text-xl animate-pulse">✨</div>
        </div>
    </div>
);

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
                className="w-full bg-transparent border-b-2 border-eclectico-negro/15 text-eclectico-negro font-bold py-2 px-0.5 outline-none focus:border-eclectico-naranja transition-colors duration-300 font-sans text-[15px]"
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
                className="w-full bg-transparent border-b-2 border-eclectico-negro/15 text-eclectico-negro font-bold py-2 px-0.5 outline-none focus:border-eclectico-naranja transition-colors duration-300 font-sans text-[15px]"
                required
            />
        </div>

        <button
            type="submit"
            disabled={loading}
            className="mt-4 w-full flex items-center justify-center gap-2 bg-eclectico-naranja text-white font-bold text-base py-3.5 rounded-2xl shadow-[0_10px_25px_rgba(240,172,116,0.35)] hover:bg-eclectico-naranjaDark hover:shadow-[0_15px_35px_rgba(240,172,116,0.5)] hover:scale-[1.02] active:scale-95 transition-all duration-300 cursor-pointer"
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

export const LoginCard = (props: LoginCardProps) => (
    <div className="w-full max-w-[400px] mx-auto z-20 px-4 sm:px-0">
        <div className="lg:hidden text-center mb-8 animate-float flex flex-col items-center gap-3">
            <img src={logo} alt="Ecléctico Closet" className="w-40 h-auto shadow-sm rounded-3xl" />
            <h2 className="text-lg font-bold tracking-[0.2em] uppercase text-eclectico-negro">Boutique de moda</h2>
        </div>

        <div className="bg-white/70 backdrop-blur-3xl ring-1 ring-white/60 rounded-[35px] shadow-[0_30px_80px_rgba(59,56,56,0.07)] p-8 sm:p-10 w-full relative overflow-hidden">
            <div className="absolute top-0 right-0 w-40 h-40 bg-eclectico-crema/40 rounded-full blur-2xl -translate-y-1/2 translate-x-1/2" />

            <div className="text-left space-y-1 relative z-10">
                <h2 className="text-2xl sm:text-3xl font-black tracking-tight text-eclectico-negro">
                    Bienvenida
                </h2>
                <p className="text-[13px] text-eclectico-negro/60 font-semibold">
                    Inicia sesión para administrar tu tienda.
                </p>
            </div>
            <LoginForm {...props} />
        </div>
    </div>
);