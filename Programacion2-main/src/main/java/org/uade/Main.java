package main.java.org.uade.util;

import main.java.org.uade.model.Cliente;
import main.java.org.uade.model.pedido;
import main.java.org.uade.model.plato;
import main.java.org.uade.model.repartidor;

import main.java.org.uade.service.GestorPedido;
import main.java.org.uade.service.GestorReparto;
import main.java.org.uade.service.GestorCocina;
import main.java.org.uade.service.Reporte;

import java.util.Scanner;

import java.util.Scanner;
import main.java.org.uade.service.*;
import main.java.org.uade.model.pedido;

public class Main {

    // --- MENÚ ORDENADO ---
    private static void mostrarMenu() {
        System.out.println("\n========== SISTEMA DE GESTIÓN DE PEDIDOS ==========\n");

        System.out.println(" 📦  PEDIDOS");
        System.out.println("  1) Registrar nuevo pedido");
        System.out.println("  2) Ver pedidos pendientes");
        System.out.println("  3) Procesar siguiente pedido en cocina");
        System.out.println("  4) Marcar pedido LISTO (manual)");
        System.out.println("  5) Encolar pedido LISTO para reparto");
        System.out.println("  6) Asignar pedido a repartidor (muestra camino más corto)");
        System.out.println("  7) Take Away");
        System.out.println("  8) Ver reportes");
        System.out.println("  9) Resumen general del sistema\n");

        System.out.println(" 🛵  REPARTIDORES");
        System.out.println(" 10) Ver ubicaciones de repartidores");
        System.out.println(" 11) Ver mapa de repartidores (vista gráfica)\n");

        System.out.println(" 🍽️  CONSULTAS DE PEDIDOS");
        System.out.println(" 12) Ver pedidos entregados");
        System.out.println(" 13) Cancelar pedido");
        System.out.println(" 14) Ver pedidos en cocina\n");

        System.out.println(" ⚙️  ADMINISTRACIÓN");
        System.out.println(" 15) Ver menú completo");
        System.out.println(" 16) Agregar nuevo plato");
        System.out.println(" 17) Agregar nuevo repartidor\n");

        System.out.println(" 🚪  SALIDA");
        System.out.println(" 18) Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorPedido gestorPedidos = new GestorPedido();
        GestorCocina gestorCocina = new GestorCocina();
        GestorReparto gestorReparto = new GestorReparto();
        Reporte reportes = new Reporte();

        int opcion;
        do {
            mostrarMenu();

            while (!sc.hasNextInt()) {
                sc.next();
                System.out.print("Seleccione una opción válida: ");
            }
            opcion = sc.nextInt();

            switch (opcion) {

                // === PEDIDOS ===
                case 1 -> gestorPedidos.ingresarPedido(sc);
                case 2 -> gestorPedidos.verPendientes();
                case 3 -> gestorCocina.procesarPedido(gestorPedidos);
                case 4 -> {
                    System.out.print("ID de pedido a marcar LISTO: ");
                    int idListo = sc.nextInt();
                    gestorPedidos.marcarListo(idListo); // El método ya imprime el mensaje correcto
                }
                case 5 -> {
                    System.out.print("ID de pedido LISTO a encolar para reparto: ");
                    int idParaReparto = sc.nextInt();
                    var ped = gestorPedidos.getPedido(idParaReparto);
                    if (ped != null &&
                            ped.getEstado() == pedido.Estado.LISTO &&
                            ped.getTipo() == pedido.TIPO_DOMICILIO) {
                        gestorReparto.agregarPedidoListo(idParaReparto);
                        System.out.println("Pedido #" + idParaReparto + " agregado a la cola de reparto.");
                    } else {
                        System.out.println("El pedido no está LISTO o no es a domicilio.");
                    }
                }
                case 6 -> gestorReparto.asignarPedido(gestorPedidos);
                case 7 -> gestorReparto.finalizarEntrega(sc, gestorPedidos);
                case 8 -> reportes.mostrarReportes(gestorPedidos, gestorReparto);
                case 9 -> {
                    System.out.println("\n===== REPORTE GENERAL DEL SISTEMA =====");

                    int pendientes = gestorPedidos.contarPendientesDespacho();
                    int finalizados = gestorPedidos.contarFinalizados();

                    int mejorClienteId = gestorPedidos.clienteConMasPedidos();
                    int mejorPlatoId = gestorPedidos.platoMasPedido();

                    // --- Datos base ---
                    System.out.println("Pedidos pendientes de despacho: " + pendientes);
                    System.out.println("Pedidos finalizados (entregados): " + finalizados);

                    // --- Mejor cliente ---
                    if (mejorClienteId != -1) {
                        Cliente cli = gestorPedidos.getCliente(mejorClienteId);
                        System.out.println("Cliente con más pedidos: " + cli.getNombre() + " (ID " + mejorClienteId + ")");
                    } else {
                        System.out.println("Cliente con más pedidos: N/A");
                    }

                    // --- Plato más pedido ---
                    if (mejorPlatoId != -1) {
                        plato pl = gestorPedidos.getPlato(mejorPlatoId);
                        System.out.println("Plato más pedido: " + pl.getNombre());
                    } else {
                        System.out.println("Plato más pedido: N/A");
                    }

                    System.out.println("Fecha de generación del reporte: " + java.time.LocalDateTime.now());
                    System.out.println("=========================================\n");
                }



                // === REPARTIDORES ===
                case 10 -> gestorReparto.verUbicacionesRepartidores();
                case 11 -> gestorReparto.mostrarMapaRepartidores();

                // === PEDIDOS EXTRA ===
                case 12 -> gestorPedidos.verEntregados();
                case 13 -> gestorPedidos.cancelarPedido(sc);
                case 14 -> gestorPedidos.verEnCocina();

                // === ADMINISTRACIÓN ===
                case 15 -> gestorPedidos.verMenu();
                case 16 -> gestorPedidos.agregarPlato(sc);
                case 17 -> gestorReparto.agregarRepartidor(sc);

                // === SALIR ===
                case 18 -> System.out.println("Cerrando sistema...");

                // === invalida opcion ===
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 18);
        // === para salir1 ===

        sc.close();
    }
}
