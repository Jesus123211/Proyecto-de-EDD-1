/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

/**
 * Clase que representa un nodo (usuario) en el grafo
 * @author Jesús Bethencourt
 */
public class Nodo {
    private String id;
    private ListaPersonalizada adyacentes;
    
    /**
     * Constructor del nodo
     * @param id El identificador único del nodo
     */
    public Nodo(String id) {
        this.id = id;
        this.adyacentes = new ListaPersonalizada();
    }
    
    /**
     * Obtiene el ID del nodo
     * @return El ID del nodo
     */
    public String getId() {
        return id;
    }
    
    /**
     * Agrega un nodo adyacente
     * @param nodo El nodo adyacente a agregar
     */
    public void agregarAdyacente(Nodo nodo) {
        if (!adyacentes.contiene(nodo)) {
            adyacentes.agregar(nodo);
        }
    }
    
    /**
     * Elimina un nodo adyacente
     * @param nodo El nodo adyacente a eliminar
     */
    public void eliminarAdyacente(Nodo nodo) {
        adyacentes.eliminar(nodo);
    }
    
    /**
     * Obtiene la lista de nodos adyacentes
     * @return Lista de nodos adyacentes
     */
    public ListaPersonalizada getAdyacentes() {
        return adyacentes;
    }
    
    /**
     * Verifica si un nodo es adyacente
     * @param nodo El nodo a verificar
     * @return true si es adyacente, false en caso contrario
     */
    public boolean esAdyacente(Nodo nodo) {
        return adyacentes.contiene(nodo);
    }
    
    /**
     * Compara dos nodos por su ID
     * @param obj El objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Nodo nodo = (Nodo) obj;
        return id.equals(nodo.id);
    }
    
    /**
     * Representación en string del nodo
     * @return El ID del nodo
     */
    @Override
    public String toString() {
        return id;
    }
}
