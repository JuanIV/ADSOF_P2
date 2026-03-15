package tests;

import java.io.IOException;

import redSocial.RedSocial;

public class TestsFuncionalidadesGenerales6 {

	public static void main(String[] args) {
		testDifusionMensajeControlado();

	}

	static void testDifusionMensajeControlado() {
		try {
			System.out.println("===== TEST DIFUSION MENSAJE CONTROLADO =====");
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			r1.anadirUsuarioInteresado("Interesado", 15);
			r1.anadirUsuario("Aislado", 1);
			r1.anadirUsuario("Pepito", 20);
			r1.anadirEnlace("Interesado", "Pepito", 1);
			r1.anadirEnlace("Interesado", "luis", 10);
			r1.anadirEnlace("Pepito", "luis", 10);
			r1.anadirEnlace("Aislado", "Pepito", 1);
			r1.anadirEnlaceSenuelo("luis", "Aislado", 1, 1, 0);
			r1.cambiarExposicionUsuario("Pepito", "VIRAL");
			System.out.println(r1);
			r1.anadirMensajeControlado("Buenos dias a todos!", "Interesado", 40, "luis", "Aislado");

		} catch(IOException e) {
			System.out.println("Error con los ficheros a la hora de leer/escribir en testCompatibilidadLecturaEscritura1");
		} catch(IllegalArgumentException e) {
			System.out.println("Error en el formato de los ficheros en testCompatibilidadLecturaEscritura1");
		}
	}
}
