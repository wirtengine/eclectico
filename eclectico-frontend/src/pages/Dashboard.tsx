import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import {
    DollarSign,
    ShoppingCart,
    TrendingUp,
    Package,
    Zap,
    ArrowUpRight
} from 'lucide-react';
import {
    AreaChart,
    Area,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer
} from 'recharts';

// Tipos basados en tu especificación OpenAPI
interface KPI {
    semanaInicio: string;
    prendasVendidas: number;
    ingresos: number;
    costos: number;
    margenBruto: number;
    utilidadNeta: number;
}

interface Oferta {
    idProducto: string;
    codigo: string;
    descripcion: string;
    precioVenta: number;
    precioOfertaSugerido: number;
    descuentoPct: number;
}

export default function Dashboard() {
    // 1. Obtener KPIs históricos
    const { data: kpis, isLoading: loadingKpis } = useQuery<KPI[]>({
        queryKey: ['kpis'],
        queryFn: async () => {
            const { data } = await api.get<KPI[]>('/reportes/kpis-semanales');
            // Invertimos para que la gráfica muestre de más antiguo a más reciente
            return data.reverse();
        },
    });

    // 2. Obtener Ofertas Relámpago para la sección de acción
    const { data: ofertas } = useQuery<Oferta[]>({
        queryKey: ['ofertas-relampago'],
        queryFn: async () => {
            const { data } = await api.get<Oferta[]>('/reportes/ofertas-relampago');
            return data.slice(0, 5); // Tomamos solo las top 5 para el dashboard
        },
    });

    // Datos de la semana actual (el último registro después del reverse)
    const currentKpi = kpis?.[kpis.length - 1];

    if (loadingKpis) {
        return (
            <div className="w-full h-full flex items-center justify-center animate-pulse text-eclectico-naranja">
                Cargando tu boutique...
            </div>
        );
    }

    return (
        <div className="space-y-8 pb-10">
            {/* Encabezado elegante */}
            <div className="flex flex-col gap-1">
                <h1 className="text-3xl font-black text-eclectico-negro tracking-tight">
                    Resumen de Boutique
                </h1>
                <p className="text-sm font-semibold tracking-wider text-eclectico-negro/50 uppercase">
                    Métricas y rendimiento de tu colección
                </p>
            </div>

            {/* Tarjetas Superiores (KPIs) */}
            <div className="grid gap-5 md:grid-cols-2 xl:grid-cols-4">
                <KpiCard
                    title="Ingresos (Semana)"
                    value={`C$ ${currentKpi?.ingresos?.toLocaleString() ?? 0}`}
                    icon={DollarSign}
                    color="text-eclectico-naranja"
                    bgColor="bg-eclectico-naranja/10"
                />
                <KpiCard
                    title="Prendas Vendidas"
                    value={currentKpi?.prendasVendidas?.toString() ?? '0'}
                    icon={Package}
                    color="text-eclectico-celeste"
                    bgColor="bg-eclectico-celeste/15"
                />
                <KpiCard
                    title="Utilidad Neta"
                    value={`C$ ${currentKpi?.utilidadNeta?.toLocaleString() ?? 0}`}
                    icon={TrendingUp}
                    color="text-eclectico-rosado"
                    bgColor="bg-eclectico-rosado/15"
                />
                <KpiCard
                    title="Margen Bruto"
                    value={`${currentKpi?.margenBruto?.toFixed(1) ?? 0}%`}
                    icon={ShoppingCart}
                    color="text-eclectico-amarillo"
                    bgColor="bg-eclectico-amarillo/20"
                />
            </div>

            {/* Sección de Gráficos y Tablas */}
            <div className="grid gap-6 xl:grid-cols-3">

                {/* Gráfica Principal (Ocupa 2 columnas) */}
                <div className="xl:col-span-2 bg-white/70 backdrop-blur-2xl ring-1 ring-eclectico-negro/5 rounded-[30px] p-6 sm:p-8 shadow-[0_15px_40px_rgba(59,56,56,0.04)]">
                    <div className="flex justify-between items-end mb-8">
                        <div>
                            <h2 className="text-lg font-bold text-eclectico-negro">Tendencia de Ingresos</h2>
                            <p className="text-xs font-semibold text-eclectico-negro/50 uppercase tracking-widest mt-1">
                                Últimas Semanas
                            </p>
                        </div>
                        <div className="flex items-center gap-2 text-xs font-bold text-eclectico-naranja bg-eclectico-naranja/10 px-3 py-1.5 rounded-full">
                            <ArrowUpRight className="w-4 h-4" />
                            Crecimiento
                        </div>
                    </div>

                    <div className="h-[300px] w-full">
                        <ResponsiveContainer width="100%" height="100%">
                            <AreaChart data={kpis} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
                                <defs>
                                    <linearGradient id="colorIngresos" x1="0" y1="0" x2="0" y2="1">
                                        <stop offset="5%" stopColor="#F0AC74" stopOpacity={0.4}/>
                                        <stop offset="95%" stopColor="#F0AC74" stopOpacity={0}/>
                                    </linearGradient>
                                </defs>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#3B3838" opacity={0.05} />
                                <XAxis
                                    dataKey="semanaInicio"
                                    axisLine={false}
                                    tickLine={false}
                                    tick={{ fill: '#3B3838', opacity: 0.5, fontSize: 12, fontWeight: 600 }}
                                    dy={10}
                                />
                                <YAxis
                                    axisLine={false}
                                    tickLine={false}
                                    tick={{ fill: '#3B3838', opacity: 0.5, fontSize: 12, fontWeight: 600 }}
                                />
                                <Tooltip
                                    contentStyle={{ borderRadius: '16px', border: 'none', boxShadow: '0 10px 25px rgba(59,56,56,0.1)' }}
                                    itemStyle={{ color: '#3B3838', fontWeight: 'bold' }}
                                />
                                <Area
                                    type="monotone"
                                    dataKey="ingresos"
                                    stroke="#F0AC74"
                                    strokeWidth={3}
                                    fillOpacity={1}
                                    fill="url(#colorIngresos)"
                                />
                            </AreaChart>
                        </ResponsiveContainer>
                    </div>
                </div>

                {/* Panel lateral: Ofertas Relámpago Recomendadas */}
                <div className="bg-white/70 backdrop-blur-2xl ring-1 ring-eclectico-negro/5 rounded-[30px] p-6 sm:p-8 shadow-[0_15px_40px_rgba(59,56,56,0.04)] flex flex-col">
                    <div className="flex items-center gap-3 mb-6">
                        <div className="p-2 bg-eclectico-coral/20 text-eclectico-coral rounded-xl">
                            <Zap className="w-5 h-5 fill-current" />
                        </div>
                        <div>
                            <h2 className="text-lg font-bold text-eclectico-negro">Ofertas Relámpago</h2>
                            <p className="text-[11px] font-semibold text-eclectico-negro/50 uppercase tracking-wider mt-0.5">
                                Sugerencias de remate
                            </p>
                        </div>
                    </div>

                    <div className="flex-1 space-y-4">
                        {ofertas && ofertas.length > 0 ? (
                            ofertas.map((oferta) => (
                                <div key={oferta.idProducto} className="group flex justify-between items-center p-3.5 rounded-2xl hover:bg-eclectico-crema transition-colors cursor-pointer border border-transparent hover:border-eclectico-naranja/10">
                                    <div className="flex flex-col gap-1">
                                        <span className="text-sm font-bold text-eclectico-negro group-hover:text-eclectico-naranja transition-colors line-clamp-1">
                                            {oferta.descripcion}
                                        </span>
                                        <span className="text-xs text-eclectico-negro/50 font-medium">
                                            Cod: {oferta.codigo}
                                        </span>
                                    </div>
                                    <div className="flex flex-col items-end gap-1">
                                        <span className="text-sm font-black text-eclectico-coral">
                                            - {oferta.descuentoPct}%
                                        </span>
                                        <span className="text-xs text-eclectico-negro/40 line-through font-semibold">
                                            C$ {oferta.precioVenta}
                                        </span>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="h-full flex flex-col items-center justify-center text-center opacity-50">
                                <Package className="w-8 h-8 mb-2" />
                                <p className="text-sm font-medium">No hay sugerencias por ahora</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

// Subcomponente para las Tarjetas KPI para mantener el código limpio
function KpiCard({ title, value, icon: Icon, color, bgColor }: any) {
    return (
        <div className="bg-white/70 backdrop-blur-2xl ring-1 ring-eclectico-negro/5 rounded-[30px] p-6 shadow-[0_10px_40px_rgba(59,56,56,0.03)] hover:-translate-y-1 transition-transform duration-300">
            <div className="flex justify-between items-start">
                <div className="space-y-3">
                    <p className="text-xs font-bold text-eclectico-negro/50 uppercase tracking-widest">
                        {title}
                    </p>
                    <p className="text-3xl font-black text-eclectico-negro tracking-tight">
                        {value}
                    </p>
                </div>
                <div className={`p-3 rounded-2xl ${bgColor} ${color}`}>
                    <Icon className="w-6 h-6" strokeWidth={2.5} />
                </div>
            </div>
        </div>
    );
}