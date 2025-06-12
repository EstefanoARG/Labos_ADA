package modelo;

public class Cliente {
    private int id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private String codigoPostal;

    public Cliente(int id, String codigo, String nombres, String apellidos, 
                  String telefono, String correo, String direccion, String codigoPostal) {
        this.id = id;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
    }

    // Getters
    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
    public String getCodigoPostal() { return codigoPostal; }
    
    public String getClaveHash() { return nombres + apellidos; }
    
    @Override
    public String toString() {
        return String.format("ID: %d, Código: %s, Nombre: %s %s", id, codigo, nombres, apellidos);
    }
}