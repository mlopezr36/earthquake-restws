package cl.emergya.mlopez.earthquakerestWStest.wstools;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WSTools
{
    public static String getRestWSResponse(String urlstr)
    {
        String response = "";

        try
        {
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200)
            {
                throw new RuntimeException("Excepción en HTTP GET : " + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String strLine;

            while ((strLine = br.readLine()) != null)
                response += strLine;

            conn.disconnect();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }

        //System.out.println("REST - respuesta = " + response);
        return response;
    }

    public static int getRestWSResponseCode(String urlstr)
    {
        String response = "";

        try
        {
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode =  conn.getResponseCode();
            conn.disconnect();

            return responseCode;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static int postRestWSResponseCode(String urlstr, String jsonRequest)
    {
        String response = "";

        try
        {
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try(OutputStream os = conn.getOutputStream())
            {
                byte[] input = jsonRequest.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            conn.disconnect();

            return responseCode;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }


}
