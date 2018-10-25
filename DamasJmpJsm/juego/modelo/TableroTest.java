package juego.modelo;

import static juego.modelo.TableroTest.Data.listaConCelda00;
import static juego.modelo.TableroTest.Data.listaConCelda00SE;
import static juego.modelo.TableroTest.Data.listaConCelda25NE;
import static juego.modelo.TableroTest.Data.listaConCelda25NO;
import static juego.modelo.TableroTest.Data.listaConCelda25SE;
import static juego.modelo.TableroTest.Data.listaConCelda25SO;
import static juego.modelo.TableroTest.Data.listaConCelda33NE;
import static juego.modelo.TableroTest.Data.listaConCelda33NO;
import static juego.modelo.TableroTest.Data.listaConCelda33SE;
import static juego.modelo.TableroTest.Data.listaConCelda33SO;
import static juego.modelo.TableroTest.Data.listaConCelda66NE;
import static juego.modelo.TableroTest.Data.listaConCelda66NO;
import static juego.modelo.TableroTest.Data.listaConCelda66SE;
import static juego.modelo.TableroTest.Data.listaConCelda66SO;
import static juego.modelo.TableroTest.Data.listaVacia;
import static juego.modelo.TableroTest.Data.tableroGlobal;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import juego.util.Sentido;

/**
 * Pruebas unitarias sobre el tablero.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 * 
 */
@DisplayName("Tests sobre Tablero")
public class TableroTest {

	/** Tamaño mínimo. */
	private static final int TAMAÑO_MINIMO = 3;

	/** Tablero de testing. */
	private Tablero tablero;

	/** Inicializa valores para cada test. */
	@BeforeEach
	void inicializar() {
		tablero = new Tablero(8, 8);
	}

	/** Inicializa un tablero que no contiene piezas. */
	@DisplayName("Inicializa un tablero vacío")
	@Test
	void sinPiezasAlInicializar() {
		Tablero tablero = new Tablero(9, 9);

		assertThat(tablero.obtenerNumeroPiezas(Color.NEGRO), is(0));
		assertThat(tablero.obtenerNumeroPiezas(Color.BLANCO), is(0));
	}

	/** Inicializa tableros cuadrados de distintos tamaños. */
	@DisplayName("Inicializa tableros cuadrados con diferentes tamaños ")
	@Test
	void inicializarFilasColumnasTablerosCuadrados() {
		for (int i = 1; i < 100; i++) {
			for (int j = 1; j < 100; j++) {
				Tablero tablero = new Tablero(i, j);
				// Bien dimensionados...
				assertThat("Filas mal inicializado", tablero.obtenerNumeroFilas(), is(i));
				assertThat("Columnnas mal inicializado", tablero.obtenerNumeroColumnas(), is(j));
			}
		}
	}

	/** Inicializa tableros cuadrados de distintos tamaños colocando piezas. */
	@DisplayName("Inicializa tableros cuadrados rellenándolos con piezas")
	@Test
	void inicializarTablerosCuadrados() {
		for (int i = TAMAÑO_MINIMO; i < 10; i++) {
			for (int j = TAMAÑO_MINIMO; j < 10; j++) {
				Tablero tablero = new Tablero(i, j);

				// pero con todas sus celdas vacías
				assertThat("Debe estar vacío sin piezas blancas", tablero.obtenerNumeroPiezas(Color.BLANCO), is(0));
				assertThat("Debe estar vacío sin piezas negras", tablero.obtenerNumeroPiezas(Color.NEGRO), is(0));
				int contadorPiezas = 0;
				for (int fila = 0; fila < tablero.obtenerNumeroFilas(); fila++) {
					for (int columna = 0; columna < tablero.obtenerNumeroColumnas(); columna++) {
						Celda celda = tablero.obtenerCelda(fila, columna);
						assertTrue(celda.estaVacia());
						tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.PEON), celda);
						contadorPiezas++;
						assertThat("Valores fila:" + fila + " columna:" + columna, tablero.obtenerNumeroPiezas(Color.NEGRO),
								is(contadorPiezas));
					}
				}
			}
		}
	}

	/**
	 * Inicializa tableros rectangulares de distintos tamaños.
	 */
	@DisplayName("Inicializa tableros rectangulares con diferentes tamaños ")
	@Test
	void inicializarFilasColumnasTablerosRectangulares() {
		for (int i = 1; i < 100; i++) {
			for (int j = 1; j < 100; j++) {
				Tablero tablero = new Tablero(i, j + 1); // OJO sumamos uno a las columnas
				// Bien dimensionados...
				assertThat("Filas mal inicializado", tablero.obtenerNumeroFilas(), is(i));
				assertThat("Columnnas mal inicializado", tablero.obtenerNumeroColumnas(), is(j + 1));
			}
		}
	}

	/** Inicializa tableros rectángulares de distintos tamaños. */
	@DisplayName("Inicializa tableros rectangulares vacíos")
	@Test
	void inicializarVacíosTablerosRectangulares() {
		for (int i = 1; i < 100; i++) {
			for (int j = 1; j < 100; j++) {
				Tablero tablero = new Tablero(i, j + 1); // OJO sumamos uno a las columnas
				// pero con todas sus celdas vacías
				assertThat("Debe estar vacío sin piezas blancas", tablero.obtenerNumeroPiezas(Color.BLANCO), is(0));
				assertThat("Debe estar vacío sin piezas negras", tablero.obtenerNumeroPiezas(Color.NEGRO), is(0));
				for (int ii = 0; ii < tablero.obtenerNumeroFilas(); ii++) {
					for (int jj = 0; jj < tablero.obtenerNumeroColumnas(); jj++) {
						Celda celda = tablero.obtenerCelda(ii, jj);
						assertTrue("La celda no está vacía", celda.estaVacia());
					}
				}
			}
		}
	}

	/**
	 * Rellenado del tablero de piezas hasta ver que está completo.
	 */
	@DisplayName("Rellena el tablero de piezas hasta completarlo")
	@Test
	void rellenarTableros() {
		Tablero tablero = new Tablero(6, 7);

		Color[] colores = Color.values();
		for (Color color : colores) {
			Pieza pieza = new Pieza(color, TipoPieza.PEON);
			for (int ii = 0; ii < tablero.obtenerNumeroFilas(); ii++) {
				for (int jj = 0; jj < tablero.obtenerNumeroColumnas(); jj++) {
					Celda celda = tablero.obtenerCelda(ii, jj);
					tablero.colocar(pieza, celda);
					assertThat("Celda mal asignada.", pieza.obtenerCelda(), is(celda));
					assertThat("Pieza mal asignada.", celda.obtenerPieza(), is(pieza));
					assertFalse("La celda está vacía", celda.estaVacia());
				}
			}
		}

	}

	/**
	 * Revisa la consulta de celdas en distintas posiciones del tablero.
	 */
	@DisplayName("Comprueba la consulta de celdas en posiciones correctas e incorrectas del tablero")
	@Test
	void comprobarAccesoACeldas() {
		for (int fila = 1; fila < 100; fila++) {
			for (int columna = 1; columna < 100; columna++) {
				Tablero tablero = new Tablero(fila, columna);
				// coordenadas incorrectas
				int[][] coordenadasIncorrectas = { { -1, -1 }, { fila, -1 }, { -1, columna }, { fila, columna } };
				for (int i = 0; i < coordenadasIncorrectas.length; i++) {
					assertNull("La celda no debería estar contenida en el tablero devolviendo un nulo", tablero.obtenerCelda(coordenadasIncorrectas[i][0], coordenadasIncorrectas[i][1]));
				}
				// coordenadas correctas
				int[][] coordenadasCorrectas = { { fila / 2, columna / 2 }, { 0, 0 }, { fila - 1, columna - 1 },
						{ 0, columna - 1 }, { fila - 1, 0 } };
				for (int i = 0; i < coordenadasCorrectas.length; i++) {
					assertNotNull("La celda sí debería estar contenida en el tablero, no debe devolver un nulo", tablero.obtenerCelda(coordenadasCorrectas[i][0], coordenadasCorrectas[i][1]));
				}
			}
		}

	}

	/**
	 * Comprueba la consulta de celdas en la diagonal de un tablero.
	 * 
	 * @param origen  origen
	 * @param sentido sentido
	 * @param celdas  conjunto de celdas que deben ser devueltas por la consulta
	 */
	@DisplayName("Consulta de celdas en la diagonal")
	@ParameterizedTest
	@MethodSource("crearArgumentosConsultaCeldasEnDiagonal")
	@SuppressWarnings("unchecked")
	void probarConsultaCeldasEnDiagonal(Celda origen, Sentido sentido, List<Celda> celdas) {
		assertThat((List<Celda>) tablero.obtenerCeldasEnDiagonal(origen, sentido),
				containsInAnyOrder(celdas.toArray()));
	}

	/**
	 * Generador de argumentos para {@link probarConsultaCeldasEnDiagonal(Celda,
	 * Sentido, List)}.
	 * 
	 * @return argumentos para testing
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> crearArgumentosConsultaCeldasEnDiagonal() {
		return Stream.of(
				// diagonales de la celda (0,0)
				Arguments.of(tableroGlobal.obtenerCelda(0, 0), Sentido.DIAGONAL_SO, listaConCelda00),
				Arguments.of(tableroGlobal.obtenerCelda(0, 0), Sentido.DIAGONAL_NO, listaConCelda00),
				Arguments.of(tableroGlobal.obtenerCelda(0, 0), Sentido.DIAGONAL_NE, listaConCelda00),
				Arguments.of(tableroGlobal.obtenerCelda(0, 0), Sentido.DIAGONAL_SE, listaConCelda00SE),
				// diagonales de la celda (3,3)
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), Sentido.DIAGONAL_NO, listaConCelda33NO),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), Sentido.DIAGONAL_NE, listaConCelda33NE),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), Sentido.DIAGONAL_SO, listaConCelda33SO),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), Sentido.DIAGONAL_SE, listaConCelda33SE),
				// diagonales de la celda (6,6)
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), Sentido.DIAGONAL_NO, listaConCelda66NO),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), Sentido.DIAGONAL_NE, listaConCelda66NE),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), Sentido.DIAGONAL_SO, listaConCelda66SO),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), Sentido.DIAGONAL_SE, listaConCelda66SE),
				// diagonales de la celda (2,5)
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), Sentido.DIAGONAL_NO, listaConCelda25NO),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), Sentido.DIAGONAL_NE, listaConCelda25NE),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), Sentido.DIAGONAL_SO, listaConCelda25SO),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), Sentido.DIAGONAL_SE, listaConCelda25SE));
	}

	/**
	 * Comprueba la consulta de celdas en la diagonal entre dos celdas de un
	 * tablero.
	 * 
	 * @param origen  origen
	 * @param destino celda
	 * @param celdas  conjunto de celdas que deben ser devueltas por la consulta
	 */
	@DisplayName("Consulta de celdas en la diagonal entre dos celdas origen y destino")
	@ParameterizedTest
	@MethodSource("crearArgumentosConsultaCeldasEnDiagonalEntreDosCeldas")
	@SuppressWarnings("unchecked")
	void probarConsultaCeldasEnDiagonalEntreDosCeldas(Celda origen, Celda destino, List<Celda> celdas) {
		assertThat((List<Celda>) tablero.obtenerCeldasEnDiagonal(origen, destino),
				containsInAnyOrder(celdas.toArray()));
	}

	/**
	 * Generador de argumentos para
	 * {@link probarConsultaCeldasEnDiagonalEntreDosCeldas(Celda, Celda, List)}.
	 * 
	 * @return argumentos para testing
	 */
	@SuppressWarnings("unused")
	private static Stream<Arguments> crearArgumentosConsultaCeldasEnDiagonalEntreDosCeldas() {
		return Stream.of(
				// diagonales de la celda (0,0)
				Arguments.of(tableroGlobal.obtenerCelda(0, 0), tableroGlobal.obtenerCelda(0, 0), listaVacia),
				Arguments.of(tableroGlobal.obtenerCelda(0, 0), tableroGlobal.obtenerCelda(7, 7), listaConCelda00SE),
				// diagonales de la celda (3,3)
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), tableroGlobal.obtenerCelda(3, 3), listaVacia),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), tableroGlobal.obtenerCelda(0, 0), listaConCelda33NO),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), tableroGlobal.obtenerCelda(0, 6), listaConCelda33NE),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), tableroGlobal.obtenerCelda(6, 0), listaConCelda33SO),
				Arguments.of(tableroGlobal.obtenerCelda(3, 3), tableroGlobal.obtenerCelda(7, 7), listaConCelda33SE),
				// diagonales de la celda (6,6)
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), tableroGlobal.obtenerCelda(6, 6), listaVacia),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), tableroGlobal.obtenerCelda(0, 0), listaConCelda66NO),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), tableroGlobal.obtenerCelda(5, 7), listaConCelda66NE),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), tableroGlobal.obtenerCelda(7, 5), listaConCelda66SO),
				Arguments.of(tableroGlobal.obtenerCelda(6, 6), tableroGlobal.obtenerCelda(7, 7), listaConCelda66SE),
				// diagonales de la celda (2,5)
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), tableroGlobal.obtenerCelda(2, 5), listaVacia),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), tableroGlobal.obtenerCelda(0, 3), listaConCelda25NO),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), tableroGlobal.obtenerCelda(0, 7), listaConCelda25NE),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), tableroGlobal.obtenerCelda(7, 0), listaConCelda25SO),
				Arguments.of(tableroGlobal.obtenerCelda(2, 5), tableroGlobal.obtenerCelda(4, 7), listaConCelda25SE));
	}

	/**
	 * Método de utilidad para convertir arrays de enteros a celdas en un tablero.
	 * 
	 * @param coordenadas coordenadas
	 * @param tablero     tablero
	 * @return celdas correspondientes a las coordenadas si estas pertenecen al
	 *         tablero
	 */
	private static List<Celda> obtenerCeldas(int[][] coordenadas, Tablero tablero) {
		List<Celda> celdas = new ArrayList<>();
		for (int[] coordenadasCelda : coordenadas) {
			Celda celda = tablero.obtenerCelda(coordenadasCelda[0], coordenadasCelda[1]);
			if (celda != null)
				celdas.add(celda);
		}
		return celdas;
	}

	/**
	 * Comprueba si el movimiento entre dos celdas dadas es en diagonal o no.
	 */
	@DisplayName("Detecta si el movimiento es diagonal o no")
	@Test
	void comprobarMovimientosEnDiagonal() {
		assertAll(() -> assertTrue(tablero.esMovimientoDiagonal(new Celda(0, 0), new Celda(5, 5))),
				() -> assertTrue(tablero.esMovimientoDiagonal(new Celda(0, 1), new Celda(4, 5))),
				() -> assertTrue(tablero.esMovimientoDiagonal(new Celda(2, 1), new Celda(6, 5))),
				() -> assertTrue(tablero.esMovimientoDiagonal(new Celda(2, 5), new Celda(6, 1))),
				() -> assertTrue(tablero.esMovimientoDiagonal(new Celda(5, 5), new Celda(1, 1))),
				() -> assertTrue(tablero.esMovimientoDiagonal(new Celda(7, 4), new Celda(6, 5))));
	}

	/**
	 * Comprueba que el movimiento entre dos celdas dadas NO es en diagonal.
	 */
	@DisplayName("Detecta que el movimiento NO es en diagonal")
	@Test
	void comprobarNoSonMovimientosEnDiagonal() {
		assertAll(() -> assertFalse(tablero.esMovimientoDiagonal(new Celda(0, 1), new Celda(5, 5))),
				() -> assertFalse(tablero.esMovimientoDiagonal(new Celda(1, 1), new Celda(4, 5))),
				() -> assertFalse(tablero.esMovimientoDiagonal(new Celda(2, 2), new Celda(6, 5))),
				() -> assertFalse(tablero.esMovimientoDiagonal(new Celda(2, 4), new Celda(6, 1))),
				() -> assertFalse(tablero.esMovimientoDiagonal(new Celda(5, 4), new Celda(1, 1))),
				() -> assertFalse(tablero.esMovimientoDiagonal(new Celda(7, 3), new Celda(6, 5))));
	}

	/**
	 * Comprueba que la consulta de celdas entre dos celdas dadas que NO están en
	 * diagonal devuelve una lista vacía.
	 */
	@DisplayName("Detecta que la lista no está vacía si el movimiento no es diagonal")
	@Test
	void comprobarListasVaciasConCeldasNoEnDiagonal() {
		assertAll(() -> assertTrue(tablero.obtenerCeldasEnDiagonal(new Celda(0, 1), new Celda(5, 5)).isEmpty()),
				() -> assertTrue(tablero.obtenerCeldasEnDiagonal(new Celda(1, 1), new Celda(4, 5)).isEmpty()),
				() -> assertTrue(tablero.obtenerCeldasEnDiagonal(new Celda(2, 2), new Celda(6, 5)).isEmpty()),
				() -> assertTrue(tablero.obtenerCeldasEnDiagonal(new Celda(5, 4), new Celda(1, 1)).isEmpty()),
				() -> assertTrue(tablero.obtenerCeldasEnDiagonal(new Celda(7, 3), new Celda(6, 5)).isEmpty()));
	}

	/**
	 * Comprueba el correcto cálculo de distancia Manhattan entre dos celdas.
	 */
	@DisplayName("Calculo correcto de la distancia Manhattan")
	@Test
	void comprobarDistanciaManhattan() {
		assertAll(() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(0, 0), new Celda(5, 5)), is(10)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(0, 1), new Celda(4, 5)), is(8)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(2, 2), new Celda(6, 5)), is(7)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(5, 5), new Celda(5, 5)), is(0)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(5, 5), new Celda(5, 6)), is(1)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(5, 5), new Celda(6, 6)), is(2)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(2, 3), new Celda(5, 6)), is(6)),
				() -> assertThat(tablero.obtenerDistanciaManhattan(new Celda(0, 7), new Celda(7, 0)), is(14)));
	}

	/**
	 * Comprueba la detección del sentido de movimiento entre dos celdas origen y
	 * destino que están en diagonal.
	 */
	@DisplayName("Detección del sentido en el movimiento diagonal entre dos celdas")
	@Test
	void comprobarSentidoEnMovimientoDiagonal() {
		assertAll(
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(0, 0), new Celda(5, 5)),
						is(Sentido.DIAGONAL_SE)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(0, 1), new Celda(4, 5)),
						is(Sentido.DIAGONAL_SE)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(2, 2), new Celda(0, 0)),
						is(Sentido.DIAGONAL_NO)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(5, 5), new Celda(0, 0)),
						is(Sentido.DIAGONAL_NO)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(5, 5), new Celda(3, 7)),
						is(Sentido.DIAGONAL_NE)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(2, 2), new Celda(0, 4)),
						is(Sentido.DIAGONAL_NE)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(4, 6), new Celda(6, 4)),
						is(Sentido.DIAGONAL_SO)),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(2, 4), new Celda(6, 0)),
						is(Sentido.DIAGONAL_SO)));
	}

	/**
	 * Comprueba que el sentido es nulo al no moverse en diagonal entre dos celdas
	 * origen y destino.
	 */
	@DisplayName("Detección del sentido nulo en movimiento que NO es diagonal entre dos celdas")
	@Test
	void comprobarSentidoNuloEnMovimientoNoDiagonal() {
		assertAll(
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(0, 1), new Celda(5, 5)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(0, 1), new Celda(4, 3)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(2, 3), new Celda(0, 0)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(5, 6), new Celda(0, 0)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(5, 7), new Celda(3, 7)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(6, 7), new Celda(0, 4)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(5, 6), new Celda(6, 4)),
						is(nullValue())),
				() -> assertThat(tablero.obtenerSentidoEnMovimientoDiagonal(new Celda(3, 4), new Celda(6, 0)),
						is(nullValue())));
	}

	/**
	 * Interfaz con datos globales para tests.
	 *
	 * 
	 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
	 * @version 1.0 20180806
	 * @see #probarConsultaCeldasEnDiagonal
	 * @see #probarConsultaCeldasEnDiagonalEntreDosCeldas
	 * @see #crearArgumentosConsultaCeldasEnDiagonalEntreDosCeldas
	 * @see #crearArgumentosConsultaCeldasEnDiagonal
	 */

	static interface Data {
		/** Tablero global. */
		Tablero tableroGlobal = new Tablero(8, 8);

		/** Coordenadas celda 0 0. */
		int[][] coordenadasCelda00 = { { 0, 0 } };
		/** Coordenadas celda 0 0 sentido sureste. */
		int[][] coordenadasCelda00SE = { { 0, 0 }, { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 },
				{ 7, 7 } };

		/** Coordenadas celda 3 3 sentido noroeste. */
		int[][] coordenadasCelda33NO = { { 0, 0 }, { 1, 1 }, { 2, 2 }, { 3, 3 } };
		/** Coordenadas celda 3 3 sentido noreste. */
		int[][] coordenadasCelda33NE = { { 2, 4 }, { 1, 5 }, { 0, 6 }, { 3, 3 } };
		/** Coordenadas celda 3 3 sentido suroeste. */
		int[][] coordenadasCelda33SO = { { 4, 2 }, { 5, 1 }, { 6, 0 }, { 3, 3 } };
		/** Coordenadas celda 3 3 sentido sureste. */
		int[][] coordenadasCelda33SE = { { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 }, { 3, 3 } };

		/** Coordenadas celda 6 6 sentido noroeste. */
		int[][] coordenadasCelda66NO = { { 5, 5 }, { 4, 4 }, { 3, 3 }, { 2, 2 }, { 1, 1 }, { 0, 0 }, { 6, 6 } };
		/** Coordenadas celda 6 6 sentido noreste. */
		int[][] coordenadasCelda66NE = { { 5, 7 }, { 6, 6 } };
		/** Coordenadas celda 6 6 sentido suroeste. */
		int[][] coordenadasCelda66SO = { { 7, 5 }, { 6, 6 } };
		/** Coordenadas celda 6 6 sentido sureste. */
		int[][] coordenadasCelda66SE = { { 7, 7 }, { 6, 6 } };

		/** Coordenadas celda 2 5 sentido noroeste. */
		int[][] coordenadasCelda25NO = { { 1, 4 }, { 0, 3 }, { 2, 5 } };
		/** Coordenadas celda 2 5 sentido noreste. */
		int[][] coordenadasCelda25NE = { { 1, 6 }, { 0, 7 }, { 2, 5 } };
		/** Coordenadas celda 2 5 sentido suroeste. */
		int[][] coordenadasCelda25SO = { { 3, 4 }, { 4, 3 }, { 5, 2 }, { 6, 1 }, { 7, 0 }, { 2, 5 } };
		/** Coordenadas celda 2 5 sentido sureste. */
		int[][] coordenadasCelda25SE = { { 3, 6 }, { 4, 7 }, { 2, 5 } };

		// Listas para celda (0,0) en distintos sentidos
		/** Lista vacía. */
		List<Celda> listaVacia = new ArrayList<>(); // vacío
		/** Lista para celda 0 0. */
		List<Celda> listaConCelda00 = obtenerCeldas(coordenadasCelda00, tableroGlobal);
		/** Lista para celda 0 0 sureste. */
		List<Celda> listaConCelda00SE = obtenerCeldas(coordenadasCelda00SE, tableroGlobal);
		// Listas para celda (3,3) en distintos sentidos
		/** Lista para celda 3 3 noroeste. */
		List<Celda> listaConCelda33NO = obtenerCeldas(coordenadasCelda33NO, tableroGlobal);
		/** Lista para celda 3 3 noreste. */
		List<Celda> listaConCelda33NE = obtenerCeldas(coordenadasCelda33NE, tableroGlobal);
		/** Lista para celda 3 3 suroeste. */
		List<Celda> listaConCelda33SO = obtenerCeldas(coordenadasCelda33SO, tableroGlobal);
		/** Lista para celda 3 3 sureste. */
		List<Celda> listaConCelda33SE = obtenerCeldas(coordenadasCelda33SE, tableroGlobal);
		// Listas para celda (6,6) en distintos sentidos
		/** Lista para celda 6 6 noroeste. */
		List<Celda> listaConCelda66NO = obtenerCeldas(coordenadasCelda66NO, tableroGlobal);
		/** Lista para celda 6 6 noreste. */
		List<Celda> listaConCelda66NE = obtenerCeldas(coordenadasCelda66NE, tableroGlobal);
		/** Lista para celda 6 6 suroeste. */
		List<Celda> listaConCelda66SO = obtenerCeldas(coordenadasCelda66SO, tableroGlobal);
		/** Lista para celda 6 6 sureste. */
		List<Celda> listaConCelda66SE = obtenerCeldas(coordenadasCelda66SE, tableroGlobal);
		// Listas para celda (2,5) en distintos sentidos
		/** Lista para celda 2 5 noroeste. */
		List<Celda> listaConCelda25NO = obtenerCeldas(coordenadasCelda25NO, tableroGlobal);
		/** Lista para celda 2 5 noreste. */
		List<Celda> listaConCelda25NE = obtenerCeldas(coordenadasCelda25NE, tableroGlobal);
		/** Lista para celda 2 5 suroeste. */
		List<Celda> listaConCelda25SO = obtenerCeldas(coordenadasCelda25SO, tableroGlobal);
		/** Lista para celda 2 5 sureste. */
		List<Celda> listaConCelda25SE = obtenerCeldas(coordenadasCelda25SE, tableroGlobal);
	}

	/** Genera la cadena de texto correcta para un tablero vacío. */
	@DisplayName("Comprueba la generación de la cadena de texto en toString con tablero vacío")
	@Test
	void comprobarCadenaTextoConTableroVacio() {
		String patron = "--------";
		String salida = String.join("", Collections.nCopies(8, patron)); // copiamos ocho veces el patrón
		String cadenaEsperada = tablero.toString().toUpperCase().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero vacío es incorecta.");
	}
	
	/** Genera la cadena de texto correcta para un tablero con algunos peones colocados en las esquinas del tablero. */
	@DisplayName("Comprueba la generación de la cadena de texto en toString con tablero con algunos peones en las esquinas")
	@Test
	void comprobarCadenaTextoConTableroConPeonesEnEsquinas() {
		String salida = "X------X" + "--------" + "--------" + "--------" + "--------" + "--------"
				+ "--------" + "O------O";
		tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.PEON), tablero.obtenerCelda(0, 0));
		tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.PEON), tablero.obtenerCelda(0, 7));
		tablero.colocar(new Pieza(Color.BLANCO, TipoPieza.PEON), tablero.obtenerCelda(7, 0));
		tablero.colocar(new Pieza(Color.BLANCO, TipoPieza.PEON), tablero.obtenerCelda(7, 7));
		
		String cadenaEsperada = tablero.toString().toUpperCase().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero con peones en las esquinas es incorecta.");
	}
	
	
	/** Genera la cadena de texto correcta para un tablero con algunos peones y damas. */
	@DisplayName("Comprueba la generación de la cadena de texto en toString con tablero con algunos peones y damas")
	@Test
	void comprobarCadenaTextoConTableroConPeonesYDamas() {
		String salida = "X------X" + "--------" + "--------" + "---NN---" + "---BB---" + "--------"
				+ "--------" + "O------O";
		tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.PEON), tablero.obtenerCelda(0, 0));
		tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.PEON), tablero.obtenerCelda(0, 7));
		tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.DAMA), tablero.obtenerCelda(3, 3));
		tablero.colocar(new Pieza(Color.NEGRO, TipoPieza.DAMA), tablero.obtenerCelda(3, 4));
		
		tablero.colocar(new Pieza(Color.BLANCO, TipoPieza.PEON), tablero.obtenerCelda(7, 0));
		tablero.colocar(new Pieza(Color.BLANCO, TipoPieza.PEON), tablero.obtenerCelda(7, 7));
		tablero.colocar(new Pieza(Color.BLANCO, TipoPieza.DAMA), tablero.obtenerCelda(4, 3));
		tablero.colocar(new Pieza(Color.BLANCO, TipoPieza.DAMA), tablero.obtenerCelda(4, 4));
		
		String cadenaEsperada = tablero.toString().toUpperCase().replaceAll("\\s", "");
		assertEquals(cadenaEsperada, salida, "La cadena de texto generada para un tablero con peones y damas es incorecta.");
	}
}
