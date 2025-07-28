package modelo;

public class PagoDescuento implements Pago{
    private double montoBase;
    private double descuento = 0.10;

    public PagoDescuento(double montoBase) {
        this.montoBase = montoBase;
    }

    @Override
    public double calcularTotal() {
        return montoBase * (1 - descuento);
    }
    
}
