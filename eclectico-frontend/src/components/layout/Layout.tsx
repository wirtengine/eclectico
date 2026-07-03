import { useState } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import Sidebar from './Sidebar';
import Header from './Header';

export default function Layout() {
    const token = localStorage.getItem('token');
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
    const [isDesktopCollapsed, setIsDesktopCollapsed] = useState(false);

    if (!token) return <Navigate to="/login" />;

    return (
        <div className="flex h-screen overflow-hidden bg-eclectico-crema font-sans text-eclectico-negro relative selection:bg-eclectico-naranja/20">

            {/* Capa oscura elegante para el celular */}
            {isMobileMenuOpen && (
                <div
                    className="fixed inset-0 bg-eclectico-negro/20 backdrop-blur-sm z-40 md:hidden transition-all duration-500"
                    onClick={() => setIsMobileMenuOpen(false)}
                />
            )}

            <Sidebar
                isMobileOpen={isMobileMenuOpen}
                setIsMobileOpen={setIsMobileMenuOpen}
                isDesktopCollapsed={isDesktopCollapsed}
            />

            <div className="flex-1 flex flex-col w-full min-w-0 transition-all duration-500 ease-[cubic-bezier(0.2,0.8,0.2,1)]">
                <Header
                    onMenuClick={() => setIsMobileMenuOpen(true)}
                    isDesktopCollapsed={isDesktopCollapsed}
                    toggleDesktopMenu={() => setIsDesktopCollapsed(!isDesktopCollapsed)}
                />

                {/* Contenedor principal con animación de entrada */}
                <main className="flex-1 overflow-y-auto p-4 sm:p-6 lg:p-8 custom-scrollbar">
                    <div className="mx-auto max-w-7xl h-full animate-in fade-in slide-in-from-bottom-4 duration-700">
                        <Outlet />
                    </div>
                </main>
            </div>
        </div>
    );
}