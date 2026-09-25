package tresEnRaya;

import java.util.Scanner;

/**
 * Punto de entrada para jugar una partida desde la consola.
 */
public class Main {

    public static void main(String[] args) {
        Partida partida = new Partida(3);

        try (Scanner scanner = new Scanner(System.in)) {
            while (!partida.terminada()) {
                System.out.println(partida);
                System.out.println("Turno de " + partida.siguiente().siguiente() + ".");

                if (!scanner.hasNextLine()) {
                    return;
                }
                Integer fila = leerNumero(scanner, "Fila: ");
                if (!scanner.hasNextLine()) {
                    return;
                }
                Integer columna = leerNumero(scanner, "Columna: ");
                if (fila == null || columna == null) {
                    System.out.println("Introduce números enteros válidos.");
                    continue;
                }

                Ficha siguienteAntes = partida.siguiente();
                partida.jugar(fila, columna);
                if (partida.siguiente() == siguienteAntes) {
                    System.out.println("Jugada no válida. Prueba otra casilla.");
                }
            }

            System.out.println(partida);
            if (partida.ganador() == null) {
                System.out.println("Empate.");
            } else {
                System.out.println("Ha ganado " + partida.ganador() + ".");
            }
        }
    }

    private static Integer leerNumero(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine();
        try {
            return Integer.valueOf(entrada.trim());
        } catch (NumberFormatException excepcion) {
            return null;
        }
    }
}
