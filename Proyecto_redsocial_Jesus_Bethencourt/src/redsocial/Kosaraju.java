/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

/**
 * Implementación del algoritmo de Kosaraju para encontrar componentes fuertemente conectados
 * @author Jesús Bethencourt
 */
public class Kosaraju {
    private Grafo grafo;
    private PilaPersonalizada pila;
    private boolean[] visitado;
    private ListaPersonalizada componentes;
    
    /**
     * Constructor del algoritmo de Kosaraju
     * @param grafo El grafo a analizar
     */
    public Kosaraju(Grafo grafo) {
        this.grafo = grafo;
        this.pila = new PilaPersonalizada();
        this.visitado = new boolean[grafo.getNumeroNodos()];
        this.componentes = new ListaPersonalizada();
    }
    
/**
 * Ejecuta el algoritmo de Kosaraju para encontrar CFCs
 * @return Lista de componentes fuertemente conectados
 */
public ListaPersonalizada encontrarCFCs() {
    componentes.limpiar();
    
    if (grafo.getNumeroNodos() == 0) {
        return componentes;
    }
    
    // Reiniciar el arreglo de visitados
    this.visitado = new boolean[grafo.getNumeroNodos()];
    this.pila = new PilaPersonalizada();
    
    // Primer paso: DFS en el grafo original para llenar la pila
    for (int i = 0; i < grafo.getNumeroNodos(); i++) {
        if (!visitado[i]) {
            primeraDFS(i);
        }
    }
    
    // Segundo paso: Obtener el grafo transpuesto
    Grafo grafoTranspuesto = grafo.obtenerTranspuesto();
    
    // Reiniciar el arreglo de visitados
    this.visitado = new boolean[grafo.getNumeroNodos()];
    
    // Tercer paso: DFS en el grafo transpuesto en orden de la pila
    while (!pila.estaVacia()) {
        int indiceNodo = (Integer) pila.desapilar();
        if (!visitado[indiceNodo]) {
            ListaPersonalizada componente = new ListaPersonalizada();
            segundaDFS(grafoTranspuesto, indiceNodo, componente);
            componentes.agregar(componente);
        }
    }
    
    return componentes;
}
    
    /**
     * Primera DFS para llenar la pila
     * @param indiceNodo Índice del nodo actual
     */
    private void primeraDFS(int indiceNodo) {
        visitado[indiceNodo] = true;
        
        Nodo nodoActual = (Nodo) grafo.getNodos().obtener(indiceNodo);
        ListaPersonalizada adyacentes = nodoActual.getAdyacentes();
        
        for (int i = 0; i < adyacentes.tamaño(); i++) {
            Nodo adyacente = (Nodo) adyacentes.obtener(i);
            int indiceAdyacente = obtenerIndiceNodo(adyacente);
            
            if (!visitado[indiceAdyacente]) {
                primeraDFS(indiceAdyacente);
            }
        }
        
        pila.apilar(indiceNodo);
    }
    
    /**
     * Segunda DFS en el grafo transpuesto
     * @param grafoT Grafo transpuesto
     * @param indiceNodo Índice del nodo actual
     * @param componente Componente actual que se está formando
     */
    private void segundaDFS(Grafo grafoT, int indiceNodo, ListaPersonalizada componente) {
        visitado[indiceNodo] = true;
        componente.agregar(grafoT.getNodos().obtener(indiceNodo));
        
        Nodo nodoActual = (Nodo) grafoT.getNodos().obtener(indiceNodo);
        ListaPersonalizada adyacentes = nodoActual.getAdyacentes();
        
        for (int i = 0; i < adyacentes.tamaño(); i++) {
            Nodo adyacente = (Nodo) adyacentes.obtener(i);
            int indiceAdyacente = obtenerIndiceNodoGrafo(adyacente, grafoT);
            
            if (!visitado[indiceAdyacente]) {
                segundaDFS(grafoT, indiceAdyacente, componente);
            }
        }
    }
    
    /**
     * Obtiene el índice de un nodo en el grafo original
     * @param nodo El nodo a buscar
     * @return El índice del nodo
     */
    private int obtenerIndiceNodo(Nodo nodo) {
        for (int i = 0; i < grafo.getNumeroNodos(); i++) {
            Nodo n = (Nodo) grafo.getNodos().obtener(i);
            if (n.equals(nodo)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Obtiene el índice de un nodo en un grafo específico
     * @param nodo El nodo a buscar
     * @param grafoBuscado El grafo donde buscar
     * @return El índice del nodo
     */
    private int obtenerIndiceNodoGrafo(Nodo nodo, Grafo grafoBuscado) {
        for (int i = 0; i < grafoBuscado.getNumeroNodos(); i++) {
            Nodo n = (Nodo) grafoBuscado.getNodos().obtener(i);
            if (n.equals(nodo)) {
                return i;
            }
        }
        return -1;
    }
}