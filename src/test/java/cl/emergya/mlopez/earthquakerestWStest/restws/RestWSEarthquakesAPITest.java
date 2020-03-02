package cl.emergya.mlopez.earthquakerestWStest.restws;

import cl.emergya.mlopez.earthquakerestWStest.wstools.WSTools;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import static org.junit.Assert.*;

public class RestWSEarthquakesAPITest {

    @Test
    public void testRestAPI()
    {
        // 1.- Probar si los servicios existen y responden correctamente (sin parametrización).
        assertNotEquals(200, WSTools.getRestWSResponseCode("http://localhost:9090/earthquakes/getEarthquakesByDate"));
        assertNotEquals(200, WSTools.getRestWSResponseCode("http://localhost:9090/earthquakes/getEarthquakesByMagnitude"));
        assertNotEquals(200, WSTools.postRestWSResponseCode("http://localhost:9090/earthquakes/storeTodayEarthquakesInH2", ""));

        // 2.- Probar si los servicios existen y responden correctamente (con parametrización correcta).
        /*assertEquals(200, WSTools.postRestWSResponseCode("http://localhost:9090/earthquakes/getEarthquakesByDate",
             "{\"starttime\":\"2020-02-01\",\"endtime\":\"2020-02-02\"}"));
        */

        /*assertEquals(200, WSTools.postRestWSResponseCode("http://localhost:9090/earthquakes/getEarthquakesByMagnitude",
                "{\"minMagnitude\":\"2.0\",\"maxMagnitude\":\"3.5\"}"));
        */

        assertEquals(200, WSTools.getRestWSResponseCode("http://localhost:9090/earthquakes/storeTodayEarthquakes"));

    }
}