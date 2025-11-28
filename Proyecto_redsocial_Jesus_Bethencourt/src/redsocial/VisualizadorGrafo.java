/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase para visualizar grafos con componentes fuertemente conectados
 * @author Equipo de desarrollo
 */
public class VisualizadorGrafo extends JPanel {
    private Grafo grafo;
    private ListaPersonalizada componentes;
    private ListaPersonalizada posiciones;
    private static final Color[] COLORES = {
        Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, 
        Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW
    };
    
    /**
     * Constructor del visualizador
     * @param grafo El grafo a visualizar
     */
    public VisualizadorGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.componentes = new ListaPersonalizada();
        this.posiciones = new ListaPersonalizada();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        calcularPosiciones();
    }
    
    /**
     * Constructor con componentes
     * @param grafo El grafo a visualizar
     * @param componentes Lista de componentes fuertemente conectados
     */
    public VisualizadorGrafo(Grafo grafo, ListaPersonalizada componentes) {
        this(grafo);
        this.componentes = componentes;
    }
    
    /**
     * Calcula las posiciones de los nodos en un círculo
     */
    private void calcularPosiciones() {
        posiciones.limpiar();
        int numNodos = grafo.getNumeroNodos();
        int centroX = 400;
        int centroY = 300;
        int radio = 200;
        
        for (int i = 0; i < numNodos; i++) {
            double angulo = 2 * Math.PI * i / numNodos;
            int x = centroX + (int)(radio * Math.cos(angulo));
            int y = centroY + (int)(radio * Math.sin(angulo));
            PosicionNodo pos = new PosicionNodo(x, y);
            posiciones.agregar(pos);
        }
    }
    
    /**
     * Método principal de pintado
     * @param g El objeto Graphics para pintar
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar aristas primero
        dibujarAristas(g2d);
        
        // Dibujar nodos después (para que queden encima)
        dibujarNodos(g2d);
    }
    
    /**
     * Dibuja todas las aristas del grafo
     * @param g2d El objeto Graphics2D para dibujar
     */
    private void dibujarAristas(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));
        
        ListaPersonalizada nodos = grafo.getNodos();
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo origen = (Nodo) nodos.obtener(i);
            PosicionNodo posOrigen = (PosicionNodo) posiciones.obtener(i);
            
            ListaPersonalizada adyacentes = origen.getAdyacentes();
            for (int j = 0; j < adyacentes.tamaño(); j++) {
                Nodo destino = (Nodo) adyacentes.obtener(j);
                int indiceDestino = obtenerIndiceNodo(destino);
                if (indiceDestino != -1) {
                    PosicionNodo posDestino = (PosicionNodo) posiciones.obtener(indiceDestino);
                    dibujarFlecha(g2d, posOrigen, posDestino);
                }
            }
        }
    }
    
    /**
     * Dibuja una flecha entre dos puntos
     * @param g2d El objeto Graphics2D para dibujar
     * @param origen Posición del nodo origen
     * @param destino Posición del nodo destino
     */
    private void dibujarFlecha(Graphics2D g2d, PosicionNodo origen, PosicionNodo destino) {
        // Calcular ángulo
        double dx = destino.x - origen.x;
        double dy = destino.y - origen.y;
        double angulo = Math.atan2(dy, dx);
        
        // Acortar la línea para que no llegue hasta el centro del nodo destino
        int radioNodo = 20;
        int xDest = destino.x - (int)(radioNodo * Math.cos(angulo));
        int yDest = destino.y - (int)(radioNodo * Math.sin(angulo));
        int xOrig = origen.x + (int)(radioNodo * Math.cos(angulo));
        int yOrig = origen.y + (int)(radioNodo * Math.sin(angulo));
        
        // Dibujar línea
        g2d.drawLine(xOrig, yOrig, xDest, yDest);
        
        // Dibujar punta de flecha
        int largoPunta = 10;
        double anguloPunta1 = angulo + Math.PI * 0.75;
        double anguloPunta2 = angulo + Math.PI * 1.25;
        
        int xPunta1 = xDest + (int)(largoPunta * Math.cos(anguloPunta1));
        int yPunta1 = yDest + (int)(largoPunta * Math.sin(anguloPunta1));
        int xPunta2 = xDest + (int)(largoPunta * Math.cos(anguloPunta2));
        int yPunta2 = yDest + (int)(largoPunta * Math.sin(anguloPunta2));
        
        g2d.drawLine(xDest, yDest, xPunta1, yPunta1);
        g2d.drawLine(xDest, yDest, xPunta2, yPunta2);
    }
    
    /**
     * Dibuja todos los nodos del grafo
     * @param g2d El objeto Graphics2D para dibujar
     */
    private void dibujarNodos(Graphics2D g2d) {
        ListaPersonalizada nodos = grafo.getNodos();
        
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            PosicionNodo pos = (PosicionNodo) posiciones.obtener(i);
            
            // Determinar color según el componente
            Color colorNodo = determinarColorNodo(nodo);
            
            // Dibujar círculo del nodo
            g2d.setColor(colorNodo);
            g2d.fillOval(pos.x - 20, pos.y - 20, 40, 40);
            
            // Borde del nodo
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(pos.x - 20, pos.y - 20, 40, 40);
            
            // Texto del nodo
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            String texto = nodo.getId().replace("@", "");
            FontMetrics fm = g2d.getFontMetrics();
            int anchoTexto = fm.stringWidth(texto);
            int altoTexto = fm.getHeight();
            g2d.drawString(texto, pos.x - anchoTexto/2, pos.y + altoTexto/4);
        }
    }
    
    /**
     * Determina el color de un nodo basado en su componente
     * @param nodo El nodo a evaluar
     * @return El color asignado al nodo
     */
    private Color determinarColorNodo(Nodo nodo) {
        if (componentes.estaVacia()) {
            return Color.LIGHT_GRAY; // Color por defecto si no hay componentes
        }
        
        for (int i = 0; i < componentes.tamaño(); i++) {
            ListaPersonalizada componente = (ListaPersonalizada) componentes.obtener(i);
            if (componente.contiene(nodo)) {
                return COLORES[i % COLORES.length];
            }
        }
        
        return Color.LIGHT_GRAY; // Nodo no está en ningún componente
    }
    
    /**
     * Obtiene el índice de un nodo en la lista de nodos
     * @param nodo El nodo a buscar
     * @return El índice del nodo o -1 si no se encuentra
     */
    private int obtenerIndiceNodo(Nodo nodo) {
        ListaPersonalizada nodos = grafo.getNodos();
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo n = (Nodo) nodos.obtener(i);
            if (n.equals(nodo)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Muestra el visualizador en un frame
     */
    public void mostrar() {
        JFrame frame = new JFrame("Visualización del Grafo - Red Social");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(this));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Clase interna para almacenar posiciones de nodos
     */
    private class PosicionNodo {
        int x, y;
        
        PosicionNodo(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}