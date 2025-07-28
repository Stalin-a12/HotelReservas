package modelo;

public class PagoEfectivo implements Pago {
    private double montoBase;

    public PagoEfectivo(double montoBase) {
        this.montoBase = montoBase;
    }

    @Override
    public double calcularTotal() {
        return montoBase;
    }
    
}
