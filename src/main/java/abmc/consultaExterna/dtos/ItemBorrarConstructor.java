package abmc.consultaExterna.dtos;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

import poi.Item_Borrar;


@SuppressWarnings("rawtypes")
public class ItemBorrarConstructor implements InstanceCreator {

	public Item_Borrar createInstance(Type type) {
		return new Item_Borrar();
	}
}
