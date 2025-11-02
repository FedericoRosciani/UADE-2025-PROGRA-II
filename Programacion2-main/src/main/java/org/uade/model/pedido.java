package main.java.org.uade.model;

public class pedido {
    public static final int TIPO_LLEVAR = 0;
    public static final int TIPO_DOMICILIO = 1;

    public static final int PRIORIDAD_VIP = 1;     // menor número = mayor prioridad
    public static final int PRIORIDAD_NORMAL = 2;

    public enum Estado {
        PENDIENTE,
        EN_COCINA,
        LISTO,
        ASIGNADO,
        ENTREGADO,
        CANCELADO
    }

    private final int id;
    private final int clienteId;
    private final int[] platosIds;
    private final int tipo;
    private final int prioridad;
    private Estado estado = Estado.PENDIENTE;

    // 🔹 Nuevo atributo para grafo
    private int destinoId = 0; // zona donde se entregará el pedido (1..15)

    // --- Constructor ---
    public pedido(int id, int clienteId, int[] platosIds, int tipo, int prioridad) {
        this.id = id;
        this.clienteId = clienteId;
        this.platosIds = platosIds;
        this.tipo = tipo;
        this.prioridad = prioridad;
    }

    // --- Getters y setters ---
    public int getId() { return id; }
    public int getClienteId() { return clienteId; }
    public int[] getPlatosIds() { return platosIds; }
    public int getTipo() { return tipo; }
    public int getPrioridad() { return prioridad; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado e) { this.estado = e; }

    // 🔹 Nuevos métodos para el grafo
    public int getDestinoId() { return destinoId; }
    public void setDestinoId(int destinoId) { this.destinoId = destinoId; }
}
