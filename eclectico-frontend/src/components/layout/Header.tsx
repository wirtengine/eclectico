import { useNavigate } from 'react-router-dom';
import { LogOut } from 'lucide-react';

export default function Header() {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <header className="h-16 bg-white border-b flex items-center justify-end px-6">
            <button
                onClick={logout}
                className="flex items-center gap-2 text-sm text-gray-600 hover:text-gray-900 transition-colors"
            >
                <LogOut className="h-4 w-4" />
                Cerrar sesión
            </button>
        </header>
    );
}