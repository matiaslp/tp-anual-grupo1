package POI;

import java.util.ArrayList;
import java.util.Calendar;
import Geolocation.GeoLocation;

public class Banco extends POI {
	
	String sucursal;
	String gerente;
	public ArrayList<nodoServicio> Servicios = new ArrayList<nodoServicio>();	
	
	public void agregarServicio(String nombre, ArrayList<Integer> dias, int horaInicio, int horaFin) {
		nodoServicio nuevoNodo = new nodoServicio();
		nuevoNodo.nombre = nombre;
		nuevoNodo.listaDias = dias;
		nuevoNodo.horaInicio = horaInicio;
		nuevoNodo.horaFin = horaFin;
		Servicios.add(nuevoNodo);
	}



	public boolean disponible(String servicio){
		/* el cajero automatico siempre esta disponible
		 * y si no pongo nada pide que devuelva si el banco tiene algun servicio
		 * disponible. Como el cajero siempre está disponible entonces es true.
		*/
		if(servicio == "cajero" || servicio == ""){
			return true; 
		}else{
			//obtengo el nro del dia de la semana
			int dia = obtenerDia(); 
			if((Calendar.MONDAY <= dia) && (dia <=Calendar.FRIDAY)){
				dia = 0;
				//si es de lunes a viernes, comparo la hora
				return compararHora(); 
			}else{
				 //sino, falso.
				return false;
			}
		}
	}
	
	
	public int obtenerDia(){
		Calendar calendario = Calendar.getInstance();
		//obtengo el nro de dia de la semana
		//ej: lunes = 1, martes = 2, etc
		int dia = calendario.get(Calendar.DAY_OF_WEEK);
		return dia;
	}
	
	public boolean compararHora(){
		int horaInicio=10;
		int horaFin=15;
		Calendar calendario = Calendar.getInstance();
		//si la hora actual esta entre 10 y 15 es true 
		//leer HOUR_OF_DAY antes de tocar los operadores de comparacion (?
		if(horaInicio <= calendario.get(Calendar.HOUR_OF_DAY) && calendario.get(Calendar.HOUR_OF_DAY) < horaFin){
			return true; 
		}else{
			return false; //sino es false
		}
	}
	
	public Banco (String nombre, double latitud, double longitud){
		this.Ubicacion = GeoLocation.fromDegrees(latitud, longitud);
		this.setNombre(nombre);
	}

}
