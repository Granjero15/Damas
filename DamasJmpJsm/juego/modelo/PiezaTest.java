package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias sobre la pieza.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 * 
 */
@DisplayName("Tests sobre Pieza")
public class PiezaTest {

	/**
	 * Inicialización reseteando el contador de piezas.
	 */
	@BeforeEach
	void inicializar() {
		Pieza.resetearContador();
	}

	/**
	 * Test del constructor.
	 */
	@DisplayName("Constructor con estado inicial correcto")
	@Test
	void constructor() {
		Pieza pieza = new Pieza(Color.BLANCO, TipoPieza.PEON);
		assertAll(() -> assertThat("Color mal inicializado", pieza.obtenerColor(), is(Color.BLANCO)),
				() -> assertThat("Tipo mal inicializado", pieza.obtenerTipoPieza(), is(TipoPieza.PEON)),
				() -> assertNull(pieza.obtenerCelda(), "La pieza inicialmente no debe estar en una celda."));
	}

	/**
	 * Coloca la pieza en la celda.
	 */
	@DisplayName("Coloca la pieza en la celda")
	@Test
	void colocarEnCelda() {
		Pieza pieza = new Pieza(Color.BLANCO, TipoPieza.DAMA);
		Celda celda = new Celda(0, 0);
		pieza.colocarEn(celda);
		assertAll(() -> assertThat("Pieza mal asociada a celda", pieza.obtenerCelda(), is(celda)),
				() -> assertThat("Tipo mal inicializado", pieza.obtenerTipoPieza(), is(TipoPieza.DAMA)),
				() -> assertThat("Celda no debería estar asociada a la pieza", celda.obtenerPieza(), is(nullValue())));
	}

	/**
	 * Prueba del método toString.
	 */
	@DisplayName("Formato de texto")
	@Test
	void probarToString() {
		Pieza pieza = new Pieza(Color.BLANCO, TipoPieza.PEON);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is("[1]-(-/-)-" + TipoPieza.PEON + "-" + Color.BLANCO));

		pieza = new Pieza(Color.NEGRO, TipoPieza.PEON);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is("[2]-(-/-)-" + TipoPieza.PEON + "-" + Color.NEGRO));

		Celda celda = new Celda(3, 4);
		pieza = new Pieza(Color.BLANCO, TipoPieza.DAMA);
		pieza.colocarEn(celda);
		assertThat("Texto mal construido o estado incorrecto", pieza.toString().replaceAll("\\s", ""),
				is("[3]-(3/4)-" + TipoPieza.DAMA + "-" + Color.BLANCO));
	}

	/**
	 * Comprueba el correcto manejo del id y del reseteo del mismo.
	 */
	@DisplayName("Gestión del id y reinicio del valor")
	@Test
	void comprobarReseteoGenerador() {
		Pieza pieza1 = new Pieza(Color.BLANCO, TipoPieza.PEON);
		assertThat("Id. mal generado", pieza1.obtenerId(), is(1));

		for (int contadorPiezas = 2; contadorPiezas < 100; contadorPiezas++) {
			pieza1 = new Pieza(Color.BLANCO, TipoPieza.PEON);
			assertThat("Id. mal generado", pieza1.obtenerId(), is(contadorPiezas));
		}

		Pieza.resetearContador();
		pieza1 = new Pieza(Color.BLANCO, TipoPieza.PEON);
		assertThat("Id. mal generado", pieza1.obtenerId(), is(1));
	}

	/**
	 * Comprueba que los métodos heredados toString, equals y hashCode son redefinidos correctamente.
	 * 
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 * @see Object#toString()
	 */
	@DisplayName("Redefinición de métodos toString, equals y hashCode")
	@Test
	void testMetodosHeredadosRedefinidos() {
		Pieza peonNegro = new Pieza(Color.NEGRO, TipoPieza.PEON);
		Pieza peonBlanco = new Pieza(Color.BLANCO, TipoPieza.PEON);
		Pieza damaNegra = new Pieza(Color.NEGRO, TipoPieza.DAMA);
		Pieza damaBlanca = new Pieza(Color.BLANCO, TipoPieza.DAMA);
		Pieza damaBlancaRepetida = new Pieza(Color.BLANCO, TipoPieza.DAMA); // tendra id 5

		assertAll(() -> assertThat(peonNegro.toString().replaceAll("\\s", ""), is("[1]-(-/-)-PEON-NEGRO")),
				() -> assertThat(peonBlanco.toString().replaceAll("\\s", ""), is("[2]-(-/-)-PEON-BLANCO")),
				() -> assertThat(damaNegra.toString().replaceAll("\\s", ""), is("[3]-(-/-)-DAMA-NEGRO")),
				() -> assertThat(damaBlanca.toString().replaceAll("\\s", ""), is("[4]-(-/-)-DAMA-BLANCO")));

		assertAll(() -> assertTrue(peonNegro.equals(peonNegro)), () -> assertFalse(peonNegro.equals(peonBlanco)),
				() -> assertFalse(peonNegro.equals(damaNegra)), () -> assertFalse(peonNegro.equals(damaBlanca)));

		assertAll(
				() -> assertFalse(
						"Dos damas no pueden tener el mismo hashcode porque sus ids deberían ser siempre diferentes",
						damaBlanca.hashCode() == damaBlancaRepetida.hashCode()));

	}

}
