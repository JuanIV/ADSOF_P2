package tests;

import java.io.IOException;

import redSocial.RedSocial;

public class TestsCompatibilidadYDifusion {
	public static void main(String[] args) {
		testCompatibilidadLecturaEscritura();
		testCompatibilidadYDifusionMensaje();
		
	}
	
	static void testCompatibilidadLecturaEscritura() {
		try {
			System.out.println("===== TEST COMPATIBILIDAD ESCRITURA-LECTURA=====");
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			System.out.println("RedSocial r1:\n"+r1);
			r1.guardarRedSocial("usuarios1_2.txt", "enlaces1_2.txt", "mensaje1_2.txt");
			RedSocial r3 = new RedSocial("usuarios1_2.txt", "enlaces1_2.txt", "mensaje1_2.txt", null);
			System.out.println("RedSocial r3:\n"+r3);
			System.out.println("Contenido igual: "+r1.toString().equals(r3.toString()));
		} catch(IOException e) {
			System.out.println("Error con los ficheros a la hora de leer/escribir en testCompatibilidadLecturaEscritura1");
		} catch(IllegalArgumentException e) {
			System.out.println("Error en el formato de los ficheros en testCompatibilidadLecturaEscritura1");
		}
	}

	static void testCompatibilidadYDifusionMensaje() {
		try {
			System.out.println("===== TEST COMPATIBILIDAD ESCRITURA-LECTURA 2 =====");			
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			r1.anadirUsuarioInteresado("Interesado", 15);
			r1.anadirUsuario("Pepito", 20);
			r1.anadirEnlaceSenuelo("Interesado", "Pepito", 5, 2, 1);
			r1.anadirEnlace("Interesado", "luis", 10);
			r1.anadirEnlace("Pepito", "luis", 10);
			r1.anadirMensaje("Buenos dias a todos!", "Interesado", "luis", "mario");
			r1.guardarRedSocial("usuarios3.txt", "enlaces3.txt", "mensaje3.txt");
			System.out.println(r1);
			RedSocial r3 = new RedSocial("usuarios3.txt", "enlaces3.txt", "mensaje3.txt", null);
			System.out.println(r3);
			System.out.println("Contenido igual: "+r1.toString().equals(r3.toString()));
			
		} catch(IOException e) {
			System.out.println("Error con los ficheros a la hora de leer/escribir en testCompatibilidadLecturaEscritura1");
		} catch(IllegalArgumentException e) {
			System.out.println("Error en el formato de los ficheros en testCompatibilidadLecturaEscritura1");
		}
	}
}
