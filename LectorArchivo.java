import java.io.*;
import java.util.*;
import java.nio.file.*;

public class LectorArchivo {
    public static int leerPiezasTotales(String path) throws IOException {
        List<String> lineas = Files.readAllLines(Paths.get(path));
        return Integer.parseInt(lineas.get(0)); // la primera linea es el total de piezas
    }

    public static List<Maquina> leerMaquinas(String path) throws IOException {
        List<String> lineas = Files.readAllLines(Paths.get(path));
        List<Maquina> maquinas = new ArrayList<>();

        // empieza desde la linea 1 
        for (int i = 1; i < lineas.size(); i++) {
            String[] partes = lineas.get(i).split(","); // separa por coma
            String idTexto = partes[0]; // "M1", "M2", etc.
            int piezas = Integer.parseInt(partes[1]); // el numero despues de la coma
            maquinas.add(new Maquina(idTexto, piezas));
        }

        return maquinas;
    }
}
 
