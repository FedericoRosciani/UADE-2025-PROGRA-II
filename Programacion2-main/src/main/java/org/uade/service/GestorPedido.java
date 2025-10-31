package main.java.org.uade.service;

import main.java.org.uade.model.pedido;
import main.java.org.uade.model.Cliente;
import main.java.org.uade.model.plato;

import java.util.Arrays;
import java.util.Scanner;

import main.java.org.uade.structure.definition.PriorityQueueADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;
import main.java.org.uade.structure.definition.MultipleDictionaryADT;

import main.java.org.uade.structure.implementation.DynamicPriorityQueueADT;
import main.java.org.uade.structure.implementation.DynamicSimpleDictionaryADT;
import main.java.org.uade.structure.implementation.DynamicMultipleDictionaryADT;

public class GestorPedido {

    // Repositorios simples
    private final pedido[] pedidos = new pedido[1000];
    private int nextPedidoId = 1;

    private final Cliente[] clientes = new Cliente[500];
    private int nextClienteId = 1;

    private final plato[] platos = new plato[500];
    private int nextPlatoId = 1;

    // Índices con TDAs
    private final PriorityQueueADT colaPrioridad;        // almacena idPedido con prioridad
    private final SimpleDictionaryADT idxPedido;         // idPedido -> index en array pedidos
    private final MultipleDictionaryADT pedidosPorCliente; // clienteId -> {pedidoId, ...}
    private final SimpleDictionaryADT conteoPlatos;      // idPlato -> cantidad pedida

    public GestorPedido() {
        this.colaPrioridad = new DynamicPriorityQueueADT();
        this.idxPedido = new DynamicSimpleDictionaryADT();
        this.pedidosPorCliente = new DynamicMultipleDictionaryADT();
        this.conteoPlatos = new DynamicSimpleDictionaryADT();
        precargarPlatos();
        precargarClientes();
        precargarPedidosDemo();
    }

    // --- Clientes / Platos ---

    private void precargarClientes() {
        addCliente("Cliente A");
        addCliente("Cliente B");
        addCliente("Cliente C");
    }

    private void precargarPlatos() {
        addPlato("Milanesa con papas");
        addPlato("Pizza muzzarella");
        addPlato("Hamburguesa completa");
        addPlato("Ensalada César");
        addPlato("Empanadas x6");
    }

    private void precargarPedidosDemo() {
        // Crea 5 pedidos aleatorios para el escenario
        for (int i = 0; i < 5; i++) {
            int cliente = 1 + (i % 3);
            int tipo = (i % 2 == 0) ? pedido.TIPO_DOMICILIO : pedido.TIPO_LLEVAR;
            int prioridad = (i % 3 == 0) ? pedido.PRIORIDAD_VIP : pedido.PRIORIDAD_NORMAL;
            int[] platos = new int[]{1 + (i % nextPlatoId)};
            crearPedido(cliente, platos, tipo, prioridad);
        }
    }

    public int addCliente(String nombre) {
        int id = nextClienteId++;
        clientes[id] = new Cliente(id, nombre);
        return id;
    }

    public int addPlato(String nombre) {
        int id = nextPlatoId++;
        platos[id] = new plato(id, nombre);
        return id;
    }

    public Cliente getCliente(int id) { return clientes[id]; }
    public plato getPlato(int id) { return platos[id]; }

    // --- Pedidos ---

    public int crearPedido(int clienteId, int[] platosIds, int tipo, int prioridad) {
        int id = nextPedidoId++;
        pedido p = new pedido(id, clienteId, platosIds, tipo, prioridad);
        pedidos[id] = p;
        idxPedido.add(id, id);           // id -> id (index = id porque guardamos directo por id)
        pedidosPorCliente.add(clienteId, id);
        // registrar en cola por prioridad
        colaPrioridad.add(id, prioridad);
        // conteo platos
        for (int pid : platosIds) {
            int actual = 0;
            if (!conteoPlatos.isEmpty()) {
                try { actual = conteoPlatos.get(pid); } catch (Exception ignored) {}
            }
            conteoPlatos.add(pid, actual + 1);
        }
        return id;
    }

    public void ingresarPedido(Scanner sc) {
        sc.nextLine(); // limpiar salto de línea previo

        System.out.println("\n=== NUEVO PEDIDO ===");
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        int clienteId = addCliente(nombre);

        // Mostrar platos disponibles antes de elegir
        System.out.println("\nPlatos disponibles:");
        for (int i = 1; i < nextPlatoId; i++) {
            plato p = platos[i];
            if (p != null)
                System.out.println("  " + i + ") " + p.getNombre());
        }

        System.out.print("\nIngrese la cantidad de platos: ");
        int cant = sc.nextInt();
        int[] platosIds = new int[cant];

        for (int i = 0; i < cant; i++) {
            System.out.print("Seleccione ID del plato #" + (i + 1) + ": ");
            platosIds[i] = sc.nextInt();
            if (platosIds[i] <= 0 || platosIds[i] >= nextPlatoId || platos[platosIds[i]] == null) {
                System.out.println("ID inválido. Intente nuevamente.");
                i--;
            }
        }

        System.out.print("Tipo de pedido (0 = para llevar, 1 = domicilio): ");
        int tipo = sc.nextInt();

        System.out.print("Prioridad (1 = VIP, 2 = Normal): ");
        int prioridad = sc.nextInt();

        int id = crearPedido(clienteId, platosIds, tipo, prioridad);
        System.out.println("\n✅ Pedido creado exitosamente: #" + id);
        System.out.println(describirPedido(pedidos[id]));
    }

    public void verPendientes() {
        System.out.println("\n--- Pedidos no entregados ---");
        for (int i = 1; i < nextPedidoId; i++) {
            pedido p = pedidos[i];
            if (p == null) continue;
            if (p.getEstado() != pedido.Estado.ENTREGADO) {
                System.out.println(describirPedido(p));
            }
        }
    }

    public String describirPedido(pedido p) {
        String cliente = getCliente(p.getClienteId()).getNombre();
        String platosStr = Arrays.toString(
                Arrays.stream(p.getPlatosIds()).mapToObj(id -> getPlato(id).getNombre()).toArray());
        String tipo = p.getTipo() == pedido.TIPO_DOMICILIO ? "domicilio" : "llevar";
        String prio = p.getPrioridad() == pedido.PRIORIDAD_VIP ? "VIP" : "Normal";
        return "Pedido #" + p.getId() + " - " + cliente + " - " + tipo + " - " + prio + " - " + p.getEstado()
                + " - Platos: " + platosStr;
    }

    // Cocina: toma el siguiente por prioridad
    public Integer tomarSiguienteParaCocina() {
        if (colaPrioridad.isEmpty()) return null;
        int id = colaPrioridad.getElement();
        colaPrioridad.remove();
        pedido p = pedidos[id];
        if (p == null) return null;
        p.setEstado(pedido.Estado.EN_COCINA);
        return id;
    }

    public void marcarListo(int pedidoId) {
        pedido p = pedidos[pedidoId];
        if (p != null) p.setEstado(pedido.Estado.LISTO);
    }

    public pedido getPedido(int id) { return pedidos[id]; }

    // Reportes
    public int contarPendientesDespacho() {
        int c = 0;
        for (int i = 1; i < nextPedidoId; i++) {
            pedido p = pedidos[i];
            if (p == null) continue;
            if (p.getEstado() != pedido.Estado.ENTREGADO) c++;
        }
        return c;
    }

    public int contarFinalizados() {
        int c = 0;
        for (int i = 1; i < nextPedidoId; i++) {
            pedido p = pedidos[i];
            if (p == null) continue;
            if (p.getEstado() == pedido.Estado.ENTREGADO) c++;
        }
        return c;
    }

    public int clienteConMasPedidos() {
        int mejorCliente = -1, max = -1;
        // recorremos clientes conocidos
        for (int cid = 1; cid < nextClienteId; cid++) {
            int[] arr;
            try { arr = pedidosPorCliente.get(cid); }
            catch (Exception e) { continue; }
            if (arr != null && arr.length > max) {
                max = arr.length;
                mejorCliente = cid;
            }
        }
        return mejorCliente;
    }

    public int platoMasPedido() {
        int bestPlato = -1, best = -1;
        for (int pid = 1; pid < nextPlatoId; pid++) {
            int cnt = 0;
            try { cnt = conteoPlatos.get(pid); } catch (Exception ignored) {}
            if (cnt > best) { best = cnt; bestPlato = pid; }
        }
        return bestPlato;
    }
}
