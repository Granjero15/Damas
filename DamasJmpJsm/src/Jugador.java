import java.awt.Color;

/**
 * 
 * @author Javier Martínez Pérez
 * @author Javier Santano Martínez
 * @version 1.0
 *
 */
public class Jugador {
	
	private String nombre;
	private Color color;
	
	public Jugador(String nombre, Color color) {
		this.nombre = nombre;
		this.color = color;
	}
	
	public Color ObtenerColor() {
		return color;
	}
	
	public String ObtenerNombre() {
		return nombre;
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}
	
	

}
