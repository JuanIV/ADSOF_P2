package tests;

import redSocial.*;

/**
 * Clase para comprobar el funcionamiento de los usuarios
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class TestsUsuarios {

    public static void main(String[] args) {

        System.out.println("===== TESTS USUARIO =====");

        Usuario u1 = new Usuario("Juan", 3);
        Usuario u2 = new Usuario("Maria");

        // Getters
        System.out.println("Nombre correcto: " + u1.getNombre().equals("Juan"));
        System.out.println("Capacidad correcta: " + (u1.getCapacidadAmp() == 3));

        // Añadir enlace válido
        boolean añadido = u1.addEnlace(u2, 5);
        System.out.println("Enlace añadido correctamente: " + añadido);

        // Número de enlaces
        System.out.println("Num enlaces correcto: " + (u1.getNumEnlaces() == 1));

        // Obtener enlace por índice
        Enlace e = u1.getEnlace(0);
        System.out.println("Enlace correcto por índice: " + (e.getUsuarioDestino() == u2));

        // Obtener enlace por destino
        Enlace e2 = u1.getEnlace(u2);
        System.out.println("Enlace correcto por destino: " + (e2 != null));

        // Intentar añadir enlace duplicado
        boolean duplicado = u1.addEnlace(u2, 10);
        System.out.println("No permite duplicados: " + (!duplicado));

        // Intentar auto-enlace
        boolean auto = u1.addEnlace(u1, 5);
        System.out.println("No permite auto-enlace: " + (!auto));

        System.out.println("toString(): " + u1);
    }
}