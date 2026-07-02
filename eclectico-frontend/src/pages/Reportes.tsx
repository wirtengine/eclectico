import { useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';

interface KPI {
    semanaInicio: string;
    prendasVendidas: number;
    ingresos: number;
    costos: number;
    margenBruto: number;
    utilidadNeta: number;
}

export default function Reportes() {
    const { data: kpis } = useQuery<KPI[]>({
        queryKey: ['kpis-semanales'],
        queryFn: async () => {
            const { data } = await api.get<KPI[]>('/reportes/kpis-semanales');
            return data;
        },
    });

    return (
        <div>
            <h1 className="text-2xl font-bold mb-6">Reportes KPIs Semanales</h1>
            <div className="rounded-md border">
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>Semana Inicio</TableHead>
                            <TableHead>Prendas Vendidas</TableHead>
                            <TableHead>Ingresos</TableHead>
                            <TableHead>Costos</TableHead>
                            <TableHead>Margen Bruto</TableHead>
                            <TableHead>Utilidad Neta</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {kpis?.map((k, i) => (
                            <TableRow key={i}>
                                <TableCell>{k.semanaInicio}</TableCell>
                                <TableCell>{k.prendasVendidas}</TableCell>
                                <TableCell>C${k.ingresos ?? 0}</TableCell>
                                <TableCell>C${k.costos ?? 0}</TableCell>
                                <TableCell>C${k.margenBruto ?? 0}</TableCell>
                                <TableCell className="font-bold">C${k.utilidadNeta ?? 0}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </div>
    );
}