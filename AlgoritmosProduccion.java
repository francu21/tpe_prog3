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

        Solucion solucion = new Solucion("Greedy", "cantidad de candidatos considerados");
        int suma = 0;
        int i = 0;

        while (i < maquinas.size() && suma < objetivo) {
            Maquina m = maquinas.get(i);
            if (m.getPiezas()+suma <= objetivo) {
                solucion.agregar(m);
                suma += m.getPiezas();
            }
            else {
                i++;
            }
        }
        if (suma == objetivo) {
            solucion.setCosto(i+1);
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
        llamadas[0]++;

        // CORTE
        // Si encontramos una solucion
        if (actual.getTotalPiezas() == objetivo) {
            if (mejor.getSeleccionadas().isEmpty() || actual.getSeleccionadas().size() < mejor.getSeleccionadas().size()) {
                mejor.getSeleccionadas().clear();
                mejor.getSeleccionadas().addAll(actual.getSeleccionadas());
                mejor.setTotalPiezas(actual.getTotalPiezas());
            }
            return;
        }else{
            //PODAS
            //si no es posible una mejor solucion
            if (mejor.getSeleccionadas().size()==1) {
                return;
            }
            //no hay mas maquinas
            if (i >= maquinas.size()) {
                return;
            }
            // poda por tamanio solo si ya tengo una solucion
            if (!mejor.getSeleccionadas().isEmpty() && actual.getSeleccionadas().size() >= mejor.getSeleccionadas().size()) {
                return;
            }

            //RECURSION
            //incluyendo la maquina actual
            //poda si incluyendo nos pasamos
            if (actual.getTotalPiezas() + maquinas.get(i).getPiezas() <= objetivo) {
                actual.agregar(maquinas.get(i));
                backtracking(maquinas, objetivo, actual, mejor, i, llamadas);
                //back
                actual.quitarUltima();
            }
            
            //sin incluir la maquina actual
            backtracking(maquinas, objetivo, actual, mejor, i+1, llamadas);
    }
        }

}
