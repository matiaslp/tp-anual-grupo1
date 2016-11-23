package ar.edu.utn.dds.grupouno.db.poi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

public class FlyweightFactoryEtiqueta {
	static FlyweightFactoryEtiqueta instance;
	
	public static FlyweightFactoryEtiqueta getInstance() {
		if (instance == null) {
			instance = new FlyweightFactoryEtiqueta();
		}
		return instance;
	}
	

	private static ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

	
	private static Etiqueta obtenerEtiqueta(String nombre){
		for (Etiqueta etiqueta : etiquetas){
			if (etiqueta.getNombre().equals(nombre))
				return etiqueta;
		}
		return null;
		
	}
	
	public void refresh(){
		etiquetas = Repositorio.getInstance().etiquetas().getListado();
	}
	
	
	public static Etiqueta getEtiqueta(String nombre) {
		Etiqueta etiqueta = obtenerEtiqueta(nombre);
		if (etiqueta == null) {
			List<Etiqueta> etiquetasDB = Repositorio.getInstance().etiquetas().getEtiquetabyNombre(nombre);
			if (etiquetasDB.size() == 0){
				etiqueta = new Etiqueta(nombre);
				etiquetas.add(etiqueta);
				Repositorio.getInstance().etiquetas().agregarEtiqueta(etiqueta);
			}else {
				etiqueta = etiquetasDB.get(0);
		}}
		return etiqueta;
	}

	
	FlyweightFactoryEtiqueta(){
		etiquetas = Repositorio.getInstance().etiquetas().getListado();

	}
	
}
