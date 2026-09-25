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

    private final int tamanio;
    private final Ficha[][] casillas;

    /**
     * Crea un tablero de tamanio x tamanio casillas.
     *
     * @param tamanio dimensión del tablero (ej. 3 para tres en raya)
     */
    public Tablero(int tamanio) {
        this.tamanio = tamanio;
        this.casillas = new Ficha[tamanio][tamanio];
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
        if (fila < 0 || fila >= tamanio || columna < 0 || columna >= tamanio) {
            return false;
        }
        if (casillas[fila][columna] != null) {
            return false;
        }
        casillas[fila][columna] = ficha;
        return true;
    }

    /**
     * Comprueba si el tablero está completamente lleno.
     *
     * @return true si no hay ninguna casilla vacía
     */
    public boolean estaLleno() {
        for (int f = 0; f < tamanio; f++) {
            for (int c = 0; c < tamanio; c++) {
                if (casillas[f][c] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Devuelve la ficha ganadora, o null si no hay ganador todavía.
     *
     * @return Ficha.X, Ficha.O, o null
     */
    public Ficha ganador() {
        for (Ficha ficha : Ficha.values()) {
            if (gana(ficha)) {
                return ficha;
            }
        }
        return null;
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
     * Comprueba si la ficha gana en alguna fila horizontal.
     *
     * @param ficha la ficha a comprobar
     * @return true si hay tres en raya horizontal
     */
    protected boolean ganaHorizontal(Ficha ficha) {
        for (int f = 0; f < tamanio; f++) {
            boolean linea = true;
            for (int c = 0; c < tamanio; c++) {
                if (casillas[f][c] != ficha) {
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
        for (int c = 0; c < tamanio; c++) {
            boolean linea = true;
            for (int f = 0; f < tamanio; f++) {
                if (casillas[f][c] != ficha) {
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
        for (int i = 0; i < tamanio; i++) {
            if (casillas[i][i] != ficha) {
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
        for (int i = 0; i < tamanio; i++) {
            if (casillas[i][tamanio - 1 - i] != ficha) {
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
        // Encabezado de columnas
        sb.append("  ");
        for (int c = 0; c < tamanio; c++) {
            sb.append(c).append(" ");
        }
        sb.append("\n");
        // Separador
        sb.append("  ");
        sb.append("--".repeat(tamanio));
        sb.append("\n");
        // Filas
        for (int f = 0; f < tamanio; f++) {
            sb.append(f).append("|");
            for (int c = 0; c < tamanio; c++) {
                sb.append(casillas[f][c] == null ? "." : casillas[f][c]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
