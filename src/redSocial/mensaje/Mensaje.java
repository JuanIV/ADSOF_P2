package redSocial.mensaje;

import redSocial.enlace.Enlace;
import redSocial.usuario.Usuario;

/**
 * Clase básica de Mensaje
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class Mensaje {
	private String mensaje;
	private int alcance;
	private Usuario lector;
	
	
	/**
	 * Creador especificando un usuario lector inicial
	 * @param msj Contenido del mensaje
	 * @param alcance Alcance inicial del mensaje
	 * @param lector Usuario lector inicial del mensaje
	 */
	public Mensaje(String msj, int alcance, Usuario autor, Usuario lector) {
		this.mensaje = msj;
		this.alcance = alcance;
		this.lector = lector;
		
	}
	
	/**
	 * Creador de Mensaje. El lector inicial será el autor
	 * @param msj Contenido del mensaje
	 * @param alcance Alcance inicial del mensaje
	 * @param autor Usuario autor del mensaje
	 */
	public Mensaje(String msj, int alcance, Usuario autor) {
		this(msj, alcance, autor, autor);
	}
	
	/**
	 * Getter del contenido del mensaje
	 * @return Contenido del mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Getter del alcance del mensaje
	 * @return Alcance del mensaje
	 */
	public int getAlcance() {
		return alcance;
	}

	/**
	 * Getter del lector actual del mensaje
	 * @return Usuario en el que se encuentra el mensaje
	 */
	public Usuario getLector() {
		return lector;
	}
	
	/**
	 * Método auxiliar para comprobar si el mensaje se puede difundir
	 * @param e Enlace a través del cual se comprueba la posibilidad de difusión
	 * @return true si se puede difundir, false si no
	 */
	private boolean puedeDifundirPor(Enlace e) {
		if(this.lector.getEnlace(e.getUsuarioDestino()) == null)
			return false;
		if(this.alcance < e.costeReal()) 
			return false;
		return true;
	}
	
	/**
	 * Método para comprobar si el mensaje fue aceptado
	 * @param u Usuario que puede aceptar o no el mensaje
	 * @return true si lo acepta, false si lo rechaza
	 */
	private boolean aceptadoPor(Usuario u) {
		return true;
	}
	
	/**
	 * Método para difundir el mensaje a través de un enlace
	 * @param e Enlace desde el lector hasta un nuevo usuario
	 * @return true si se pudo difundir, false si no se pudo
	 */
	public boolean difunde(Enlace e) {
		if(!this.puedeDifundirPor(e))
			return false;
		if(!this.aceptadoPor(e.getUsuarioDestino()))
			return false;
		
		this.lector = e.getUsuarioDestino();
		this.alcance -= e.costeReal();
		this.alcance += e.getUsuarioDestino().getCapacidadAmp();
		
		return true;
	}
	
	/**
	 * Método para difundir a una cadena de usuarios de una vez
	 * @param usuarios Usuarios a los que se difunde en orden
	 * @return true si se pudo difundir todas las veces, false si no
	 */
	public boolean difunde(Usuario...usuarios) {
		boolean ret = true;
		
		for(Usuario u : usuarios) {
			Enlace e = this.lector.getEnlace(u);
			if(e != null) {
				if(this.difunde(e) == false)
					ret = false;
			} else ret = false;
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		return "Mensaje ("+this.mensaje+":"+this.alcance+") en @"+this.lector.getNombre();
	}
}
