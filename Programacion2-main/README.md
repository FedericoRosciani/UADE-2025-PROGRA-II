Sistema de Gestión y Reparto de Pedidos — TP Programación 2
Integrantes
Federico Rosciani

Descripción general del proyecto

Este trabajo práctico implementa un sistema completo de gestión de pedidos para un restaurante, incluyendo:

Registro de pedidos

Manejo de cola con prioridades (VIP / Normal)

Procesamiento en cocina

Asignación inteligente de repartidores

Envíos con rutas mínimas entre zonas (Dijkstra)

Reportes generales

Uso de estructuras de datos (TDAs) construidas por el grupo

El proyecto está desarrollado en Java aplicando TDAs personalizados, sin usar colecciones nativas de Java.

TDAs utilizados y motivo de selección
1. PriorityQueueADT (Cola con prioridad)

Dónde se usa:

Para ordenar los pedidos según su prioridad (VIP primero).

Motivo de la elección:

Permite extraer siempre el pedido más urgente sin recorrer toda la lista.

Refleja el comportamiento real del restaurante.

2. SimpleDictionaryADT

Dónde se usa:

Índice pedidoId → posición en el array

Conteo de platos más pedidos

Relación pedidoId → repartidorId

Motivo:

Acceso directo en O(1) para búsquedas frecuentes.

Es simple y eficiente para mapear claves a valores únicos.

3. MultipleDictionaryADT

Dónde se usa:

Relación clienteId → lista de pedidos del cliente.

Motivo:

Un cliente puede tener múltiples pedidos.

Facilita reportes y consultas sin escanear toda la estructura.

4. QueueADT

Dónde se usa:

Cola de repartidores libres

Cola de pedidos listos para reparto

Motivo:

Representa correctamente un flujo FIFO (primero en entrar, primero en salir).

Modelo simple y eficiente para manejo de turnos.

5. SetADT

Dónde se usa:

Conjunto de repartidores ocupados.

Motivo:

Evita duplicados.

Permite saber rápidamente quién está libre/ocupado.

6. GraphADT

Dónde se usa:

Mapa de zonas de la Ciudad de Buenos Aires.

Cálculo de ruta mínima entre zonas con Dijkstra.

Motivo:

Permite modelar distancias y caminos reales.

Base para asignación de repartidores más cercanos.

Decisiones de diseño

Separación en módulos (GestorPedido, GestorCocina, GestorReparto)

Facilita el mantenimiento y evita acoplamiento excesivo.

Pedido pasa por estados definidos

NUEVO → EN_COCINA → LISTO → ASIGNADO → ENTREGADO

Esto permite validar correctamente el flujo y evitar errores.

Asignación inteligente de repartidores

Utiliza distancia mínima + repartidor con menos entregas.

Reglas para evitar inconsistencias

No se puede marcar un pedido como LISTO si no estuvo en cocina.

No se puede asignar un pedido no LISTO.

No se puede cancelar un pedido entregado.

Datos precargados para demo

Platos

Clientes

Zonas

Repartidores

Pedidos de ejemplo

Cómo ejecutar

Abrir el proyecto en IntelliJ.

Ejecutar Main.java.

Navegar con el menú interactivo.

Pruebas (Unit Tests)

Se incluyen tests con JUnit para validar:

Que un pedido VIP se procesa antes que uno Normal.

Que un pedido solo puede pasar a LISTO si estuvo antes en cocina.