import main.java.org.uade.service.GestorPedido;
import main.java.org.uade.model.plato;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorPedidoAgregarPlatoTest {

    @Test
    public void testAgregarPlato() {

        GestorPedido gestor = new GestorPedido();

        // Cantidad de platos antes
        int cantidadAntes = 5; // En tu TP siempre se precargan 5 platos

        // Agregar un plato nuevo
        int nuevoId = gestor.addPlato("Ravioles con salsa");

        // 1) El ID debe ser 6 (porque hay 5 precargados)
        assertEquals(cantidadAntes + 1, nuevoId,
                "El ID del nuevo plato debe ser " + (cantidadAntes + 1));

        // 2) Verificar que el plato se guardó correctamente
        plato pl = gestor.getPlato(nuevoId);
        assertNotNull(pl, "El plato recién agregado no debería ser null");

        // 3) El nombre debe coincidir
        assertEquals("Ravioles con salsa", pl.getNombre(),
                "El plato debe guardarse con el nombre correcto");
    }
}
