package redSocial;

import java.io.IOException;

public class EjemploDeUsoRedSocial {

	public static void main(String[] args) {
		System.out.println("Entraaaa");
		try {
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
		} catch(IOException e) {
			
		}
		/*try {
			RedSocial r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			RedSocial r2 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje2.txt");
		} catch (IOException e) {
			System.out.println("Error en archivos");
		}*/
		
	}

}
