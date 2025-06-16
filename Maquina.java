public class Maquina implements Comparable<Maquina> {
    private String id;
    private int piezas;

    public Maquina(String id, int piezas) {
        this.id = id;
        this.piezas = piezas;
    }

    public String getId() {
        return id;
    }

    public int getPiezas() {
        return piezas;
    }

    @Override
    public String toString() {
        return id + "(" + piezas + ")";
    }

    /** ordena de mayor a menor produccion */
    @Override
    public int compareTo(Maquina o) {
        return Integer.compare(o.piezas, this.piezas);
    }
}
