package procesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import antlr.collections.List;
import autentification.Accion;
import autentification.AuthAPI;
import autentification.Rol;
import autentification.Usuario;
import db.DB_Usuarios;
import db.ResultadoProceso;
import procesos.Proceso;

public class AgregarAcciones extends Proceso {

	String filePath;

	@Override
	public void execute() {
		DateTime start = new DateTime();
		// tu codigo
		// archivo esta de esta forma
				// unUsuario nomAccion nomAccion nomAccion
				String linea;
				String palabras[];
				String unUsername;
				String listaAcciones[] = null;
				FileReader fr=null;
		//REVISA SI EXISTE O NO Y SI SE PUEDE LEER O NO
				try {

					
					
					if ((fr = new FileReader(filePath))!=null){
					
					BufferedReader br = new BufferedReader(fr);
					// lee linea a linea
					while ((linea = br.readLine()) != null) {
						System.out.println(linea);

						palabras = linea.split(" ");
						unUsername = palabras[0];

						// arma la lista de acciones para un usuario
						for (int i = 1; i <= palabras.length; i++) {
							listaAcciones[i - 1] = palabras[i];
						}

						AgregarAcciones.AgregarAccionesAUsuario(unUsername, listaAcciones);
					}
					br.close();
					}
					
					DateTime end = new DateTime();
					
					
				} catch (FileNotFoundException e) {
					DateTime end = new DateTime();
					//ResultadoProceso resultado = new ResultadoProceso(0,start,end,this,userID,"FileNotFoundException:No existe archivo "+filePath, resultado.ERROR);
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					DateTime end = new DateTime();
					//ResultadoProceso resultado = new ResultadoProceso(0,start,end,this,userID,"IOException:No se puede leer archivo "+filePath, resultado.ERROR);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
		
		//ResultadoProceso resultado = new ResultadoProceso(0,start,end,this,userID,"tumensajede error", resultado.OK)
		
		
		
		
		
		// TODO Auto-generated method stub

		// lee prmer renglon
		// for
		// agrega funcionabilidad con metodo de auth api
		// lee siguiente renglon
	}

/*<<<<<<< HEAD
	public AgregarAcciones(int cantidadReintentos, boolean enviarEmail, boolean disableAccion, String filePath) throws IOException {
		super(cantidadReintentos, enviarEmail, disableAccion);
=======
	/public AgregarAcciones(int cantidadReintentos, boolean enviarEmail, boolean disableAccion, String filePath,
			Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
>>>>>>> 3a579bc2d0aaaa48de54bc2e64cc5fbae2f0b3fd
		this.filePath = filePath;
		

	}*/

	// REVISAR
	public static boolean AgregarAccionesAUsuario(String unUsername, ArrayList<String> listadoAcciones) {
		boolean agregoAccion = false;
		Usuario unUsuario;
		DB_Usuarios db_usuario = DB_Usuarios.getInstance();
		AuthAPI authapi=AuthAPI.getInstance();
		
		if (db_usuario.buscarUsuarioEnLista(unUsername)) {
			//
			System.out.println("AgregarAccionesAUsuario(String unUsername, ArrayList<String> listadoAcciones) lo encontro en lista");
			unUsuario = db_usuario.consegirUsuarioDeLista(unUsername);
			for (String unaAccion : listadoAcciones) {

				agregoAccion = authapi.agregarFuncionalidad(unaAccion, unUsuario);
				if (agregoAccion == false) {
					return false;
				}
			}
			return true;
		} else {
			System.out.println("AgregarAccionesAUsuario(String unUsername, String[] listaAcciones) no lo encontro en lista");
			return false;
		}

		
		
	}

	private boolean validarAccionUsuarioTerminal(Accion accion) {
		return true;
	}

	private boolean validarAccionUsuarioAdministrador(Accion accion) {
		return true;
	}

}
