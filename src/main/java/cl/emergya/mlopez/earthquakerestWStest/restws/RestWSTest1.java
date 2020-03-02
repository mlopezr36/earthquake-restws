package cl.emergya.mlopez.earthquakerestWStest.restws;

import cl.emergya.mlopez.earthquakerestWStest.wstools.WSTools;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/example")
public class RestWSTest1
{
    @GetMapping("/hello-world1")
    public ResponseEntity<String> get1() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/hello-world2")
    public ResponseEntity<String> get2()
    {
        JSONObject json = new JSONObject();
        json.put("edad", 30);
        json.append("edadArr", 40);
        json.append("edadArr", 50);

        String response = WSTools.getRestWSResponse("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02");

        JSONObject jsonResponse = new JSONObject(response);
        return ResponseEntity.ok(jsonResponse.toString(5));
    }

    @GetMapping("/hello-world3")
    public ResponseEntity<String> get3() {
        return ResponseEntity.ok("Hello World!");
    }
}
