package redSocial.enlace;

import redSocial.usuario.Usuario;

/**
 * Clase básica enlace
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class Enlace {
	private static int sumaCostes;
	private Usuario origen;
	private Usuario destino;
	private int coste;
	
	/**
	 * Creador completo del enlace
	 * @param origen Usuario de origen del enlace
	 * @param destino Usuario de destino del enlace
	 * @param coste Coste de difusión del enlace
	 */
	public Enlace(Usuario origen, Usuario destino, int coste) {
		this.origen = origen;
		this.destino = destino;
		if(coste <= 0) {
			this.coste = 1;
			sumaCostes++;
		}
		else {
			this.coste = coste;
			sumaCostes += coste;
		}
	}
	
	/**
	 * Creador sin especificar coste
	 * @param origen Usuario de origen del enlace
	 * @param destino Usuario de destino del enlace
	 */
	public Enlace(Usuario origen, Usuario destino) {
		this(origen, destino, 1);
	}
	
	/**
	 * Método para obtener la suma de costes total
	 * @return Suma de todos los costes de los enlaces
	 */
	public static int getSumaCostes() {
		return sumaCostes;
	}
	
	/**
	 * Getter del usuario de origen
	 * @return Usuario de origen del enlace
	 */
	public Usuario getUsuarioOrigen() {
		return origen;
	}
	
	/**
	 * Getter del usuario de destino
	 * @return Usuario de destino del enlace
	 */
	public Usuario getUsuarioDestino() {
		return destino;
	}
	
	/**
	 * Getter del coste del enlace
	 * @return Coste de difusión del enlace
	 */
	public int getCoste() {
		return coste;
	}
	
	/**
	 * Método para cambiar el usuario de destino
	 * @param destino Nuevo usuario de destino
	 * @param coste Nuevo coste del enlace
	 */
	public boolean cambiarDestino(Usuario destino, int coste) {
		this.destino = destino;
		
		if(coste <= 0) this.coste = 1;
		
		sumaCostes += coste - this.coste;
		
		
		return true;
	}
	
	/**
	 * Devuelve el coste especial del enlace
	 * @return Coste especial del enlace. 0 si no es enlace especial
	 */
	public int costeEspecial() {
		return 0;
	}
	
	/**
	 * Método que determina si un mensaje puede cruzar un enlace
	 * @return true para los enlaces normales
	 */
	public boolean puedePasar() {
		return true;
	}
	
	/**
	 * Devuelve la suma del coste y el coste especial del enlace
	 * @return Coste real del enlace
	 */
	public int costeReal() {
		return this.coste + this.costeEspecial();
	}
	
	@Override
	public String toString() {
		return "(@"+this.origen.getNombre()+"--"+this.coste+"-->"+this.destino.getNombre()+")";
	}
}
