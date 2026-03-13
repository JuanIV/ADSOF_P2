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
	 * Función que guarda la red social en los ficheros especificados
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
	 * Función privada que carga los usuarios a la red social a partir de un fichero
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
				
				if (splitLine.length < 2) throw new IllegalArgumentException();
				
				if (usuarios.containsKey(splitLine[0])) throw new IllegalArgumentException();
				capAmplificacion = Integer.parseInt(splitLine[1]);
				
				nArgs = splitLine.length;
				switch(nArgs) {
					case 2:
						usuarios.put(splitLine[0], new Usuario(splitLine[0], capAmplificacion));
						break;
					case 4:
					case 5:
						avgAlcance = Integer.parseInt(splitLine[2]);
						exp = Exposicion.valueOf(splitLine[3]);
						if (nArgs == 4) usuarios.put(splitLine[0], new Usuario(splitLine[0], capAmplificacion, exp, avgAlcance));
						else usuarios.put(splitLine[0], new UsuarioInteresado(splitLine[0], capAmplificacion, exp, avgAlcance));
						break;
					default: 
						throw new IllegalArgumentException();
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Función privada que carga los enlaces a la red social a partir de un fichero
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
				
				if (splitLine.length < 3) throw new IllegalArgumentException();
				
				if ((uOrigen = usuarios.get(splitLine[0])) == null || (uDestino = usuarios.get(splitLine[1])) == null) throw new IllegalArgumentException();
				coste = Integer.parseInt(splitLine[2]);
				
				nArgs = splitLine.length;
				switch(nArgs) {
					case 3:
						if (uOrigen.addEnlace(uDestino, coste) == false) throw new IllegalArgumentException();
						break;
					case 5:
						factorCoste = Integer.parseInt(splitLine[3]);
						probRetorno = Double.parseDouble(splitLine[4]);
						if (uOrigen.addEnlace(new EnlaceSenuelo(uOrigen, uDestino, coste, factorCoste, probRetorno)) == false) throw new IllegalArgumentException();
						break;
					default: 
						throw new IllegalArgumentException();
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Función privada que carga los mensajes que ya fueron enviados a la red social a partir de un fichero
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
					splitLine = bufferLine.trim().split("\"");
					if (splitLine.length < 3) throw new IllegalArgumentException();
					
					mensaje = splitLine[1];
					splitLine = splitLine[2].trim().split("\\s+");
					
					alcance = Integer.parseInt(splitLine[0]);
					if ((user = usuarios.get(splitLine[1])) == null)  throw new IllegalArgumentException();
					
					
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
					if (msj == null) throw new IOException();
					
					nombre = bufferLine.trim();
					if ((user = usuarios.get(nombre)) == null) throw new IllegalArgumentException();
					
					if (user.addMensaje(msj) == false) throw new IllegalArgumentException();
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Función privada que carga un mensaje a partir de un fichero y lo difunde en la red social
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
			
			if ((bufferLine = reader.readLine()) == null) throw new IllegalArgumentException();
			
			if (bufferLine.contains("\"") == false) throw new IllegalArgumentException();
			splitLine = bufferLine.trim().split("\"");
			if (splitLine.length < 3) throw new IllegalArgumentException();
			
			mensaje = splitLine[1];
			splitLine = splitLine[2].trim().split("\\s+");
			
			alcance = Integer.parseInt(splitLine[0]);
			if ((autor = usuarios.get(splitLine[1])) == null)  throw new IllegalArgumentException();
			
			
			switch(splitLine.length) {
				case 2:
					msj = new Mensaje(mensaje, alcance, autor);
					break;
				case 3: 
					rigidez = Integer.parseInt(splitLine[2]);
					msj = new MensajeControlado(mensaje, alcance, autor, rigidez);	
					break;
				default:
					throw new IllegalArgumentException();
			}
			
			mensajes.add(msj);
			
			while((bufferLine = reader.readLine()) != null) {
				splitLine = bufferLine.trim().split("\\s+");
				if ((user = usuarios.get(splitLine[0])) == null) throw new IllegalArgumentException();
				
				propagacion.add(user);
			}
			
			for (Usuario u: propagacion) {
				if (msj.difunde(u)) System.out.println(msj);
			}
		
			
			return true;
		}
	}
	
	/**
	 * Función privada que nos permite guardar la información de los usuarios que se encuentren en la red social
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
	 * Función privada que nos permite guardar la información de los enlaces que se encuentren en la red social
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
				for (i = 0; i < nEnlaces; i++) {
					if ((e = u.getEnlace(i)) == null) throw new IOException();
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
	 * Función privada que nos permite guardar la información de los mensajes que se encuentren en la red social
	 * @param fMensjes fichero en el que se van a almacenar los enlaces
	 * @return true en caso de éxito
	 * @throws IOException excepción lanzada en caso de problemas relacionados con el archivo
	 * @throws IllegalArgumentException excepción lanzada en caso de problemas de formato del archivo
	 */
	private boolean guardarMensajes (String fMensajes) throws IOException, IllegalArgumentException{
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(fMensajes))) {
			MensajeControlado mCtrl;
			for (Mensaje m : mensajes) {
				if (m instanceof MensajeControlado) {
					mCtrl = (MensajeControlado) m;
					writer.printf("\"%s\" %d %s %d%n", mCtrl.getMensaje(), mCtrl.getAlcance(), 
							mCtrl.getLector().getNombre(), mCtrl.getRigidez());
				} else {
					writer.printf("\"%s\" %d %s%n", m.getMensaje(), m.getAlcance(), 
							m.getLector().getNombre());
				}
				
				for (Usuario u: usuarios.values()) {
					if (u.hasMensaje(m)) writer.printf("%s%n", u.getNombre());
				}
			}
		}
		
		return true;
	}
	
}
