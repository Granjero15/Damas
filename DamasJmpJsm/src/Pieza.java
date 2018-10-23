
/**
 * 
 * @author Javier Mart�nez P�rez
 * @author Javier Santano Mart�nez
 * @version 1.0
 *
 */
public class Pieza {

	private int id;
	private Color color;
	private Celda celda;
	private TipoPieza tipo;

	public Pieza(Color color, TipoPieza tipo) {
		this.color = color;
		this.tipo = tipo;
	}

	public void resetearContador() {

	}

	public int obtenerId() {
		return id;
	}

	public Color obtenerColor() {
		return color;
	}
	public void colocoarEn(Celda celda) {
		this.celda = celda;
	}
	public Celda colocarCelda() {
		return celda;
	}
	public TipoPieza tipoPieza(TipoPieza tipo) {
		return tipo;
		
	}
	public void establecerTipoPieza(TipoPieza tipo) {
		
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celda == null) ? 0 : celda.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + id;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		if (celda == null) {
			if (other.celda != null)
				return false;
		} else if (!celda.equals(other.celda))
			return false;
		if (color != other.color)
			return false;
		if (id != other.id)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pieza []";
	}
	public void mostrar() {
		
	}
	
}