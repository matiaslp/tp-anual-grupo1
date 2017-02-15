package ar.edu.utn.dds.grupouno.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LeerProperties {

	public Properties prop;
	private static LeerProperties instance = null;
	InputStream input;

	public LeerProperties() {
		prop = new Properties();

		try (InputStream input = new FileInputStream("WebContent/WEB-INF/config.properties")) {
			// carga archivo de propiedades si esta corriendo unit tests
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
			File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
			System.out.printf("catalina.base : %s \n", System.getProperty("catalina.base"));
			File propertyFile = new File(catalinaBase, "wtpwebapps/tp-anual/WEB-INF/config.properties");
			try (InputStream input = new FileInputStream(propertyFile)) {

				// carga archivo de propiedades si esta deployado en tomcat
				prop.load(input);

			} catch (IOException ex2) {
				ex2.printStackTrace();
			}

		}
	}

	public static LeerProperties getInstance() {
		if (instance == null)
			instance = new LeerProperties();
		return instance;
	}
}
