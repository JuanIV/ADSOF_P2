package redSocial;
import java.util.*;

import redSocial.mensaje.*;
import redSocial.usuario.*;
import redSocial.enlace.*;
import java.io.*;

public class RedSocial {
	private Map<String, Usuario> usuarios = new HashMap<>();
	private List<Mensaje> mensajes  = new ArrayList<>();
	
	/**
	 * Constructor de la clase que carga Usuarios y Enlaces y realiza una simulación de propagación de un mensaje
	 * @param fUsuarios fichero en el que esyá la información de los usuarios
	 * @param fEnlaces fichero en el que esyá la información de los enlaces
	 * @param fSimulacion fichero en el que esyá la información de la simulación
	 * @throws IOException excepción lanzada en caso de haber un problema con los ficheros
	 * @throws IllegalArgumentException excepción lanzada en caso de haber problemas en los contenidos de los ficheros
	 */
	public RedSocial(String fUsuarios, String fEnlaces, String fSimulacion) throws IOException, IllegalArgumentException {
		leerUsuarios(fUsuarios);
		leerEnlaces(fEnlaces);
		leerSimulacion(fSimulacion);
	}
	
	/**
	 * Constructor de la clase que carga Usuarios, Enlaces y Mensajes
	 * @param fUsuarios fichero en el que esyá la información de los usuarios
	 * @param fEnlaces fichero en el que esyá la información de los enlaces
	 * @param fMensajes fichero en el que esyá la información de los mensajes anteriores
	 * @param fSimulacion fichero en el que esyá la información de la simulación
	 * @throws IOException excepción lanzada en caso de haber un problema con los ficheros
	 * @throws IllegalArgumentException excepción lanzada en caso de haber problemas en los contenidos de los ficheros
	 */
	public RedSocial(String fUsuarios, String fEnlaces, String fMensajes, String fSimulacion) throws IOException, IllegalArgumentException {
		leerUsuarios(fUsuarios);
		leerEnlaces(fEnlaces);
		leerMensajes(fMensajes);
		
		if (fSimulacion != null) leerSimulacion(fSimulacion);
	}
	
	/**
	 * Método que guarda la red social en los ficheros especificados
	 * @param fUsuarios fichero en el que se guardan los usuarios
	 * @param fEnlaces fichero en el que se guardan los enlaces
	 * @param fMensajes  fichero en el que se guardan los mensajes
	 * @return true si todo funciona bien, false en caso contrario
	 * @throws IOException excepción lanzada en caso de haber un problema con los ficheros
	 * @throws IllegalArgumentException excepción lanzada en caso de haber problemas en los contenidos de los ficheros
	 */
	public boolean guardarRedSocial(String fUsuarios, String fEnlaces, String fMensajes) throws IOException, IllegalArgumentException {
		boolean st = true;
		
		if (this.guardarUsuarios(fUsuarios) == false) st = false;
		if (this.guardarEnlaces(fEnlaces) == false) st = false;
		if (this.guardarMensajes(fMensajes) == false) st = false;
		
		return st;
	}

	/**
	 * Método que añade un usuario a la red social
	 * @param nombre Username del nuevo usuario
	 * @param capacidadAmp capacidad de amplificación del nuevo Usuario
	 * @return true si todo ha funcionado correctamente, false en caso contrario
	 */
	public boolean anadirUsuario(String nombre, int capacidadAmp) throws IllegalArgumentException {
		if (usuarios.containsKey(nombre)) throw new IllegalArgumentException("Nombre de usuario ya existente");
		
		Usuario user = new Usuario(nombre, capacidadAmp);
		
		usuarios.put(nombre, user);
		
		System.out.println("Nuevo Usuario: "+user);
		
		return true;
	}
	
	/**
	 * Método que añade un usuario interesado a la red social
	 * @param nombre Username del nuevo usuario interesado
	 * @param capacidadAmp capacidad de amplificación del nuevo Usuario interesado
	 * @return true si todo ha funcionado correctamente, false en caso contrario
	 */
	public boolean anadirUsuarioInteresado(String nombre, int capacidadAmp) throws IllegalArgumentException{
		if (usuarios.containsKey(nombre)) throw new IllegalArgumentException("Nombre de usuario ya existente");;
		
		Usuario user = new UsuarioInteresado(nombre, capacidadAmp);
		
		usuarios.put(nombre, user);
		
		System.out.println("Nuevo UsuarioInteresado: "+user);
		
		return true;
	}
	
	/**
	 * Método que añade un enlace a la red social
	 * @param origen nombre del usuario de origen del enlace
	 * @param destino origen nombre del usuario de destino del enlace
	 * @param coste Valor coste de propagación del enlace
	 * @return true si todo ha funcionado correctamente, false en caso contrario
	 */
	public boolean anadirEnlace(String origen, String destino, int coste) throws IllegalArgumentException {
		Usuario uOrigen, uDestino;
		
		if ((uOrigen = usuarios.get(origen)) == null || (uDestino  = usuarios.get(destino)) == null) throw new IllegalArgumentException("No se añadió el enlace: alguno de los nombres de usuario no existe");
		
		Enlace e = new Enlace(uOrigen, uDestino, coste);
		if(uOrigen.addEnlace(e) == false) throw new IllegalArgumentException("No se añadió el enlace: parámetros del nuevo enlace inválidos");
		
		System.out.println("Nuevo Enlace: "+e);
		
		return true;
	}
	
	/**
	 * Método que añade un enlace señuelo a la red social
	 * @param origen nombre del usuario de origen del enlace señuelo
	 * @param destino origen nombre del usuario de destino del enlace señuelo
	 * @param coste Valor coste de propagación del enlace señuelo
	 * @param factorCoste Factor por el que se multiplica el coste para calcular el precio especial
	 * @param probRetorno Probabilidad entre 0 y 1 que representa lo probable que es que un mensaje no se difunda y vuelva al propio usuario
	 * @return true si todo ha funcionado correctamente, false en caso contrario
	 */
	public boolean anadirEnlaceSenuelo(String origen, String destino, int coste, int factorCoste, double probRetorno) throws IllegalArgumentException{
		Usuario uOrigen, uDestino;
		
		if ((uOrigen = usuarios.get(origen)) == null || (uDestino  = usuarios.get(destino)) == null) throw new IllegalArgumentException("No se añadió el enlace: alguno de los nombres de usuario no existe");
		
		EnlaceSenuelo eSen = new EnlaceSenuelo(uOrigen, uDestino, coste, factorCoste, probRetorno);
		if (uOrigen.addEnlace(eSen) == false) throw new IllegalArgumentException("No se añadió el enlace: parámetros del nuevo enlace inválidos");
		
		System.out.println("Nuevo EnlaceSenuelo: "+ eSen);
		
		return true;
	}
	
	/**
	 * Método que crea un mensaje que se encuentra en el uInicial. Su alcance será aquel de la capacidad de ampliación del usuario inicial
	 * @param mensaje contenido del mensaje. Este no puede contener el caracter '"'
	 * @param uInicial usuario que crea el mensaje y en el cual se encuentra inicialmente
	 * @param propagacion nombres de todos los usuarios a los que se propagará el mensaje
	 * @return true si todo ha funcionado correctamente, false en caso contrario
	 */
	public boolean anadirMensaje(String mensaje, String uInicial, String...propagacion) throws IllegalArgumentException {
		if (mensaje.isBlank()) throw new IllegalArgumentException("Contenido del mensaje vacío");
		
		List<Usuario> listaPropagacion = new ArrayList<>();
		Usuario orig, dest;
		
		if ((orig = usuarios.get(uInicial)) == null) throw new IllegalArgumentException("Alguno de los nombres de usuario no existe");
		
		for (String name: propagacion) {
			if ((dest = usuarios.get(name)) == null) throw new IllegalArgumentException("Alguno de los nombres de usuario no existe");
			listaPropagacion.add(dest);
		}
		
		Mensaje msj = new Mensaje(mensaje, orig.getCapacidadAmp(), orig);
		mensajes.add(msj);
		
		System.out.println("Nuevo Mensaje: "+msj);
		
		msj.difunde(listaPropagacion.toArray(new Usuario[0]));
		
		return true;
	}
	
	/**
	 * Método que crea un mensaje que se encuentra en el uInicial. Su alcance será aquel de la capacidad de ampliación del usuario inicial
	 * @param mensaje contenido del mensaje. Este no puede contener el caracter '"'
	 * @param uInicial usuario que crea el mensaje y en el cual se encuentra inicialmente
	 * @param propagacion nombres de todos los usuarios a los que se propagará el mensaje
	 * @return true si todo ha funcionado correctamente, false en caso contrario
	 */
	public boolean anadirMensajeControlado(String mensaje, String uInicial, int rigidez, String...propagacion) throws IllegalArgumentException{
		if (mensaje.contains("\"")) return false;
		
		List<Usuario> listaPropagacion = new ArrayList<>();
		Usuario orig, dest;
		
		if ((orig = usuarios.get(uInicial)) == null) throw new IllegalArgumentException("Alguno de los nombres de usuario no existe");
		
		for (String name: propagacion) {
			if ((dest = usuarios.get(name)) == null) throw new IllegalArgumentException("Alguno de los nombres de usuario no existe");
			listaPropagacion.add(dest);
		}
		
		Mensaje msj = new MensajeControlado(mensaje, orig.getCapacidadAmp(), orig, rigidez);
		mensajes.add(msj);
		
		System.out.println("Nuevo MensajeControlado: "+msj);
		
		msj.difunde(listaPropagacion.toArray(new Usuario[0]));
		
		return true;
	}
	
	/**
	 * Método que altera un enlace a partir del usuario origen, destino original y nuevo destino.
	 * Para que funcione correctamente deben existir previamente todos los usuarios y el enlace 
	 * @param origen
	 * @param destOriginal
	 * @param destNuevo
	 * @param coste
	 * @return
	 */
	public boolean cambiarEnlace(String origen, String destOriginal, String destNuevo, int coste) throws IllegalArgumentException{
		Usuario uOrigen, uDestOriginal, uDestNuevo;
		
		if ((uOrigen = usuarios.get(origen)) == null ||
				(uDestOriginal = usuarios.get(destOriginal)) == null ||
				(uDestNuevo = usuarios.get(destNuevo)) == null) throw new IllegalArgumentException("Alguno de los nombres de usuario no existe");
		
		return uOrigen.cambiarEnlace(uDestOriginal, uDestNuevo, coste);
	}
	
	/**
	 * Método privado que carga los usuarios a la red social a partir de un fichero
	 * @param fUsuarios nombre/ direccion del fichero leído
	 * @return true en caso de que funcione bien
	 * @throws IOException en caso de haber un error a la hora de abrir el fichero
	 * @throws IllegalArgumentException en caso de que los argumentos leÍdos sean inválidos
	 */
	private boolean leerUsuarios (String fUsuarios) throws IOException, IllegalArgumentException{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fUsuarios)))) {
			int capAmplificacion, nArgs;
			double avgAlcance;
			Exposicion exp;
			String bufferLine;
			String[] splitLine;

			while ((bufferLine = reader.readLine()) != null) {
				splitLine = bufferLine.trim().split("\\s+");
				
				if (splitLine.length < 2) throw new IllegalArgumentException("Faltan parámetros de entrada");
				
				if (usuarios.containsKey(splitLine[0])) throw new IllegalArgumentException("Nombre de usuario repetido");
				capAmplificacion = Integer.parseInt(splitLine[1]);
				
				nArgs = splitLine.length;
				switch(nArgs) {
					case 2:
						usuarios.put(splitLine[0], new Usuario(splitLine[0], capAmplificacion));
						break;
					case 4:
					case 5:
						exp = Exposicion.valueOf(splitLine[2]);
						avgAlcance = Double.parseDouble(splitLine[3]);
						if (nArgs == 4) usuarios.put(splitLine[0], new Usuario(splitLine[0], capAmplificacion, exp, avgAlcance));
						else usuarios.put(splitLine[0], new UsuarioInteresado(splitLine[0], capAmplificacion, exp, avgAlcance));
						break;
					default: 
						throw new IllegalArgumentException("Número de parámetros de entrada inválido");
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Método privado que carga los enlaces a la red social a partir de un fichero
	 * @param fEnlaces nombre/ direccion del fichero leído
	 * @return true en caso de que funcione bien
	 * @throws IOException en caso de haber un error a la hora de abrir el fichero
	 * @throws IllegalArgumentException en caso de que los argumentos leÍdos sean inválidos
	 */
	private boolean leerEnlaces (String fEnlaces) throws IOException, IllegalArgumentException{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fEnlaces)))) {
			Usuario uOrigen, uDestino;
			int factorCoste, nArgs, coste;
			double probRetorno;
			String bufferLine;
			String[] splitLine;
			
			while ((bufferLine = reader.readLine()) != null) {
				splitLine = bufferLine.trim().split("\\s+");
				
				if (splitLine.length < 3) throw new IllegalArgumentException("Faltan parámetros de entrada");
				
				if ((uOrigen = usuarios.get(splitLine[0])) == null || (uDestino = usuarios.get(splitLine[1])) == null) throw new IllegalArgumentException("Alguno de los nombres de usuario no existe");
				coste = Integer.parseInt(splitLine[2]);
				
				nArgs = splitLine.length;
				switch(nArgs) {
					case 3:
						if (uOrigen.addEnlace(uDestino, coste) == false) throw new IllegalArgumentException("Formato de enlace inválido, Formato correcto NombreUsuarioOrigen(String) NombreUsuarioDestino(String) Coste(int)");
						break;
					case 5:
						factorCoste = Integer.parseInt(splitLine[3]);
						probRetorno = Double.parseDouble(splitLine[4]);
						if (uOrigen.addEnlace(new EnlaceSenuelo(uOrigen, uDestino, coste, factorCoste, probRetorno)) == false) throw new IllegalArgumentException("Formato de enlace señuelo inválido, Formato correcto NombreUsuarioOrigen(String) NombreUsuarioDestino(String) Coste(int) FactorCoste(int) ProbabilidadRetorno(double)");
						break;
					default: 
						throw new IllegalArgumentException("Número deparámetros de entrada inválido");
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Método privado que carga los mensajes que ya fueron enviados a la red social a partir de un fichero
	 * @param fMensajes nombre/ direccion del fichero leído
	 * @return true en caso de que funcione bien
	 * @throws IOException en caso de haber un error a la hora de abrir el fichero
	 * @throws IllegalArgumentException en caso de que los argumentos leÍdos sean inválidos
	 */
	private boolean leerMensajes (String fMensajes) throws IOException, IllegalArgumentException{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fMensajes)))) {
			Usuario user;
			Mensaje msj = null;
			int alcance, rigidez;
			String bufferLine, nombre, mensaje;
			String[] splitLine;
			
			while ((bufferLine = reader.readLine()) != null) {
				if (bufferLine.contains("\"")) {
					int inicioMensaje = bufferLine.indexOf("\""), finalMensaje = bufferLine.lastIndexOf("\"");
					if (inicioMensaje == -1 || inicioMensaje == finalMensaje) throw new IllegalArgumentException("Contenido del mensaje no entrecomillado correctamente \"contenido\"");
					CharSequence contenido = bufferLine.subSequence(inicioMensaje + 1, finalMensaje);
					mensaje = contenido.toString();
					
					splitLine = bufferLine.substring(finalMensaje + 1).trim().split("\\s+");
					
					alcance = Integer.parseInt(splitLine[0]);
					if ((user = usuarios.get(splitLine[1])) == null)  throw new IllegalArgumentException("Usuario actual del mensaje inválido");
					
					
					switch(splitLine.length) {
						case 2:
							msj = new Mensaje(mensaje, alcance, user);
							break;
						case 3: 
							rigidez = Integer.parseInt(splitLine[2]);
							msj = new MensajeControlado(mensaje, alcance, user, rigidez);	
					}
					
					mensajes.add(msj);
					
				} else {
					if (msj == null || bufferLine.isBlank()) break;
					
					nombre = bufferLine.trim();
					if ((user = usuarios.get(nombre)) == null) throw new IllegalArgumentException("Nombre de usuario de entrada inexistente");
					
					if (user.addMensaje(msj) == false) throw new IllegalArgumentException("No se añadió correctamente el mensaje al usuario");
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Método privado que carga un mensaje a partir de un fichero y lo difunde en la red social
	 * @param fMensaje nombre/ direccion del fichero leído
	 * @return true en caso de que funcione bien
	 * @throws IOException en caso de haber un error a la hora de abrir el fichero
	 * @throws IllegalArgumentException en caso de que los argumentos leÍdos sean inválidos
	 */
	public boolean leerSimulacion(String fMensaje) throws IOException, IllegalArgumentException {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fMensaje)))) {
			Mensaje msj;
			Usuario autor, user;
			List<Usuario> propagacion = new ArrayList<>();
			String bufferLine, mensaje;
			String[] splitLine;
			int alcance, rigidez;
			
			if ((bufferLine = reader.readLine()) == null) throw new IllegalArgumentException("Simulación sin mensaje");
			
			int inicioMensaje = bufferLine.indexOf("\""), finalMensaje = bufferLine.lastIndexOf("\"");
			if ((inicioMensaje == -1) || (inicioMensaje + 1 >= finalMensaje)) throw new IllegalArgumentException("Contenido del mensaje no entrecomillado correctamente \"contenido\"");
			CharSequence contenido = bufferLine.subSequence(inicioMensaje+1, finalMensaje);
			mensaje = contenido.toString();
			
			splitLine = bufferLine.substring(finalMensaje + 1).trim().split("\\s+");
			
			alcance = Integer.parseInt(splitLine[0]);
			if ((autor = usuarios.get(splitLine[1])) == null)  throw new IllegalArgumentException("Nombre del usuario actual no existente");
			
			
			switch(splitLine.length) {
				case 2:
					msj = new Mensaje(mensaje, alcance, autor);
					break;
				case 3: 
					rigidez = Integer.parseInt(splitLine[2]);
					msj = new MensajeControlado(mensaje, alcance, autor, rigidez);	
					break;
				default:
					throw new IllegalArgumentException("Número de parámetros de entrada inválido");
			}
			
			mensajes.add(msj);
			
			while((bufferLine = reader.readLine()) != null) {
				splitLine = bufferLine.trim().split("\\s+");
				if ((user = usuarios.get(splitLine[0])) == null) throw new IllegalArgumentException("Algún nombre de usuario destino inexistente");
				
				propagacion.add(user);
			}
			
			msj.difunde(propagacion.toArray(new Usuario[0]));
		
			return true;
		}
	}
	
	/**
	 * Método privado que nos permite guardar la información de los usuarios que se encuentren en la red social
	 * @param fUsuarios fichero en el que se van a almacenar los usuarios
	 * @return true en caso de éxito
	 * @throws IOException excepción lanzada en caso de problemas relacionados con el archivo
	 * @throws IllegalArgumentException excepción lanzada en caso de problemas de formato del archivo
	 */
	private boolean guardarUsuarios (String fUsuarios) throws IOException, IllegalArgumentException{
		try(PrintWriter writer = new PrintWriter(new FileOutputStream(fUsuarios))) {
			for (Usuario u: usuarios.values()) {
				if (u instanceof UsuarioInteresado)
					writer.printf("%s %d %s %f %d%n", u.getNombre(), u.getCapacidadAmp(), u.getNivelExposicion().name(), u.getAlcancePromedio(), 1);
				else 
					writer.printf("%s %d %s %f%n", u.getNombre(), u.getCapacidadAmp(), u.getNivelExposicion().name(), u.getAlcancePromedio());
				
			}
		}
			
		return true;
	}
	
	/**
	 * Método privado que nos permite guardar la información de los enlaces que se encuentren en la red social
	 * @param fEnlaces fichero en el que se van a almacenar los enlaces
	 * @return true en caso de éxito
	 * @throws IOException excepción lanzada en caso de problemas relacionados con el archivo
	 * @throws IllegalArgumentException excepción lanzada en caso de problemas de formato del archivo
	 */
	private boolean guardarEnlaces (String fEnlaces) throws IOException, IllegalArgumentException{
		try(PrintWriter writer = new PrintWriter(new FileOutputStream(fEnlaces))) {
			int nEnlaces, i;
			Enlace e;
			EnlaceSenuelo eSen;
			
			for (Usuario u: usuarios.values()) {
				nEnlaces = u.getNumEnlaces();
				//System.out.println(nEnlaces);
				for (i = 0; i < nEnlaces; i++) {
					if ((e = u.getEnlace(i)) == null) throw new IllegalArgumentException("Algún enlace no se pudo guardar correctamente");
					//System.out.println(e);
					if (e instanceof EnlaceSenuelo) {
						eSen = (EnlaceSenuelo) e;
						writer.printf("%s %s %d %d %f%n", eSen.getUsuarioOrigen().getNombre(), eSen.getUsuarioDestino().getNombre(), 
								eSen.getCoste(), eSen.getFactorCoste(), eSen.getProbRetorno());
					} else {
						writer.printf("%s %s %d%n", e.getUsuarioOrigen().getNombre(), e.getUsuarioDestino().getNombre(), 
								e.getCoste());
					}
				}
			}
		}
			
		
		return true;
	}
		
	/**
	 * Método privado que nos permite guardar la información de los mensajes que se encuentren en la red social
	 * @param fMensjes fichero en el que se van a almacenar los enlaces
	 * @return true en caso de éxito
	 * @throws IOException excepción lanzada en caso de problemas relacionados con el archivo
	 * @throws IllegalArgumentException excepción lanzada en caso de problemas de formato del archivo
	 */
	private boolean guardarMensajes (String fMensajes) throws IOException, IllegalArgumentException{
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(fMensajes))) {
			MensajeControlado mCtrl;
			Usuario[] lectores;
			int nLectores, i;
			
			for (Mensaje m : mensajes) {
				if (m instanceof MensajeControlado) {
					mCtrl = (MensajeControlado) m;
					writer.printf("\"%s\" %d %s %d%n", mCtrl.getMensaje(), mCtrl.getAlcance(), 
							mCtrl.getLector().getNombre(), mCtrl.getRigidez());
				} else {
					writer.printf("\"%s\" %d %s%n", m.getMensaje(), m.getAlcance(), 
							m.getLector().getNombre());
				}
				
				lectores = m.getLectores();
				nLectores = m.getNLectores();
				/* Estamos excluyendo el ultimo elemento de los lectores que corresponde al autor, ya que este corresponde 
				 * al lector actual y se añade en la creación del mensaje
				 */
				for (i = 0; i + 1 < nLectores; i++) {
					writer.printf("%s%n", lectores[i].getNombre());
				}
			}
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return usuarios + "\n"+mensajes;
	}
	
}
