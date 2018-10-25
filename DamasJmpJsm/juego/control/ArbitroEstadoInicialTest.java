package juego.control;

import static juego.control.ArbitroDamas.TAMAÑO_POR_DEFECTO;
import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.TipoPieza.PEON;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import juego.modelo.Celda;
import juego.modelo.Pieza;
import juego.modelo.Tablero;

/**
 * Pruebas unitarias sobre el estado inicial del árbitro de las damas. Este
 * conjunto de pruebas se centra en verificar el estado del árbitro justo
 * después de instanciar y después de colocar las piezas.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 */
@DisplayName("Tests sobre el estado inicial del árbitro")
public class ArbitroEstadoInicialTest {

	/** Árbitro de testing. */
	private ArbitroDamas arbitro;
	
	/** Tablero de testing. */
	private Tablero tablero;

	/** Generación del árbitro para testing. */
	@BeforeEach
	void inicializar() {
		// Inyección de tablero para testing...
		tablero = new Tablero(TAMAÑO_POR_DEFECTO, TAMAÑO_POR_DEFECTO);
		arbitro = new ArbitroDamas(tablero, "Negras", "Blancas");
	}

	/**
	 * Comprobacion de inicialización de turno y tablero.
	 */
	// @formatter:off
	/* Partimos de un tablero vacío como el que se muestra:
	 *   A B C D E F G H 
	 * 8 - - - - - - - - 
	 * 7 - - - - - - - - 
	 * 6 - - - - - - - - 
	 * 5 - - - - - - - - 
	 * 4 - - - - - - - - 
	 * 3 - - - - - - - - 
	 * 2 - - - - - - - - 
	 * 1 - - - - - - - -
	 */
	 // @formatter:on
	@DisplayName("Estado inicial correcto al iniciar árbitro")
	@Test
	void comprobarEstadoInicial() {
		assertNotNull("Debe existir un ganador al principio de la partida si no hay colocadas piezas",
				arbitro.obtenerGanador());
		assertNotNull("Debería iniciarse con turno", arbitro.obtenerJugadorConTurno());
		assertThat("El turno debería ser de blancas", arbitro.obtenerJugadorConTurno().obtenerColor(), is(BLANCO));
	}

	/**
	 * Comprobacion de inicialización acabado del árbitro al no colocar piezas
	 * inicialmente. Dado que se considera acabado cuando algún jugador se queda sin
	 * piezas, hasta que no coloquemos piezas, debería estar en estado "acabado".
	 */
	// @formatter:off
	 /* Partimos de un tablero vacío como el que se muestra:
	 *   A B C D E F G H 
	 * 8 - - - - - - - - 
	 * 7 - - - - - - - - 
	 * 6 - - - - - - - - 
	 * 5 - - - - - - - - 
	 * 4 - - - - - - - - 
	 * 3 - - - - - - - - 
	 * 2 - - - - - - - - 
	 * 1 - - - - - - - -
	 */
	 // @formatter:on
	@DisplayName("Estado inicial acabado al no tener piezas todavía sobre el tablero")
	@Test
	void comprobarEstadoInicialAcabadoSiNoPonemosPiezas() {
		assertTrue(arbitro.estaAcabado(), "Si no hay piezas colocadas debería estar en estado acabado.");
	}

	/**
	 * Comprobacion de inicialización correcta del tablero, sin colocar ninguna
	 * pieza, con un tablero vacío.
	 */
	// @formatter:off
	 /* Partimos de un tablero vacío como el que se muestra:
	 *   A B C D E F G H 
	 * 8 - - - - - - - - 
	 * 7 - - - - - - - - 
	 * 6 - - - - - - - - 
	 * 5 - - - - - - - - 
	 * 4 - - - - - - - - 
	 * 3 - - - - - - - - 
	 * 2 - - - - - - - - 
	 * 1 - - - - - - - -
	 */
	 // @formatter:on
	@DisplayName("Estado inicial del tablero vacío con tamaños correctos")
	@Test
	void comprobarEstadoInicialConTablero() {
		assertThat(tablero.obtenerNumeroFilas(), is(TAMAÑO_POR_DEFECTO));
		assertThat(tablero.obtenerNumeroColumnas(), is(TAMAÑO_POR_DEFECTO));
		assertThat(tablero.obtenerNumeroPiezas(NEGRO), is(0));
		assertThat(tablero.obtenerNumeroPiezas(BLANCO), is(0));
	}

	/**
	 * Pruebas particulares añadiendo las piezas de los jugadores. Se hace uso de la
	 * posibilidad de anidar clases dentro de JUnit 5.
	 * 
	 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
	 * @version 1.0 20180807
	 */
	@DisplayName("Colocando las piezas iniciales en un tablero de 8x8")
	@Nested
	class ColocacionInicialDePiezas {

		/**
		 * Inicialización de piezas nuevas sobre el tablero.
		 */
		@BeforeEach
		void colocarPiezas() {
			Pieza.resetearContador();
			arbitro.colocarPeonesIniciales();
		}

		/**
		 * Comprueba que se colocan 12 peones negros en las tres filas inferiores
		 * numeradas consecutivamente desde 1 a 12.
		 * 
		 * @param fila    fila
		 * @param columna columna
		 * @param id      identificador de la pieza
		 */
		//  @formatter:off
		/* Partimos de un tablero que debe tener este estado.
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

		@DisplayName("Coloca las piezas negras en la parte superior del tablero")
		@ParameterizedTest
		@CsvSource({ "0,0,1", "0,2,2", "0,4,3", "0,6,4", "1,1,5", "1,3,6", "1,5,7", "1,7,8", "2,0,9", "2,2,10",
				"2,4,11", "2,6,12" })
		void comprobarColocacionInicialDePiezasNegras(int fila, int columna, int id) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			assertThat("Celda vacia", celda.estaVacia(), is(false));
			assertThat("Color de pieza colocada incorrecta", celda.obtenerColorDePieza(), is(NEGRO));
			assertThat("Tipo de pieza incorrecto", celda.obtenerPieza().obtenerTipoPieza(), is(PEON));
			assertThat("Identificador incorrecto", celda.obtenerPieza().obtenerId(), is(id));
			assertThat("Numero de peones negros incorrecto", tablero.obtenerNumeroPiezas(NEGRO), is(12));
		}

		/**
		 * Comprueba que se colocan 12 peones blancos en las tres filas inferiores
		 * numeradas consecutivamente desde 13 a 24.
		 *
		 * @param fila    fila
		 * @param columna columna
		 * @param id      identificador de la pieza
		 */
		// @formatter:off
		/* Partimos de un tablero que debe tener este estado.
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

		@DisplayName("Coloca las piezas blancas en la parte inferior del tablero")
		@ParameterizedTest
		@CsvSource({ "5,1,13", "5,3,14", "5,5,15", "5,7,16", "6,0,17", "6,2,18", "6,4,19", "6,6,20", "7,1,21", "7,3,22",
				"7,5,23", "7,7,24" })
		void comprobarColocacionInicialDePiezasBlancas(int fila, int columna, int id) {
			Celda celda = tablero.obtenerCelda(fila, columna);
			assertThat("Celda vacia", celda.estaVacia(), is(false));
			assertThat("Color de pieza colocada incorrecta", celda.obtenerColorDePieza(), is(BLANCO));
			assertThat("Tipo de pieza incorrecto", celda.obtenerTipoDePieza(), is(PEON));
			assertThat("Identificador incorrecto", celda.obtenerPieza().obtenerId(), is(id));
			assertThat("Numero de peones negros incorrecto", tablero.obtenerNumeroPiezas(BLANCO), is(12));
		}
	}
}
