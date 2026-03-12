package redSocial.usuario;

import java.util.*;

import redSocial.Exposicion;
import redSocial.enlace.Enlace;

public class UsuarioInteresado extends Usuario{

	public UsuarioInteresado(String nombre, int capacidadAmp, Exposicion exposicion, double alcancePromedio) {
		super(nombre, capacidadAmp, exposicion, alcancePromedio);
	}
	
	public UsuarioInteresado(String nombre, int capacidadAmp, Exposicion exposicion) {
		super(nombre, capacidadAmp, exposicion);
	}
	
	public UsuarioInteresado(String nombre, int capacidadAmp) {
		super(nombre, capacidadAmp);
	}	
	
	public Enlace getEnlacePopular() {
		for (Enlace e: enlacesOrdenados) {
			if (e.getUsuarioDestino().getNivelExposicion().compareTo(Exposicion.ALTA) >= 0) {
				return e;
			}
		}
		
		return null;
	}

}
