package ar.edu.utn.dds.grupouno.abmc;

import java.util.ArrayList;
import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.db.DB_POI;
import ar.edu.utn.dds.grupouno.db.poi.POI;

import java.io.IOException;
import java.net.MalformedURLException;

// Esta clase funciona como Facade para ocultar el subsistema de
// busqueda/consulta y sus multiples clases de las que se podrian
// hacer consultas Algunas con timer y registrando en el 
// historial otras sin...

public class POI_ABMC implements Busqueda {

	private static POI_ABMC instance = null;

	public static POI_ABMC getInstance() {
		if (instance == null)
			instance = new POI_ABMC();
		return instance;
	}

	public boolean alta(POI_DTO dto) {

		POI nuevoPOI = dto.converttoPOI();
		if (nuevoPOI.equals(null)) {
			return false;
		} else {
			DB_POI.getInstance().agregarPOI(nuevoPOI);
			return true;
		}
	}

	public boolean delete(int ID) {
		POI poi = DB_POI.getInstance().getPOIbyId(ID);
		//Si existe el poi y no tiene una fecha de baja
		if (poi != null && poi.getFechaBaja() == null) {
			DateTime now = new DateTime();
			poi.setFechaBaja(now);
			return true;
		} else
			return false;
	}

	public boolean modificar(POI_DTO dto) {
		POI poi = null;
		poi = DB_POI.getInstance().getPOIbyId(dto.getId());
		if (poi != null) {
			poi.setDatos(dto);
			return true;
		}
		return false;
	}

	// Busqueda por texto libre ABIERTA (busca todos los pois que contengan al
	// menos UNA palabra contenida en la busqueda)
	public ArrayList<POI> buscar(String url, String texto, long userID)
			throws JSONException, MalformedURLException, IOException, MessagingException {

		return Historico.getInstance().buscar(url, texto, userID);
	}
}
