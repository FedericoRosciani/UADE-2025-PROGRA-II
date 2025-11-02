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
    private final PriorityQueueADT colaPrioridad;           // almacena idPedido con prioridad
    private final SimpleDictionaryADT idxPedido;            // idPedido -> index en array pedidos
    private final MultipleDictionaryADT pedidosPorCliente;  // clienteId -> {pedidoId, ...}
    private final SimpleDictionaryADT conteoPlatos;         // idPlato -> cantidad pedida

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
        // Crea 5 pedidos de demo
        for (int i = 0; i < 5; i++) {
            int cliente = 1 + (i % 3);
            int tipo = (i % 2 == 0) ? pedido.TIPO_DOMICILIO : pedido.TIPO_LLEVAR;
            int prioridad = (i % 3 == 0) ? pedido.PRIORIDAD_VIP : pedido.PRIORIDAD_NORMAL;
            int[] arrPlatos = new int[]{1 + (i % (nextPlatoId - 1 == 0 ? 1 : nextPlatoId - 1))};
            int id = crearPedido(cliente, arrPlatos, tipo, prioridad);
            if (tipo == pedido.TIPO_DOMICILIO) {
                pedidos[id].setDestinoId(1 + (i % 5)); // Palermo..Retiro para demo
            }
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
        idxPedido.add(id, id);           // id -> id (índice = id)
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

    // =========================
    // NUEVA versión completa
    // =========================
    public void ingresarPedido(Scanner sc) {
        sc.nextLine(); // limpiar salto de línea previo

        System.out.println("\n=== NUEVO PEDIDO ===");
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        int clienteId = addCliente(nombre);

        // Mostrar platos disponibles antes de elegir
        System.out.println("\nPlatos disponibles:");
        for (int i = 1; i < nextPlatoId; i++) {
            plato pl = platos[i];
            if (pl != null) System.out.println("  " + i + ") " + pl.getNombre());
        }

        System.out.print("\nIngrese la cantidad de platos: ");
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Ingrese la cantidad de platos: "); }
        int cant = sc.nextInt();
        while (cant <= 0) {
            System.out.print("Cantidad inválida. Ingrese nuevamente: ");
            while (!sc.hasNextInt()) { sc.next(); }
            cant = sc.nextInt();
        }

        int[] platosIds = new int[cant];
        for (int i = 0; i < cant; i++) {
            System.out.print("Seleccione ID del plato #" + (i + 1) + ": ");
            while (!sc.hasNextInt()) { sc.next(); System.out.print("Seleccione ID del plato #" + (i + 1) + ": "); }
            int elegido = sc.nextInt();
            if (elegido <= 0 || elegido >= nextPlatoId || platos[elegido] == null) {
                System.out.println("ID inválido. Intente nuevamente.");
                i--;
            } else {
                platosIds[i] = elegido;
            }
        }

        System.out.print("Tipo de pedido (0 = para llevar, 1 = domicilio): ");
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Tipo de pedido (0 = llevar, 1 = domicilio): "); }
        int tipo = sc.nextInt();
        while (tipo != pedido.TIPO_LLEVAR && tipo != pedido.TIPO_DOMICILIO) {
            System.out.print("Valor inválido. Ingrese 0 (llevar) o 1 (domicilio): ");
            while (!sc.hasNextInt()) { sc.next(); }
            tipo = sc.nextInt();
        }

        int zonaDestino = 0;
        if (tipo == pedido.TIPO_DOMICILIO) {
            // Lista de zonas (1..15) – alineada con el grafo de GestorReparto
            String[] zonas = {
                    "", "Palermo","Recoleta","Almagro","Belgrano","Retiro",
                    "San Telmo","Caballito","Flores","Núñez","Puerto Madero",
                    "La Boca","Liniers","Saavedra","Mataderos","Villa Urquiza"
            };
            System.out.println("\nSeleccione la zona de entrega:");
            for (int i = 1; i <= 15; i++) {
                System.out.println(i + ") " + zonas[i]);
            }
            System.out.print("Zona destino (1–15): ");
            while (!sc.hasNextInt()) { sc.next(); System.out.print("Zona destino (1–15): "); }
            zonaDestino = sc.nextInt();
            while (zonaDestino < 1 || zonaDestino > 15) {
                System.out.print("Valor inválido. Zona destino (1–15): ");
                while (!sc.hasNextInt()) { sc.next(); }
                zonaDestino = sc.nextInt();
            }
        }

        System.out.print("Prioridad (1 = VIP, 2 = Normal): ");
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Prioridad (1 = VIP, 2 = Normal): "); }
        int prioridad = sc.nextInt();
        while (prioridad != pedido.PRIORIDAD_VIP && prioridad != pedido.PRIORIDAD_NORMAL) {
            System.out.print("Valor inválido. Ingrese 1 (VIP) o 2 (Normal): ");
            while (!sc.hasNextInt()) { sc.next(); }
            prioridad = sc.nextInt();
        }

        int id = crearPedido(clienteId, platosIds, tipo, prioridad);

        // Si es a domicilio, guardar destino en el pedido
        if (tipo == pedido.TIPO_DOMICILIO) {
            pedidos[id].setDestinoId(zonaDestino);
            System.out.println("Zona de entrega seleccionada: " + zonaDestino);
        }

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
                Arrays.stream(p.getPlatosIds()).mapToObj(id -> getPlato(id).getNombre()).toArray()
        );
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

    // --- 12. Ver pedidos entregados ---
    public void verEntregados() {
        System.out.println("\n--- Pedidos ENTREGADOS ---");
        boolean hay = false;
        for (int i = 1; i < nextPedidoId; i++) {
            pedido p = pedidos[i];
            if (p != null && p.getEstado() == pedido.Estado.ENTREGADO) {
                System.out.println(describirPedido(p));
                hay = true;
            }
        }
        if (!hay) System.out.println("No hay pedidos entregados todavía.");
    }

    // --- 13. Cancelar pedido ---
    public void cancelarPedido(Scanner sc) {
        System.out.println("\nIngrese ID del pedido a cancelar:");
        int id = sc.nextInt();
        pedido p = pedidos[id];
        if (p == null) {
            System.out.println("❌ Pedido inexistente.");
            return;
        }
        if (p.getEstado() == pedido.Estado.ENTREGADO) {
            System.out.println("❌ El pedido ya fue entregado, no puede cancelarse.");
            return;
        }
        p.setEstado(pedido.Estado.CANCELADO);
        System.out.println("🚫 Pedido #" + id + " cancelado correctamente.");
    }

    // --- 16. Ver pedidos en cocina ---
    public void verEnCocina() {
        System.out.println("\n--- Pedidos EN COCINA ---");
        boolean hay = false;
        for (int i = 1; i < nextPedidoId; i++) {
            pedido p = pedidos[i];
            if (p != null && p.getEstado() == pedido.Estado.EN_COCINA) {
                System.out.println(describirPedido(p));
                hay = true;
            }
        }
        if (!hay) System.out.println("No hay pedidos en cocina actualmente.");
    }

    // --- 17. Ver menú completo ---
    public void verMenu() {
        System.out.println("\n=== MENÚ DEL RESTAURANTE ===");
        for (int i = 1; i < nextPlatoId; i++) {
            if (platos[i] != null) {
                System.out.println("ID " + i + " - " + platos[i].getNombre());
            }
        }
    }

    // --- 18. Agregar nuevo plato ---
    public void agregarPlato(Scanner sc) {
        sc.nextLine(); // limpiar buffer
        System.out.print("\nIngrese nombre del nuevo plato: ");
        String nombre = sc.nextLine();
        int id = addPlato(nombre);
        System.out.println("✅ Plato agregado con éxito: [" + id + "] " + nombre);
    }

}
