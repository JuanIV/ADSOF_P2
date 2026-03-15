package tests;

import java.io.IOException;

import redSocial.RedSocial;

public class EjemploDeUsoRedSocial {

	public static void main(String[] args) {
		try {
			RedSocial s;
			s = new RedSocial("txt/usuarios1.txt", "txt/enlaces1.txt", "txt/mensaje1.txt");
			s = new RedSocial("txt/usuarios1.txt", "txt/enlaces1.txt", "txt/mensajeblablabla.txt");
			
			
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error en archivos");
		}
	}

}
