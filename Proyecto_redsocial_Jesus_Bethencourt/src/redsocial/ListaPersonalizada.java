/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

/**
 * Implementación personalizada de una lista enlazada
 * @author Jesús Bethencourt
 */
public class ListaPersonalizada {
    private NodoLista cabeza;
    private int tamaño;
    
    /**
     * Constructor de la lista personalizada
     */
    public ListaPersonalizada() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    /**
     * Agrega un elemento a la lista
     * @param elemento El elemento a agregar
     */
    public void agregar(Object elemento) {
        NodoLista nuevoNodo = new NodoLista(elemento);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            NodoLista actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        tamaño++;
    }
    
    /**
     * Obtiene un elemento por índice
     * @param indice El índice del elemento
     * @return El elemento en la posición especificada
     */
    public Object obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            return null;
        }
        
        NodoLista actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    /**
     * Elimina un elemento por índice
     * @param indice El índice del elemento a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int indice) {
        if (indice < 0 || indice >= tamaño) {
            return false;
        }
        
        if (indice == 0) {
            cabeza = cabeza.siguiente;
        } else {
            NodoLista anterior = null;
            NodoLista actual = cabeza;
            for (int i = 0; i < indice; i++) {
                anterior = actual;
                actual = actual.siguiente;
            }
            anterior.siguiente = actual.siguiente;
        }
        tamaño--;
        return true;
    }
    
    /**
     * Elimina un elemento específico
     * @param elemento El elemento a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(Object elemento) {
        if (cabeza == null) return false;
        
        if (cabeza.dato.equals(elemento)) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }
        
        NodoLista anterior = cabeza;
        NodoLista actual = cabeza.siguiente;
        
        while (actual != null) {
            if (actual.dato.equals(elemento)) {
                anterior.siguiente = actual.siguiente;
                tamaño--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }
    
    /**
     * Verifica si la lista contiene un elemento
     * @param elemento El elemento a buscar
     * @return true si contiene el elemento, false en caso contrario
     */
    public boolean contiene(Object elemento) {
        NodoLista actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(elemento)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    
    /**
     * Obtiene el tamaño de la lista
     * @return El número de elementos en la lista
     */
    public int tamaño() {
        return tamaño;
    }
    
    /**
     * Convierte la lista a un arreglo
     * @return Un arreglo con todos los elementos
     */
    public Object[] toArray() {
        Object[] arreglo = new Object[tamaño];
        NodoLista actual = cabeza;
        int i = 0;
        while (actual != null) {
            arreglo[i++] = actual.dato;
            actual = actual.siguiente;
        }
        return arreglo;
    }
    
    /**
     * Verifica si la lista está vacía
     * @return true si está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return tamaño == 0;
    }
    
    /**
     * Limpia todos los elementos de la lista
     */
    public void limpiar() {
        cabeza = null;
        tamaño = 0;
    }
    
    /**
     * Clase interna para los nodos de la lista
     */
    private class NodoLista {
        Object dato;
        NodoLista siguiente;
        
        NodoLista(Object dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }
}