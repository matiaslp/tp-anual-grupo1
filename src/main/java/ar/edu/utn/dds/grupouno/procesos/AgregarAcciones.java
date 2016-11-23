package ar.edu.utn.dds.grupouno.procesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import ar.edu.utn.dds.grupouno.autentification.AuthAPI;
import ar.edu.utn.dds.grupouno.autentification.Usuario;
import ar.edu.utn.dds.grupouno.db.AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_AgregarAccionesTransaction;
import ar.edu.utn.dds.grupouno.db.DB_Usuarios;
import ar.edu.utn.dds.grupouno.db.Resultado;
import ar.edu.utn.dds.grupouno.db.ResultadoProceso;
import ar.edu.utn.dds.grupouno.db.repositorio.Repositorio;
import ar.edu.utn.dds.grupouno.quartz.Proceso;

public class AgregarAcciones extends Proceso {

	public AgregarAcciones() {

	}
	
	@Override
	public void execute(JobExecutionContext contexto) throws JobExecutionException {
		
		JobDataMap dataMap = contexto.getJobDetail().getJobDataMap();
		String filePath = dataMap.getString("filePath");
		
		// Formato de archivo: unUsuario nomAccion nomAccion nomAccion
		String linea;
		String palabras[];
		String unUsername;
		String usuariosInexistentes = null;
		String accionesInexistentes = null;
		ArrayList<String> listadoAcciones = new ArrayList<String>();
		FileReader fr = null;
		SchedulerContext schedulerContext = null;
		Usuario usuario = null;
		ResultadoProceso resultado = null;
		
		try {
			//Traigo el contexto
			schedulerContext = contexto.getScheduler().getContext();
			
			// Obtengo del contexto el usuario y el resultado de proceso.
			usuario = (Usuario)schedulerContext.get("Usuario");
			resultado = (ResultadoProceso)schedulerContext.get("ResultadoProceso");
						
			resultado.setUserID(usuario.getId());
			resultado.setProc(TiposProceso.AGREGARACIONES);
			
		} catch (SchedulerException excepcion) {
			excepcion.printStackTrace();
        }
		
		// Creamos la Transaccion
		AgregarAccionesTransaction transaction = new AgregarAccionesTransaction(usuario.getId());
		
		// REVISA SI EXISTE O NO Y SI SE PUEDE LEER O NO
		try {
			if ((fr = new FileReader(filePath)) != null) {
				BufferedReader br = new BufferedReader(fr);
				
				// lee linea a linea
				while ((linea = br.readLine()) != null) {
					palabras = linea.split(" ");
					unUsername = palabras[0];
					
					if (Repositorio.getInstance().usuarios().getUsuarioByName(unUsername) == null){
						usuariosInexistentes = usuariosInexistentes + unUsername;
					}
						
					// arma la lista de acciones para un usuario
					for (int i = 1; i < palabras.length; i++) {
						if (AuthAPI.getInstance().getAccion(palabras[i]) == null){
							accionesInexistentes = accionesInexistentes + palabras[i];
						}
							
						listadoAcciones.add(palabras[i]);
					}

					AgregarAcciones.AgregarAccionesAUsuario(unUsername, listadoAcciones, transaction);

				}
				// Se agrega la transaccion a DB_AgregarAccionesTransaction
				DB_AgregarAccionesTransaction.getInstance().agregarTransactions(transaction);
				br.close();
			}
			
			
		} catch (FileNotFoundException e) {
			// Si el archivo no es encontrado
			// Armamos el Resultado del proceso que es guardado en DB_ResultadosProcesos
			String mensaje = "Usuarios inexistentes: " + usuariosInexistentes + "\n" +
					"Acciones inexistentes: " + accionesInexistentes + "\n";
			resultado.setResultado(Resultado.ERROR);
			resultado.setMensajeError("FileNotFoundException:No existe archivo " + filePath + "\n" + mensaje);

			e.printStackTrace();
			
			
			
		} catch (IOException e) {
			// Si el archivo no se puede leer (permisos)
			// Armamos el Resultado del proceso que es guardado en DB_ResultadosProcesos
			String mensaje = "Usuarios inexistentes: " + usuariosInexistentes + "\n" +
					"Acciones inexistentes: " + accionesInexistentes + "\n";
			resultado.setResultado(Resultado.ERROR);
			resultado.setMensajeError("IOException:No se puede leer archivo " + filePath + "\n" + mensaje);
				
			e.printStackTrace();
		}
			
		// Ejecucion exitosa
		// Armamos el Resultado del proceso que es guardado en el contexto.
		if (usuariosInexistentes == null || accionesInexistentes == null){
			resultado.setResultado(Resultado.OK);
		} else {
			String mensaje = "Usuarios inexistentes: " + usuariosInexistentes + "\n" +
					"Acciones inexistentes: " + accionesInexistentes + "\n";
			resultado.setResultado(Resultado.ERROR);
			resultado.setMensajeError(mensaje);
		}
		
		schedulerContext.replace("ResultadoProceso", resultado);
		schedulerContext.replace("ejecutado", true);
	}
	
	private static boolean AgregarAccionesAUsuario(String unUsername, ArrayList<String> listadoAcciones,
			AgregarAccionesTransaction transaction) {
		String transac = null;
		Usuario unUsuario;
		DB_Usuarios db_usuario = Repositorio.getInstance().usuarios();

		if ((unUsuario = db_usuario.getUsuarioByName(unUsername)) != null) {
			transac = unUsername;
			for (String unaAccion : listadoAcciones) {
				if ((AuthAPI.getInstance().agregarFuncionalidad(unaAccion, unUsuario)) != false){
					// Agregar accion a transaccion
					transac = transac + " " + unaAccion;
				}
			}
			// Agregamos a la transaccion los cambios realizados a este usuario
			transaction.agregarCambios(transac);
			return true;
		} else {
			return false;
		}

	}

	
}
