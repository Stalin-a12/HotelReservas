package modelo;

public class Recepcionista extends Usuario {

    public Recepcionista(String username, String password) {
        super(username, password);
    }

    public void atenderCliente() {
        System.out.println("Recepcionista atendiendo cliente...");
    }

    @Override
public String getRol() {
    return "Recepcionista";
}

}
