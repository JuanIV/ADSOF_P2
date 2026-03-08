package tests;

import redSocial.*;

/**
 * Clase para comprobar el funcionamiento de los enlaces
 * 
 * Autores: Juan Ibáñez y Tiago Oselka
 * Version: 1.0
 */
public class TestsEnlaces {

    public static void main(String[] args) {

        System.out.println("===== TESTS ENLACE =====");

        Usuario u1 = new Usuario("Ana");
        Usuario u2 = new Usuario("Luis");

        // Constructor con coste válido
        Enlace e1 = new Enlace(u1, u2, 10);
        System.out.println("Coste correcto: " + (e1.getCoste() == 10));

        // Constructor con coste inválido (<=0)
        Enlace e2 = new Enlace(u1, u2, -5);
        System.out.println("Coste mínimo aplicado: " + (e2.getCoste() == 1));

        // Getter origen/destino
        System.out.println("Origen correcto: " + (e1.getUsuarioOrigen() == u1));
        System.out.println("Destino correcto: " + (e1.getUsuarioDestino() == u2));

        // cambiarDestino
        Usuario u3 = new Usuario("Carmen");
        e1.cambiarDestino(u3, 7);
        System.out.println("Destino cambiado: " + (e1.getUsuarioDestino() == u3));
        System.out.println("Coste cambiado: " + (e1.getCoste() == 7));

        // costeReal (sin especial)
        System.out.println("Coste real correcto: " + (e1.costeReal() == e1.getCoste()));

        // sumaCostes estática
        System.out.println("Suma de costes total: " + Enlace.getSumaCostes());

        System.out.println("toString(): " + e1);
    }
}