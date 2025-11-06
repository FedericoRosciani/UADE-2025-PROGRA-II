package org.uade.util;
import java.util.Scanner;
import main.java.org.uade.service.*;
import main.java.org.uade.model.pedido;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        GestorPedido gestorPedidos = new GestorPedido();
        GestorCocina gestorCocina = new GestorCocina();
        GestorReparto gestorReparto = new GestorReparto();
        Reporte reportes = new Reporte();

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE PEDIDOS ===");
            System.out.println("1. Registrar nuevo pedido");
            System.out.println("2. Ver pedidos pendientes");
            System.out.println("3. Procesar siguiente pedido en cocina");
            System.out.println("4. Marcar pedido LISTO (manual)");
            System.out.println("5. Encolar pedido LISTO para reparto");
            System.out.println("6. Asignar pedido a repartidor (muestra camino más corto)");
            System.out.println("7. Finalizar entrega");
            System.out.println("8. Ver reportes");
            System.out.println("9. Ver reportes");
            System.out.println("10. Ver ubicaciones de repartidores");
            System.out.println("11. Ver mapa de repartidores (vista gráfica)");
            System.out.println("12. Ver pedidos entregados");
            System.out.println("13. Cancelar pedido");
            System.out.println("14. Ver pedidos en cocina");
            System.out.println("15. Ver menú completo");
            System.out.println("16. Agregar nuevo plato");
            System.out.println("17. Agregar nuevo repartidor");
            System.out.println("19. Salir");
            System.out.print("Opción: ");
            while (!sc.hasNextInt()) {
                sc.next();
                System.out.print("Opción: ");
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    gestorPedidos.ingresarPedido(sc);
                    break;

                case 2:
                    gestorPedidos.verPendientes();
                    break;

                case 3:
                    gestorCocina.procesarPedido(gestorPedidos);
                    break;

                case 4:
                    System.out.print("ID de pedido a marcar LISTO: ");
                    int idListo = sc.nextInt();
                    gestorPedidos.marcarListo(idListo);
                    System.out.println("Pedido #" + idListo + " marcado como LISTO.");
                    break;

                case 5:
                    System.out.print("ID de pedido LISTO a encolar para reparto: ");
                    int idParaReparto = sc.nextInt();
                    if (gestorPedidos.getPedido(idParaReparto) != null &&
                            gestorPedidos.getPedido(idParaReparto).getEstado() == pedido.Estado.LISTO &&
                            gestorPedidos.getPedido(idParaReparto).getTipo() == pedido.TIPO_DOMICILIO) {

                        // ✅ Se encola y se muestra la zona
                        gestorReparto.agregarPedidoListo(idParaReparto);
                        System.out.println("Pedido #" + idParaReparto + " agregado a la cola de reparto.");
                    } else {
                        System.out.println("El pedido no está LISTO o no es a domicilio.");
                    }
                    break;

                case 6:
                    // ✅ Ahora al asignar, muestra el camino más corto con las zonas reales
                    gestorReparto.asignarPedido(gestorPedidos);
                    break;

                case 7:
                    gestorReparto.finalizarEntrega(sc, gestorPedidos);
                    break;

                case 8:
                    reportes.mostrarReportes(gestorPedidos, gestorReparto);
                    break;

                case 9:

                    break;

                case 10:
                    gestorReparto.verUbicacionesRepartidores();
                    break;
                case 11:
                    gestorReparto.mostrarMapaRepartidores();
                    break;
                case 12:
                    gestorPedidos.verEntregados();
                    break;

                case 13:
                    gestorPedidos.cancelarPedido(sc);
                    break;

                case 14:
                    gestorPedidos.verEnCocina();
                    break;

                case 15:
                    gestorPedidos.verMenu();
                    break;

                case 16:
                    gestorPedidos.agregarPlato(sc);
                    break;

                case 17:
                    gestorReparto.agregarRepartidor(sc);
                    break;

                case 19:
                    System.out.println("Cerrando sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 9);

        sc.close();
    }
}
