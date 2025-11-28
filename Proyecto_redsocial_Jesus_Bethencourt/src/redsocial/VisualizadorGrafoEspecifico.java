/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Visualizador específico que reproduce el layout del grafo final con 3 componentes
 * @author Jesús Bethencourt
 */
public class VisualizadorGrafoEspecifico extends JPanel {
    private Grafo grafo;
    private ListaPersonalizada componentes;
    private static final Color[] COLORES_COMPONENTES = {
        new Color(255, 200, 200),  // Rojo claro - Componente 1
        new Color(200, 255, 200),  // Verde claro - Componente 2
        new Color(200, 200, 255)   // Azul claro - Componente 3
    };
    
    // Posiciones predefinidas para reproducir el layout específico
    private PosicionNodo[] posiciones = {
        // Componente 1 (izquierda)
        new PosicionNodo(150, 150), // Nodo 1
        new PosicionNodo(100, 250), // Nodo 2
        new PosicionNodo(200, 250), // Nodo 3
        new PosicionNodo(150, 350), // Nodo 4
        
        // Componente 2 (centro)
        new PosicionNodo(400, 150), // Nodo 5
        new PosicionNodo(350, 250), // Nodo 6
        new PosicionNodo(450, 250), // Nodo 7
        new PosicionNodo(400, 350), // Nodo 8
        
        // Componente 3 (derecha)
        new PosicionNodo(650, 150), // Nodo 9
        new PosicionNodo(600, 250), // Nodo 10
        new PosicionNodo(700, 250), // Nodo 11
        new PosicionNodo(650, 350)  // Nodo 12
    };
    
    /**
     * Constructor del visualizador específico
     * @param grafo El grafo a visualizar
     * @param componentes Lista de componentes fuertemente conectados
     */
    public VisualizadorGrafoEspecifico(Grafo grafo, ListaPersonalizada componentes) {
        this.grafo = grafo;
        this.componentes = componentes;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
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
        
        // Dibujar leyenda
        dibujarLeyenda(g2d);
    }
    
    /**
     * Dibuja todas las aristas del grafo
     * @param g2d El objeto Graphics2D para dibujar
     */
    private void dibujarAristas(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        
        ListaPersonalizada nodos = grafo.getNodos();
        
        // Mapeo de nodos a posiciones (asumiendo un orden específico)
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo origen = (Nodo) nodos.obtener(i);
            PosicionNodo posOrigen = posiciones[i];
            
            ListaPersonalizada adyacentes = origen.getAdyacentes();
            for (int j = 0; j < adyacentes.tamaño(); j++) {
                Nodo destino = (Nodo) adyacentes.obtener(j);
                int indiceDestino = obtenerIndiceNodo(destino);
                if (indiceDestino != -1) {
                    PosicionNodo posDestino = posiciones[indiceDestino];
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
        double distancia = Math.sqrt(dx * dx + dy * dy);
        double angulo = Math.atan2(dy, dx);
        
        // Acortar la línea para que no llegue hasta el centro del nodo destino
        int radioNodo = 25;
        int xDest = destino.x - (int)((radioNodo * dx) / distancia);
        int yDest = destino.y - (int)((radioNodo * dy) / distancia);
        int xOrig = origen.x + (int)((radioNodo * dx) / distancia);
        int yOrig = origen.y + (int)((radioNodo * dy) / distancia);
        
        // Dibujar línea
        g2d.drawLine(xOrig, yOrig, xDest, yDest);
        
        // Dibujar punta de flecha
        int largoPunta = 12;
        double anguloPunta1 = angulo + Math.PI * 0.8;
        double anguloPunta2 = angulo + Math.PI * 1.2;
        
        int xPunta1 = xDest + (int)(largoPunta * Math.cos(anguloPunta1));
        int yPunta1 = yDest + (int)(largoPunta * Math.sin(anguloPunta1));
        int xPunta2 = xDest + (int)(largoPunta * Math.cos(anguloPunta2));
        int yPunta2 = yDest + (int)(largoPunta * Math.sin(anguloPunta2));
        
        // Rellenar la punta de flecha
        Polygon puntaFlecha = new Polygon();
        puntaFlecha.addPoint(xDest, yDest);
        puntaFlecha.addPoint(xPunta1, yPunta1);
        puntaFlecha.addPoint(xPunta2, yPunta2);
        
        g2d.setColor(Color.BLACK);
        g2d.fill(puntaFlecha);
    }
    
    /**
     * Dibuja todos los nodos del grafo
     * @param g2d El objeto Graphics2D para dibujar
     */
    private void dibujarNodos(Graphics2D g2d) {
        ListaPersonalizada nodos = grafo.getNodos();
        
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            PosicionNodo pos = posiciones[i];
            
            // Determinar color según el componente
            Color colorNodo = determinarColorNodo(nodo);
            
            // Dibujar círculo del nodo
            g2d.setColor(colorNodo);
            g2d.fillOval(pos.x - 25, pos.y - 25, 50, 50);
            
            // Borde del nodo
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(pos.x - 25, pos.y - 25, 50, 50);
            
            // Texto del nodo (número)
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            String texto = String.valueOf(i + 1); // Mostrar número en lugar de ID
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
            return Color.LIGHT_GRAY;
        }
        
        for (int i = 0; i < componentes.tamaño(); i++) {
            ListaPersonalizada componente = (ListaPersonalizada) componentes.obtener(i);
            if (componente.contiene(nodo)) {
                return COLORES_COMPONENTES[i % COLORES_COMPONENTES.length];
            }
        }
        
        return Color.LIGHT_GRAY;
    }
    
    /**
     * Dibuja la leyenda de componentes
     * @param g2d El objeto Graphics2D para dibujar
     */
    private void dibujarLeyenda(Graphics2D g2d) {
        int leyendaX = 50;
        int leyendaY = 450;
        int anchoCaja = 30;
        int altoCaja = 30;
        int separacion = 40;
        
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        for (int i = 0; i < componentes.tamaño(); i++) {
            // Caja de color
            g2d.setColor(COLORES_COMPONENTES[i]);
            g2d.fillRect(leyendaX, leyendaY + i * separacion, anchoCaja, altoCaja);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(leyendaX, leyendaY + i * separacion, anchoCaja, altoCaja);
            
            // Texto
            g2d.drawString("Componente " + (i + 1), leyendaX + anchoCaja + 10, 
                          leyendaY + i * separacion + 20);
        }
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
        JFrame frame = new JFrame("Red Social - Componentes Fuertemente Conectados");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(this);
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