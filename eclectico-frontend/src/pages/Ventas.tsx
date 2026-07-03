import { useForm, useFieldArray } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useMutation, useQuery } from '@tanstack/react-query';
import api from '@/lib/axios';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { toast } from 'sonner';
import { Plus, Trash2, ShoppingBag, Receipt, User } from 'lucide-react';

// Esquemas validados para coincidir con el DTO de tu API
const detalleSchema = z.object({
    idProducto: z.string().uuid('Seleccione un producto válido'),
    precioVentaReal: z.number().min(0.01),
    descuentoAplicado: z.number().min(0),
});

const ventaSchema = z.object({
    idCliente: z.string().uuid('Seleccione un cliente'),
    idMetodoPago: z.number(),
    estadoEntrega: z.string().min(1),
    notas: z.string().optional(),
    detalles: z.array(detalleSchema).min(1, 'Agregue al menos un producto'),
});

type VentaForm = z.infer<typeof ventaSchema>;

export default function Ventas() {
    // Queries para catálogos
    const { data: clientes } = useQuery<any[]>({ queryKey: ['clientes'], queryFn: async () => (await api.get('/personas?tipo=C')).data });
    const { data: productos } = useQuery<any[]>({ queryKey: ['productos'], queryFn: async () => (await api.get('/reportes/inventario-disponible')).data });
    const { data: metodos } = useQuery<any[]>({ queryKey: ['metodos'], queryFn: async () => (await api.get('/catalogos/metodos-pago')).data });

    const form = useForm<VentaForm>({
        resolver: zodResolver(ventaSchema),
        defaultValues: { idCliente: '', idMetodoPago: 1, estadoEntrega: 'Entregado', detalles: [{ idProducto: '', precioVentaReal: 0, descuentoAplicado: 0 }] },
    });

    const { fields, append, remove } = useFieldArray({ control: form.control, name: 'detalles' });

    const mutation = useMutation({
        mutationFn: (values: VentaForm) => api.post('/ventas', values),
        onSuccess: () => {
            toast.success('Venta procesada con elegancia ✨');
            form.reset();
        },
    });

    return (
        <div className="max-w-4xl mx-auto pb-10 animate-in fade-in duration-500">
            <div className="mb-8">
                <h1 className="text-3xl font-black text-eclectico-negro flex items-center gap-3">
                    <Receipt className="w-8 h-8 text-eclectico-naranja" /> Nueva Transacción
                </h1>
                <p className="text-sm font-semibold text-eclectico-negro/50 mt-1 uppercase tracking-widest">
                    Registra una venta en tu boutique
                </p>
            </div>

            <Form {...form}>
                <form onSubmit={form.handleSubmit((v) => mutation.mutate(v))} className="space-y-6">
                    {/* Tarjeta de Datos Principales */}
                    <div className="grid md:grid-cols-2 gap-6 bg-white/70 backdrop-blur-2xl p-8 rounded-[30px] border border-eclectico-negro/5 shadow-[0_15px_40px_rgba(59,56,56,0.04)]">
                        <FormField control={form.control} name="idCliente" render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-bold uppercase text-xs text-eclectico-negro/60">Cliente</FormLabel>
                                <Select onValueChange={field.onChange} value={field.value}>
                                    <SelectTrigger className="rounded-xl h-12 border-eclectico-negro/10"><SelectValue placeholder="Seleccionar..." /></SelectTrigger>
                                    <SelectContent className="rounded-xl"><User className="w-4 h-4 ml-2"/>{clientes?.map(c => <SelectItem key={c.idPersona} value={c.idPersona}>{c.nombre}</SelectItem>)}</SelectContent>
                                </Select>
                            </FormItem>
                        )}/>

                        <FormField control={form.control} name="idMetodoPago" render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-bold uppercase text-xs text-eclectico-negro/60">Método de Pago</FormLabel>
                                <Select onValueChange={(v) => field.onChange(Number(v))} value={String(field.value)}>
                                    <SelectTrigger className="rounded-xl h-12 border-eclectico-negro/10"><SelectValue /></SelectTrigger>
                                    <SelectContent className="rounded-xl">{metodos?.map(m => <SelectItem key={m.idMetodo} value={String(m.idMetodo)}>{m.nombre}</SelectItem>)}</SelectContent>
                                </Select>
                            </FormItem>
                        )}/>
                    </div>

                    {/* Sección Detalle */}
                    <div className="bg-white/70 backdrop-blur-2xl p-8 rounded-[30px] border border-eclectico-negro/5 shadow-[0_15px_40px_rgba(59,56,56,0.04)]">
                        <div className="flex items-center justify-between mb-6">
                            <h3 className="font-black text-lg flex items-center gap-2"><ShoppingBag className="w-5 h-5 text-eclectico-naranja"/> Artículos</h3>
                            <Button type="button" variant="outline" size="sm" className="rounded-full" onClick={() => append({ idProducto: '', precioVentaReal: 0, descuentoAplicado: 0 })}>
                                <Plus className="w-4 h-4 mr-2"/> Agregar
                            </Button>
                        </div>

                        <div className="space-y-4">
                            {fields.map((f, index) => (
                                <div key={f.id} className="grid grid-cols-12 gap-3 items-end p-4 bg-eclectico-crema/50 rounded-2xl">
                                    <div className="col-span-6">
                                        <FormField control={form.control} name={`detalles.${index}.idProducto`} render={({ field }) => (
                                            <Select onValueChange={field.onChange} value={field.value}>
                                                <SelectTrigger className="bg-white rounded-xl"><SelectValue placeholder="Producto"/></SelectTrigger>
                                                <SelectContent>{productos?.map(p => <SelectItem key={p.idProducto} value={p.idProducto}>{p.codigo} - {p.descripcion}</SelectItem>)}</SelectContent>
                                            </Select>
                                        )}/>
                                    </div>
                                    <div className="col-span-2">
                                        <FormField control={form.control} name={`detalles.${index}.precioVentaReal`} render={({ field }) => (
                                            <Input type="number" placeholder="C$" className="bg-white rounded-xl" onChange={e => field.onChange(Number(e.target.value))}/>
                                        )}/>
                                    </div>
                                    <div className="col-span-1">
                                        <Button type="button" variant="ghost" size="icon" onClick={() => remove(index)}><Trash2 className="text-red-400 w-4 h-4"/></Button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                    <Button type="submit" size="lg" className="w-full rounded-2xl h-14 bg-eclectico-naranja hover:bg-eclectico-naranjaDark text-white font-bold text-lg shadow-xl shadow-eclectico-naranja/20">
                        Finalizar Venta
                    </Button>
                </form>
            </Form>
        </div>
    );
}