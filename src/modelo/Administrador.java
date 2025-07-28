package modelo;

public class Administrador extends Usuario {

    public Administrador(String username, String password) {
        super(username, password);
    }

    public void generarReporte() {
        System.out.println("Administrador generando reporte...");
    }

    @Override
public String getRol() {
    return "Administrador";
}

}
