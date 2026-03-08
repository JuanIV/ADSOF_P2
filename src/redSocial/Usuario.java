package redSocial;
import java.util.*;

/**
 * Clase básica Usuario
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class Usuario {
	private String nombre;
	private int capacidadAmp;
	private List<Enlace> enlaces;
	private Map<Usuario, Enlace> enlacesPorDestino;
	
	/**
	 * Creador completo de Usuario
	 * @param nombre Nombre del usuario
	 * @param capacidadAmp Capacidad de ampliación del usuario
	 */
	public Usuario(String nombre, int capacidadAmp) {
		this.nombre = nombre;
		this.capacidadAmp = capacidadAmp;
		this.enlaces = new ArrayList<Enlace>();
		this.enlacesPorDestino = new HashMap<Usuario, Enlace>();
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
	 * Método para obtener un enlace por su índice en la lista
	 * @param i Índice del enlace en el array de enlaces
	 * @return Enlace con el índice especificado
	 */
	public Enlace getEnlace(int i) {
		return this.enlaces.get(i);
	}
	
	/**
	 * Método para obtener el número de enlaces que tiene el usuario
	 * @return Número de enlaces salientes del usuario
	 */
	public int getNumEnlaces() {
		return this.enlaces.size();
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
		if((!e.getUsuarioOrigen().equals(this))) 
			return false;
		if(e.getUsuarioDestino().equals(this)) 
			return false;
		if(this.enlacesPorDestino.containsKey(e.getUsuarioDestino()))
			return false;
		
		this.enlacesPorDestino.put(e.getUsuarioDestino(), e);
		return this.enlaces.add(e);
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
	
	@Override
	public String toString() {
		return "@"+this.nombre+"("+this.capacidadAmp+")"+this.enlaces.toString();
	}
}
