import { NavLink } from 'react-router-dom';
import { Package, ShoppingCart, BarChart3, LayoutDashboard } from 'lucide-react';

const links = [
    { to: '/', icon: LayoutDashboard, label: 'Dashboard' },
    { to: '/productos', icon: Package, label: 'Productos' },
    { to: '/ventas', icon: ShoppingCart, label: 'Ventas' },
    { to: '/reportes', icon: BarChart3, label: 'Reportes' },
];

export default function Sidebar() {
    return (
        <aside className="w-64 bg-white border-r hidden md:block">
            <div className="h-16 flex items-center px-6 font-bold text-lg text-emerald-700">
                Ecléctico
            </div>
            <nav className="mt-6 space-y-1 px-3">
                {links.map((link) => (
                    <NavLink
                        key={link.to}
                        to={link.to}
                        end={link.to === '/'}
                        className={({ isActive }) =>
                            `flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors ${
                                isActive
                                    ? 'bg-emerald-50 text-emerald-700'
                                    : 'text-gray-600 hover:bg-gray-100 hover:text-gray-900'
                            }`
                        }
                    >
                        <link.icon className="h-5 w-5" />
                        {link.label}
                    </NavLink>
                ))}
            </nav>
        </aside>
    );
}