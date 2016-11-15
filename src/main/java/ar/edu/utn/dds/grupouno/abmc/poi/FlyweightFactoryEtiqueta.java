package ar.edu.utn.dds.grupouno.abmc.poi;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.dds.grupouno.repositorio.Repositorio;

class FlyweightFactoryEtiqueta {
	IFlyweightEtiqueta etiqueta;

	private static ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

	private static Etiqueta obtenerEtiqueta(String nombre) {
		for (Etiqueta etiqueta : etiquetas) {
			if (etiqueta.getNombre().equals(nombre))
				return etiqueta;
		}
		return null;

	}

	public static Etiqueta getEtiqueta(String nombre) {
		Etiqueta etiqueta = obtenerEtiqueta(nombre);
		if (etiqueta == null) {
			List<Etiqueta> etiquetas = Repositorio.getInstance().etiquetas().getEtiquetabyNombre(nombre);
			if (etiquetas.size() == 0) {
				etiqueta = new Etiqueta(nombre);
				etiquetas.add(etiqueta);
				Repositorio.getInstance().etiquetas().agregarEtiqueta(etiqueta);
			}
		}
		return etiqueta;
	}

	FlyweightFactoryEtiqueta() {
		etiquetas = Repositorio.getInstance().etiquetas().getListado();

	}

}
