package tresEnRaya;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interfaz gráfica del juego Tres en Raya usando Java Swing.
 *
 * La GUI solo se encarga de mostrar el estado del juego y recoger pulsaciones.
 * Toda la lógica reside en Partida, Tablero y Ficha.
 */
public class TresEnRayaGUI extends JFrame {

    private static final int TAMANIO = 3;

    // Paleta de colores
    private static final Color COLOR_FONDO       = new Color(18, 18, 30);
    private static final Color COLOR_BOTON        = new Color(40, 40, 58);
    private static final Color COLOR_BOTON_HOVER  = new Color(58, 58, 80);
    private static final Color COLOR_X            = new Color(232, 90, 90);
    private static final Color COLOR_O            = new Color(80, 160, 255);
    private static final Color COLOR_ESTADO       = new Color(210, 210, 240);
    private static final Color COLOR_GANADOR_X    = new Color(100, 230, 100);
    private static final Color COLOR_GANADOR_O    = new Color(100, 190, 255);
    private static final Color COLOR_EMPATE       = new Color(255, 200, 70);
    private static final Color COLOR_BTN_REINICIO = new Color(55, 75, 145);

    private Partida partida;
    private final JButton[][] botones = new JButton[TAMANIO][TAMANIO];
    private final JLabel etiquetaEstado;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    public TresEnRayaGUI() {
        super("Tres en Raya");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(COLOR_FONDO);

        // ---- Encabezado ---------------------------------------------------
        JLabel titulo = new JLabel("TRES EN RAYA", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(new Color(180, 180, 255));
        titulo.setBorder(BorderFactory.createEmptyBorder(16, 10, 0, 10));

        etiquetaEstado = new JLabel("", SwingConstants.CENTER);
        etiquetaEstado.setFont(new Font("SansSerif", Font.PLAIN, 18));
        etiquetaEstado.setForeground(COLOR_ESTADO);
        etiquetaEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 8, 10));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(COLOR_FONDO);
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(etiquetaEstado, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // ---- Tablero 3×3 --------------------------------------------------
        JPanel panelTablero = new JPanel(new GridLayout(TAMANIO, TAMANIO, 6, 6));
        panelTablero.setBackground(new Color(10, 10, 20));
        panelTablero.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        for (int f = 0; f < TAMANIO; f++) {
            for (int c = 0; c < TAMANIO; c++) {
                JButton btn = construirBoton(f, c);
                botones[f][c] = btn;
                panelTablero.add(btn);
            }
        }
        add(panelTablero, BorderLayout.CENTER);

        // ---- Botón reiniciar ----------------------------------------------
        JButton btnReiniciar = new JButton("Nueva partida");
        btnReiniciar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnReiniciar.setBackground(COLOR_BTN_REINICIO);
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setBorderPainted(false);
        btnReiniciar.setOpaque(true);
        btnReiniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnReiniciar.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        btnReiniciar.addActionListener(e -> reiniciar());

        JPanel sur = new JPanel();
        sur.setBackground(COLOR_FONDO);
        sur.setBorder(BorderFactory.createEmptyBorder(4, 10, 14, 10));
        sur.add(btnReiniciar);
        add(sur, BorderLayout.SOUTH);

        // ---- Inicio de partida -------------------------------------------
        nuevaPartida();

        // ---- Ventana ------------------------------------------------------
        setSize(380, 460);
        setMinimumSize(new Dimension(320, 400));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // -----------------------------------------------------------------------
    // Construcción de botones
    // -----------------------------------------------------------------------

    private JButton construirBoton(int fila, int columna) {
        JButton btn = new JButton("");
        btn.setFont(new Font("SansSerif", Font.BOLD, 60));
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> manejarClic(fila, columna));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled() && btn.getText().isEmpty() && !partida.terminada()) {
                    btn.setBackground(COLOR_BOTON_HOVER);
                }
            }
            @Override public void mouseExited(MouseEvent e) {
                if (btn.isEnabled() && btn.getText().isEmpty()) {
                    btn.setBackground(COLOR_BOTON);
                }
            }
        });

        return btn;
    }

    // -----------------------------------------------------------------------
    // Lógica de interacción
    // -----------------------------------------------------------------------

    private void manejarClic(int fila, int columna) {
        if (partida.terminada()) return;

        // Determinar turno actual ANTES de jugar
        // partida.siguiente() devuelve el siguiente del turno actual,
        // luego el turno actual = partida.siguiente().siguiente()
        Ficha turnoAntes = partida.siguiente().siguiente();

        partida.jugar(fila, columna);

        // Si el turno cambió, la jugada fue válida y debemos reflejar la ficha
        Ficha turnoAhora = partida.terminada() ? null : partida.siguiente().siguiente();
        if (turnoAhora == null || turnoAntes != turnoAhora) {
            // Jugada válida: mostrar ficha en botón
            JButton btn = botones[fila][columna];
            btn.setText(turnoAntes.toString());
            btn.setForeground(turnoAntes == Ficha.X ? COLOR_X : COLOR_O);
            btn.setEnabled(false);
        }

        actualizarEstado();
    }

    private void actualizarEstado() {
        if (partida.terminada()) {
            Ficha ganador = partida.ganador();
            if (ganador != null) {
                etiquetaEstado.setText("¡Gana " + ganador + "!");
                etiquetaEstado.setForeground(ganador == Ficha.X ? COLOR_GANADOR_X : COLOR_GANADOR_O);
            } else {
                etiquetaEstado.setText("¡Empate!");
                etiquetaEstado.setForeground(COLOR_EMPATE);
            }
            // Deshabilitar casillas vacías restantes
            for (int f = 0; f < TAMANIO; f++) {
                for (int c = 0; c < TAMANIO; c++) {
                    if (botones[f][c].getText().isEmpty()) {
                        botones[f][c].setEnabled(false);
                        botones[f][c].setBackground(new Color(30, 30, 45));
                    }
                }
            }
        } else {
            Ficha turnoActual = partida.siguiente().siguiente();
            etiquetaEstado.setText("Turno: " + turnoActual);
            etiquetaEstado.setForeground(turnoActual == Ficha.X ? COLOR_X : COLOR_O);
        }
    }

    // -----------------------------------------------------------------------
    // Reinicio
    // -----------------------------------------------------------------------

    private void reiniciar() {
        nuevaPartida();
    }

    private void nuevaPartida() {
        partida = new Partida(TAMANIO);

        for (int f = 0; f < TAMANIO; f++) {
            for (int c = 0; c < TAMANIO; c++) {
                botones[f][c].setText("");
                botones[f][c].setEnabled(true);
                botones[f][c].setBackground(COLOR_BOTON);
                botones[f][c].setForeground(Color.WHITE);
            }
        }

        // El turno inicial es X
        etiquetaEstado.setText("Turno: X");
        etiquetaEstado.setForeground(COLOR_X);
    }

    // -----------------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TresEnRayaGUI::new);
    }
}
