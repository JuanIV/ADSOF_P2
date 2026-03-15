package redSocial.mensaje;

import java.util.*;
import redSocial.enlace.Enlace;
import redSocial.usuario.*;

/**
 * Clase básica de Mensaje
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class Mensaje {
	private String mensaje;
	private int alcance;
	private List<Usuario> lectores = new ArrayList<>();
	
	
	/**
	 * Creador especificando un usuario lector inicial, al que se le añade el mensaje
	 * @param msj Contenido del mensaje
	 * @param alcance Alcance inicial del mensaje
	 * @param lectorActual Usuario lector inicial del mensaje
	 */
	public Mensaje(String msj, int alcance, Usuario lectorActual) {
		this.mensaje = msj;
		this.alcance = alcance;
		lectores.add(lectorActual);
		lectorActual.addMensaje(this);
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
		return lectores.getLast();
	}
	
	/**
	 * Getter de todos los usuarios que han leído el mensaje
	 * @return Array de usuarios
	 */
	public Usuario[] getLectores() {
		return lectores.toArray(new Usuario[0]);
	}
	
	/**
	 * Devuelve el número de Usuarios a los que se les ha compartido este mensaje
	 * @return el número de lectores
	 */
	public int getNLectores() {
		return lectores.size();
	}
	
	/**
	 * Método auxiliar para comprobar si el mensaje se puede difundir
	 * @param e Enlace a través del cual se comprueba la posibilidad de difusión
	 * @return true si se puede difundir, false si no
	 */
	protected boolean puedeDifundirPor(Enlace e) {
		if(this.lectores.getLast().getEnlace(e.getUsuarioDestino()) == null)
			return false;
		if(this.alcance < e.costeReal()) 
			return false;
		if(e.puedePasar() == false) 
			return false;
		
		return true;
	}
	
	/**
	 * Método para comprobar si el mensaje fue aceptado
	 * @param u Usuario que puede aceptar o no el mensaje
	 * @return true si lo acepta, false si lo rechaza
	 */
	public boolean aceptadoPor(Usuario u) {
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
		// Para evitar bucles infinitos entre dos UsuarioInteresado añado esta línea
		if (e.getUsuarioDestino().hasMensaje(this)) 
			return false;
		
		lectores.add(e.getUsuarioDestino());
		e.getUsuarioDestino().registrarMensaje(this);
		
		this.alcance -= e.costeReal();
		this.alcance += e.getUsuarioDestino().getCapacidadAmp();
		
		System.out.println(this);
		
		return true;
	}
	
	/**
	 * Método para difundir a una cadena de usuarios de una vez
	 * @param usuarios Usuarios a los que se difunde en orden
	 * @return true si se pudo difundir todas las veces, false si no
	 */
	public boolean difunde(Usuario...usuarios) {
		Enlace e = null;
		boolean ret = true, status = true;
		
		for(Usuario u : usuarios) {
			
			while(lectores.getLast() instanceof UsuarioInteresado && status) {
				if((e = ((UsuarioInteresado) lectores.getLast()).getEnlacePopular()) == null) break;
				
				status = difunde(e);
				e = null;
			}
			
			//System.out.println("- Lector: "+lector+"\n- Destinado a: "+u);
			
			e = lectores.getLast().getEnlace(u);
			
			
			if(e != null) {
				//System.out.println(e);
				if(this.difunde(e) == false)
					ret = false;
			} else ret = false;
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		return "Mensaje ("+this.mensaje+":"+this.alcance+") en @"+this.lectores.getLast().getNombre();
	}
}
