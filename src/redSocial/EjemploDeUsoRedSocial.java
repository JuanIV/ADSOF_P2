package redSocial;

import java.io.IOException;

public class EjemploDeUsoRedSocial {

	public static void main(String[] args) {
		try {
			RedSocial s;
			s = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			s = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensajeblablabla.txt");
			
			
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error en archivos");
		}
	}

}
