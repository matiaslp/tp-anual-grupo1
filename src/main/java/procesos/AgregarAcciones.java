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
import db.AgregarAccionesTransaction;
import db.DB_AgregarAccionesTransaction;
import db.DB_ResultadosProcesos;
import db.DB_Usuarios;
import db.Resultado;
import db.ResultadoProceso;
import procesos.Proceso;

public class AgregarAcciones extends Proceso {

	public AgregarAcciones(int cantidadReintentos, boolean enviarEmail, boolean disableAccion, Usuario unUser) {
		super(cantidadReintentos, enviarEmail, disableAccion, unUser);
		// TODO Auto-generated constructor stub
	}

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
				ArrayList<String> listadoAcciones = new ArrayList<String>();
				FileReader fr=null;
				// Creo la Transaccion
				AgregarAccionesTransaction Transaction  = new AgregarAccionesTransaction(user.getID());
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
							listadoAcciones.add(palabras[i]);
						}

						AgregarAcciones.AgregarAccionesAUsuario(unUsername, listadoAcciones, Transaction);

					}
					DB_AgregarAccionesTransaction.getInstance().agregarTransactions(Transaction);
					br.close();
					}
					
					DateTime end = new DateTime();
					
					
				} catch (FileNotFoundException e) {
					DateTime end = new DateTime();

					ResultadoProceso resultado = new ResultadoProceso(0,start,end,this,user.getID(),"FileNotFoundException:No existe archivo "+filePath, Resultado.ERROR);
					DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);
					e.printStackTrace();
				} catch (IOException e) {
					DateTime end = new DateTime();
					ResultadoProceso resultado = new ResultadoProceso(0,start,end,this,user.getID(),"IOException:No se puede leer archivo "+filePath, Resultado.ERROR);
					DB_ResultadosProcesos.getInstance().agregarResultadoProceso(resultado);

					e.printStackTrace();
				}
				
		
		
		//ResultadoProceso resultado = new ResultadoProceso(0,start,end,this,userID,"tumensajede error", resultado.OK)
		
		
		
		
		
		// TODO Auto-generated method stub

		// lee prmer renglon
		// for
		// agrega funcionabilidad con metodo de auth api
		// lee siguiente renglon
	}



	// REVISAR
	private static boolean AgregarAccionesAUsuario(String unUsername, ArrayList<String> listadoAcciones, AgregarAccionesTransaction transaction) {
		boolean agregoAccion = false;
		String transac = null;
		Usuario unUsuario;
		DB_Usuarios db_usuario = DB_Usuarios.getInstance();
		AuthAPI authapi=AuthAPI.getInstance();
		
		if (db_usuario.buscarUsuarioEnLista(unUsername)) {
			//
			System.out.println("AgregarAccionesAUsuario(String unUsername, ArrayList<String> listadoAcciones) lo encontro en lista");
			transac = transac + unUsername;
			unUsuario = db_usuario.getUusarioByName(unUsername);
			for (String unaAccion : listadoAcciones) {

				agregoAccion = authapi.agregarFuncionalidad(unaAccion, unUsuario);
				if (agregoAccion == false) {
					return false;
				}
				else {
					// Agregar accion a transaccion
					transac = transac + unaAccion;
				}
			}
			transaction.agregarCambios(transac);
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
	
	
	public void undo() {
		
		AgregarAccionesTransaction transaction = DB_AgregarAccionesTransaction.getInstance().getLastTransactionByUser(user.getID());
		// De aca en mas es muy similar al execute() pero en vez de agregar, se eliminan las funcionalidades
		
		
	}

}
