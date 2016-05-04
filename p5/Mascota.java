package practica5;

import java.util.ArrayList;
import java.util.List;

public class Mascota {
    protected String codigo;
    protected String alias;
    protected String especie;
    protected String raza;
    protected String color;
    protected String fechaNacimiento;
    protected float pesoMedio;
    protected float[] pesos;
    protected int indicePesoActual;
    protected List<Cliente> cliente;
    protected CalendarioVacuna calendarioVacuna;
    protected Historial historial;
    
    public Mascota() {
        indicePesoActual = 0;
        cliente = new ArrayList<>();
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
    
    public String getAlias() {
        return alias;
    }
    
    public boolean setAlias(String alias) {
        if (alias != null) {
            this.alias = alias;
            return true;
        }
        return false;
    }
    
    public String getEspecie() {
        return especie;
    }
    
    public boolean setEspecie(String especie) {
        if (especie != null) {
            this.especie = especie;
            return true;
        }
        return false;
    }
    
    public String getRaza() {
        return raza;
    }
    
    public boolean setRaza(String raza) {
        if (raza != null) {
            this.raza = raza;
            return true;
        }
        return false;
    }
    
    public String getColor() {
        return color;
    }
    
    public boolean setColor(String color) {
        if (color != null) {
            this.color = color;
            return true;
        }
        return false;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public boolean setFechaNacimiento(String fecha) {
        if (fecha != null) {
            fechaNacimiento = fecha;
            return true;
        }
        return false;
    }
    
    public float getPesoMedio() {
        return pesoMedio;
    }
    
    public float getPesoActual() {
        if (pesos.length > 0) {
            return pesos[indicePesoActual];
        }
        return 0;
    }
    
    public boolean setPesoActual(float peso) {
        if (peso > 0) {
            if (indicePesoActual < 10) {
                indicePesoActual++;
            }
            else
                indicePesoActual = 0;
            pesos[indicePesoActual] = peso;
            float total = 0;
            for (int i = 0; i < pesos.length; i++) {
                total += pesos[i];
            }
            pesoMedio = total / 10f;
            return true;
        }
        else
            return false;
    }
    
    public CalendarioVacuna getCalendario() {
        return calendarioVacuna;
    }
    
    public boolean addCalendario(CalendarioVacuna calendarioVacuna) {
        if (calendarioVacuna != null) {
            this.calendarioVacuna = calendarioVacuna;
            return true;
        }
        return false;
    }
    
    public Historial getHistorial() {
        return historial;
    }
    
    public boolean addHistorial(Historial historial) {
        if (historial != null) {
            historial.addMascota(this);
            this.historial = historial;
            return true;
        }
        return false;
    }
    
    public Cliente getClienteAt(int index) {
        if (index >= 0 && index < cliente.size()) {
            return cliente.get(index);
        }
        return null;
    }
    
    public Cliente[] getClientes() {
        return (Cliente[]) cliente.toArray();
    }
    
    public boolean addCliente(Cliente cliente) {
        if (cliente != null) {
            cliente.addMascota(this);
            this.cliente.add(cliente);
            return true;
        }
        return false;
    }
}
