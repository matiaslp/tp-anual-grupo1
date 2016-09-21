package abmc;

import java.util.ArrayList;
import javax.mail.MessagingException;
import org.json.JSONException;
import abmc.consulta.Busqueda;
import abmc.consulta.Historico;

import java.io.IOException;
import java.net.MalformedURLException;
import db.DB_POI;
import poi.POI;

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
			DB_POI.agregarPOI(nuevoPOI);
			return true;
		}
	}

	public boolean delete(int ID) {
		if (DB_POI.getPOIbyId(ID) != null) {
			return DB_POI.eliminarPOI(ID);
		} else
			return false;
	}

	public boolean modificar(POI_DTO dto) {
		POI poi = null;
		poi = DB_POI.getPOIbyId(dto.getId());
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
