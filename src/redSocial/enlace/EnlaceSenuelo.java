package redSocial.enlace;

import redSocial.usuario.Usuario;

public class EnlaceSenuelo extends Enlace {
	private int factorCoste;
	private double probRetorno;
	
	public EnlaceSenuelo(Usuario origen, Usuario destino, int coste, int factorCoste, double probRetorno) {
		super(origen, destino, coste);
		this.factorCoste = factorCoste;
		this.probRetorno = probRetorno;
	}
	
	public EnlaceSenuelo(Usuario origen, Usuario destino, int factorCoste, double probRetorno) {
		super(origen, destino);
		this.factorCoste = factorCoste;
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
	
	/**
	 * Getter del factor de multiplicaciÓn del coste
	 * @return el factor de coste
	 */
	public double getFactorCoste() {
		return factorCoste;
	}
	
	
	
	
}
