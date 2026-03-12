package tests;
import redSocial.*;
import redSocial.enlace.Enlace;
import redSocial.mensaje.Mensaje;
import redSocial.usuario.Usuario;

/**
 * Clase para comprobar el funcionamiento del codigo implementado en los primeros 3 apartados
 * 
 * Autores: Profesores EPS
 * Version: 1.0
 */
public class EjemploUsoMensajesBasicos {

	public static void main(String[] args) {
		Usuario ana = new Usuario("ana", 1);
		Usuario luis = new Usuario("luis", 5);
		Usuario carmen = new Usuario("carmen");
		Mensaje m = new Mensaje("Hi!", 50, ana);
		ana.addEnlace(new Enlace(ana, luis, 68));
		ana.addEnlace(carmen, 33);
		System.out.println(m);
		m.difunde(luis, carmen);
		System.out.println(m);
		carmen.addEnlace(new Enlace(carmen, luis, 11));
		m.difunde(carmen.getEnlace(luis));
		System.out.println(m);
	}
}
