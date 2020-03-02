package cl.emergya.mlopez.earthquakerestWStest.restws;

import cl.emergya.mlopez.earthquakerestWStest.db.DAOH2Utils;
import cl.emergya.mlopez.earthquakerestWStest.dto.DatePair;
import cl.emergya.mlopez.earthquakerestWStest.dto.MagnitudePair;
import cl.emergya.mlopez.earthquakerestWStest.wstools.WSTools;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/earthquakes/")
public class RestWSEarthquakesAPI
{
    @PostMapping("/getEarthquakesByDate")
    @ResponseBody
    public ResponseEntity<String> getEarthquakesByDate(@RequestBody String data)
    {
        JSONObject json = new JSONObject(data);
        System.out.println(json.toString(5));

        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?" +
                    "format=geojson&" +
                    "starttime=" + json.get("starttime").toString() + "&" +
                    "endtime=" + json.get("endtime").toString();

        String earthquakeData = WSTools.getRestWSResponse(url);
        return ResponseEntity.ok(earthquakeData);
    }

    @PostMapping("/getEarthquakesByMagnitude")
    @ResponseBody
    public ResponseEntity<String> getEarthquakesByMagnitude(@RequestBody String data)
    {
        JSONObject json = new JSONObject(data);
        System.out.println(json.toString(5));

        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?" +
                "format=geojson&" +
                "minmagnitude=" + json.get("minMagnitude").toString() + "&" +
                "maxmagnitude=" + json.get("maxMagnitude").toString();

        String earthquakeData = WSTools.getRestWSResponse(url);
        return ResponseEntity.ok(earthquakeData);
    }

    @GetMapping("/storeTodayEarthquakes")
    @ResponseBody
    public ResponseEntity<String> storeTodayEarthquakesInH2()
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = fmt.format(date);
        System.out.println(currentDate);

        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?" +
                "format=geojson&" +
                "starttime=" + currentDate + "T00:00:00&" +
                "endtime=" + currentDate + "T23:59:59";

        String earthquakeData = WSTools.getRestWSResponse(url);
        JSONObject jsonResponse = new JSONObject(earthquakeData);

        if(DAOH2Utils.insertEarthquakeData(jsonResponse))
            return ResponseEntity.ok("OK. Today data stored.");
        else
            return ResponseEntity.status(500).body("Error 500. Data storing failed.");
    }

    @GetMapping("/createDBTables")
    @ResponseBody
    public ResponseEntity<String> createDBTables()
    {
        if(DAOH2Utils.createDBSchema())
            return ResponseEntity.ok("OK. Tables created.");
        else
            return ResponseEntity.status(500).body("Error 500. Creation failed.");
    }


    @GetMapping("/dropDBTables")
    @ResponseBody
    public ResponseEntity<String> dropDBTables()
    {
        if(DAOH2Utils.dropDBTables())
            return ResponseEntity.ok("OK. Tables dropped.");
        else
            return ResponseEntity.status(500).body("Error 500. Drop failed.");
    }


    @GetMapping("/deleteDBData")
    @ResponseBody
    public ResponseEntity<String> deleteDBData()
    {
        if(DAOH2Utils.deleteDBData())
            return ResponseEntity.ok("OK. Data deleted.");
        else
            return ResponseEntity.status(500).body("Error 500. Delete failed.");
    }
}
