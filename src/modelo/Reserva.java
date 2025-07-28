package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva implements Pago {
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Reserva(Cliente cliente, Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }

    @Override
    public double calcularTotal() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        double tarifa = switch (habitacion.getTipo()) {
            case DOBLE -> 75.0;
            case SUITE -> 100.0;
            default -> 50.0;
        };
        return dias * tarifa;
    }
}
