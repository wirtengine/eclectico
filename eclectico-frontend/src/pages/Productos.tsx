import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import api from '@/lib/axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { toast } from 'sonner';
import { Plus } from 'lucide-react';

interface ProductoInventario {
    idProducto: number;
    codigo: string;
    descripcion: string;
    linea: string;
    precioVenta: number;
    precioOfertaSugerido: number;
}

const productoSchema = z.object({
    descripcion: z.string().min(1),
    idLinea: z.number(),
    costo: z.number().positive(),
    precioVenta: z.number().positive(),
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
    const { data: productos } = useQuery<ProductoInventario[]>({
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
            toast.success('Producto creado');
        },
        onError: (error: unknown) => {
            if (axios.isAxiosError(error) && error.response?.data?.error) {
                toast.error(error.response.data.error);
            } else {
                toast.error('Error al crear producto');
            }
        },
    });

    const form = useForm<ProductoForm>({
        resolver: zodResolver(productoSchema),
        defaultValues: { idLinea: 1 },
    });

    return (
        <div>
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-2xl font-bold">Productos</h1>
                <Dialog>
                    <DialogTrigger asChild>
                        <Button>
                            <Plus className="mr-2 h-4 w-4" /> Nuevo Producto
                        </Button>
                    </DialogTrigger>
                    <DialogContent>
                        <DialogHeader>
                            <DialogTitle>Agregar Producto</DialogTitle>
                        </DialogHeader>
                        <Form {...form}>
                            <form onSubmit={form.handleSubmit((v) => mutation.mutate(v))} className="space-y-4">
                                <FormField
                                    control={form.control}
                                    name="descripcion"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Descripción</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="idLinea"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Línea</FormLabel>
                                            <Select onValueChange={(v) => field.onChange(Number(v))} defaultValue="1">
                                                <FormControl>
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="Seleccionar línea" />
                                                    </SelectTrigger>
                                                </FormControl>
                                                <SelectContent>
                                                    <SelectItem value="1">Rápida</SelectItem>
                                                    <SelectItem value="2">Premium</SelectItem>
                                                </SelectContent>
                                            </Select>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <div className="grid grid-cols-2 gap-4">
                                    <FormField
                                        control={form.control}
                                        name="costo"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Costo</FormLabel>
                                                <FormControl>
                                                    <Input type="number" {...field} onChange={(e) => field.onChange(Number(e.target.value))} />
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
                                                <FormLabel>Precio Venta</FormLabel>
                                                <FormControl>
                                                    <Input type="number" {...field} onChange={(e) => field.onChange(Number(e.target.value))} />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                                <FormField
                                    control={form.control}
                                    name="talla"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Talla</FormLabel>
                                            <FormControl>
                                                <Input {...field} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <FormField
                                    control={form.control}
                                    name="idProveedor"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>ID Proveedor</FormLabel>
                                            <FormControl>
                                                <Input type="number" {...field} onChange={(e) => field.onChange(Number(e.target.value))} />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                                <Button type="submit" className="w-full" disabled={mutation.isPending}>
                                    {mutation.isPending ? 'Guardando...' : 'Guardar Producto'}
                                </Button>
                            </form>
                        </Form>
                    </DialogContent>
                </Dialog>
            </div>

            <div className="rounded-md border">
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>Código</TableHead>
                            <TableHead>Descripción</TableHead>
                            <TableHead>Línea</TableHead>
                            <TableHead>Precio Venta</TableHead>
                            <TableHead>Oferta Sugerida</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {productos?.map((p) => (
                            <TableRow key={p.idProducto}>
                                <TableCell>{p.codigo}</TableCell>
                                <TableCell>{p.descripcion}</TableCell>
                                <TableCell>{p.linea}</TableCell>
                                <TableCell>C${p.precioVenta}</TableCell>
                                <TableCell>C${p.precioOfertaSugerido}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </div>
    );
}