package main.java.org.uade.service;

import java.util.Scanner;

import main.java.org.uade.model.pedido;
import main.java.org.uade.model.repartidor;

import main.java.org.uade.structure.definition.QueueADT;
import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;

import main.java.org.uade.structure.implementation.DynamicQueueADT;
import main.java.org.uade.structure.implementation.DynamicSetADT;
import main.java.org.uade.structure.implementation.DynamicSimpleDictionaryADT;

public class GestorReparto {

    private final repartidor[] repartidores = new repartidor[500];
    private int nextRepartidorId = 1;

    private final QueueADT libres;            // ids de repartidor libre
    private final SetADT ocupados;            // ids en reparto
    private final QueueADT pedidosListos;     // ids de pedido listos para envío
    private final SimpleDictionaryADT pedido2repartidor; // pedidoId -> repartidorId

    public GestorReparto() {
        this.libres = new DynamicQueueADT();
        this.ocupados = new DynamicSetADT();
        this.pedidosListos = new DynamicQueueADT();
        this.pedido2repartidor = new DynamicSimpleDictionaryADT();
        precargarRepartidores();
    }

    private void precargarRepartidores() {
        for (int i = 0; i < 10; i++) {
            int id = addRepartidor("Repartidor " + (i + 1));
            libres.add(id);
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

    public void asignarPedido(GestorPedido gestor) {
        // buscar uno listo
        if (pedidosListos.isEmpty()) {
            // arrastrar de cocina si hay alguno LISTO manualmente marcado
            // se asume que GestorCocina ya llamó a marcarListo y alguien llamó a agregarPedidoListo.
            System.out.println("No hay pedidos listos para asignar.");
            return;
        }
        if (libres.isEmpty()) {
            System.out.println("No hay repartidores disponibles. Pedido queda en espera de asignación.");
            return;
        }

        int pedidoId = pedidosListos.getElement(); pedidosListos.remove();
        int repId = libres.getElement(); libres.remove();

        pedido p = gestor.getPedido(pedidoId);
        if (p == null) {
            System.out.println("Pedido inexistente.");
            return;
        }
        p.setEstado(pedido.Estado.ASIGNADO);

        repartidor r = repartidores[repId];
        r.setDisponible(false);
        ocupados.add(repId);

        pedido2repartidor.add(pedidoId, repId);

        System.out.println("Asignado Pedido #" + pedidoId + " a " + r.getNombre());
    }

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

        // cerrar ciclo
        p.setEstado(pedido.Estado.ENTREGADO);
        repartidor r = repartidores[repId];
        r.setDisponible(true);
        r.incEntregas();
        ocupados.remove(repId);
        libres.add(repId);

        System.out.println("Entrega finalizada. Pedido #" + pedidoId + " entregado por " + r.getNombre());
    }

    // Reportes
    public void imprimirEntregasPorRepartidor() {
        System.out.println("\n--- Entregas por repartidor ---");
        for (int i = 1; i < nextRepartidorId; i++) {
            repartidor r = repartidores[i];
            if (r == null) continue;
            System.out.println(r.getNombre() + ": " + r.getEntregas());
        }
    }
}
