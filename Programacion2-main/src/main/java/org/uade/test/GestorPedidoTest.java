package main.java.org.uade.test;

import main.java.org.uade.service.GestorPedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorPedidoTest {

    @Test
    public void testPrioridadVIPPrimero() {

        // Usa los datos del constructor (con demo)
        GestorPedido gestor = new GestorPedido();

        // Procesar el primer pedido real del sistema
        int primero = gestor.tomarSiguienteParaCocina();

        // En la carga demo, el primer pedido VIP siempre es el #1
        assertEquals(1, primero,
                "El primer pedido procesado debe ser el VIP precargado (#1)");
    }
}

