package controlador;

import javax.swing.JOptionPane;

import modelo.Usuario;
import modelo.Administrador;
import modelo.Recepcionista;
import vista.LoginVista;
import vista.MenuPrincipalVista;

public class LoginController {
    private LoginVista vista;
    private Usuario usuarioLogueado;

    public LoginController(LoginVista vista) {
        this.vista = vista;
        this.vista.btnLogin.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String user = vista.txtUsuario.getText();
        String pass = String.valueOf(vista.txtPassword.getPassword());

        // Lógica con objetos
        if (user.equals("admin") && pass.equals("1234")) {
            usuarioLogueado = new Administrador(user, pass);
        } else if (user.equals("recepcion") && pass.equals("abcd")) {
            usuarioLogueado = new Recepcionista(user, pass);
        }

        if (usuarioLogueado != null) {
            JOptionPane.showMessageDialog(vista, "Bienvenido " + usuarioLogueado.getRol());
            vista.dispose();

            // Ahora pasas el usuario al menú
            new MenuPrincipalVista(usuarioLogueado);

        } else {
            JOptionPane.showMessageDialog(vista, "Credenciales incorrectas");
        }
    }
}
