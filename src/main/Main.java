package main;

import controlador.LoginController;
import vista.LoginVista;

public class Main {
    public static void main(String[] args){
        LoginVista loginVista = new LoginVista();
        new LoginController(loginVista);
    }
    
}
