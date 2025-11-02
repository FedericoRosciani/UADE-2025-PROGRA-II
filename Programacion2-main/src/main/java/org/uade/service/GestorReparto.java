package main.java.org.uade.service;

import java.util.Scanner;

import main.java.org.uade.model.pedido;
import main.java.org.uade.model.repartidor;

import main.java.org.uade.structure.definition.QueueADT;
import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;
import main.java.org.uade.structure.definition.GraphADT;

import main.java.org.uade.structure.implementation.DynamicQueueADT;
import main.java.org.uade.structure.implementation.DynamicSetADT;
import main.java.org.uade.structure.implementation.DynamicSimpleDictionaryADT;
import main.java.org.uade.structure.implementation.DynamicGraphADT;

public class GestorReparto {

    // --- Atributos principales ---
    private final repartidor[] repartidores = new repartidor[500];
    private int nextRepartidorId = 1;
    // Zona base del restaurante (Palermo = 1)
    private static final int ZONA_RESTAURANTE = 1;


    private final QueueADT libres;            // ids de repartidor libre
    private final SetADT ocupados;            // ids en reparto
    private final QueueADT pedidosListos;     // ids de pedido listos para envío
    private final SimpleDictionaryADT pedido2repartidor; // pedidoId -> repartidorId

    private final GraphADT grafoZonas;        // grafo de zonas de Buenos Aires
    private final String[] zonas = new String[16]; // índice 1..15

    // --- Constructor ---
    public GestorReparto() {
        this.libres = new DynamicQueueADT();
        this.ocupados = new DynamicSetADT();
        this.pedidosListos = new DynamicQueueADT();
        this.pedido2repartidor = new DynamicSimpleDictionaryADT();
        this.grafoZonas = new DynamicGraphADT(15);  // ✅ corregido: grafo con 15 zonas
        precargarZonasBuenosAires();
        precargarRepartidores();
    }

    // --- Precarga de zonas de Buenos Aires ---
    private void precargarZonasBuenosAires() {
        String[] nombres = {
                "", "Palermo","Recoleta","Almagro","Belgrano","Retiro",
                "San Telmo","Caballito","Flores","Núñez","Puerto Madero",
                "La Boca","Liniers","Saavedra","Mataderos","Villa Urquiza"
        };
        for (int i = 1; i <= 15; i++) {
            grafoZonas.addVertx(i);
            zonas[i] = nombres[i];
        }

        grafoZonas.addEdge(1,2,3); grafoZonas.addEdge(1,3,2); grafoZonas.addEdge(1,4,4);
        grafoZonas.addEdge(2,5,3); grafoZonas.addEdge(2,6,5);
        grafoZonas.addEdge(3,7,3); grafoZonas.addEdge(3,8,4);
        grafoZonas.addEdge(4,5,2); grafoZonas.addEdge(4,9,5);
        grafoZonas.addEdge(5,10,5);
        grafoZonas.addEdge(6,7,4); grafoZonas.addEdge(6,11,3);
        grafoZonas.addEdge(7,8,2);
        grafoZonas.addEdge(8,12,3);
        grafoZonas.addEdge(9,13,3);
        grafoZonas.addEdge(10,11,2);
        grafoZonas.addEdge(12,14,5);
        grafoZonas.addEdge(13,15,4);
        grafoZonas.addEdge(14,15,6);
    }

    // --- Precarga de repartidores ---
    private void precargarRepartidores() {
        // 10 repartidores distribuidos en zonas distintas (1..15)
        int[] zonasAsignadas = {1, 2, 3, 4, 5, 6, 8, 10, 12, 14};

        for (int i = 0; i < 10; i++) {
            int id = addRepartidor("Repartidor " + (i + 1));
            repartidores[id].setUbicacionActual(zonasAsignadas[i]);
            libres.add(id);
        }

        System.out.println("Se cargaron 10 repartidores distribuidos en distintas zonas de Buenos Aires:");
        for (int i = 1; i <= 10; i++) {
            repartidor r = repartidores[i];
            if (r != null)
                System.out.println(" - " + r.getNombre() + " en zona " + zonas[r.getUbicacionActual()]);
        }
    }


    public int addRepartidor(String nombre) {
        int id = nextRepartidorId++;
        repartidores[id] = new repartidor(id, nombre);
        return id;
    }

    public repartidor getRepartidor(int id) { return repartidores[id]; }

    public void agregarPedidoListo(int pedidoId) {
        pedidosListos.add(pedidoId);
    }

    // --- Asignación de pedido a repartidor ---
// --- Asignación inteligente de pedido a repartidor ---
// --- Asignación automática: elige al repartidor más cercano y más libre ---
    public void asignarPedido(GestorPedido gestor) {
        if (pedidosListos.isEmpty()) {
            System.out.println("No hay pedidos listos para asignar.");
            return;
        }

        int pedidoId = pedidosListos.getElement();
        pedidosListos.remove();

        pedido p = gestor.getPedido(pedidoId);
        if (p == null) {
            System.out.println("Pedido inexistente.");
            return;
        }

        if (p.getTipo() != pedido.TIPO_DOMICILIO) {
            System.out.println("El pedido #" + pedidoId + " es para llevar, no requiere reparto.");
            return;
        }

        if (libres.isEmpty()) {
            System.out.println("No hay repartidores disponibles. Pedido en espera.");
            return;
        }

        int zonaDestino = p.getDestinoId();
        int mejorRepartidor = -1;
        int mejorDistancia = Integer.MAX_VALUE;
        int menorEntregas = Integer.MAX_VALUE;

        // Buscar el repartidor más cercano y más libre
        for (int i = 1; i < nextRepartidorId; i++) {
            repartidor r = repartidores[i];
            if (r == null || !r.isDisponible()) continue;

            int zonaRep = r.getUbicacionActual();
            int distancia = calcularDistanciaMinima(zonaRep, ZONA_RESTAURANTE); // hasta el restaurante

            if (distancia < mejorDistancia ||
                    (distancia == mejorDistancia && r.getEntregas() < menorEntregas)) {
                mejorDistancia = distancia;
                menorEntregas = r.getEntregas();
                mejorRepartidor = i;
            }
        }

        if (mejorRepartidor == -1) {
            System.out.println("No se encontró repartidor disponible para esa zona.");
            return;
        }

        repartidor elegido = repartidores[mejorRepartidor];
        elegido.setDisponible(false);
        ocupados.add(mejorRepartidor);
        pedido2repartidor.add(pedidoId, mejorRepartidor);
        p.setEstado(pedido.Estado.ASIGNADO);

        // --- Simulación de recorrido ---
        System.out.println("\n✅ Pedido #" + pedidoId + " asignado a " + elegido.getNombre() +
                " (zona actual: " + zonas[elegido.getUbicacionActual()] + ")");

        // 1️⃣ Ir desde su zona actual al restaurante
        System.out.println("\n➡️  Repartidor " + elegido.getNombre() +
                " se dirige al restaurante (" + zonas[ZONA_RESTAURANTE] + ")");
        mostrarCaminoMasCorto(elegido.getUbicacionActual(), ZONA_RESTAURANTE);

        // 2️⃣ Luego desde el restaurante hasta el cliente
        System.out.println("\n➡️  Repartidor toma el pedido y sale hacia el cliente (" + zonas[zonaDestino] + ")");
        mostrarCaminoMasCorto(ZONA_RESTAURANTE, zonaDestino);

        // 3️⃣ Actualizar su nueva ubicación
        elegido.setUbicacionActual(zonaDestino);

        System.out.println("🚩 Pedido entregado en la zona: " + zonas[zonaDestino] + "\n");
    }

    // --- Mostrar camino más corto entre zonas (versión simple) ---
    // --- Mostrar camino más corto entre zonas (versión con Dijkstra) ---
    public void mostrarCaminoMasCorto(int origen, int destino) {
        System.out.println("\nCalculando camino más corto entre " + zonas[origen] + " y " + zonas[destino] + "...");

        // Número total de zonas
        int n = 15;
        int[] dist = new int[n + 1];
        boolean[] visitado = new boolean[n + 1];
        int[] previo = new int[n + 1];

        // Inicialización
        final int INF = 9999;
        for (int i = 1; i <= n; i++) {
            dist[i] = INF;
            visitado[i] = false;
            previo[i] = -1;
        }
        dist[origen] = 0;

        // Algoritmo de Dijkstra básico
        for (int count = 1; count <= n; count++) {
            // Encontrar el nodo no visitado con menor distancia
            int u = -1;
            int minDist = INF;
            for (int i = 1; i <= n; i++) {
                if (!visitado[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }
            if (u == -1) break; // sin más caminos
            visitado[u] = true;

            // Actualizar distancias de los vecinos
            for (int v = 1; v <= n; v++) {
                if (grafoZonas.existsEdge(u, v)) {
                    int peso = grafoZonas.edgeWeight(u, v);
                    if (!visitado[v] && dist[u] + peso < dist[v]) {
                        dist[v] = dist[u] + peso;
                        previo[v] = u;
                    }
                }
            }
        }

        // Si no hay camino
        if (dist[destino] == INF) {
            System.out.println("No existe conexión posible entre esas zonas.\n");
            return;
        }

        // Reconstruir el camino
        StringBuilder camino = new StringBuilder();
        int actual = destino;
        while (actual != -1) {
            camino.insert(0, zonas[actual]);
            actual = previo[actual];
            if (actual != -1) camino.insert(0, " → ");
        }

        // Mostrar resultado
        System.out.println("Camino más corto: " + camino);
        System.out.println("Distancia total: " + dist[destino] + " km");
        double tiempo = dist[destino] * 3.0; // 3 minutos por km
        System.out.println("Tiempo estimado: " + tiempo + " minutos\n");
    }

    // --- Calcula solo la distancia mínima (sin imprimir camino) ---
    private int calcularDistanciaMinima(int origen, int destino) {
        int n = 15;
        int[] dist = new int[n + 1];
        boolean[] visitado = new boolean[n + 1];
        final int INF = 9999;

        for (int i = 1; i <= n; i++) {
            dist[i] = INF;
            visitado[i] = false;
        }
        dist[origen] = 0;

        for (int count = 1; count <= n; count++) {
            int u = -1, minDist = INF;
            for (int i = 1; i <= n; i++) {
                if (!visitado[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    u = i;
                }
            }
            if (u == -1) break;
            visitado[u] = true;

            for (int v = 1; v <= n; v++) {
                if (grafoZonas.existsEdge(u, v)) {
                    int peso = grafoZonas.edgeWeight(u, v);
                    if (!visitado[v] && dist[u] + peso < dist[v]) {
                        dist[v] = dist[u] + peso;
                    }
                }
            }
        }
        return dist[destino];
    }


    // --- Finalizar entrega ---
    public void finalizarEntrega(Scanner sc, GestorPedido gestor) {
        System.out.println("Ingrese ID de pedido entregado:");
        int pedidoId = sc.nextInt();
        pedido p = gestor.getPedido(pedidoId);
        if (p == null || p.getEstado() != pedido.Estado.ASIGNADO) {
            System.out.println("Pedido no asignado o inexistente.");
            return;
        }

        int repId;
        try { repId = pedido2repartidor.get(pedidoId); }
        catch (Exception e) {
            System.out.println("No se encontró repartidor para el pedido.");
            return;
        }

        p.setEstado(pedido.Estado.ENTREGADO);
        repartidor r = repartidores[repId];
        r.setDisponible(true);
        r.incEntregas();
        ocupados.remove(repId);
        libres.add(repId);

        System.out.println("Entrega finalizada. Pedido #" + pedidoId + " entregado por " + r.getNombre());
    }

    // --- Reportes ---
    public void imprimirEntregasPorRepartidor() {
        System.out.println("\n--- Entregas por repartidor ---");
        for (int i = 1; i < nextRepartidorId; i++) {
            repartidor r = repartidores[i];
            if (r == null) continue;
            System.out.println(r.getNombre() + ": " + r.getEntregas());
        }
    }
    // --- Mostrar ubicaciones de repartidores ---
    public void verUbicacionesRepartidores() {
        System.out.println("\n=== UBICACIONES DE REPARTIDORES ===");
        for (int i = 1; i < nextRepartidorId; i++) {
            repartidor r = repartidores[i];
            if (r == null) continue;
            String zona = zonas[r.getUbicacionActual()];
            String estado = r.isDisponible() ? "🟢 Libre" : "🔴 Ocupado";
            System.out.println("ID " + r.getId() + " - " + r.getNombre() + " | Zona: " + zona + " | " + estado);
        }
    }

    // --- Mapa visual de repartidores ---
    public void mostrarMapaRepartidores() {
        System.out.println("\n🗺️  MAPA DE REPARTIDORES EN CABA");
        System.out.println("--------------------------------------------------");

        String[][] mapa = {
                {"1) Palermo", "2) Recoleta", "3) Almagro", "4) Belgrano", "5) Retiro"},
                {"6) San Telmo", "7) Caballito", "8) Flores", "9) Núñez", "10) Puerto Madero"},
                {"11) La Boca", "12) Liniers", "13) Saavedra", "14) Mataderos", "15) Villa Urquiza"}
        };

        // Imprimir mapa base
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int col = 0; col < mapa[fila].length; col++) {
                System.out.printf("%-20s", mapa[fila][col]);
            }
            System.out.println();
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Ubicación actual de repartidores:");
        System.out.println("--------------------------------------------------");

        // Mostrar repartidores según su zona actual
        for (int zona = 1; zona <= 15; zona++) {
            StringBuilder repEnZona = new StringBuilder();
            for (int i = 1; i < nextRepartidorId; i++) {
                repartidor r = repartidores[i];
                if (r == null) continue;
                if (r.getUbicacionActual() == zona) {
                    repEnZona.append("[").append(r.getNombre()).append("] ");
                }
            }
            if (repEnZona.length() > 0)
                System.out.println(zonas[zona] + ": " + repEnZona);
        }

        System.out.println("--------------------------------------------------");
    }

    // --- 24. Agregar nuevo repartidor ---
    public void agregarRepartidor(Scanner sc) {
        sc.nextLine();
        System.out.print("\nIngrese nombre del nuevo repartidor: ");
        String nombre = sc.nextLine();
        int id = addRepartidor(nombre);

        System.out.println("Seleccione zona inicial del repartidor:");
        for (int i = 1; i <= 15; i++) {
            System.out.println(i + ") " + zonas[i]);
        }

        int zona = sc.nextInt();
        if (zona < 1 || zona > 15) zona = 1; // default a Palermo
        repartidores[id].setUbicacionActual(zona);
        libres.add(id);

        System.out.println("✅ Repartidor agregado correctamente: " + nombre + " en " + zonas[zona]);
    }


}
