import { useNavigate } from 'react-router-dom';
import { LogOut, Menu, PanelLeftClose, PanelLeftOpen } from 'lucide-react';

interface HeaderProps {
    onMenuClick: () => void;
    isDesktopCollapsed: boolean;
    toggleDesktopMenu: () => void;
}

export default function Header({ onMenuClick, isDesktopCollapsed, toggleDesktopMenu }: HeaderProps) {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <header className="h-[76px] bg-white/60 backdrop-blur-2xl border-b border-eclectico-negro/5 flex items-center justify-between px-4 sm:px-8 sticky top-0 z-30 shadow-[0_10px_40px_rgba(59,56,56,0.02)] transition-all duration-500">

            <div className="flex items-center gap-4">
                {/* Botón menú hamburguesa (Solo Móvil) */}
                <button
                    onClick={onMenuClick}
                    className="p-2 -ml-2 rounded-xl text-eclectico-negro/60 hover:bg-eclectico-naranja/10 hover:text-eclectico-naranja transition-colors md:hidden"
                >
                    <Menu className="h-6 w-6" />
                </button>

                {/* Botón colapsar menú (Solo Escritorio) */}
                <button
                    onClick={toggleDesktopMenu}
                    className="hidden md:flex p-2.5 -ml-3 rounded-xl text-eclectico-negro/40 hover:bg-eclectico-naranja/10 hover:text-eclectico-naranja transition-all duration-300"
                    title={isDesktopCollapsed ? "Expandir menú" : "Ocultar menú"}
                >
                    {isDesktopCollapsed ? (
                        <PanelLeftOpen className="h-5 w-5" />
                    ) : (
                        <PanelLeftClose className="h-5 w-5" />
                    )}
                </button>
            </div>

            {/* Botón Salir Estilizado */}
            <button
                onClick={logout}
                className="flex items-center gap-2.5 px-5 py-2.5 rounded-2xl text-sm font-bold text-eclectico-negro/60 bg-white shadow-sm ring-1 ring-eclectico-negro/5 hover:text-white hover:bg-eclectico-naranja hover:ring-eclectico-naranja hover:shadow-[0_8px_20px_rgba(240,172,116,0.3)] transition-all duration-400 group"
            >
                <span>Salir</span>
                <LogOut className="h-4 w-4 group-hover:translate-x-0.5 transition-transform" />
            </button>
        </header>
    );
}