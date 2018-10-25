package juego.control;

import static juego.control.ArbitroDamas.TAMAÑO_POR_DEFECTO;
import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.TipoPieza.DAMA;
import static juego.modelo.TipoPieza.PEON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.MultipleFailuresError;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Pieza;
import juego.modelo.Tablero;
import juego.util.ConversorJugada;

/**
 * Pruebas unitarias sobre el árbitro de las damas.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 * 
 */
@DisplayName("Tests de funcionamiento básico en partida del árbitro")
public class ArbitroTest {

	/** Tablero auxiliar. */
	private static final Tablero tableroReferencia = new Tablero(TAMAÑO_POR_DEFECTO, TAMAÑO_POR_DEFECTO);

	/** Tablero para testing. */
	private Tablero tablero;

	/**
	 * Inicialización del tablero antes de cada test.
	 */
	@BeforeEach
	void inicializar() {
		tablero = new Tablero(TAMAÑO_POR_DEFECTO, TAMAÑO_POR_DEFECTO);
	}

	/**
	 * Comprueba movimientos ilegales de un peón intentando mover distancias,
	 * sentidos incorrectos o posiciones ocupadas.
	 * 
	 * @param jugada conjunto de celdas que forman una jugada
	 */
	 // @formatter:off
	 /* Partimos del tablero con las piezas colocadas por defecto con 24 piezas.
	 * 
	 *   A B C D E F G H 
	 * 8 X - X - X - X - 
	 * 7 - X - X - X - X 
	 * 6 X - X - X - X - 
	 * 5 - - - - - - - - 
	 * 4 - - - - - - - - 
	 * 3 - O - O - O - O 
	 * 2 O - O - O - O - 
	 * 1 - O - O - O - O
	 */
	 // @formatter:on
	@DisplayName("Comprueba movimientos ilegales de un peón al iniciar la partida")
	@ParameterizedTest
	@MethodSource("generarJugadasIncorrectasInicialmenteConPeon")
	void movimientoIlegalPeon(ArrayList<Celda> jugada) {
		ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");
		arbitro.colocarPeonesIniciales();
		jugada = proyectarSobreTablero(tablero, jugada);
		assertThat("Jugada ilegal para un peón se reconoce como legal", arbitro.esMovimientoLegal(jugada), is(false));
	}

	/**
	 * Genera jugadas incorrectas para el peón. Se utiliza como fábrica de datos de
	 * un test.
	 * 
	 * @return flujo de datos para testing
	 * @see ArbitroTest#movimientoIlegalPeon(ArrayList)
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> generarJugadasIncorrectasInicialmenteConPeon() {
		return Stream.of(Arguments.of(ConversorJugada.traducirTextoACeldas("3B-4B", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3B-6B", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3B-7B", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3F-4F", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3F-5D", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3F-8F", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("2A-3B", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("2C-3B", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("1F-2E", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("1F-2G", tableroReferencia)));
	}

	/**
	 * Comprueba movimientos legales de un peón intentando mover distancias o
	 * sentidos correctos.
	 * 
	 * @param jugada conjunto de celdas que forman una jugada
	 */
	// @formatter:off
	 /* Partimos del tablero con las piezas colocadas por defecto con 24 piezas.
	 *   A B C D E F G H 
	 * 8 X - X - X - X - 
	 * 7 - X - X - X - X 
	 * 6 X - X - X - X - 
	 * 5 - - - - - - - - 
	 * 4 - - - - - - - - 
	 * 3 - O - O - O - O 
	 * 2 O - O - O - O - 
	 * 1 - O - O - O - O
	 */
	// @formatter:on
	@DisplayName("Comprueba movimientos legales simples de un peón al iniciar la partida")
	@ParameterizedTest
	@MethodSource("generarJugadasCorrectasInicialmenteConPeon")
	void movimientoLegalPeon(ArrayList<Celda> jugada) {
		ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");
		arbitro.colocarPeonesIniciales();
		jugada = proyectarSobreTablero(tablero, jugada);
		assertThat("Jugada legal para un peón se reconoce como ilegal", arbitro.esMovimientoLegal(jugada), is(true));

	}
	
	/** 
	 * Traduce la jugada sobre el tablero de referencia al tablero real de juego. 
	 * 
	 * @param tablero tablero de juego
	 * @param celdas celdas del tablero de referencia
	 * @return jugada real
	 */
	private ArrayList<Celda> proyectarSobreTablero(Tablero tablero, ArrayList<Celda> celdas) {
		ArrayList<Celda> jugada = new ArrayList<>();
		for (Celda celda : celdas) {
			jugada.add(tablero.obtenerCelda(celda.obtenerFila(), celda.obtenerColumna()));
		}
		return jugada;
	}

	/**
	 * Genera jugadas correctas para el peón. Se utiliza como fábrica de datos de un
	 * test.
	 * 
	 * @return flujo de datos para testing
	 * @see ArbitroTest#movimientoLegalPeon(ArrayList)
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> generarJugadasCorrectasInicialmenteConPeon() {
		return Stream.of(Arguments.of(ConversorJugada.traducirTextoACeldas("3B-4A", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3B-4C", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3D-4C", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3D-4E", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3F-4E", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3F-4G", tableroReferencia)),
				Arguments.of(ConversorJugada.traducirTextoACeldas("3H-4G", tableroReferencia)));
	}

	/**
	 * Pruebas de saltos múltiples con captura por parte de los peones.
	 * 
	 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
	 * @version 1.0 20180807
	 *
	 */
	@DisplayName("Realizando múltiples capturas con un peón")
	@Nested
	class CapturasMultiplesConPeon {
		/**
		 * Comprueba captura múltiple con peones.
		 *
		 */
		// @formatter:off
		/*
		* Se parte del tablero con la siguiente situación inicial. Mueven blancas 2G-4E-6C, 
		* comiendo dos piezas negras. La partida continúa porque queda
		* una piedra negra viva en 8H.
		* 
		*   A B C D E F G H 
		* 8 - - - - - - - X
		* 7 - - - - - - - - 
		* 6 - - - - - - - - 
		* 5 - - - X - - - - 
		* 4 - - - - - - - - 
		* 3 - - - - - X - - 
		* 2 - - - - - - O - 
		* 1 - - - - - - - -
		*/
		// @formatter:on
		@SuppressWarnings("unchecked")
		@DisplayName("Captura múltiple de dos peones con un peón contrario sin finalizar partida")
		@Test
		void comprobarCapturaMultipleConSaltoPorPeonBlancoSimple() {
			ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");

			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(0, 7));
			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(3, 3));
			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(5, 5));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(6, 6));

			ArrayList<Celda> jugada = (ArrayList<Celda>) ConversorJugada.convertir("2G-4E-6C", tablero);
			assertThat("Hay tres piezas negras", tablero.obtenerCeldasConColor(NEGRO).size(), is(3));
			assertThat("Hay una pieza blanca", tablero.obtenerCeldasConColor(BLANCO).size(), is(1));
			assertThat(arbitro.esMovimientoLegal(jugada), is(true));
			arbitro.moverCambiandoTurno(jugada);
			assertAll(() -> assertThat("Se han comido dos piezas", tablero.obtenerCeldasConColor(NEGRO).size(), is(1)),
					() -> assertThat("No hay una pieza blanca", tablero.obtenerCeldasConColor(BLANCO).size(), is(1)),
					() -> assertThat("Sigue viva la celda en la esquina 8H", tablero.obtenerCelda(0, 7).estaVacia(),
							is(false)),
					() -> assertThat("El peón blanco ha cambiado incorrectamente de color",
							tablero.obtenerCelda(2, 2).obtenerPieza().obtenerColor(), is(BLANCO)),
					() -> assertThat("El peón blanco no se debe cambiar a dama",
							tablero.obtenerCelda(2, 2).obtenerPieza().obtenerTipoPieza(), is(PEON)),
					() -> assertThat("No está acabada la partida", arbitro.estaAcabado(), is(false)));
		}

		/**
		 * Comprueba captura múltiple con peones.
		 */
		// @formatter:off
		/* Se parte del tablero con la siguiente situación inicial. Mueven blancas 2G-4E-6C-8A, 
		* comiendo tres piezas negras y tranformándose en dama. La partida continua porque queda
		* una piedra negra en 8H.
		*
		*   A B C D E F G H 
		* 8 - - - - - - - X
		* 7 - X - - - - - - 
		* 6 - - - - - - - - 
		* 5 - - - X - - - - 
		* 4 - - - - - - - - 
		* 3 - - - - - X - - 
		* 2 - - - - - - O - 
		* 1 - - - - - - - -
		*/
		// @formatter:on
		@SuppressWarnings("unchecked")
		@Test
		@DisplayName("Captura múltiple de tres peones con un peón contrario sin finalizar partida")
		void comprobarCapturaMultipleConSaltoPorPeonBlancoComplejo() {
			ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");

			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(0, 7));
			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(1, 1));
			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(3, 3));
			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(5, 5));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(6, 6));

			ArrayList<Celda> jugada = (ArrayList<Celda>) ConversorJugada.convertir("2G-4E-6C-8A", tablero);
			assertThat("Hay cuatro piezas negras", tablero.obtenerCeldasConColor(NEGRO).size(), is(4));
			assertThat("Hay una pieza blanca", tablero.obtenerCeldasConColor(BLANCO).size(), is(1));
			assertThat(arbitro.esMovimientoLegal(jugada), is(true));
			arbitro.moverCambiandoTurno(jugada);
			assertAll(() -> assertThat("Se han comido tres piezas", tablero.obtenerCeldasConColor(NEGRO).size(), is(1)),
					() -> assertThat("No hay una pieza blanca", tablero.obtenerCeldasConColor(BLANCO).size(), is(1)),
					() -> assertThat("Sigue viva la celda en la esquina 8H", tablero.obtenerCelda(0, 7).estaVacia(),
							is(false)),
					() -> assertThat("El peón blanco ha cambiado incorrectamente de color",
							tablero.obtenerCelda(0, 0).obtenerPieza().obtenerColor(), is(BLANCO)),
					() -> assertThat("El peón blanco no se ha transformado a dama",
							tablero.obtenerCelda(0, 0).obtenerPieza().obtenerTipoPieza(), is(DAMA)),
					() -> assertThat("No está acabada la partida", arbitro.estaAcabado(), is(false)));
		}
	}

	/**
	 * Pruebas del cambio de peón a dama al llegar a la última fila del jugador
	 * contrario.
	 * 
	 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
	 * @version 1.0 20180807
	 */
	@DisplayName("Cambiando el peón a dama al llegar a última fila del contrario")
	@Nested
	class CambioDePeonADama {

		/**
		 * Comprueba la transformación de peón a dama al llegar a los bordes de los
		 * tableros.
		 */
		// @formatter:off		
		/* Se parte del tablero con la siguiente situación inicial. Mueve peón blanco 
		* y luego peón negro transformándose ambos en damas que denotamos como (B) y (N).
		* 
	 	*   A B C D E F G H 
	 	* 8 - - - - - - - - 
	 	* 7 - - - O - - - - 
	 	* 6 - - - - - - - - 
	 	* 5 - - - - - - - - 
	 	* 4 - - - - - - - - 
	 	* 3 - - - - - - - - 
	 	* 2 - - X - - - - - 
	 	* 1 - - - - - - - -
	 	*/
	 	// @formatter:on
		@SuppressWarnings("unchecked")
		@Test
		@DisplayName("Transformación correcta de peón a dama al llegar a última fila del contrario")
		void comprobarTransformacionDePeonADama() {
			ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");

			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(6, 2));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(1, 3));

			// Movemos con peón blanco
			ArrayList<Celda> jugada = (ArrayList<Celda>) ConversorJugada.convertir("7D-8E", tablero);
			assertThat("El movimiento debería ser legal", arbitro.esMovimientoLegal(jugada), is(true));
			assertThat("El tamaño de la lista de celdas con piezas blancas no es correcto",
					(List<Celda>) tablero.obtenerCeldasConColor(BLANCO), hasSize(1));

			// si es legal, jugamos con blancas
			arbitro.moverCambiandoTurno(jugada);
			Celda celdaB = tablero.obtenerCelda(0, 4); // 8E
			comprobarPeonTransformadoADama(jugada, celdaB, BLANCO);

			// Movemos con peón negro
			jugada = (ArrayList<Celda>) ConversorJugada.convertir("2C-1B", tablero);
			assertThat("El movimiento debería ser legal", arbitro.esMovimientoLegal(jugada), is(true));
			assertThat("El tamaño de la lista de celdas con piezas negras no es correcto",
					(List<Celda>) tablero.obtenerCeldasConColor(NEGRO), hasSize(1));

			// si es legal, jugamos con negras
			arbitro.moverCambiandoTurno(jugada);
			Celda celdaN = tablero.obtenerCelda(7, 1); // 1B
			comprobarPeonTransformadoADama(jugada, celdaN, NEGRO);

			// Comprueba que solo queda una pieza de cada color
			assertThat(tablero.obtenerNumeroPiezas(NEGRO), is(1));
			assertThat(tablero.obtenerNumeroPiezas(BLANCO), is(1));

		}

		/**
		 * Comprueba que el peón se ha transformado correctamente a damaa.
		 * 
		 * @param jugada jugada
		 * @param celda  celda
		 * @param color  color
		 * @throws MultipleFailuresError fallos recopilados por JUnit
		 */
		@SuppressWarnings("unchecked")
		private void comprobarPeonTransformadoADama(List<Celda> jugada, Celda celda, final Color color)
				throws MultipleFailuresError {
			assertAll(() -> assertThat("No se ha colocado el peón", celda.estaVacia(), is(false)),
					() -> assertThat("El color no es correcto", celda.obtenerColorDePieza(), is(color)),
					() -> assertThat(
							"El tipo de pieza no ha cambiado de peon de color " + color + " a dama de color " + color,
							celda.obtenerPieza().obtenerTipoPieza(), is(DAMA)),
					() -> assertThat("El tamaño de la lista de celdas con piezas de color " + color + " no es correcto",
							(List<Celda>) tablero.obtenerCeldasConColor(color), hasSize(1)));
		}
	}

	/**
	 * Pruebas de finalización de partida por bloqueos al jugador contrario.
	 * 
	 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
	 * @version 1.0 20180807
	 *
	 */
	@DisplayName("Comprobando casos de bloqueo al jugador contario")
	@Nested
	class ComprobacionBloqueos {

		/**
		 * Comprueba que el jugador con negras se queda bloqueado una vez el jugador con
		 * blancas mueve. El jugador con negras no puede mover ninguno de sus dos peones
		 * perdiendo la partida.
		 */
		// @formatter:off
		/* Se parte del tablero con la siguiente situación inicial. 
		* Mueven blancas 5B-6C bloqueando a negras.
		*
		*   A B C D E F G H 
		* 8 X - - - - - - - 
		* 7 - X - - - - - - 
		* 6 O - - - - - - - 
		* 5 - O - O - - - - 
		* 4 - - - - - - - - 
		* 3 - - - - - - - - 
		* 2 - - - - - - - - 
		* 1 - - - - - - - -
		*/
		// @formatter:on
		@SuppressWarnings("unchecked")
		@DisplayName("Bloqueo de negras con derrota al impedir mover sus dos peones")
		@Test
		void comprobarDerrotaPorBloqueoDeNegrasMasComplejo() {
			ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");

			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(0, 0));
			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(1, 1));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(2, 0));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(3, 1));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(3, 3));

			ArrayList<Celda> jugada = (ArrayList<Celda>) ConversorJugada.convertir("5B-6C", tablero);
			assertThat(arbitro.esMovimientoLegal(jugada), is(true));
			arbitro.moverCambiandoTurno(jugada);
			assertAll(() -> assertThat("No está acabada la partida", arbitro.estaAcabado(), is(true)),
					() -> assertThat("El ganador no es correcto", arbitro.obtenerGanador().obtenerColor(), is(BLANCO)),
					() -> assertThat("El jugador con negras no está bloqueado", arbitro.estaBloqueadoJugador(NEGRO),
							is(true)));

		}

		/**
		 * Comprueba que el jugador con negras se queda bloqueado una vez el jugador con
		 * blancas mueve. El jugador con negras no puede mover su peón perdiendo la
		 * partida.
		 */
		// @formatter:off
		 /* Se parte del tablero con la siguiente situación inicial. Mueven blancas 5D-6C bloqueando a negras.
		 * 
		 *   A B C D E F G H 
		 * 8 X - - - - - - - 
		 * 7 - O - - - - - - 
		 * 6 - - - - - - - - 
		 * 5 - - - O - - - - 
		 * 4 - - - - - - - - 
		 * 3 - - - - - - - - 
		 * 2 - - - - - - - - 
		 * 1 - - - - - - - -
		 */
		 // @formatter:on
		@SuppressWarnings("unchecked")
		@DisplayName("Bloqueo de negras con derrota al impedir mover su único peón")
		@Test
		public void comprobarDerrotaPorBloqueoDeNegrasSimple() {
			ArbitroDamas arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");

			tablero.colocar(new Pieza(NEGRO, PEON), tablero.obtenerCelda(0, 0));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(1, 1));
			tablero.colocar(new Pieza(BLANCO, PEON), tablero.obtenerCelda(3, 3));

			ArrayList<Celda> jugada = (ArrayList<Celda>) ConversorJugada.convertir("5D-6C", tablero);
			assertThat(arbitro.esMovimientoLegal(jugada), is(true));
			arbitro.moverCambiandoTurno(jugada);
			assertAll(() -> assertThat("No está acabada la partida", arbitro.estaAcabado(), is(true)),
					() -> assertThat("El ganador no es correcto", arbitro.obtenerGanador().obtenerColor(), is(BLANCO)),
					() -> assertThat("El jugador con negras no está bloqueado", arbitro.estaBloqueadoJugador(NEGRO),
							is(true)));

		}
	}
}
