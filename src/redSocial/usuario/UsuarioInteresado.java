package redSocial.usuario;

import redSocial.Exposicion;
import redSocial.enlace.Enlace;

/**
 * Clase UsuarioInteresado, subclase de Usuario
 * 
 * @author Juan Ibáñez y Tiago Oselka
 * @version 1.2
 */
public class UsuarioInteresado extends Usuario{

	/**
	 * Creador de usuario interesado para ficheros
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 * @param exposicion Exposicion de nuestro usuario
	 * @param alcancePromedio Media de los alcances de todos los mensajes que le hayan llegado
	 */
	public UsuarioInteresado(String nombre, int capacidadAmp, Exposicion exposicion, double alcancePromedio) {
		super(nombre, capacidadAmp, exposicion, alcancePromedio);
	}
	
	/**
	 * Creador completo de UsuarioInteresado
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 * @param exposicion Nivel de exposición asociada al Usuario
	 */
	public UsuarioInteresado(String nombre, int capacidadAmp, Exposicion exposicion) {
		super(nombre, capacidadAmp, exposicion);
	}
	
	/**
	 * Creador de UsuarioInteresado con exposición predeterminada de Usuario
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 */
	public UsuarioInteresado(String nombre, int capacidadAmp) {
		super(nombre, capacidadAmp);
	}	
	
	/**
	 * Encuentra el primer enlace de su lista ordenada cuyo Usuario destino tenga una Exposición >= ALTA
	 * @return El primer enlace que encuentra, null en caso de no encontrarlo
	 */
	public Enlace getEnlacePopular() {
		for (Enlace e: enlacesOrdenados) {
			if (e.getUsuarioDestino().getNivelExposicion().compareTo(Exposicion.ALTA) >= 0) {
				return e;
			}
		}
		
		return null;
	}

}
