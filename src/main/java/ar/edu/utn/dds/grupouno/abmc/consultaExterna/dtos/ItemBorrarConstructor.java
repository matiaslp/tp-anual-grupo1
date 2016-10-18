package ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

import ar.edu.utn.dds.grupouno.db.poi.Item_Borrar;


@SuppressWarnings("rawtypes")
public class ItemBorrarConstructor implements InstanceCreator {

	public Item_Borrar createInstance(Type type) {
		return new Item_Borrar();
	}
}
