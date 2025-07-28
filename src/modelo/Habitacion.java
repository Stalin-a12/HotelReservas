package modelo;

public class Habitacion {
    private int id;
    private String numero;
    private TipoHabitacion tipo;
    private double precio;  // Nuevo campo añadido
    private boolean disponible;

    // Constructor actualizado
    public Habitacion(String numero, TipoHabitacion tipo, double precio, boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.precio = precio;
        this.disponible = disponible;
    }
    public Habitacion(String numero, TipoHabitacion tipo, boolean disponible){
        this(numero, tipo, tipo.getPrecioBase(),disponible);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public TipoHabitacion getTipo() { return tipo; }
    public void setTipo(TipoHabitacion tipo) { this.tipo = tipo; }

    public double getPrecio() { return precio; }  // Nuevo getter
    public void setPrecio(double precio) { this.precio = precio; }  // Nuevo setter

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // Método toString para representación textual
    @Override
    public String toString() {
        return "Habitacion{" +
               "id=" + id +
               ", numero='" + numero + '\'' +
               ", tipo=" + tipo +
               ", precio=" + precio +
               ", disponible=" + disponible +
               '}';
    }
}
