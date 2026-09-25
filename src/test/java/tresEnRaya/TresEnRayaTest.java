package tresEnRaya;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el juego Tres en Raya.
 *
 * Cubre:
 * - Ficha.siguiente()
 * - Colocación correcta de fichas
 * - Intento de ocupar casilla ocupada
 * - Jugadas válidas e inválidas
 * - Victoria horizontal, vertical, diagonal directa e indirecta
 * - Tablero lleno / ausencia de ganador (empate)
 * - Finalización de la partida
 * - Cambio correcto de turno
 * - Que una jugada inválida no cambie el turno
 */
@DisplayName("Tests Tres en Raya")
class TresEnRayaTest {

    private Partida partida;
    private Tablero tablero;

    @BeforeEach
    void setUp() {
        partida = new Partida(3);
        tablero = new Tablero(3);
    }

    // -----------------------------------------------------------------------
    // Ficha.siguiente()
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Ficha.siguiente(): X -> O")
    void fichaSiguienteXesO() {
        assertEquals(Ficha.O, Ficha.X.siguiente());
    }

    @Test
    @DisplayName("Ficha.siguiente(): O -> X")
    void fichaSiguienteOesX() {
        assertEquals(Ficha.X, Ficha.O.siguiente());
    }

    // -----------------------------------------------------------------------
    // Colocación de fichas en Tablero
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Tablero: colocar una ficha en casilla vacía devuelve true")
    void tableroJugarCasillaVaciaDevolverTrue() {
        assertTrue(tablero.jugar(Ficha.X, 0, 0));
    }

    @Test
    @DisplayName("Tablero: colocar en casilla ocupada devuelve false")
    void tableroJugarCasillaOcupadaDevolverFalse() {
        tablero.jugar(Ficha.X, 1, 1);
        assertFalse(tablero.jugar(Ficha.O, 1, 1));
    }

    @Test
    @DisplayName("Tablero: posición fuera de límites devuelve false (fila negativa)")
    void tableroJugarFilaNegativa() {
        assertFalse(tablero.jugar(Ficha.X, -1, 0));
    }

    @Test
    @DisplayName("Tablero: posición fuera de límites devuelve false (columna >= tamaño)")
    void tableroJugarColumnaFueraLimite() {
        assertFalse(tablero.jugar(Ficha.X, 0, 3));
    }

    // -----------------------------------------------------------------------
    // Turno en Partida
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Partida: el turno inicial es X")
    void partidaTurnoInicialEsX() {
        // siguiente() devuelve el siguiente de X, que es O
        assertEquals(Ficha.O, partida.siguiente());
    }

    @Test
    @DisplayName("Partida: jugada válida cambia el turno")
    void partidaJugadaValidaCambiaTurno() {
        // turno inicial = X → siguiente() = O
        assertEquals(Ficha.O, partida.siguiente());
        partida.jugar(0, 0); // X juega
        // turno ahora = O → siguiente() = X
        assertEquals(Ficha.X, partida.siguiente());
    }

    @Test
    @DisplayName("Partida: jugada en casilla ocupada NO cambia el turno")
    void partidaJugadaInvalidaNoCarbiaTurno() {
        partida.jugar(0, 0); // X juega en (0,0)
        Ficha turnoAntes = partida.siguiente(); // O → siguiente = X
        partida.jugar(0, 0); // O intenta jugar en (0,0) → inválido
        assertEquals(turnoAntes, partida.siguiente()); // sigue siendo turno de O
    }

    @Test
    @DisplayName("Partida: jugada fuera de límites NO cambia el turno")
    void partidaJugadaFueraLimitesNoCarbiaTurno() {
        Ficha turnoAntes = partida.siguiente();
        partida.jugar(5, 5); // fuera de límites
        assertEquals(turnoAntes, partida.siguiente());
    }

    // -----------------------------------------------------------------------
    // Victoria horizontal
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Tablero: victoria horizontal X en fila 0")
    void tableroGanaHorizontal() {
        tablero.jugar(Ficha.X, 0, 0);
        tablero.jugar(Ficha.X, 0, 1);
        tablero.jugar(Ficha.X, 0, 2);
        assertTrue(tablero.ganaHorizontal(Ficha.X));
        assertEquals(Ficha.X, tablero.ganador());
    }

    @Test
    @DisplayName("Partida: victoria horizontal X")
    void partidaGanaHorizontal() {
        // X: (0,0), O: (1,0), X: (0,1), O: (1,1), X: (0,2)
        partida.jugar(0, 0); // X
        partida.jugar(1, 0); // O
        partida.jugar(0, 1); // X
        partida.jugar(1, 1); // O
        partida.jugar(0, 2); // X → gana horizontal fila 0
        assertTrue(partida.terminada());
        assertEquals(Ficha.X, partida.ganador());
    }

    // -----------------------------------------------------------------------
    // Victoria vertical
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Tablero: victoria vertical X en columna 2")
    void tableroGanaVertical() {
        tablero.jugar(Ficha.X, 0, 2);
        tablero.jugar(Ficha.X, 1, 2);
        tablero.jugar(Ficha.X, 2, 2);
        assertTrue(tablero.ganaVertical(Ficha.X));
        assertEquals(Ficha.X, tablero.ganador());
    }

    @Test
    @DisplayName("Partida: victoria vertical O")
    void partidaGanaVertical() {
        // X: (0,0), O: (0,1), X: (1,0), O: (1,1), X: (2,2), O: (2,1)
        partida.jugar(0, 0); // X
        partida.jugar(0, 1); // O
        partida.jugar(1, 0); // X
        partida.jugar(1, 1); // O
        partida.jugar(2, 2); // X
        partida.jugar(2, 1); // O → gana vertical columna 1
        assertTrue(partida.terminada());
        assertEquals(Ficha.O, partida.ganador());
    }

    // -----------------------------------------------------------------------
    // Victoria diagonal directa
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Tablero: victoria diagonal directa X (↘)")
    void tableroGanaDiagonalDirecta() {
        tablero.jugar(Ficha.X, 0, 0);
        tablero.jugar(Ficha.X, 1, 1);
        tablero.jugar(Ficha.X, 2, 2);
        assertTrue(tablero.ganaDiagonalDirecta(Ficha.X));
        assertEquals(Ficha.X, tablero.ganador());
    }

    @Test
    @DisplayName("Partida: victoria diagonal directa X")
    void partidaGanaDiagonalDirecta() {
        // X: (0,0), O: (0,1), X: (1,1), O: (0,2), X: (2,2)
        partida.jugar(0, 0); // X
        partida.jugar(0, 1); // O
        partida.jugar(1, 1); // X
        partida.jugar(0, 2); // O
        partida.jugar(2, 2); // X → gana diagonal directa
        assertTrue(partida.terminada());
        assertEquals(Ficha.X, partida.ganador());
    }

    // -----------------------------------------------------------------------
    // Victoria diagonal indirecta
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Tablero: victoria diagonal indirecta X (↙)")
    void tableroGanaDiagonalIndirecta() {
        tablero.jugar(Ficha.X, 0, 2);
        tablero.jugar(Ficha.X, 1, 1);
        tablero.jugar(Ficha.X, 2, 0);
        assertTrue(tablero.ganaDiagonalIndirecta(Ficha.X));
        assertEquals(Ficha.X, tablero.ganador());
    }

    @Test
    @DisplayName("Partida: victoria diagonal indirecta X")
    void partidaGanaDiagonalIndirecta() {
        // X: (0,2), O: (0,0), X: (1,1), O: (0,1), X: (2,0)
        partida.jugar(0, 2); // X
        partida.jugar(0, 0); // O
        partida.jugar(1, 1); // X
        partida.jugar(0, 1); // O
        partida.jugar(2, 0); // X → gana diagonal indirecta
        assertTrue(partida.terminada());
        assertEquals(Ficha.X, partida.ganador());
    }

    // -----------------------------------------------------------------------
    // Tablero lleno / empate
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Tablero: está lleno cuando todas las casillas están ocupadas")
    void tableroEstaLleno() {
        // Llenar sin ganador
        tablero.jugar(Ficha.X, 0, 0);
        tablero.jugar(Ficha.O, 0, 1);
        tablero.jugar(Ficha.X, 0, 2);
        tablero.jugar(Ficha.O, 1, 0);
        tablero.jugar(Ficha.X, 1, 1);
        tablero.jugar(Ficha.X, 1, 2);
        tablero.jugar(Ficha.O, 2, 0);
        tablero.jugar(Ficha.X, 2, 1);
        tablero.jugar(Ficha.O, 2, 2);
        assertTrue(tablero.estaLleno());
    }

    @Test
    @DisplayName("Tablero: no está lleno si hay casillas vacías")
    void tableroNoEstaLleno() {
        tablero.jugar(Ficha.X, 0, 0);
        assertFalse(tablero.estaLleno());
    }

    @Test
    @DisplayName("Tablero: ausencia de ganador en tablero vacío")
    void tableroSinGanador() {
        assertNull(tablero.ganador());
    }

    @Test
    @DisplayName("Partida: empate cuando el tablero está lleno sin ganador")
    void partidaEmpate() {
        // Secuencia sin ganador:
        // X O X
        // X X O
        // O X O  → no hay tres en raya
        partida.jugar(0, 0); // X
        partida.jugar(0, 1); // O
        partida.jugar(0, 2); // X
        partida.jugar(1, 0); // O
        // Hmm, hay que construir una secuencia sin ganador
        // Reiniciamos la lógica con una secuencia conocida
        // Tablero final esperado:
        // X O X
        // O X X
        // O X O  → sin tres en raya (X no tiene columna 1 completa, ni diagonal)
        // Turno: X(0,0) O(0,1) X(0,2) O(1,0) X(1,1) O(2,0) X(1,2) O(2,2) X(2,1)
        // Fila 0: X O X → no gana X
        // Fila 1: O X X → no gana
        // Fila 2: O X O → no gana
        // Col 0: X O O → no gana
        // Col 1: O X X → no gana
        // Col 2: X X O → no gana
        // Diag dir: X X O → no gana
        // Diag ind: X X O → no gana
        partida = new Partida(3);
        partida.jugar(0, 0); // X
        partida.jugar(0, 1); // O
        partida.jugar(0, 2); // X
        partida.jugar(1, 0); // O
        partida.jugar(1, 1); // X
        partida.jugar(2, 0); // O
        partida.jugar(1, 2); // X
        partida.jugar(2, 2); // O
        partida.jugar(2, 1); // X
        assertTrue(partida.terminada());
        assertNull(partida.ganador());
    }

    // -----------------------------------------------------------------------
    // Finalización de partida
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("Partida: no se puede jugar después de que termine")
    void partidaNoPuedeJugarDespuesDeTerminar() {
        // X gana horizontal en fila 0
        partida.jugar(0, 0); // X
        partida.jugar(1, 0); // O
        partida.jugar(0, 1); // X
        partida.jugar(1, 1); // O
        partida.jugar(0, 2); // X → gana
        assertTrue(partida.terminada());

        // Intentar jugar después de terminar no debe cambiar nada
        Ficha ganadorAntes = partida.ganador();
        partida.jugar(2, 2);
        assertEquals(ganadorAntes, partida.ganador());
    }

    @Test
    @DisplayName("Partida: terminada() = false al inicio")
    void partidaNoTerminadaAlInicio() {
        assertFalse(partida.terminada());
    }
}
