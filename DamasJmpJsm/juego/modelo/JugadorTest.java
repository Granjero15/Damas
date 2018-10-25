package juego.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias sobre el jugador.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20181008
 *
 */
@DisplayName("Tests sobre Jugador")
public class JugadorTest {

	/**
	 * Test del constructor.
	 */
	@DisplayName("Constructor con estado inicial correcto")
	@Test
	void constructor() {
		Jugador jugador = new Jugador("Raúl", Color.BLANCO);
		assertAll(() -> assertThat("Nombre mal inicializado", jugador.obtenerNombre(), is("Raúl")),
				() -> assertThat("Color mal inicializado", jugador.obtenerColor(), is(Color.BLANCO)));
	}

	/**
	 * Comprueba que los meétodos heredados son redefinidos correctamente.
	 * 
	 * @see Object#toString()
	 */
	@DisplayName("Redefinición de métodos heredados")
	@Test
	void testMetodosHeredadosRedefinidos() {
		Jugador jugador1 = new Jugador("Dummy", Color.NEGRO);
		Jugador jugador2 = new Jugador("Dummy", Color.NEGRO);
		Jugador jugador3 = new Jugador("Otro", Color.BLANCO);
		
		assertAll(() -> assertThat("Cadena de texto generada incorrecta", jugador1.toString().replaceAll("\\s", ""), is("Dummy/NEGRO")),
				() -> assertThat("Cadena de texto generada incorrecta", jugador2.toString().replaceAll("\\s", ""), is("Dummy/NEGRO")),
				() -> assertThat("Cadena de texto generada incorrecta", jugador3.toString().replaceAll("\\s", ""), is("Otro/BLANCO")));
	}
}
