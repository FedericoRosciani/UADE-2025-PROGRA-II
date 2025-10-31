package main.java.org.uade.service;

import main.java.org.uade.model.pedido;

public class Reporte {

    public void mostrarReportes(GestorPedido gp, GestorReparto gr) {
        int pendientes = gp.contarPendientesDespacho();
        int finalizados = gp.contarFinalizados();
        int mejorClienteId = gp.clienteConMasPedidos();
        int bestPlatoId = gp.platoMasPedido();

        System.out.println("\n=== REPORTES ===");
        System.out.println("Pedidos pendientes de ser despachados: " + pendientes);
        System.out.println("Pedidos finalizados: " + finalizados);

        if (mejorClienteId != -1) {
            System.out.println("Cliente con mayor número de pedidos: ID " + mejorClienteId);
        } else {
            System.out.println("Cliente con mayor número de pedidos: N/D");
        }

        if (bestPlatoId != -1) {
            System.out.println("Plato más pedido: ID " + bestPlatoId);
        } else {
            System.out.println("Plato más pedido: N/D");
        }

        gr.imprimirEntregasPorRepartidor();
    }

    // helper por si querés marcar listo e ingresar a cola de reparto en un paso
    public void empujarListoAReparto(GestorPedido gp, GestorReparto gr, int pedidoId) {
        pedido p = gp.getPedido(pedidoId);
        if (p == null) return;
        if (p.getEstado() == pedido.Estado.LISTO && p.getTipo() == pedido.TIPO_DOMICILIO) {
            gr.agregarPedidoListo(pedidoId);
        }
    }
}
