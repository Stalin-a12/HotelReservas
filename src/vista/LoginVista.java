package vista;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {
    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnLogin;

    public LoginVista() {
        setTitle("Login - Sistema de Hotel");
        setSize(350, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Iniciar Sesión");

        add(lblUsuario);
        add(txtUsuario);
        add(lblPassword);
        add(txtPassword);
        add(new JLabel());  // Espacio vacío
        add(btnLogin);

        setVisible(true);
    }
}
