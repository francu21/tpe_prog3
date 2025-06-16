import java.util.*;

public class Solucion {
    private String algoritmo;
    private List<Maquina> seleccionadas;
    private int totalPiezas;
    private String metrica;
    private int costo;

    public Solucion(String algoritmo, String metrica) {
        this.algoritmo = algoritmo;
        this.seleccionadas = new ArrayList<>();
        this.totalPiezas = 0;
        this.costo = 0;
        this.metrica = metrica;
    }
    public Solucion(){
        this.seleccionadas = new ArrayList<>();
        this.totalPiezas = 0;
    }

    public void agregar(Maquina m) {
        seleccionadas.add(m);
        totalPiezas += m.getPiezas();
    }

    public void quitarUltima() {
        if (!seleccionadas.isEmpty()) {
            Maquina ultima = seleccionadas.remove(seleccionadas.size() - 1);
            totalPiezas -= ultima.getPiezas();
        }
    }

    public int getTotalPiezas() {
        return totalPiezas;
    }
    public void setTotalPiezas(int totalPiezas) {
        this.totalPiezas = totalPiezas;
    }
    
    public List<Maquina> getSeleccionadas() {
        return seleccionadas;
    }
    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        if (seleccionadas.isEmpty()) {
            return "No se encontró una solución";
        }
        return algoritmo + 
                "\nSecuencia de maquinas:"+seleccionadas.toString() + 
                "\nTotal de piezas: " + totalPiezas + 
                "\nMetrica :"+metrica+" (" +costo+ ")\n";
    }
}

