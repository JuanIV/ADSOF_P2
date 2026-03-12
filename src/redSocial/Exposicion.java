package redSocial;

public enum Exposicion {
	OCULTA(0),
	BAJA(5),
	MEDIA(10),
	ALTA(20),
	VIRAL(50);
	private final int rigidezMinima;
	
	private Exposicion(int rigidezMinima) {
		this.rigidezMinima = rigidezMinima;
	}
	
	public static Exposicion getNext(Exposicion e) {
		Exposicion[] valores = values();
		int posicion = e.ordinal();
		
		if (posicion < valores.length) {
			return valores[posicion + 1];
		} else {
			return valores[posicion];
		}
	}
	
	public static Exposicion getPrev(Exposicion e) {
		Exposicion[] valores = values();
		int posicion = e.ordinal();
		
		if (posicion > 0) {
			return valores[posicion - 1];
		} else {
			return valores[posicion];
		}
	}
	
	public int getRigidez() {
		return this.rigidezMinima;
	}
}
