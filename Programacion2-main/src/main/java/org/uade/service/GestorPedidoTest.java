import main.java.org.uade.service.GestorPedido;
import main.java.org.uade.model.pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorPedidoTest {
    @Test
    public void testPrioridadVIPPrimero() {
        GestorPedido gestor = new GestorPedido();

        // Crear dos pedidos: VIP y Normal
        int clienteA = gestor.addCliente("Test VIP");
        int clienteB = gestor.addCliente("Test Normal");
        int[] platos = new int[]{1};

        int vipId = gestor.crearPedido(clienteA, platos, pedido.TIPO_LLEVAR, pedido.PRIORIDAD_VIP);
        int normalId = gestor.crearPedido(clienteB, platos, pedido.TIPO_LLEVAR, pedido.PRIORIDAD_NORMAL);

        // Tomar el siguiente pedido a procesar
        int primero = gestor.tomarSiguienteParaCocina();

        // Afirmar que el primero en salir es el VIP
        assertEquals(vipId, primero, "El pedido VIP debería procesarse antes que el Normal");
    }
}
