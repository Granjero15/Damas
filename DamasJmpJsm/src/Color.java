/**
 * 
 * @author Javier Martínez Pérez
 * @author Javier Santano Martínez
 * @version 1.0
 *
 */
public enum Color {
		
		/**
		 * Determinamos el color con el simbolo correspondiente, para blanco la O y para negro la X
		 */
		BLANCO('O'),
		NEGRO('X');
		
		/**
		 * letra
		 */
		private char letra;
		
		/**
		 * color se une a su letra correspondiente
		 * @param letra
		 */
		private Color(char letra) {
			this.letra = letra;
		}
		
		/**
		 * devuelves al color la letra
		 * @return
		 */
		public char toChar() {
			return letra;
		}

}
