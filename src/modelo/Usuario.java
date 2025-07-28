package modelo;

public abstract class Usuario {
    protected String username;
    protected String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public abstract String getRol(); // Agrega esto a Usuario.java
}
