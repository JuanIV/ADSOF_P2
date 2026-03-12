package redSocial.usuario;
import java.util.*;

import redSocial.Exposicion;
import redSocial.enlace.Enlace;
import redSocial.mensaje.Mensaje;

/**
 * Clase básica Usuario
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class Usuario {
	private String nombre;
	private int capacidadAmp;
	private HashMap<Usuario, Enlace> enlacesPorDestino = new HashMap<>();
	protected List<Enlace> enlacesOrdenados = new ArrayList<>();
	private List<Mensaje> historial = new ArrayList<>();
	private double alcancePromedio = 0;
	private Exposicion exposicion;
	
	
	/**
	 * Creador de usuario para ficheros
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 * @param exposicion Exposicion de nuestro usuario
	 * @param alcancePromedio Media de los alcances de todos los mensajes que le hayan llegado
	 */
	public Usuario(String nombre, int capacidadAmp, Exposicion exposicion, double alcancePromedio) {
		this.nombre = nombre;
		this.capacidadAmp = capacidadAmp;
		this.exposicion = exposicion;
	}
	
	/**
	 * Creador completo de Usuario
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 */
	public Usuario(String nombre, int capacidadAmp, Exposicion exposicion) {
		this.nombre = nombre;
		this.capacidadAmp = capacidadAmp;
		this.exposicion = exposicion;
	}
	
	/**
	 * Creador completo de Usuario
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 */
	public Usuario(String nombre, int capacidadAmp) {
		this(nombre, capacidadAmp, Exposicion.ALTA);
	}
	
	/**
	 * Creador sin especificar capacidad de ampliación
	 * @param nombre Nombre del usuario
	 */
	public Usuario(String nombre) {
		this(nombre, 2);
	}
	
	/**
	 * Getter del nombre
	 * @return Nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Getter de la capacidad de ampliación
	 * @return Capacidad de ampliación del usuario
	 */
	public int getCapacidadAmp() {
		return capacidadAmp;
	}
	
	/**
	 * Getter del nivel de exposicion del usuario
	 * @return nivel de exposicion del usuario
	 */
	public Exposicion getNivelExposicion() {
		return exposicion;
	}
	
	/**
	 * Método para obtener un enlace por su índice en la lista
	 * @param i Índice del enlace en el array de enlaces
	 * @return Enlace con el índice especificado
	 */
	public Enlace getEnlace(int i) {
		return this.enlacesOrdenados.get(i);
	}
	
	/**
	 * Método para obtener el número de enlaces que tiene el usuario
	 * @return Número de enlaces salientes del usuario
	 */
	public int getNumEnlaces() {
		return this.enlacesOrdenados.size();
	}
	
	/**
	 * Método para obtener un enlace a un destino concreto
	 * @param destino Usuario destino al que se quiere el enlace
	 * @return Enlace desde this hasya destino o null si no existe
	 */
	public Enlace getEnlace(Usuario destino) {
		return this.enlacesPorDestino.get(destino);
	}

	/**
	 * Método para añadir un enlace a la lista de enlaces
	 * @param e Enlace que se quiere añadir
	 * @return true si se pudo añadir, false si no se pudo
	 */
	public boolean addEnlace(Enlace e) {
		if((!e.getUsuarioOrigen().equals(this)) || e.getUsuarioDestino().equals(this)) 
			return false;
	
		if(this.enlacesPorDestino.containsKey(e.getUsuarioDestino()))
			return false;
		
		if(this.enlacesPorDestino.put(e.getUsuarioDestino(), e) != null) return false;
		
		return this.enlacesOrdenados.add(e);
	}
	
	/**
	 * Método para añadir un nuevo enlace a la vez que se crea
	 * @param destino Usuario destino del enlace
	 * @param coste Coste del enlace
	 * @return true si se pudo añadir, false si no se pudo
	 */
	public boolean addEnlace(Usuario destino, int coste) {
		Enlace e = new Enlace(this, destino, coste);
		return this.addEnlace(e);
	}
	
	public boolean addMensaje(Mensaje msj) {
		return historial.add(msj);
	}
	
	public boolean registrarMensaje (Mensaje msj) {
		if (msj == null) return false;
		
		if (msj.getLector().equals(this) == false) return false;
		
		if (historial.add(msj) == false) return false;
		
		int alcance = msj.getAlcance() ; 
		if (alcance > alcancePromedio) {
			exposicion = Exposicion.getNext(exposicion);
		} else if (alcance < alcancePromedio) {
			exposicion = Exposicion.getPrev(exposicion);			
		}
		
		int size = historial.size();
		alcancePromedio = (alcancePromedio *(size-1) + alcance)/size;
		
		return true;
	}
	
	/**
	 * Modificador del nivel de exposicion de un usuario
	 * @param nuevo nivel de exposicion
	 */
	void cambiarExposicion(Exposicion e) {
		exposicion = e;
	}
	
	@Override
	public String toString() {
		return "@"+this.nombre+"("+this.capacidadAmp+")"+this.enlacesOrdenados.toString();
	}
}
