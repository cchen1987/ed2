package practica5;

public class Persona {
    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    
    public Persona(String dni, String nombre, String primerApellido, String segundoApellido) {
        this.dni = dni == null ? "" : dni;
        this.nombre = nombre == null ? "" : nombre;
        this.primerApellido = primerApellido == null ? "" : primerApellido;
        this.segundoApellido = segundoApellido == null ? "" : segundoApellido;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDni() {
        return dni;
    }
}
