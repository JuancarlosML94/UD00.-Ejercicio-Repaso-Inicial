package tresEnRaya;

/**
 * Clase que representa el tablero del juego Tres en Raya.
 *
 * Responsabilidades:
 * - Almacenar las fichas
 * - Comprobar si una posición está disponible
 * - Colocar una ficha
 * - Comprobar si está lleno
 * - Comprobar si una ficha ha ganado
 * - Determinar la ficha ganadora
 * - Representar el tablero mediante toString()
 */
public class Tablero {

    private final Ficha[][] tablero;

    /**
     * Crea un tablero de tamanio x tamanio casillas.
     *
     * @param tamanio dimensión del tablero (ej. 3 para tres en raya)
     */
    public Tablero(int tamanio) {
        this.tablero = new Ficha[tamanio][tamanio];
    }

    /**
     * Intenta colocar una ficha en la posición (fila, columna).
     *
     * @param ficha   la ficha a colocar
     * @param fila    índice de fila (0-based)
     * @param columna índice de columna (0-based)
     * @return true si la jugada se ha realizado; false si la posición no es válida o está ocupada
     */
    public boolean jugar(Ficha ficha, int fila, int columna) {
        if (ficha == null) {
            return false;
        }
        if (fila < 0 || fila >= tablero.length
                || columna < 0 || columna >= tablero.length) {
            return false;
        }
        if (tablero[fila][columna] != null) {
            return false;
        }
        tablero[fila][columna] = ficha;
        return true;
    }

    /**
     * Comprueba si el tablero está completamente lleno.
     *
     * @return true si no hay ninguna casilla vacía
     */
    public boolean estaLleno() {
        for (int f = 0; f < tablero.length; f++) {
            for (int c = 0; c < tablero.length; c++) {
                if (tablero[f][c] == null) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Comprueba si la ficha indicada ha ganado (en cualquier dirección).
     *
     * @param ficha la ficha a comprobar
     * @return true si la ficha ha ganado
     */
    public boolean gana(Ficha ficha) {
        return ganaHorizontal(ficha)
                || ganaVertical(ficha)
                || ganaDiagonalDirecta(ficha)
                || ganaDiagonalIndirecta(ficha);
    }

    /**
     * Devuelve la ficha que ha conseguido una línea, o null si no hay ganador.
     *
     * @return Ficha.X, Ficha.O o null
     */
    public Ficha ganador() {
        if (gana(Ficha.X)) {
            return Ficha.X;
        }
        if (gana(Ficha.O)) {
            return Ficha.O;
        }
        return null;
    }

    /**
     * Comprueba si la ficha gana en alguna fila horizontal.
     *
     * @param ficha la ficha a comprobar
     * @return true si hay tres en raya horizontal
     */
    protected boolean ganaHorizontal(Ficha ficha) {
        if (ficha == null || tablero.length == 0) {
            return false;
        }
        for (int f = 0; f < tablero.length; f++) {
            boolean linea = true;
            for (int c = 0; c < tablero.length; c++) {
                if (tablero[f][c] != ficha) {
                    linea = false;
                    break;
                }
            }
            if (linea) return true;
        }
        return false;
    }

    /**
     * Comprueba si la ficha gana en alguna columna vertical.
     *
     * @param ficha la ficha a comprobar
     * @return true si hay tres en raya vertical
     */
    protected boolean ganaVertical(Ficha ficha) {
        if (ficha == null || tablero.length == 0) {
            return false;
        }
        for (int c = 0; c < tablero.length; c++) {
            boolean linea = true;
            for (int f = 0; f < tablero.length; f++) {
                if (tablero[f][c] != ficha) {
                    linea = false;
                    break;
                }
            }
            if (linea) return true;
        }
        return false;
    }

    /**
     * Comprueba si la ficha gana en la diagonal directa (↘, de [0][0] a [n-1][n-1]).
     *
     * @param ficha la ficha a comprobar
     * @return true si hay tres en raya en la diagonal directa
     */
    protected boolean ganaDiagonalDirecta(Ficha ficha) {
        if (ficha == null || tablero.length == 0) {
            return false;
        }
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i][i] != ficha) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si la ficha gana en la diagonal indirecta (↙, de [0][n-1] a [n-1][0]).
     *
     * @param ficha la ficha a comprobar
     * @return true si hay tres en raya en la diagonal indirecta
     */
    protected boolean ganaDiagonalIndirecta(Ficha ficha) {
        if (ficha == null || tablero.length == 0) {
            return false;
        }
        for (int i = 0; i < tablero.length; i++) {
            if (tablero[i][tablero.length - 1 - i] != ficha) {
                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve una representación textual del tablero.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < tablero.length; c++) {
            sb.append(c).append(" ");
        }
        sb.append("\n");
        sb.append("  ");
        sb.append("--".repeat(tablero.length));
        sb.append("\n");
        for (int f = 0; f < tablero.length; f++) {
            sb.append(f).append("|");
            for (int c = 0; c < tablero.length; c++) {
                sb.append(tablero[f][c] == null ? "." : tablero[f][c]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
