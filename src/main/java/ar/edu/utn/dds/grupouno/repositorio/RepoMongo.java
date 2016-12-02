package ar.edu.utn.dds.grupouno.repositorio;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class RepoMongo {
	
	private static RepoMongo instance = null;
	private final Datastore datastore;
	
	public static RepoMongo getInstance(){
		if(instance == null){
			instance = new RepoMongo();
		}
		return instance;
	}
	
	private RepoMongo(){
		final Morphia morphia = new Morphia();
		
		morphia.mapPackage("ar.edu.utn.dds.grupouno");
		
		this.datastore = morphia.createDatastore(new MongoClient(), "puntosdeinteres");
		datastore.ensureIndexes();
	}
	
	public Datastore getDatastore(){
		return this.datastore;
	}
}
