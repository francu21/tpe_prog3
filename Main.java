import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        int objetivo = LectorArchivo.leerPiezasTotales("caso1.txt"); 
        List<Maquina> maquinas = LectorArchivo.leerMaquinas("caso1.txt");
        //greedy
        System.out.println(AlgoritmosProduccion.resolverGreedy(maquinas, objetivo));
        //backtracking
        System.out.println(AlgoritmosProduccion.resolverBacktracking(maquinas, objetivo));
       
    }
}

