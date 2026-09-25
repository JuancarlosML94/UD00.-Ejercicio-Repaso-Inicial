# Tres en Raya - Juego en Java

Implementación del juego **Tres en Raya** para el ejercicio de repaso de 2º DAW.

## Estructura del proyecto

```
UD00.-Ejercicio-Repaso-Inicial/
├── src/
│   ├── main/java/tresEnRaya/
│   │   ├── Ficha.java          # enum: X, O + siguiente()
│   │   ├── Tablero.java        # Lógica del tablero
│   │   ├── Partida.java        # Control de turno y partida
│   │   └── TresEnRayaGUI.java  # Interfaz gráfica Swing
│   └── test/java/tresEnRaya/
│       └── TresEnRayaTest.java # 24 tests JUnit 5
├── lib/
│   └── junit-platform-console-standalone-1.10.2.jar
├── out/
│   ├── main/                   # Clases compiladas
│   └── test/                   # Tests compilados
└── run.bat                     # Script de compilación y ejecución
```

## Cómo ejecutar

### Compilar y ejecutar todo (tests + GUI)
```bat
run.bat
```

### Solo compilar las clases principales
```bat
javac -encoding UTF-8 -d out\main src\main\java\tresEnRaya\Ficha.java src\main\java\tresEnRaya\Tablero.java src\main\java\tresEnRaya\Partida.java src\main\java\tresEnRaya\TresEnRayaGUI.java
```

### Solo ejecutar la GUI
```bat
java -cp out\main tresEnRaya.TresEnRayaGUI
```

### Solo ejecutar los tests
```bat
java -cp "out\main;out\test;lib\junit-platform-console-standalone-1.10.2.jar" org.junit.platform.console.ConsoleLauncher --scan-class-path="out\test"
```

## Diagrama de clases

```
Ficha (enum)
  └── siguiente(): Ficha

Partida
  ├── -turno: Ficha
  ├── -tablero: Tablero
  ├── +Partida(int)
  ├── +siguiente(): Ficha
  ├── +jugar(int, int): void
  ├── +terminada(): boolean
  ├── +ganador(): Ficha
  └── +toString(): String

Tablero
  ├── +Tablero(int)
  ├── +jugar(Ficha, int, int): boolean
  ├── +estaLleno(): boolean
  ├── +ganador(): Ficha
  ├── +gana(Ficha): boolean
  ├── #ganaHorizontal(Ficha): boolean
  ├── #ganaVertical(Ficha): boolean
  ├── #ganaDiagonalDirecta(Ficha): boolean
  ├── #ganaDiagonalIndirecta(Ficha): boolean
  └── +toString(): String
```
