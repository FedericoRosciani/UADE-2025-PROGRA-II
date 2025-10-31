package main.java.org.uade.service;

import main.java.org.uade.model.pedido;

public class GestorCocina {

    public void procesarPedido(GestorPedido gestor) {
        Integer id = gestor.tomarSiguienteParaCocina();
        if (id == null) {
            System.out.println("No hay pedidos en espera.");
            return;
        }
        pedido p = gestor.getPedido(id);
        // Simulación simple: procesar todos los platos y marcar listo
        gestor.marcarListo(id);
        System.out.println("Pedido #" + id + " listo en cocina.");
    }
}
