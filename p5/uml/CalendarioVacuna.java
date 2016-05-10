package practica5;

import java.util.ArrayList;
import java.util.List;

public class CalendarioVacuna {
    private List<String> fechaVacuna;
    private List<String> enfermedadVacuna;
    private Mascota mascota;
    
    public CalendarioVacuna(Mascota mascota) {
        enfermedadVacuna = new ArrayList<>();
        fechaVacuna = new ArrayList<>();
        this.mascota = mascota;
    }
    
    public String getFechaVacunaAt(int index) {
        if (index >= 0 && index < fechaVacuna.size()) {
            return fechaVacuna.get(index);
        }
        return null;
    }
    
    public String[] getFechasVacuna() {
        return (String[])fechaVacuna.toArray();
    }
    
    public boolean addFechaVacuna(String fechaVacuna) {
        if (fechaVacuna != null) {
            this.fechaVacuna.add(fechaVacuna);
            return true;
        }
        return false;
    }
    
    public String getEnfermedadVacunaAt(int index) {
        if (index >= 0 && index < enfermedadVacuna.size()) {
            return enfermedadVacuna.get(index);
        }
        return null;
    }
    
    public boolean addEnfermedadVacuna(String enfermedad) {
        if (enfermedad != null) {
            enfermedadVacuna.add(enfermedad);
            return true;
        }
        return false;
    }
    
    public String[] getEnfermedadesVacuna() {
        return (String[]) enfermedadVacuna.toArray();
    }
    
    public Mascota getMascota() {
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
