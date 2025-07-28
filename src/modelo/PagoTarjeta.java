package modelo;

public class PagoTarjeta implements Pago {
    private double montoBase;
    private double recargo = 0.05;

    public PagoTarjeta(double montoBase) {
        this.montoBase = montoBase;
    }

    @Override
    public double calcularTotal() {
        return montoBase * (1 + recargo);
    }
    
}
