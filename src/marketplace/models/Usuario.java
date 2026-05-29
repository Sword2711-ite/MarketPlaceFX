package marketplace.models;

public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private String email;
    private boolean esAdmin;

    public Usuario(String username, String password, String nombre, String email, boolean esAdmin) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.esAdmin = esAdmin;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public boolean isEsAdmin() { return esAdmin; }
}