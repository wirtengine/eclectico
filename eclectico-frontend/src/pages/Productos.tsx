import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import api from '@/lib/axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger, DialogDescription } from '@/components/ui/dialog';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { toast } from 'sonner';
import { Plus, Tag, Search, Sparkles } from 'lucide-react';
import { useState } from 'react';

// Tipos corregidos según tu OpenAPI (idProducto es string/uuid)
interface ProductoInventario {
    idProducto: string;
    codigo: string;
    descripcion: string;
    linea: string;
    precioVenta: number;
    precioOfertaSugerido: number;
    talla?: string;
}

const productoSchema = z.object({
    descripcion: z.string().min(1, "La descripción es requerida"),
    idLinea: z.number().min(1, "Selecciona una línea"),
    costo: z.number().positive("El costo debe ser mayor a 0"),
    precioVenta: z.number().positive("El precio debe ser mayor a 0"),
    talla: z.string().optional(),
    medidas: z.string().optional(),
    color: z.string().optional(),
    marcaOriginal: z.string().optional(),
    estadoNotas: z.string().optional(),
    idProveedor: z.number().optional(),
});

type ProductoForm = z.infer<typeof productoSchema>;

export default function Productos() {
    const queryClient = useQueryClient();
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    const { data: productos, isLoading } = useQuery<ProductoInventario[]>({
        queryKey: ['productos'],
        queryFn: async () => {
            const { data } = await api.get<ProductoInventario[]>('/reportes/inventario-disponible');
            return data;
        },
    });

    const mutation = useMutation({
        mutationFn: (values: ProductoForm) => api.post('/productos', values),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['productos'] });
            toast.success('✨ Prenda agregada exitosamente a la colección');
            setIsDialogOpen(false);
            form.reset();
        },
        onError: (error: unknown) => {
            if (axios.isAxiosError(error) && error.response?.data?.error) {
                toast.error(error.response.data.error);
            } else {
                toast.error('Error al registrar la prenda');
            }
        },
    });

    const form = useForm<ProductoForm>({
        resolver: zodResolver(productoSchema),
        defaultValues: {
            idLinea: 1,
            descripcion: "",
            talla: "",
            costo: 0,
            precioVenta: 0,
            idProveedor: 1
        },
    });

    return (
        <div className="space-y-8 pb-10">
            {/* Encabezado y Acción Principal */}
            <div className="flex flex-col sm:flex-row sm:items-end justify-between gap-4">
                <div className="flex flex-col gap-1">
                    <h1 className="text-3xl font-black text-eclectico-negro tracking-tight flex items-center gap-3">
                        <Tag className="w-8 h-8 text-eclectico-naranja" />
                        Colección Actual
                    </h1>
                    <p className="text-sm font-semibold tracking-wider text-eclectico-negro/50 uppercase">
                        Gestión de inventario disponible
                    </p>
                </div>

                <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
                    <DialogTrigger asChild>
                        <Button className="bg-eclectico-naranja hover:bg-eclectico-naranjaDark text-white shadow-[0_8px_20px_rgba(240,172,116,0.3)] rounded-2xl py-6 px-6 font-bold tracking-wide transition-all duration-300 hover:scale-[1.02]">
                            <Plus className="mr-2 h-5 w-5" /> Nueva Prenda
                        </Button>
                    </DialogTrigger>
                    <DialogContent className="sm:max-w-[600px] bg-white/90 backdrop-blur-2xl border-eclectico-negro/5 rounded-[35px] shadow-[0_30px_80px_rgba(59,56,56,0.1)] p-8 max-h-[90vh] overflow-y-auto custom-scrollbar">
                        <DialogHeader className="mb-6">
                            <DialogTitle className="text-2xl font-black text-eclectico-negro flex items-center gap-2">
                                <Sparkles className="w-6 h-6 text-eclectico-amarillo" />
                                Añadir a la Colección
                            </DialogTitle>
                            <DialogDescription className="text-eclectico-negro/60 font-medium">
                                Registra los detalles de la nueva prenda para tu boutique.
                            </DialogDescription>
                        </DialogHeader>

                        <Form {...form}>
                            <form onSubmit={form.handleSubmit((v) => mutation.mutate(v))} className="space-y-5">
                                <FormField
                                    control={form.control}
                                    name="descripcion"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel className="text-xs font-bold uppercase tracking-wider text-eclectico-negro/70">Descripción</FormLabel>
                                            <FormControl>
                                                <Input
                                                    placeholder="Ej. Vestido floral de verano..."
                                                    className="rounded-xl border-eclectico-negro/10 bg-white/50 focus-visible:ring-eclectico-naranja h-12"
                                                    {...field}
                                                />
                                            </FormControl>
                                            <FormMessage className="text-red-500" />
                                        </FormItem>
                                    )}
                                />

                                <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
                                    <FormField
                                        control={form.control}
                                        name="idLinea"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs font-bold uppercase tracking-wider text-eclectico-negro/70">Línea de Diseño</FormLabel>
                                                <Select onValueChange={(v) => field.onChange(Number(v))} defaultValue="1">
                                                    <FormControl>
                                                        <SelectTrigger className="rounded-xl border-eclectico-negro/10 bg-white/50 focus:ring-eclectico-naranja h-12">
                                                            <SelectValue placeholder="Seleccionar línea" />
                                                        </SelectTrigger>
                                                    </FormControl>
                                                    <SelectContent className="rounded-xl border-eclectico-negro/5 shadow-lg">
                                                        <SelectItem value="1" className="cursor-pointer">Línea Rápida</SelectItem>
                                                        <SelectItem value="2" className="cursor-pointer">Línea Premium</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                    <FormField
                                        control={form.control}
                                        name="talla"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs font-bold uppercase tracking-wider text-eclectico-negro/70">Talla</FormLabel>
                                                <FormControl>
                                                    <Input placeholder="Ej. M, L, 32" className="rounded-xl border-eclectico-negro/10 bg-white/50 focus-visible:ring-eclectico-naranja h-12" {...field} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>

                                <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
                                    <FormField
                                        control={form.control}
                                        name="costo"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs font-bold uppercase tracking-wider text-eclectico-negro/70">Costo Adquisición (C$)</FormLabel>
                                                <FormControl>
                                                    <Input type="number" step="0.01" className="rounded-xl border-eclectico-negro/10 bg-white/50 focus-visible:ring-eclectico-naranja h-12" {...field} onChange={(e) => field.onChange(Number(e.target.value))} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                    <FormField
                                        control={form.control}
                                        name="precioVenta"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs font-bold uppercase tracking-wider text-eclectico-negro/70">Precio Venta (C$)</FormLabel>
                                                <FormControl>
                                                    <Input type="number" step="0.01" className="rounded-xl border-eclectico-negro/10 bg-white/50 focus-visible:ring-eclectico-naranja h-12 text-eclectico-naranja font-bold" {...field} onChange={(e) => field.onChange(Number(e.target.value))} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>

                                <FormField
                                    control={form.control}
                                    name="idProveedor"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel className="text-xs font-bold uppercase tracking-wider text-eclectico-negro/70">ID Proveedor</FormLabel>
                                            <FormControl>
                                                <Input type="number" className="rounded-xl border-eclectico-negro/10 bg-white/50 focus-visible:ring-eclectico-naranja h-12" {...field} onChange={(e) => field.onChange(Number(e.target.value))} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />

                                <div className="pt-4">
                                    <Button
                                        type="submit"
                                        className="w-full bg-eclectico-negro hover:bg-black text-white rounded-2xl h-14 text-base font-bold transition-all duration-300"
                                        disabled={mutation.isPending}
                                    >
                                        {mutation.isPending ? 'Guardando en colección...' : 'Guardar Prenda'}
                                    </Button>
                                </div>
                            </form>
                        </Form>
                    </DialogContent>
                </Dialog>
            </div>

            {/* Contenedor de la Tabla con estilo Boutique */}
            <div className="bg-white/70 backdrop-blur-2xl ring-1 ring-eclectico-negro/5 rounded-[30px] shadow-[0_15px_40px_rgba(59,56,56,0.04)] overflow-hidden">
                <div className="p-4 sm:p-6 border-b border-eclectico-negro/5 flex justify-between items-center bg-white/50">
                    <h2 className="text-lg font-bold text-eclectico-negro">Inventario Disponible</h2>
                    <div className="relative">
                        <Search className="w-4 h-4 absolute left-3 top-1/2 -translate-y-1/2 text-eclectico-negro/40" />
                        <Input
                            placeholder="Buscar código..."
                            className="w-full sm:w-64 pl-9 rounded-full bg-white border-none shadow-sm focus-visible:ring-1 focus-visible:ring-eclectico-naranja text-sm h-10"
                        />
                    </div>
                </div>

                <div className="w-full overflow-x-auto custom-scrollbar">
                    {isLoading ? (
                        <div className="p-10 text-center text-eclectico-naranja animate-pulse font-medium">
                            Cargando tu colección...
                        </div>
                    ) : (
                        <Table className="w-full min-w-[700px]">
                            <TableHeader>
                                <TableRow className="hover:bg-transparent border-eclectico-negro/5">
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase tracking-widest h-14">Código</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase tracking-widest h-14">Descripción</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase tracking-widest h-14">Línea & Talla</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase tracking-widest h-14 text-right">Precio Venta</TableHead>
                                    <TableHead className="text-xs font-bold text-eclectico-negro/50 uppercase tracking-widest h-14 text-right pr-6">Oferta Sugerida</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {productos?.map((p) => (
                                    <TableRow key={p.idProducto} className="hover:bg-eclectico-naranja/5 border-eclectico-negro/5 transition-colors group">
                                        <TableCell className="font-bold text-eclectico-negro/70">{p.codigo}</TableCell>
                                        <TableCell className="font-medium text-eclectico-negro max-w-[250px] truncate">{p.descripcion}</TableCell>
                                        <TableCell>
                                            <div className="flex flex-col">
                                                <span className="font-semibold text-eclectico-naranja">{p.linea}</span>
                                                {p.talla && <span className="text-xs text-eclectico-negro/50">Talla: {p.talla}</span>}
                                            </div>
                                        </TableCell>
                                        <TableCell className="text-right font-bold text-eclectico-negro">
                                            C$ {p.precioVenta.toLocaleString()}
                                        </TableCell>
                                        <TableCell className="text-right pr-6">
                                            <span className="inline-flex items-center justify-center px-3 py-1 rounded-full bg-eclectico-coral/15 text-eclectico-coral font-bold text-sm">
                                                C$ {p.precioOfertaSugerido.toLocaleString()}
                                            </span>
                                        </TableCell>
                                    </TableRow>
                                ))}
                                {productos?.length === 0 && (
                                    <TableRow>
                                        <TableCell colSpan={5} className="h-32 text-center text-eclectico-negro/50 font-medium">
                                            No hay prendas en el inventario. ¡Agrega tu primera pieza!
                                        </TableCell>
                                    </TableRow>
                                )}
                            </TableBody>
                        </Table>
                    )}
                </div>
            </div>
        </div>
    );
}