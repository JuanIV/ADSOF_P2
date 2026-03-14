package redSocial;

import java.io.IOException;

public class EjemploDeUsoRedSocial {

	public static void main(String[] args) {
		testCompatibilidadLecturaEscritura1();
		testDifusionMensajeControlado();
		
	}
	
	static void testCompatibilidadLecturaEscritura1() {
		try {
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			r1.guardarRedSocial("usuarios1.txt", "enlaces1.txt", "mensaje3.txt");
			RedSocial r3 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje3.txt", null);
			System.out.println(r1.toString().equals(r3.toString()));
		} catch(IOException e) {
			System.out.println("Error con los ficheros a la hora de leer/escribir en testCompatibilidadLecturaEscritura1");
		} catch(IllegalArgumentException e) {
			System.out.println("Error en el formato de los ficheros en testCompatibilidadLecturaEscritura1");
		}
	}

	static void testCompatibilidadYDifusionMensaje() {
		try {
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			System.out.println("Se crea la red social a partir de ficheros");
			r1.anadirUsuarioInteresado("Interesado", 15);
			r1.anadirUsuario("Pepito", 20);
			System.out.println("Se crean los usuarios Pepito (Usuario) e Interesado (UsuarioInteresado)");
			r1.anadirEnlaceSenuelo("Interesado", "Pepito", 5, 2, 0);
			r1.anadirEnlace("Interesado", "luis", 10);
			r1.anadirEnlace("Pepito", "luis", 10);
			r1.anadirMensaje("Buenos dias a todos!", "Interesado", "luis", "mario");
			r1.guardarRedSocial("usuarios3.txt", "enlaces3.txt", "mensaje3.txt");
			System.out.println(r1);
			RedSocial r3 = new RedSocial("usuarios3.txt", "enlaces3.txt", "mensaje3.txt", null);
			System.out.println(r3);
		} catch(IOException e) {
			System.out.println("Error con los ficheros a la hora de leer/escribir en testCompatibilidadLecturaEscritura1");
		} catch(IllegalArgumentException e) {
			System.out.println("Error en el formato de los ficheros en testCompatibilidadLecturaEscritura1");
		}
	}
	
	static void testDifusionMensajeControlado() {
		try {
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			System.out.println("Se crea la red social a partir de ficheros");
			r1.anadirUsuarioInteresado("Interesado", 15);
			r1.anadirUsuario("Pepito", 20);
			System.out.println("Se crean los usuarios Pepito (Usuario) e Interesado (UsuarioInteresado)");
			r1.anadirEnlaceSenuelo("Interesado", "Pepito", 5, 2, 0);
			r1.anadirEnlace("Interesado", "luis", 10);
			r1.anadirEnlace("Pepito", "luis", 10);
			r1.anadirMensajeControlado("Buenos dias a todos!", "Interesado", 100, "luis", "mario");

		} catch(IOException e) {
			System.out.println("Error con los ficheros a la hora de leer/escribir en testCompatibilidadLecturaEscritura1");
		} catch(IllegalArgumentException e) {
			System.out.println("Error en el formato de los ficheros en testCompatibilidadLecturaEscritura1");
		}
	}
}
