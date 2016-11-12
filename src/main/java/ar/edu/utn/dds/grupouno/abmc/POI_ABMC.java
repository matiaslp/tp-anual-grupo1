package ar.edu.utn.dds.grupouno.abmc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.json.JSONException;

import ar.edu.utn.dds.grupouno.abmc.consultaExterna.dtos.POI_DTO;
import ar.edu.utn.dds.grupouno.db.poi.POI;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;

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
	@Transactional
	public boolean alta(POI_DTO dto) {

		POI nuevoPOI = dto.converttoPOI();
		if (nuevoPOI.equals(null)) {
			return false;
		} else {
			Repositorio.getInstance().pois().agregarPOI(nuevoPOI);
			return true;
		}
	}
	@Transactional
	public boolean delete(long l) {
		Repositorio.getInstance().getEm().getTransaction().begin();
		POI poi = Repositorio.getInstance().pois().getPOIbyId(l);
		//Si existe el poi y no tiene una fecha de baja
		if (poi != null && poi.getFechaBaja() == null) {
			DateTime now = new DateTime();
			poi.setFechaBaja(now);
			Repositorio.getInstance().getEm().getTransaction().commit();
			return true;
		} else {
			Repositorio.getInstance().getEm().getTransaction().commit();
			return false;
		}
	}
	@Transactional
	public boolean modificar(POI poi) {
		if (poi == null) {
			return false;
		} else {
			return Repositorio.getInstance().pois().actualizarPOI(poi);
		}
	}

	// Busqueda por texto libre ABIERTA (busca todos los pois que contengan al
	// menos UNA palabra contenida en la busqueda)
	public ArrayList<POI> buscar(String url, String texto, long userID)
			throws JSONException, MalformedURLException, IOException, MessagingException {

		return Historico.getInstance().buscar(url, texto, userID);
	}
}
