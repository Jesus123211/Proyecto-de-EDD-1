/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

/**
 * Clase que representa un grafo dirigido usando lista de adyacencia
 * @author Jesús Bethencourt
 */
public class Grafo {
    private ListaPersonalizada nodos;
    
    /**
     * Constructor del grafo
     */
    public Grafo() {
        this.nodos = new ListaPersonalizada();
    }
    
    /**
     * Agrega un nodo al grafo
     * @param id El ID del nodo a agregar
     * @return El nodo creado o null si ya existe
     */
    public Nodo agregarNodo(String id) {
        if (obtenerNodo(id) != null) {
            return null; // El nodo ya existe
        }
        Nodo nuevoNodo = new Nodo(id);
        nodos.agregar(nuevoNodo);
        return nuevoNodo;
    }
    
    /**
     * Elimina un nodo del grafo
     * @param id El ID del nodo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarNodo(String id) {
        Nodo nodo = obtenerNodo(id);
        if (nodo == null) {
            return false;
        }
        
        // Eliminar todas las referencias al nodo en las listas de adyacencia
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo n = (Nodo) nodos.obtener(i);
            n.eliminarAdyacente(nodo);
        }
        
        // Eliminar el nodo
        return nodos.eliminar(nodo);
    }
    
    /**
     * Agrega una arista dirigida entre dos nodos
     * @param idOrigen ID del nodo origen
     * @param idDestino ID del nodo destino
     * @return true si se agregó correctamente, false en caso contrario
     */
    public boolean agregarArista(String idOrigen, String idDestino) {
        Nodo origen = obtenerNodo(idOrigen);
        Nodo destino = obtenerNodo(idDestino);
        
        if (origen == null || destino == null) {
            return false;
        }
        
        origen.agregarAdyacente(destino);
        return true;
    }
    
    /**
     * Elimina una arista dirigida entre dos nodos
     * @param idOrigen ID del nodo origen
     * @param idDestino ID del nodo destino
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarArista(String idOrigen, String idDestino) {
        Nodo origen = obtenerNodo(idOrigen);
        Nodo destino = obtenerNodo(idDestino);
        
        if (origen == null || destino == null) {
            return false;
        }
        
        origen.eliminarAdyacente(destino);
        return true;
    }
    
    /**
     * Obtiene un nodo por su ID
     * @param id El ID del nodo a buscar
     * @return El nodo encontrado o null si no existe
     */
    public Nodo obtenerNodo(String id) {
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            if (nodo.getId().equals(id)) {
                return nodo;
            }
        }
        return null;
    }
    
    /**
     * Obtiene todos los nodos del grafo
     * @return Lista de todos los nodos
     */
    public ListaPersonalizada getNodos() {
        return nodos;
    }
    
    /**
     * Obtiene el número de nodos en el grafo
     * @return El número de nodos
     */
    public int getNumeroNodos() {
        return nodos.tamaño();
    }
    
    /**
     * Obtiene el número de aristas en el grafo
     * @return El número de aristas
     */
    public int getNumeroAristas() {
        int contador = 0;
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            contador += nodo.getAdyacentes().tamaño();
        }
        return contador;
    }
    
    /**
     * Obtiene el grafo transpuesto (con todas las aristas invertidas)
     * @return El grafo transpuesto
     */
    public Grafo obtenerTranspuesto() {
        Grafo transpuesto = new Grafo();
        
        // Agregar todos los nodos al grafo transpuesto
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            transpuesto.agregarNodo(nodo.getId());
        }
        
        // Invertir las aristas
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            ListaPersonalizada adyacentes = nodo.getAdyacentes();
            
            for (int j = 0; j < adyacentes.tamaño(); j++) {
                Nodo adyacente = (Nodo) adyacentes.obtener(j);
                transpuesto.agregarArista(adyacente.getId(), nodo.getId());
            }
        }
        
        return transpuesto;
    }
}
