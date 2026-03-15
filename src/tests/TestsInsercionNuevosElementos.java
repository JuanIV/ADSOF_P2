package tests;

import java.io.IOException;

import redSocial.RedSocial;

public class TestsInsercionNuevosElementos {
	public static void main(String[] args) {
		testInsercionNuevos();
		testInsercionParametrosInvalidos();
		
	}
	
	static void testInsercionNuevos() {
		RedSocial r1;
		try {
			r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
			
			System.out.println("===== TEST INSERCIÓN DE NUEVOS ELEMENTOS =====");
			r1.anadirUsuario("Tiago", 15);
			r1.anadirUsuarioInteresado("Juan", 15);
			r1.anadirEnlace("Tiago", "Juan", 10);
			r1.anadirEnlaceSenuelo("Juan", "Tiago", 2, 6, 0.5);
			r1.anadirMensajeControlado("¿Qué tal estás Juan?", "Tiago", 100, "Juan");
			r1.anadirMensaje("Muy bien Tiago", "Juan", "Tiago");
			
			
		} catch (IllegalArgumentException | IOException e) {
			System.out.println(e);
		}
	}
	
	static void testInsercionParametrosInvalidos() {
		RedSocial r1;
		try {
			r1 = new RedSocial("usuarios1.txt", "enlaces1.txt", "mensaje1.txt");
		
			System.out.println("===== TEST INSERCIÓN DE ELEMENTOS INVÁLIDOS =====");
			System.out.println("Intenta añadir usuario con nombre ya registrado:");
			try {
				r1.anadirUsuario("luis", 10);
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
			
			System.out.println("Intenta insertar un enlace ya existente:");
			try {
				r1.anadirEnlace("luis", "mario", 10);
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
			
			System.out.println("Intenta cambiar un enlace no existente:");
			try {
				System.out.println(r1.cambiarEnlace("luis", "carmen", "carmen", 10));
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
			
			System.out.println("Intenta crear un mensaje vacío:");
			try {
				r1.anadirMensaje("", "luis");
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			}
			
			System.out.println("Intenta crear un mensaje a partir de un usuario inexistente:");
			try {
				r1.anadirMensaje("Hola!", "Ignacio");
			} catch (IllegalArgumentException e) {
				System.out.println(e);
			} 
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error en archivos");
		}
	}
}
