import { NavLink } from 'react-router-dom';
import { Package, ShoppingCart, BarChart3, LayoutDashboard, X } from 'lucide-react';

const links = [
    { to: '/', icon: LayoutDashboard, label: 'Dashboard' },
    { to: '/productos', icon: Package, label: 'Productos' },
    { to: '/ventas', icon: ShoppingCart, label: 'Ventas' },
    { to: '/reportes', icon: BarChart3, label: 'Reportes' },
];

interface SidebarProps {
    isMobileOpen: boolean;
    setIsMobileOpen: (val: boolean) => void;
    isDesktopCollapsed: boolean;
}

export default function Sidebar({ isMobileOpen, setIsMobileOpen, isDesktopCollapsed }: SidebarProps) {
    return (
        <aside
            className={`
                fixed inset-y-0 left-0 z-50 bg-white/95 backdrop-blur-3xl shadow-[15px_0_50px_rgba(59,56,56,0.04)] 
                transform transition-all duration-500 ease-[cubic-bezier(0.2,0.8,0.2,1)]
                flex flex-col border-r border-eclectico-negro/5
                md:relative md:translate-x-0
                ${isMobileOpen ? 'translate-x-0 w-72' : '-translate-x-full w-72'}
                ${isDesktopCollapsed ? 'md:w-[88px]' : 'md:w-72'}
            `}
        >
            {/* Header del Sidebar */}
            <div className="h-[76px] flex items-center justify-between px-6 border-b border-eclectico-negro/5 flex-shrink-0 relative overflow-hidden">

                {/* Logo Expandido */}
                <div className={`flex flex-col whitespace-nowrap transition-all duration-500 absolute left-6 ${isDesktopCollapsed ? 'md:opacity-0 md:-translate-x-10' : 'opacity-100 translate-x-0'}`}>
                    <span className="font-black text-2xl tracking-widest text-eclectico-negro">
                        ECLÉCTICO
                    </span>
                    <span className="text-[10px] tracking-[0.3em] font-bold text-eclectico-naranja uppercase -mt-1">
                        Closet
                    </span>
                </div>

                {/* Logo Colapsado (Solo la 'E' brillante) */}
                <div className={`hidden md:flex flex-col items-center justify-center absolute inset-0 transition-all duration-500 ${isDesktopCollapsed ? 'opacity-100 scale-100' : 'opacity-0 scale-50 pointer-events-none'}`}>
                    <span className="font-black text-3xl text-eclectico-naranja drop-shadow-[0_2px_10px_rgba(240,172,116,0.5)]">
                        E
                    </span>
                </div>

                {/* Cerrar menú en móvil */}
                <button
                    onClick={() => setIsMobileOpen(false)}
                    className="p-2 -mr-2 rounded-xl text-eclectico-negro/40 hover:bg-eclectico-negro/5 hover:text-eclectico-negro transition-colors md:hidden ml-auto z-10"
                >
                    <X className="h-6 w-6" />
                </button>
            </div>

            {/* Navegación */}
            <nav className="flex-1 mt-6 space-y-2 px-3 overflow-y-auto overflow-x-hidden custom-scrollbar">
                {links.map((link) => (
                    <NavLink
                        key={link.to}
                        to={link.to}
                        title={isDesktopCollapsed ? link.label : ""}
                        onClick={() => setIsMobileOpen(false)}
                        className={({ isActive }) => `
                            flex items-center rounded-2xl px-3 py-3.5 text-sm font-bold transition-all duration-400 group relative
                            ${isActive
                            ? 'bg-eclectico-naranja/15 text-eclectico-naranja shadow-sm ring-1 ring-eclectico-naranja/10'
                            : 'text-eclectico-negro/50 hover:bg-eclectico-crema hover:text-eclectico-negro'
                        }
                            ${isDesktopCollapsed ? 'md:justify-center hover:md:scale-110' : 'hover:translate-x-1'}
                        `}
                    >
                        <div className="flex items-center justify-center min-w-[28px]">
                            <link.icon className="h-[22px] w-[22px] stroke-[2.2]" />
                        </div>
                        <span className={`ml-3.5 tracking-wide whitespace-nowrap transition-all duration-500 ${isDesktopCollapsed ? 'md:opacity-0 md:w-0 md:ml-0 md:invisible' : 'opacity-100 w-auto visible'}`}>
                            {link.label}
                        </span>
                    </NavLink>
                ))}
            </nav>

            {/* Caja Decorativa Inferior (Se oculta al colapsar) */}
            <div className={`p-4 transition-all duration-500 flex-shrink-0 ${isDesktopCollapsed ? 'md:opacity-0 md:h-0 md:p-0' : 'opacity-100 h-auto'}`}>
                <div className="bg-eclectico-crema rounded-2xl p-4 text-center border border-eclectico-naranja/10 shadow-inner">
                    <p className="text-xs text-eclectico-negro/50 font-medium">Boutique de Moda</p>
                    <p className="text-sm font-bold text-eclectico-naranja mt-1">Elegancia & Estilo</p>
                </div>
            </div>
        </aside>
    );
}