package tresEnRaya;

/**
 * Enumerado que representa las fichas del juego Tres en Raya.
 * Su única responsabilidad es determinar cuál es la siguiente ficha/turno.
 */
public enum Ficha {
    X, O;

    /**
     * Devuelve la siguiente ficha en el turno.
     * X -> O, O -> X
     *
     * @return la ficha del siguiente turno
     */
    public Ficha siguiente() {
        return this == X ? O : X;
    }

    @Override
    public String toString() {
        return name();
    }
}
