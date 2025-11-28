/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

/**
 * Implementación personalizada de una pila (LIFO)
 * @author Jesús Bethencourt
 */
public class PilaPersonalizada {
    private NodoPila tope;
    private int tamaño;
    
    /**
     * Constructor de la pila personalizada
     */
    public PilaPersonalizada() {
        this.tope = null;
        this.tamaño = 0;
    }
    
    /**
     * Agrega un elemento a la pila
     * @param elemento El elemento a apilar
     */
    public void apilar(Object elemento) {
        NodoPila nuevoNodo = new NodoPila(elemento);
        nuevoNodo.siguiente = tope;
        tope = nuevoNodo;
        tamaño++;
    }
    
    /**
     * Remueve y retorna el elemento en el tope de la pila
     * @return El elemento en el tope de la pila
     */
    public Object desapilar() {
        if (tope == null) {
            return null;
        }
        Object dato = tope.dato;
        tope = tope.siguiente;
        tamaño--;
        return dato;
    }
    
    /**
     * Retorna el elemento en el tope sin removerlo
     * @return El elemento en el tope de la pila
     */
    public Object verTope() {
        if (tope == null) {
            return null;
        }
        return tope.dato;
    }
    
    /**
     * Verifica si la pila está vacía
     * @return true si está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return tope == null;
    }
    
    /**
     * Obtiene el tamaño de la pila
     * @return El número de elementos en la pila
     */
    public int tamaño() {
        return tamaño;
    }
    
    /**
     * Clase interna para los nodos de la pila
     */
    private class NodoPila {
        Object dato;
        NodoPila siguiente;
        
        NodoPila(Object dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
}
