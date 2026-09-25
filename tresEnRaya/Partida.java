package tresEnRaya;

/**
 * Clase que controla una partida de Tres en Raya.
 *
 * Responsabilidades:
 * - Controlar el turno
 * - Delegar en Tablero la colocación de fichas
 * - Determinar si la partida ha terminado
 * - Determinar el ganador
 */
public class Partida {

    private Ficha turno;
    private Tablero tablero;

    /**
     * Crea una nueva partida con un tablero de tamanio x tamanio.
     * El turno inicial es siempre X.
     *
     * @param tamanio dimensión del tablero
     */
    public Partida(int tamanio) {
        this.tablero = new Tablero(tamanio);
        this.turno = Ficha.X;
    }

    /**
     * Devuelve la ficha del siguiente turno (sin cambiar el turno actual).
     *
     * @return la ficha siguiente
     */
    public Ficha siguiente() {
        return turno.siguiente();
    }

    /**
     * Realiza una jugada en la posición (fila, columna).
     * El turno solo cambia si la jugada se ha podido realizar correctamente.
     *
     * @param fila    índice de fila (0-based)
     * @param columna índice de columna (0-based)
     */
    public void jugar(int fila, int columna) {
        if (terminada()) {
            return;
        }
        boolean exito = tablero.jugar(turno, fila, columna);
        if (exito) {
            turno = turno.siguiente();
        }
    }

    /**
     * Indica si la partida ha terminado (hay ganador o el tablero está lleno).
     *
     * @return true si la partida ha terminado
     */
    public boolean terminada() {
        return tablero.ganador() != null || tablero.estaLleno();
    }

    /**
     * Devuelve la ficha ganadora, o null si no hay ganador todavía.
     *
     * @return Ficha.X, Ficha.O, o null
     */
    public Ficha ganador() {
        return tablero.ganador();
    }

    /**
     * Devuelve una representación textual del estado actual de la partida.
     */
    @Override
    public String toString() {
        return tablero.toString();
    }
}
