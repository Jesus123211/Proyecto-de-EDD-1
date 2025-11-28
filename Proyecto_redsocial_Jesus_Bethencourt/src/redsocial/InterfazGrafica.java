/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redsocial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase principal de la interfaz gráfica de usuario
 * @author Jesús Bethencourt
 */
public class InterfazGrafica extends JFrame {
    private Grafo grafo;
    private ArchivoManager archivoManager;
    private JTextArea areaTexto;
    private JFileChooser selectorArchivos;
    
    /**
     * Constructor de la interfaz gráfica
     */
    public InterfazGrafica() {
        this.grafo = new Grafo();
        this.archivoManager = new ArchivoManager();
        this.selectorArchivos = new JFileChooser();
        
        inicializarInterfaz();
        cargarArchivoInicial();
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarInterfaz() {
        setTitle("Análisis de Red Social - Detección de Comunidades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Crear barra de menú
        JMenuBar barraMenu = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemCargar = new JMenuItem("Cargar archivo");
        JMenuItem itemCargarEspecifico = new JMenuItem("Cargar grafo específico");
        JMenuItem itemGuardar = new JMenuItem("Guardar cambios");
        JMenuItem itemSalir = new JMenuItem("Salir");
        
        menuArchivo.add(itemCargar);
        menuArchivo.add(itemCargarEspecifico);
        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        
        // Menú Grafo
        JMenu menuGrafo = new JMenu("Grafo");
        JMenuItem itemAgregarUsuario = new JMenuItem("Agregar usuario");
        JMenuItem itemEliminarUsuario = new JMenuItem("Eliminar usuario");
        JMenuItem itemAgregarRelacion = new JMenuItem("Agregar relación");
        JMenuItem itemEliminarRelacion = new JMenuItem("Eliminar relación");
        JMenuItem itemMostrarInfo = new JMenuItem("Mostrar información");
        
        menuGrafo.add(itemAgregarUsuario);
        menuGrafo.add(itemEliminarUsuario);
        menuGrafo.addSeparator();
        menuGrafo.add(itemAgregarRelacion);
        menuGrafo.add(itemEliminarRelacion);
        menuGrafo.addSeparator();
        menuGrafo.add(itemMostrarInfo);
        
        // Menú Análisis
        JMenu menuAnalisis = new JMenu("Análisis");
        
        // Submenú para Componentes Conectados
        JMenu subMenuComponentes = new JMenu("Componentes Fuertemente Conectados");
        JMenuItem itemIdentificarComponentes = new JMenuItem("Identificar componentes");
        JMenuItem itemMostrarComponentes = new JMenuItem("Mostrar componentes con colores");
        JMenuItem itemGrafo3Componentes = new JMenuItem("Grafo específico - 3 componentes");
        
        subMenuComponentes.add(itemIdentificarComponentes);
        subMenuComponentes.add(itemMostrarComponentes);
        subMenuComponentes.add(itemGrafo3Componentes);
        
        // Submenú para Visualización
        JMenu subMenuVisualizacion = new JMenu("Visualización");
        JMenuItem itemVisualizarGrafo = new JMenuItem("Visualizar grafo básico");
        JMenuItem itemVisualizarCompleto = new JMenuItem("Visualización completa");
        
        subMenuVisualizacion.add(itemVisualizarGrafo);
        subMenuVisualizacion.add(itemVisualizarCompleto);
        
        // Agregar submenús al menú Análisis
        menuAnalisis.add(subMenuComponentes);
        menuAnalisis.add(subMenuVisualizacion);
        
        // Agregar menús a la barra
        barraMenu.add(menuArchivo);
        barraMenu.add(menuGrafo);
        barraMenu.add(menuAnalisis);
        
        setJMenuBar(barraMenu);
        
        // Crear panel principal con pestañas
        JTabbedPane panelPestañas = new JTabbedPane();
        
        // Pestaña 1: Información del Grafo
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        panelPestañas.addTab("Información del Grafo", scrollTexto);
        
        // Pestaña 2: Panel de Control
        JPanel panelControl = crearPanelControl();
        panelPestañas.addTab("Controles", panelControl);
        
        add(panelPestañas, BorderLayout.CENTER);
        
        // Panel de estado
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel etiquetaEstado = new JLabel("Sistema de Análisis de Red Social - Listo");
        etiquetaEstado.setFont(new Font("Arial", Font.BOLD, 12));
        panelEstado.add(etiquetaEstado);
        add(panelEstado, BorderLayout.SOUTH);
        
        // ========== CONFIGURACIÓN DE LISTENERS ==========
        
        // Listeners del menú Archivo
        itemCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });
        
        itemCargarEspecifico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarGrafoEspecifico();
            }
        });
        
        itemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
        
        itemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Listeners del menú Grafo
        itemAgregarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuario();
            }
        });
        
        itemEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        
        itemAgregarRelacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarRelacion();
            }
        });
        
        itemEliminarRelacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRelacion();
            }
        });
        
        itemMostrarInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarAreaTexto();
            }
        });
        
        // Listeners del menú Análisis - Componentes
        itemIdentificarComponentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identificarComponentes();
            }
        });
        
        itemMostrarComponentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarComponentesConColores();
            }
        });
        
        itemGrafo3Componentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafoEspecifico();
            }
        });
        
        // Listeners del menú Análisis - Visualización
        itemVisualizarGrafo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafo();
            }
        });
        
        itemVisualizarCompleto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizacionCompleta();
            }
        });
    }
    
    /**
     * Crea el panel de control con botones rápidos
     * @return JPanel con controles
     */
    private JPanel crearPanelControl() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botones de operaciones comunes
        JButton btnCargarEspecifico = new JButton("Cargar Grafo 3 Componentes");
        JButton btnIdentificar = new JButton("Identificar Componentes");
        JButton btnVisualizar = new JButton("Visualizar Grafo");
        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
        JButton btnAgregarRelacion = new JButton("Agregar Relación");
        JButton btnEliminarRelacion = new JButton("Eliminar Relación");
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnMostrarInfo = new JButton("Mostrar Información");
        JButton btnLimpiar = new JButton("Limpiar Pantalla");
        JButton btnAcercaDe = new JButton("Acerca de");
        
        // Configurar listeners para los botones
        btnCargarEspecifico.addActionListener(e -> cargarGrafoEspecifico());
        btnIdentificar.addActionListener(e -> identificarComponentes());
        btnVisualizar.addActionListener(e -> mostrarGrafo());
        btnAgregarUsuario.addActionListener(e -> agregarUsuario());
        btnEliminarUsuario.addActionListener(e -> eliminarUsuario());
        btnAgregarRelacion.addActionListener(e -> agregarRelacion());
        btnEliminarRelacion.addActionListener(e -> eliminarRelacion());
        btnGuardar.addActionListener(e -> guardarCambios());
        btnMostrarInfo.addActionListener(e -> actualizarAreaTexto());
        btnLimpiar.addActionListener(e -> areaTexto.setText(""));
        btnAcercaDe.addActionListener(e -> mostrarAcercaDe());
        
        // Agregar botones al panel
        panel.add(btnCargarEspecifico);
        panel.add(btnIdentificar);
        panel.add(btnVisualizar);
        panel.add(btnAgregarUsuario);
        panel.add(btnEliminarUsuario);
        panel.add(btnAgregarRelacion);
        panel.add(btnEliminarRelacion);
        panel.add(btnGuardar);
        panel.add(btnMostrarInfo);
        panel.add(btnLimpiar);
        panel.add(btnAcercaDe);
        
        return panel;
    }
    
    // ========== MÉTODOS ADICIONALES NECESARIOS ==========
    
    /**
     * Carga el grafo específico con 3 componentes
     */
    private void cargarGrafoEspecifico() {
        grafo = archivoManager.cargarGrafoEspecifico();
        actualizarAreaTexto();
        mostrarMensaje("Grafo específico de 3 componentes cargado exitosamente");
    }
    
    /**
     * Muestra componentes con colores en visualización básica
     */
    private void mostrarComponentesConColores() {
        Kosaraju kosaraju = new Kosaraju(grafo);
        ListaPersonalizada componentes = kosaraju.encontrarCFCs();
        
        VisualizadorGrafo visualizador = new VisualizadorGrafo(grafo, componentes);
        visualizador.mostrar();
        
        areaTexto.append("\n=== COMPONENTES IDENTIFICADOS ===\n");
        for (int i = 0; i < componentes.tamaño(); i++) {
            ListaPersonalizada componente = (ListaPersonalizada) componentes.obtener(i);
            areaTexto.append("Componente " + (i + 1) + " (" + getNombreColor(i) + "): " + 
                            componente.tamaño() + " usuarios\n");
        }
    }
    
    /**
     * Muestra la visualización específica del grafo con 3 componentes
     */
    private void mostrarGrafoEspecifico() {
        Kosaraju kosaraju = new Kosaraju(grafo);
        ListaPersonalizada componentes = kosaraju.encontrarCFCs();
        
        // Mostrar información en el área de texto
        areaTexto.append("\n\n=== VISUALIZACIÓN ESPECÍFICA ===\n");
        areaTexto.append("Mostrando grafo con " + componentes.tamaño() + " componentes:\n");
        
        for (int i = 0; i < componentes.tamaño(); i++) {
            ListaPersonalizada componente = (ListaPersonalizada) componentes.obtener(i);
            areaTexto.append("Componente " + (i + 1) + " (" + getNombreColor(i) + "): ");
            
            for (int j = 0; j < componente.tamaño(); j++) {
                Nodo nodo = (Nodo) componente.obtener(j);
                areaTexto.append(nodo.getId() + " ");
            }
            areaTexto.append("\n");
        }
        
        // Mostrar visualización específica
        VisualizadorGrafoEspecifico visualizador = new VisualizadorGrafoEspecifico(grafo, componentes);
        visualizador.mostrar();
    }
    
    /**
     * Visualización completa con análisis detallado
     */
    private void visualizacionCompleta() {
        // Primero identificar componentes
        Kosaraju kosaraju = new Kosaraju(grafo);
        ListaPersonalizada componentes = kosaraju.encontrarCFCs();
        
        // Mostrar en área de texto
        areaTexto.append("\n=== ANÁLISIS COMPLETO DE LA RED ===\n");
        areaTexto.append("Total de usuarios: " + grafo.getNumeroNodos() + "\n");
        areaTexto.append("Total de relaciones: " + grafo.getNumeroAristas() + "\n");
        areaTexto.append("Componentes fuertemente conectados: " + componentes.tamaño() + "\n\n");
        
        for (int i = 0; i < componentes.tamaño(); i++) {
            ListaPersonalizada componente = (ListaPersonalizada) componentes.obtener(i);
            areaTexto.append("COMPONENTE " + (i + 1) + " (" + getNombreColor(i) + "):\n");
            areaTexto.append("  Tamaño: " + componente.tamaño() + " usuarios\n");
            areaTexto.append("  Usuarios: ");
            
            for (int j = 0; j < componente.tamaño(); j++) {
                Nodo nodo = (Nodo) componente.obtener(j);
                areaTexto.append(nodo.getId() + " ");
            }
            areaTexto.append("\n\n");
        }
        
        // Mostrar visualización específica
        VisualizadorGrafoEspecifico visualizador = new VisualizadorGrafoEspecifico(grafo, componentes);
        visualizador.mostrar();
    }
    
    /**
     * Muestra información acerca del programa
     */
    private void mostrarAcercaDe() {
        String mensaje = "Sistema de Análisis de Red Social\n\n" +
                        "Desarrollado para:\n" +
                        "- Detección de comunidades en redes sociales\n" +
                        "- Identificación de componentes fuertemente conectados\n" +
                        "- Visualización de grafos dirigidos\n\n" +
                        "Algoritmo utilizado: Kosaraju con DFS\n" +
                        "Estructura: Lista de adyacencia personalizada\n\n" +
                        "© 2024 - Proyecto de Análisis de Grafos";
        
        JOptionPane.showMessageDialog(this, mensaje, "Acerca del Sistema", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ========== MÉTODOS ORIGINALES ==========
    
    /**
     * Carga el archivo inicial por defecto
     */
    private void cargarArchivoInicial() {
        // Cargar el grafo específico por defecto para mostrar la funcionalidad
        grafo = archivoManager.cargarGrafoEspecifico();
        actualizarAreaTexto();
        
        // Mostrar mensaje de bienvenida
        areaTexto.setText("=== SISTEMA DE ANÁLISIS DE RED SOCIAL ===\n\n");
        areaTexto.append("Bienvenido al sistema de detección de comunidades\n");
        areaTexto.append("El grafo específico con 3 componentes ha sido cargado.\n\n");
        areaTexto.append("Use el menú 'Análisis' para:\n");
        areaTexto.append("- Identificar componentes fuertemente conectados\n");
        areaTexto.append("- Visualizar el grafo con colores por componente\n");
        areaTexto.append("- Ver el grafo específico de 3 componentes\n\n");
        areaTexto.append("Use el menú 'Grafo' para modificar la red social.\n");
        
        mostrarMensaje("Sistema listo - Grafo de 3 componentes cargado");
    }
    
    /**
     * Carga un archivo seleccionado por el usuario
     */
    private void cargarArchivo() {
        int resultado = selectorArchivos.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = selectorArchivos.getSelectedFile().getPath();
            grafo = archivoManager.cargarGrafo(rutaArchivo);
            actualizarAreaTexto();
            mostrarMensaje("Archivo cargado correctamente: " + rutaArchivo);
        }
    }
    
    /**
     * Guarda los cambios en el archivo actual
     */
    private void guardarCambios() {
        boolean exito = archivoManager.guardarGrafo(grafo, "datos/red_social.txt");
        if (exito) {
            mostrarMensaje("Cambios guardados correctamente");
        } else {
            mostrarMensaje("Error al guardar los cambios");
        }
    }
    
    /**
     * Agrega un nuevo usuario al grafo
     */
    private void agregarUsuario() {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del nuevo usuario (ej: @usuario):");
        if (idUsuario != null && !idUsuario.trim().isEmpty()) {
            if (grafo.agregarNodo(idUsuario.trim()) != null) {
                actualizarAreaTexto();
                mostrarMensaje("Usuario agregado: " + idUsuario);
            } else {
                mostrarMensaje("Error: El usuario ya existe");
            }
        }
    }
    
    /**
     * Elimina un usuario del grafo
     */
    private void eliminarUsuario() {
        String idUsuario = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario a eliminar:");
        if (idUsuario != null && !idUsuario.trim().isEmpty()) {
            if (grafo.eliminarNodo(idUsuario.trim())) {
                actualizarAreaTexto();
                mostrarMensaje("Usuario eliminado: " + idUsuario);
            } else {
                mostrarMensaje("Error: El usuario no existe");
            }
        }
    }
    
    /**
     * Agrega una relación entre dos usuarios
     */
    private void agregarRelacion() {
        String origen = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario origen:");
        String destino = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario destino:");
        
        if (origen != null && destino != null && !origen.trim().isEmpty() && !destino.trim().isEmpty()) {
            if (grafo.agregarArista(origen.trim(), destino.trim())) {
                actualizarAreaTexto();
                mostrarMensaje("Relación agregada: " + origen + " -> " + destino);
            } else {
                mostrarMensaje("Error: No se pudo agregar la relación");
            }
        }
    }
    
    /**
     * Elimina una relación entre dos usuarios
     */
    private void eliminarRelacion() {
        String origen = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario origen:");
        String destino = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario destino:");
        
        if (origen != null && destino != null && !origen.trim().isEmpty() && !destino.trim().isEmpty()) {
            if (grafo.eliminarArista(origen.trim(), destino.trim())) {
                actualizarAreaTexto();
                mostrarMensaje("Relación eliminada: " + origen + " -> " + destino);
            } else {
                mostrarMensaje("Error: No se pudo eliminar la relación");
            }
        }
    }
    
    /**
     * Identifica componentes fuertemente conectados
     */
    private void identificarComponentes() {
        Kosaraju kosaraju = new Kosaraju(grafo);
        ListaPersonalizada componentes = kosaraju.encontrarCFCs();
        
        areaTexto.append("\n\n=== COMPONENTES FUERTEMENTE CONECTADOS ===\n");
        
        for (int i = 0; i < componentes.tamaño(); i++) {
            ListaPersonalizada componente = (ListaPersonalizada) componentes.obtener(i);
            areaTexto.append("Componente " + (i + 1) + ":\n");
            
            for (int j = 0; j < componente.tamaño(); j++) {
                Nodo nodo = (Nodo) componente.obtener(j);
                areaTexto.append("  " + nodo.getId() + "\n");
            }
        }
        
        mostrarMensaje("Componentes identificados: " + componentes.tamaño());
    }
    
    /**
     * Muestra la visualización gráfica del grafo
     */
    private void mostrarGrafo() {
        VisualizadorGrafo visualizador = new VisualizadorGrafo(grafo);
        visualizador.mostrar();
    }
    
    /**
     * Actualiza el área de texto con la información actual del grafo
     */
    private void actualizarAreaTexto() {
        areaTexto.setText("");
        areaTexto.append("=== INFORMACIÓN DEL GRAFO ===\n");
        areaTexto.append("Número de usuarios: " + grafo.getNumeroNodos() + "\n");
        areaTexto.append("Número de relaciones: " + grafo.getNumeroAristas() + "\n\n");
        
        areaTexto.append("=== USUARIOS ===\n");
        ListaPersonalizada nodos = grafo.getNodos();
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            areaTexto.append(nodo.getId() + "\n");
        }
        
        areaTexto.append("\n=== RELACIONES ===\n");
        for (int i = 0; i < nodos.tamaño(); i++) {
            Nodo nodo = (Nodo) nodos.obtener(i);
            ListaPersonalizada adyacentes = nodo.getAdyacentes();
            
            for (int j = 0; j < adyacentes.tamaño(); j++) {
                Nodo adyacente = (Nodo) adyacentes.obtener(j);
                areaTexto.append(nodo.getId() + " -> " + adyacente.getId() + "\n");
            }
        }
    }
    
    /**
     * Muestra un mensaje en la interfaz
     * @param mensaje El mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    /**
     * Obtiene el nombre del color basado en el índice
     * @param indice El índice del color
     * @return El nombre del color
     */
    private String getNombreColor(int indice) {
        String[] nombres = {"Rojo", "Verde", "Azul", "Naranja", "Magenta", "Cian", "Rosa", "Amarillo"};
        return indice < nombres.length ? nombres[indice] : "Color " + (indice + 1);
    }
}