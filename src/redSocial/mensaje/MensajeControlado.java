package redSocial.mensaje;

import redSocial.enlace.*;
import redSocial.usuario.Usuario;

public class MensajeControlado extends Mensaje {
	private int rigidez;
	
	/**
	 * Construcutor completo del mensaje controlado
	 * @param msj contenido en String del mensaje
	 * @param alcance int con el alcance del mensaje
	 * @param autor
	 * @param rigidez
	 */
	public MensajeControlado(String msj, int alcance, Usuario autor, int rigidez) {
		super(msj, alcance, autor);
		
		if (rigidez < 0) this.rigidez = 0;
		else this.rigidez = rigidez;
	}
	
	
	@Override
	/**
	 * Método para comprobar si el mensaje controlado fue aceptado
	 * @param u Usuario que puede aceptar o no el mensaje
	 * @return true si lo acepta, false si lo rechaza
	 */
	public boolean aceptadoPor(Usuario u) {		
		boolean st = this.rigidez >= u.getNivelExposicion().getRigidez();
		
		if (st == false) System.out.println("El mensaje no fue aceptado por falta de rigidez");
		
		return st;
	}
	
	@Override
	/**
	 * Método auxiliar para comprobar si el mensaje controlado se puede difundir
	 * @param e Enlace a través del cual se comprueba la posibilidad de difusión
	 * @return true si se puede difundir, false si no
	 */
	protected boolean puedeDifundirPor(Enlace e) {
		if (e instanceof EnlaceSenuelo) {
			System.out.println("Los mensajes controlados no pueden pasar por enlaces señuelo");
			return false;
		}
		
		return super.puedeDifundirPor(e);
	}

	/**
	 * Getter de la rigidez del mensaje controlado
	 * @return valor de la rigidez
	 */
	public int getRigidez() {
		return rigidez;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Rigidez: " + rigidez;
	}
	
}
