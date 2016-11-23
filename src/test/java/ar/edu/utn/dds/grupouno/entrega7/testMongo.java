package ar.edu.utn.dds.grupouno.entrega7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Projection;
import org.mongodb.morphia.query.Query;

import com.mongodb.client.model.Accumulators;

import static org.mongodb.morphia.aggregation.Group.sum;
import static org.mongodb.morphia.aggregation.Group.*;

import ar.edu.utn.dds.grupouno.abmc.RegistroHistorico;
import ar.edu.utn.dds.grupouno.repositorio.RepoMongo;
import ar.edu.utn.dds.grupouno.repositorio.cantBusquedas;

public class testMongo {

	@Test
	public void test() {

		// Query<RegistroHistorico> query =
		// RepoMongo.getInstance().getDatastore().createQuery(RegistroHistorico.class);
		// query.filter(arg0, arg1)

		Iterator<RegistroHistorico> algo = RepoMongo.getInstance().getDatastore()
				.createAggregation(RegistroHistorico.class).group("time", grouping("cantBusquedas", new Accumulator("$sum", 1)))
				.out("busqueda", RegistroHistorico.class);
		//
		// Iterator<RegistroHistorico> query1 =
		// RepoMongo.getInstance().getDatastore()
		// .createAggregation(RegistroHistorico.class)
		// .group("userID",grouping("cantResultados", sum("cantResultados")))
		// .out("cantUsuarios", RegistroHistorico.class);
		//
		// List<cantUsuarios> array =
		// RepoMongo.getInstance().getDatastore().createQuery(cantUsuarios.class).asList();
		// ArrayList<Object[]> lista = new ArrayList<Object[]>();
		// for(cantUsuarios registro : array){
		// Object[] objeto = new Object[2];
		// objeto[0] = (Object) registro.geUser_id();
		// objeto[1] = (Object) registro.getCantResultados();
		//
		// lista.add(objeto);
		//
		// }
		// RepoMongo.getInstance().getDatastore().delete(array);
		Assert.assertTrue(true);
		// while(query1.hasNext()) {
		// RegistroHistorico element = query1.next();
		// System.out.printf("",element.getUserID(),element.getCantResultados());
		// }

	}
}

// AggregationPipeline pipeline = RepoMongo.getInstance().getDatastore()
// .createAggregation(RegistroHistorico.class)
// .group("userID",grouping("cantResultados", sum("cantResultados")));
//
// Iterator<RegistroHistorico> query2 =
// pipeline.aggregate(RegistroHistorico.class);
//
// while(query2.hasNext()) {
// RegistroHistorico element = query2.next();
// System.out.printf("",element.getUserID(),element.getCantResultados());
// }

// Iterator<RegistroHistorico> query1 = RepoMongo.getInstance().getDatastore()
// .createAggregation(RegistroHistorico.class)
// .group("userID",grouping("", group("cantResultados",
// new Accumulator("$sum",
// new Accumulator("$add", asList("$amountFromTBInDouble",
// "$amountFromParentPNLInDouble"))
//
//
// while(query1.hasNext()) {
// RegistroHistorico element = query1.next();