import { useForm, useFieldArray } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useMutation, useQuery } from '@tanstack/react-query';
import axios from 'axios';
import api from '@/lib/axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { toast } from 'sonner';
import { Plus, Trash2 } from 'lucide-react';

// Tipos para las respuestas de la API
interface Cliente {
    idPersona: number;
    nombre: string;
}

interface ProductoDisponible {
    idProducto: number;
    codigo: string;
    descripcion: string;
}

interface MetodoPago {
    idMetodo: number;
    nombre: string;
}

// Esquemas de Zod (sin .default() en estadoEntrega)
const detalleSchema = z.object({
    idProducto: z.string().min(1, 'Seleccione un producto'),
    precioVentaReal: z.number().min(0.01, 'El precio debe ser mayor a 0'),
    descuentoAplicado: z.number().min(0), // ya no tiene .default(0)
});

const ventaSchema = z.object({
    idCliente: z.string().min(1, 'Seleccione un cliente'),
    idMetodoPago: z.number(),
    estadoEntrega: z.string(), // requerido, sin default
    notas: z.string().optional(),
    detalles: z.array(detalleSchema).min(1, 'Agregue al menos un producto'),
});

type VentaForm = z.infer<typeof ventaSchema>;

export default function Ventas() {
    const { data: clientes } = useQuery<Cliente[]>({
        queryKey: ['clientes'],
        queryFn: async () => {
            const { data } = await api.get<Cliente[]>('/personas?tipo=C');
            return data;
        },
    });

    const { data: productos } = useQuery<ProductoDisponible[]>({
        queryKey: ['productos-disponibles'],
        queryFn: async () => {
            const { data } = await api.get<ProductoDisponible[]>('/reportes/inventario-disponible');
            return data;
        },
    });

    const { data: metodosPago } = useQuery<MetodoPago[]>({
        queryKey: ['metodos-pago'],
        queryFn: async () => {
            const { data } = await api.get<MetodoPago[]>('/catalogos/metodos-pago');
            return data;
        },
    });

    const mutation = useMutation({
        mutationFn: (values: VentaForm) => api.post('/ventas', values),
        onSuccess: () => {
            toast.success('Venta registrada con éxito');
            form.reset();
        },
        onError: (error: unknown) => {
            if (axios.isAxiosError(error) && error.response?.data?.error) {
                toast.error(error.response.data.error);
            } else {
                toast.error('Error al registrar venta');
            }
        },
    });

    // Ahora el tipo VentaForm ya tiene estadoEntrega como string requerido
    const form = useForm<VentaForm>({
        resolver: zodResolver(ventaSchema),
        defaultValues: {
            idCliente: '',
            idMetodoPago: 1,
            estadoEntrega: 'Pendiente', // valor por defecto explícito
            detalles: [],
        },
    });

    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'detalles',
    });

    const onSubmit = (values: VentaForm) => mutation.mutate(values);

    return (
        <div>
            <h1 className="text-2xl font-bold mb-6">Nueva Venta</h1>
            <Card>
                <CardHeader>
                    <CardTitle>Registrar Venta</CardTitle>
                </CardHeader>
                <CardContent>
                    <Form {...form}>
                        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                            <FormField
                                control={form.control}
                                name="idCliente"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Cliente</FormLabel>
                                        <Select onValueChange={field.onChange} value={field.value}>
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Seleccionar cliente" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                {clientes?.map((c) => (
                                                    <SelectItem key={c.idPersona} value={String(c.idPersona)}>
                                                        {c.nombre}
                                                    </SelectItem>
                                                ))}
                                            </SelectContent>
                                        </Select>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="idMetodoPago"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Método de Pago</FormLabel>
                                        <Select
                                            onValueChange={(v) => field.onChange(Number(v))}
                                            value={String(field.value)}
                                        >
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Seleccionar método" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                {metodosPago?.map((m) => (
                                                    <SelectItem key={m.idMetodo} value={String(m.idMetodo)}>
                                                        {m.nombre}
                                                    </SelectItem>
                                                ))}
                                            </SelectContent>
                                        </Select>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />

                            <div>
                                <label className="block text-sm font-medium mb-2">Productos</label>
                                {fields.map((field, index) => (
                                    <div key={field.id} className="flex gap-2 mb-2 items-center">
                                        <FormField
                                            control={form.control}
                                            name={`detalles.${index}.idProducto`}
                                            render={({ field }) => (
                                                <Select onValueChange={field.onChange} value={field.value}>
                                                    <FormControl>
                                                        <SelectTrigger className="w-[200px]">
                                                            <SelectValue placeholder="Producto" />
                                                        </SelectTrigger>
                                                    </FormControl>
                                                    <SelectContent>
                                                        {productos?.map((p) => (
                                                            <SelectItem key={p.idProducto} value={String(p.idProducto)}>
                                                                {p.codigo} - {p.descripcion}
                                                            </SelectItem>
                                                        ))}
                                                    </SelectContent>
                                                </Select>
                                            )}
                                        />
                                        <FormField
                                            control={form.control}
                                            name={`detalles.${index}.precioVentaReal`}
                                            render={({ field }) => (
                                                <Input
                                                    type="number"
                                                    placeholder="Precio"
                                                    {...field}
                                                    onChange={(e) => field.onChange(Number(e.target.value))}
                                                    className="w-[120px]"
                                                />
                                            )}
                                        />
                                        <Button
                                            type="button"
                                            variant="ghost"
                                            size="icon"
                                            onClick={() => remove(index)}
                                            disabled={fields.length === 1}
                                        >
                                            <Trash2 className="h-4 w-4" />
                                        </Button>
                                    </div>
                                ))}
                                <Button
                                    type="button"
                                    variant="outline"
                                    size="sm"
                                    onClick={() =>
                                        append({
                                            idProducto: '',
                                            precioVentaReal: 0.01,
                                            descuentoAplicado: 0, // ya es obligatorio, se pasa explícitamente
                                        })
                                    }
                                >
                                    <Plus className="mr-2 h-4 w-4" /> Agregar producto
                                </Button>
                                {form.formState.errors.detalles?.message && (
                                    <p className="text-sm text-red-500 mt-1">
                                        {form.formState.errors.detalles.message}
                                    </p>
                                )}
                            </div>

                            <Button type="submit" className="w-full" disabled={mutation.isPending}>
                                {mutation.isPending ? 'Procesando...' : 'Registrar Venta'}
                            </Button>
                        </form>
                    </Form>
                </CardContent>
            </Card>
        </div>
    );
}