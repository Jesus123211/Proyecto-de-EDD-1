/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Clase para manejar la lectura y escritura de archivos
 * @author Jesús Bethencourt
 */
public class ArchivoManager {
    
    /**
     * Carga un grafo desde un archivo de texto
     * @param rutaArchivo La ruta del archivo a cargar
     * @return El grafo cargado desde el archivo
     */
    public Grafo cargarGrafo(String rutaArchivo) {
        Grafo grafo = new Grafo();
        
        try {
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            boolean enUsuarios = false;
            boolean enRelaciones = false;
            
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                
                if (linea.equals("usuarios")) {
                    enUsuarios = true;
                    enRelaciones = false;
                    continue;
                } else if (linea.equals("relaciones")) {
                    enUsuarios = false;
                    enRelaciones = true;
                    continue;
                }
                
                if (enUsuarios && linea.startsWith("@")) {
                    grafo.agregarNodo(linea);
                } else if (enRelaciones && linea.contains(",")) {
                    String[] partes = linea.split(",");
                    if (partes.length == 2) {
                        String origen = partes[0].trim();
                        String destino = partes[1].trim();
                        grafo.agregarArista(origen, destino);
                    }
                }
            }
            
            lector.close();
        } catch (Exception e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
        }
        
        return grafo;
    }
    /**
 * Carga el grafo específico con 3 componentes
 * @return El grafo cargado
 */
public Grafo cargarGrafoEspecifico() {
    return cargarGrafo("datos/grafo_especifico.txt");
}
    
    /**
     * Guarda un grafo en un archivo de texto
     * @param grafo El grafo a guardar
     * @param rutaArchivo La ruta del archivo donde guardar
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean guardarGrafo(Grafo grafo, String rutaArchivo) {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(rutaArchivo));
            
            // Escribir usuarios
            escritor.println("usuarios");
            ListaPersonalizada nodos = grafo.getNodos();
            for (int i = 0; i < nodos.tamaño(); i++) {
                Nodo nodo = (Nodo) nodos.obtener(i);
                escritor.println(nodo.getId());
            }
            
            // Escribir relaciones
            escritor.println("relaciones");
            for (int i = 0; i < nodos.tamaño(); i++) {
                Nodo nodo = (Nodo) nodos.obtener(i);
                ListaPersonalizada adyacentes = nodo.getAdyacentes();
                
                for (int j = 0; j < adyacentes.tamaño(); j++) {
                    Nodo adyacente = (Nodo) adyacentes.obtener(j);
                    escritor.println(nodo.getId() + ", " + adyacente.getId());
                }
            }
            
            escritor.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
            return false;
        }
    }
}