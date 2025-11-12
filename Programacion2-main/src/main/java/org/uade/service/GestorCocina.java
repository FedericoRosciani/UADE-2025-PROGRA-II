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

        // AHORA el pedido queda EN_COCINA, NO lo marcamos LISTO acá.
        System.out.println("Pedido #" + id + " está siendo preparado en cocina.");
    }
}
