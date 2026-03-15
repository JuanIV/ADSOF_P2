package redSocial;

/**
 * Enum del nivel de Exposicion de un Usuario
 * @author Juan Ibáñez y Tiago Oselka
 * @version 1.2
 */
public enum Exposicion {
	/** Exposición oculta, rigidez mínima: 0 */
    OCULTA(0),
    
    /** Exposición baja, rigidez mínima: 5 */
    BAJA(5),
    
    /** Exposición media, rigidez mínima: 10  */
    MEDIA(10),
    
    /** Exposición alta, rigidez mínima: 20  */
    ALTA(20),
    
    /** Exposición viral, rigidez mínima: 50 */
    VIRAL(50);
	
	/** Rigidez mínima asociada al nivel de Exposicion*/
	private final int rigidezMinima;
	
	/**
	 * Constructor privado de la Exposicion
	 * @param rigidezMinima Rigidez mínima asociada a un nivel de exposición para aceptar un mensjae
	 */
	private Exposicion(int rigidezMinima) {
		this.rigidezMinima = rigidezMinima;
	}
	
	/**
	 * A partir del orden natural de ordinal() obtenemos el nivel de Exposición siguiente
	 * @return el siguiente nivel de Exposicion
	 */
	public Exposicion getNext() {
		Exposicion[] valores = values();
		int posicion = this.ordinal();
		
		if (posicion + 1 < valores.length) {
			return valores[posicion + 1];
		} else {
			return valores[posicion];
		}
	}
	
	/**
	 * A partir del orden natural de ordinal() obtenemos el nivel de Exposición anterior
	 * @return el anterior nivel de Exposicion
	 */
	public Exposicion getPrev() {
		Exposicion[] valores = values();
		int posicion = this.ordinal();
		
		if (posicion > 0) {
			return valores[posicion - 1];
		} else {
			return valores[posicion];
		}
	}
	
	/**
	 * Getter de la rigidez asociada a un nivel de Exposicion
	 * @return la rigidez de la exposicion
	 */
	public int getRigidez() {
		return this.rigidezMinima;
	}
}
