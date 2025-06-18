import java.util.*;

public class AlgoritmosProduccion {

    
    /*greedy: elige siempre la maquina de mayor produccion disponible 
        - los candidatos son todas las maquinas disponibles, ordenadas de mayor a menor cantidad de piezas
        - Condiciones para encontrar solución:
            -cuando la suma de piezas producidas por las máquinas seleccionadas es exactamente igual al objetivo
            -Cuando encuentra una solución, establece el costo (número de candidatos considerados) y devuelve la solución completa con las máquinas seleccionadas
        - Condiciones para NO encontrar solución:
            -Si no se alcanza exactamente el objetivo, retorna una solución vacía
            -Casos en que no encuentra solución:
                -Cuando la suma de piezas supera el objetivo y no hay forma de alcanzar exactamente el valor objetivo
                -Cuando la suma de piezas es menor que el objetivo y ya se han considerado todas las máquinas disponibles
                -Cuando no existe ninguna combinación de máquinas que produzca exactamente el número objetivo de piezas
    */
    
    public static Solucion resolverGreedy(List<Maquina> maquinas, int objetivo) {
        Collections.sort(maquinas); // mayor a menor

        int costo = 0;
        Solucion solucion = new Solucion("Greedy", "cantidad de candidatos considerados");
        int suma = 0;
        int i = 0;

        while (i < maquinas.size() && suma < objetivo) {
            Maquina m = maquinas.get(i);
            if (m.getPiezas()+suma <= objetivo) {
                solucion.agregar(m);
                suma += m.getPiezas();
                costo++;
            }
            else {
                i++;
            }
        }
        if (suma == objetivo) {
            solucion.setCosto(costo);
            return solucion;    
        }
        return new Solucion(); 
    }

    /*backtracking: explora todas las combinaciones, busca la que use menos arranques 
        - Cada nivel del árbol representa una decisión sobre una máquina específica (incluirla o no incluirla)
        - Cada nodo representa un estado parcial de la solución (las máquinas seleccionadas hasta el momento)
        - Las ramas representan las decisiones:
        - Rama izquierda: incluir la máquina actual
        - Rama derecha: excluir la máquina actual
        - Los estados finales, podas (hojas del árbol) ocurren cuando: 
            -Se alcanza el objetivo
            -Se consideraron todas las máquinas
            -si ya hay una solucion y la actual ya tiene mas maquinas
            -si la suma de las piezas actuales y las siguientes es mayor al objetivo

        - Un estado se considera solución cuando:
            -Se alcanza exactamente el objetivo
            -Es mejor que la solución anterior
    */

    public static Solucion resolverBacktracking(List<Maquina> maquinas, int objetivo) {
        Solucion actual = new Solucion("Backtracking", "cantidad de llamadas recursivas");
        Solucion mejor = new Solucion("Backtracking", "cantidad de llamadas recursivas");
        int[] llamadas = {0}; // contador de llamadas recursivas como array para poder modificarlo por referencia

        backtracking(maquinas, objetivo, actual, mejor, 0, llamadas); 
    
        if (mejor.getTotalPiezas() == objetivo) {
            mejor.setCosto(llamadas[0]);
            return mejor;
        } else {
            return new Solucion(); 
        }
    }
    
    private static void backtracking(List<Maquina> maquinas, int objetivo, Solucion actual, Solucion mejor, int i, int[] llamadas) {
        llamadas[0]++; // incrementar contador de llamadas recursivas
    
        // corte - si encontramos una solución
        if (actual.getTotalPiezas() == objetivo) {
            if (mejor.getSeleccionadas().isEmpty() || actual.getSeleccionadas().size() < mejor.getSeleccionadas().size()) {
                // copia la solucion actual a la mejor
                mejor.getSeleccionadas().clear();
                mejor.getSeleccionadas().addAll(actual.getSeleccionadas());
                mejor.setTotalPiezas(actual.getTotalPiezas());
            }
            return;
        }
    
        // podas
        // 1. si ya consideramos todas las maquinas
        if (i >= maquinas.size()) {
            return;
        }
        
        // 2. poda por tamanio: si ya tenemos una solucion y la actual ya tiene mas maquinas, no seguir
        if (!mejor.getSeleccionadas().isEmpty() && actual.getSeleccionadas().size() >= mejor.getSeleccionadas().size()) {
            return;
        }
        
        // 3. poda si la suma de las piezas actuales y las siguientes es mayor al objetivo
        if (maquinas.get(i).getPiezas() + actual.getTotalPiezas() > objetivo) {
            return;
        }
        actual.agregar(maquinas.get(i));
        backtracking(maquinas, objetivo, actual, mejor, i, llamadas);
        
        actual.quitarUltima();
       
        // no incluir la maquina actual y pasar a la siguiente
        backtracking(maquinas, objetivo, actual, mejor, i + 1, llamadas);
    }
}
