package tests;

import redSocial.*;
import redSocial.mensaje.Mensaje;
import redSocial.usuario.Usuario;

/**
 * Clase para comprobar el funcionamiento de los mensajes
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class TestsMensajes {

    public static void main(String[] args) {

        System.out.println("===== TESTS MENSAJE =====");

        Usuario a = new Usuario("Ana", 2);
        Usuario b = new Usuario("Luis", 3);
        Usuario c = new Usuario("Carmen", 1);

        a.addEnlace(b, 5);
        b.addEnlace(c, 4);

        Mensaje m = new Mensaje("Hola", 20, a);

        // Getters
        System.out.println("Mensaje correcto: " + m.getMensaje().equals("Hola"));
        System.out.println("Alcance inicial correcto: " + (m.getAlcance() == 20));
        System.out.println("Lector inicial es autor: " + (m.getLector() == a));

        // Difusión válida
        boolean dif1 = m.difunde(a.getEnlace(b));
        System.out.println("Difusión válida: " + dif1);
        System.out.println("Nuevo lector es Luis: " + (m.getLector() == b));

        // Difusión en cadena válida
        boolean dif2 = m.difunde(c);
        System.out.println("Difusión en cadena válida: " + dif2);
        System.out.println("Nuevo lector es Carmen: " + (m.getLector() == c));

        // Difusión inválida (no hay enlace)
        boolean dif3 = m.difunde(a);
        System.out.println("Difusión inválida: " + (!dif3));

        System.out.println("toString(): " + m);
    }
}