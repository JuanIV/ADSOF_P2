package redSocial.enlace;

import redSocial.usuario.Usuario;

/**
 * Implementación del enlace señuelo, subclase de Enlace
 * 
 * @author Juan Ibáñez y Tiago Oselka
 * @version 1.3
 */
public class EnlaceSenuelo extends Enlace {
	private int factorCoste;
	private double probRetorno;
	
	/**
	 * Constructor completo de los enlaces señuelo
	 * @param origen Usuario origen del enlace
	 * @param destino Usuario destino del enlace
	 * @param coste Valor que costará cruzar el enlace
	 * @param factorCoste Factor por el que se multiplicará el coste para obtener el coste especial
	 * @param probRetorno Probabilidad con la cual el enlace no podrá ser cruzado y el mensaje volverá al usuario de origen
	 */
	public EnlaceSenuelo(Usuario origen, Usuario destino, int coste, int factorCoste, double probRetorno) {
		super(origen, destino, coste);
		this.factorCoste = factorCoste;
		
		if (probRetorno < 0 || probRetorno > 1) probRetorno = 0;
		this.probRetorno = probRetorno;
	}

	/**
	 * Constructor con coste predeterminado de los enlaces señuelo
	 * @param origen Usuario origen del enlace
	 * @param destino Usuario destino del enlace
	 * @param factorCoste Factor por el que se multiplicará el coste para obtener el coste especial
	 * @param probRetorno Probabilidad con la cual el enlace no podrá ser cruzado y el mensaje volverá al usuario de origen
	 */
	public EnlaceSenuelo(Usuario origen, Usuario destino, int factorCoste, double probRetorno) {
		super(origen, destino);
		this.factorCoste = factorCoste;
		
		if (probRetorno < 0 || probRetorno > 1) probRetorno = 0;
		this.probRetorno = probRetorno;
	}
	
	@Override
	/**
	 * Devuelve el coste especial del enlace seÑuelo
	 * @return Coste especial del enlace senuelo, producto del coste por el factor de coste. 
	 */
	public int costeEspecial() {
		return this.getCoste() * factorCoste;
	}
	
	/**
	 * Getter de la probabilidad de retorno obligatoria
	 * @return la probabilidad del enlace
	 */
	public double getProbRetorno() {
		return probRetorno;
	}
	
	@Override
	/**
	 * Método que determina si un mensaje puede cruzar un enlace con la función random
	 * @return true si el número no excede la probabilidad entre 0 y 1, false en caso contrario 
	 */
	public boolean puedePasar() {
		return (probRetorno < Math.random());
	}
	
	/**
	 * Getter del factor de multiplicaciÓn del coste
	 * @return el factor de coste
	 */
	public int getFactorCoste() {
		return factorCoste;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Factor coste: " + factorCoste + " Probabilidad de retorno: " + probRetorno;
	}
	
	
	
}
