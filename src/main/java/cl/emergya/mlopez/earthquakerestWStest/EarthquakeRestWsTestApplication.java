package cl.emergya.mlopez.earthquakerestWStest;

import cl.emergya.mlopez.earthquakerestWStest.db.DAOH2Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.*;

@SpringBootApplication
public class EarthquakeRestWsTestApplication
{

	public static void main(String[] args)
	{
        Logger log = Logger.getLogger(EarthquakeRestWsTestApplication.class);
        BasicConfigurator.configure();

        DAOH2Utils.createDBSchema();

		SpringApplication.run(EarthquakeRestWsTestApplication.class, args);
	}

}
