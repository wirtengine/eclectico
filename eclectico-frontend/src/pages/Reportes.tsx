import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow
} from '@/components/ui/table';
import { Card, CardContent } from '@/components/ui/card';
import { BarChart3, TrendingUp, CalendarDays } from 'lucide-react';

interface KPI {
    semanaInicio: string;
    prendasVendidas: number;
    ingresos: number;
    costos: number;
    margenBruto: number;
    utilidadNeta: number;
}

export default function Reportes() {
    const { data: kpis, isLoading } = useQuery<KPI[]>({
        queryKey: ['kpis-semanales'],
        queryFn: async () => {
            const { data } = await api.get<KPI[]>('/reportes/kpis-semanales');
            return data.reverse(); // Mostramos el más reciente primero
        },
    });

    return (
        <div className="space-y-8 pb-10">
            {/* Encabezado */}
            <div>
                <h1 className="text-3xl font-black text-eclectico-negro">Análisis Financiero</h1>
                <p className="text-sm font-semibold text-eclectico-negro/50 uppercase tracking-widest mt-1">
                    Histórico de rendimiento semanal
                </p>
            </div>

            {/* Contenedor de Tabla Boutique */}
            <div className="bg-white/70 backdrop-blur-2xl ring-1 ring-eclectico-negro/5 rounded-[30px] p-6 sm:p-8 shadow-[0_15px_40px_rgba(59,56,56,0.04)] overflow-hidden">
                {isLoading ? (
                    <div className="h-64 flex items-center justify-center text-eclectico-naranja animate-pulse font-bold">
                        Analizando métricas...
                    </div>
                ) : (
                    <div className="w-full overflow-x-auto custom-scrollbar">
                        <Table className="min-w-[800px]">
                            <TableHeader>
                                <TableRow className="hover:bg-transparent border-eclectico-negro/5">
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase">Semana</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase">Vendidas</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase">Ingresos</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase">Costos</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase">Margen</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase text-right">Utilidad Neta</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {kpis?.map((k, i) => (
                                    <TableRow key={i} className="hover:bg-eclectico-naranja/5 border-eclectico-negro/5 transition-colors">
                                        <TableCell className="font-semibold text-eclectico-negro flex items-center gap-2">
                                            <CalendarDays className="w-4 h-4 text-eclectico-naranja" />
                                            {k.semanaInicio}
                                        </TableCell>
                                        <TableCell className="font-medium">{k.prendasVendidas}</TableCell>
                                        <TableCell className="font-medium text-eclectico-negro">C$ {k.ingresos?.toLocaleString() ?? 0}</TableCell>
                                        <TableCell className="text-eclectico-negro/60">C$ {k.costos?.toLocaleString() ?? 0}</TableCell>
                                        <TableCell className="text-eclectico-celeste font-bold">{k.margenBruto?.toFixed(1)}%</TableCell>
                                        <TableCell className="font-black text-eclectico-naranja text-right">
                                            C$ {k.utilidadNeta?.toLocaleString() ?? 0}
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </div>
                )}
            </div>
        </div>
    );
}