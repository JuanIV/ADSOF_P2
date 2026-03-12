package redSocial;
import java.util.*;

import redSocial.mensaje.*;
import redSocial.usuario.*;
import redSocial.enlace.*;
import java.io.*;

public class RedSocial {
	private Map<String, Usuario> usuarios = new HashMap<>();
	private List<Mensaje> mensajes  = new ArrayList<>();
	
	public RedSocial(String fUsuarios, String fEnlaces, String fMensaje) throws IOException{
		leerUsuarios(fUsuarios);
		leerEnlaces(fEnlaces);
		leerSimulacion(fMensaje);
		
	}
	
	private boolean leerUsuarios (String fUsuarios) throws IOException{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fUsuarios)))) {
			int capAmplificacion, nArgs;
			double avgAlcance;
			Exposicion exp;
			String bufferLine;
			String[] splitLine;
for (Usuario u: usuarios.values()) {
				
			}
			while ((bufferLine = reader.readLine()) != null) {
				splitLine = bufferLine.trim().split("\\s+");
				
				if (splitLine.length < 2) throw new IOException();
				
				if (usuarios.containsKey(splitLine[0])) throw new IOException();
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
						throw new IOException();
				}
			}
		}
		
		return true;
	}
	
	private boolean leerEnlaces (String fEnlaces) throws IOException{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fEnlaces)))) {
			Usuario uOrigen, uDestino;
			int factorCoste, nArgs, coste;
			double probRetorno;
			String bufferLine;
			String[] splitLine;
			
			while ((bufferLine = reader.readLine()) != null) {
				splitLine = bufferLine.trim().split("\\s+");
				
				if (splitLine.length < 3) throw new IOException();
				
				if ((uOrigen = usuarios.get(splitLine[0])) == null || (uDestino = usuarios.get(splitLine[1])) == null) throw new IOException();
				coste = Integer.parseInt(splitLine[2]);
				
				nArgs = splitLine.length;
				switch(nArgs) {
					case 3:
						if (uOrigen.addEnlace(uDestino, coste) == false) throw new IOException();
						break;
					case 5:
						factorCoste = Integer.parseInt(splitLine[3]);
						probRetorno = Double.parseDouble(splitLine[4]);
						if (uOrigen.addEnlace(new EnlaceSenuelo(uOrigen, uDestino, coste, factorCoste, probRetorno)) == false) throw new IOException();
						break;
					default: 
						throw new IOException();
				}
			}
		}
		
		return true;
	}
	
	public boolean leerMensajes (String fMensajes) throws IOException{
		System.out.println("Entrasss111");
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fMensajes)))) {
			Usuario user;
			Mensaje msj = null;
			int alcance, rigidez;
			System.out.println("Entrasss");
			String bufferLine, nombre, mensaje;
			String[] splitLine;
			
			while ((bufferLine = reader.readLine()) != null) {
				if (bufferLine.contains("\"")) {
					splitLine = bufferLine.trim().split("\"");
					if (splitLine.length < 3) throw new IOException();
					
					mensaje = splitLine[1];
					splitLine = splitLine[2].trim().split("\\s+");
					
					alcance = Integer.parseInt(splitLine[0]);
					if ((user = usuarios.get(splitLine[1])) == null)  throw new IOException();
					
					
					switch(splitLine.length) {
						case 2:
							msj = new Mensaje(mensaje, alcance, user);
							break;
						case 3: 
							rigidez = Integer.parseInt(splitLine[2]);
							msj = new MensajeControlado(mensaje, alcance, user, rigidez);	
					}
					
					if (mensajes.add(msj) == false)  throw new IOException();
					
				} else {
					if (msj == null) throw new IOException();
					
					nombre = bufferLine.trim();
					if ((user = usuarios.get(nombre)) == null) throw new IOException();
					
					if (user.addMensaje(msj) == false) throw new IOException();
				}
			}
		}
		
		return true;
	}
	
	public boolean leerSimulacion(String fMensaje) throws IOException {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fMensaje)))) {
			Mensaje msj;
			Usuario autor, user;
			List<Usuario> propagacion = new ArrayList<>();
			String bufferLine, mensaje;
			String[] splitLine;
			int alcance, rigidez;
			
			if ((bufferLine = reader.readLine()) == null) throw new IOException();
			
			if (bufferLine.contains("\"") == false) throw new IOException();
				splitLine = bufferLine.trim().split("\"");
				if (splitLine.length < 3) throw new IOException();
				
				mensaje = splitLine[1];
				splitLine = splitLine[2].trim().split("\\s+");
				
				alcance = Integer.parseInt(splitLine[0]);
				if ((autor = usuarios.get(splitLine[1])) == null)  throw new IOException();
				
				
				switch(splitLine.length) {
					case 2:
						msj = new Mensaje(mensaje, alcance, autor);
						break;
					case 3: 
						rigidez = Integer.parseInt(splitLine[2]);
						msj = new MensajeControlado(mensaje, alcance, autor, rigidez);	
					default:
						throw new IOException();
				}
				
				if (mensajes.add(msj) == false)  throw new IOException();
				
				while((bufferLine = reader.readLine()) != null) {
					splitLine = bufferLine.trim().split("\\s+");
					if ((user = usuarios.get(splitLine[0])) == null) throw new IOException();
					
					if (propagacion.add(user) == false) throw new IOException();;
				}
				
				for (Usuario u: propagacion) {
					if (msj.difunde(u)) System.out.println(msj);
				}
		
			
			return true;
		}
	}
	
	private boolean guardarUsuarios (String fUsuarios) throws IOException{
		try(PrintWriter writer = new PrintWriter(new FileOutputStream(fUsuarios))) {
			for (Usuario u: usuarios.values()) {
				
			}
		}
			
		
		return true;
	}
		
	
	
}
