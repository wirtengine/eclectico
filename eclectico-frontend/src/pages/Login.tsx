import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import api from "@/lib/axios";
import { toast } from "sonner";
import { BackgroundBlobs, FloatingStars, BrandSection, LoginCard } from "@/components/layout/LoginComponents";

export default function Login() {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);

        try {
            const { data } = await api.post("/auth/login", { email, password });
            localStorage.setItem("token", data.token);
            toast.success("Bienvenida ❤️");
            navigate("/");
        } catch (error) {
            if (axios.isAxiosError(error) && error.response?.data?.error) {
                toast.error(error.response.data.error);
            } else {
                toast.error("Credenciales inválidas");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <main className="w-full h-screen relative flex items-center justify-center p-4 sm:p-8 overflow-hidden bg-eclectico-crema">
            <BackgroundBlobs />
            <FloatingStars />

            <div className="w-full max-w-[1300px] h-full lg:h-[85vh] grid grid-cols-1 lg:grid-cols-2 gap-4 relative z-10 items-center">
                <BrandSection />
                <div className="flex items-center justify-center w-full h-full">
                    <LoginCard
                        email={email} setEmail={setEmail}
                        password={password} setPassword={setPassword}
                        loading={loading} onSubmit={handleSubmit}
                    />
                </div>
            </div>
        </main>
    );
}