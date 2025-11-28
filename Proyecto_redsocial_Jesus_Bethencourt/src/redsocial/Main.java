/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package redsocial;

import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación
 * @author Jesús Bethencourt
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazGrafica().setVisible(true);
        });
    }
}
