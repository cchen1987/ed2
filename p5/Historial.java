package practica5;

import java.util.ArrayList;
import java.util.List;

public class Historial {
    private List<String> enfermedad;
    private List<String> fechaEnfermedad;
    private Mascota mascota;
    
    public Historial() {
        enfermedad = new ArrayList<>();
        fechaEnfermedad = new ArrayList<>();
    }
    
    public String getEnfermedadAt(int index) {
        if (index > 0 && index < enfermedad.size()) {
            return enfermedad.get(index);
        }
        return null;
    }
    
    public boolean addEnfermedad(String enfermedad) {
        if (enfermedad != null) {
            this.enfermedad.add(enfermedad);
            return true;
        }
        return false;
    }
    
    public String getFechaEnfermedadAt(int index) {
        if (index > 0 && index < fechaEnfermedad.size()) {
            return fechaEnfermedad.get(index);
        }
        return null;
    }
    
    public boolean addFechaEnfermedad(String fecha) {
        if (fecha != null) {
            fechaEnfermedad.add(fecha);
            return true;
        }
        return false;
    }
    
    public String[] getEnfermedades() {
        return (String[]) enfermedad.toArray();
    }
    
    public String[] getFechas() {
        return (String[]) fechaEnfermedad.toArray();
    }
    
    public Mascota getMascotas() {
        return mascota;
    }
    
    public boolean addMascota(Mascota mascota) {
        if (mascota != null) {
            this.mascota = mascota;
            return true;
        }
        return false;
    }
}
