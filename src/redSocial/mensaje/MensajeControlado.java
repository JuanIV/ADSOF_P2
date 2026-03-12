package redSocial.mensaje;

import redSocial.enlace.*;
import redSocial.usuario.Usuario;

public class MensajeControlado extends Mensaje {
	private int rigidez;
	
	public MensajeControlado(String msj, int alcance, Usuario autor, int rigidez) {
		super(msj, alcance, autor);
		
		if (rigidez < 0) this.rigidez = 0;
		else this.rigidez = rigidez;
	}
	
	
	/**
	 * Método para comprobar si el mensaje controlado fue aceptado
	 * @param u Usuario que puede aceptar o no el mensaje
	 * @return true si lo acepta, false si lo rechaza
	 */
	public boolean aceptadoPor(Usuario u) {
		if (this.rigidez < u.getNivelExposicion().getRigidez()) return false;
		
		return true;
	}
	
	@Override
	/**
	 * Método para difundir el mensaje controlado a través de un enlace
	 * @param e Enlace desde el lector hasta un nuevo usuario
	 * @return true si se pudo difundir, false si no se pudo
	 */
	public boolean difunde(Enlace e) {
		if (e instanceof EnlaceSenuelo) return false;
		
		return super.difunde(e);
	}
	
}
