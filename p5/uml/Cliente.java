package practica5;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String codigo;
    private String primerApellido;
    private int numCuentaBanco;
    private String direccion;
    private int telefono;
    private List<Persona> persona;
    private List<Mascota> mascota;
    
    public Cliente() {
        persona = new ArrayList<>();
        mascota = new ArrayList<>();
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public boolean setCodigo(String codigo) {
        if (codigo != null) {
            this.codigo = codigo;
            return true;
        }
        return false;
    }
    
    public String getPrimerApellido() {
        return primerApellido;
    }
    
    public boolean setPrimerApellido(String primerApellido) {
        if (primerApellido != null) {
            this.primerApellido = primerApellido;
            return true;
        }
        return false;
    }
    
    public int getNumCuenta() {
        return numCuentaBanco;
    }
    
    public boolean setNumCuenta(int numCuenta) {
        // TO DO
        numCuentaBanco = numCuenta;
        return true;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public boolean setDireccion(String direccion) {
        if (direccion != null) {
            this.direccion = direccion;
            return true;
        }
        return false;
    }
    
    public int getTelefono() {
        return telefono;
    }
    
    public boolean setTelefono(int telefono) {
        // TO DO
        this.telefono = telefono;
        return true;
    }
    
    public Persona getPersonaAt(int index) {
        if (index > 0 && index < persona.size()) {
            return persona.get(index);
        }
        return null;
    }
    
    public Persona[] getPersonas() {
        return (Persona[]) persona.toArray();
    }
    
    public boolean addPersona(Persona persona) {
        if (persona != null) {
            this.persona.add(persona);
            return true;
        }
        return false;
    }
    
    public Mascota getMascotaAt(int index) {
        if (index >= 0 && index < mascota.size()) {
            return mascota.get(index);
        }
        return null;
    }
    
    public Mascota[] getMascotas() {
        return (Mascota[]) mascota.toArray();
    }
    
    public boolean addMascota(Mascota mascota) {
        if (mascota != null) {
            mascota.addCliente(this);
            this.mascota.add(mascota);
            return true;
        }
        return false;
    }
}
