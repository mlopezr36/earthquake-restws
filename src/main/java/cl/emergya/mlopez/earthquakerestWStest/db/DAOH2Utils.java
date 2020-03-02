package cl.emergya.mlopez.earthquakerestWStest.db;

import java.sql.*;
import org.apache.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class DAOH2Utils
{
    public static boolean createDBSchema()
    {
        Logger log = Logger.getLogger(DAOH2Utils.class);
        Connection conn = null;

        try
        {
            conn = H2Connection.getConnection();
            Statement st    = conn.createStatement();

            // 1.- Se crea la tabla de BD 'Metadata'
            st.execute("create table if not exists Metadata (id integer identity primary key, url varchar(255), title varchar(255), status integer, " +
                    "api varchar(16), count integer, creationDate timestamp default current_timestamp)");

            // 2.- Se crea la tabla de BD 'Feature'
            st.execute("create table if not exists Feature (id integer identity primary key, idMetadata integer, featureType varchar(20), " +
                    "magnitude decimal(10,2), place varchar(255), time bigint," +
                    "updated bigint, tz integer, status varchar(40), tsunami integer, sig integer, net varchar(16), code varchar(20)," +
                    "magType varchar(10), propType varchar(40), title varchar(255), geomType varchar(40), coord0 decimal(18,10), " +
                    "coord1 decimal(18,10), coord2 decimal(18,10), featureId varchar(30), creationDate timestamp default current_timestamp)");

            // 3.- Se crea la tabla de BD 'Bbox'
            st.execute("create table if not exists Bbox (id integer identity primary key, idMetadata integer, val0 decimal(18,10), val1 decimal(18,10)," +
                    "val2 decimal(18,10), val3 decimal(18,10), val4 decimal(18,10), val5 decimal(18,10), creationDate timestamp default current_timestamp)");

            H2Connection.close();

            log.info("¡Tablas creadas en la BD!");
            return true;
        }
        catch(Exception e)
        {
            H2Connection.close();
            log.error("Excepción: " + e);
            return false;
        }
    }

    public static boolean dropDBTables()
    {
        Logger log = Logger.getLogger(DAOH2Utils.class);
        Connection conn = null;

        try
        {
            conn = H2Connection.getConnection();
            Statement st1   = conn.createStatement();
            Statement st2   = conn.createStatement();
            Statement st3   = conn.createStatement();

            // 1.- Se borran todas las tablas de BD.
            st1.execute("drop table if exists Feature");
            st2.execute("drop table if exists Bbox");
            st3.execute("drop table if exists Metadata");

            H2Connection.close();

            log.info("¡Tablas eliminadas de la BD!");
            return true;
        }
        catch(Exception e)
        {
            H2Connection.close();
            log.error("Excepción: " + e);
            return false;
        }
    }

    public static boolean deleteDBData()
    {
        Logger log = Logger.getLogger(DAOH2Utils.class);
        Connection conn = null;

        try
        {
            conn = H2Connection.getConnection();
            Statement st1   = conn.createStatement();
            Statement st2   = conn.createStatement();
            Statement st3   = conn.createStatement();

            // 1.- Se borran todas las tablas de BD.
            st2.execute("delete from Feature");
            st3.execute("delete from Bbox");
            st1.execute("delete from Metadata");

            H2Connection.close();

            log.info("¡Datos eliminados de la BD!");
            return true;
        }
        catch(Exception e)
        {
            H2Connection.close();
            log.error("Excepción: " + e);
            return false;
        }
    }

    public static boolean insertEarthquakeData(JSONObject jsonResponse)
    {
        Logger log = Logger.getLogger(DAOH2Utils.class);
        Connection conn = null;

        try
        {
            conn = H2Connection.getConnection();

            // 1.-
            String sql1 = "insert into Metadata (url, title, status, api, count) values (?,?,?,?,?)";
            PreparedStatement pst1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);

            // 2.-
            JSONObject jsonMetadata = jsonResponse.getJSONObject("metadata");
            pst1.setString(1, jsonMetadata.getString("url"));
            pst1.setString(2, jsonMetadata.getString("title"));
            pst1.setInt(3, jsonMetadata.getInt("status"));
            pst1.setString(4, jsonMetadata.getString("api"));
            pst1.setInt(5, jsonMetadata.getInt("count"));
            pst1.execute();

            // 3.- Se obtiene la llave primaria del registro de Metadata recién creado en el paso anterior.
            ResultSet rsId = pst1.getGeneratedKeys();
            rsId.next();
            long metadataId = rsId.getLong("id");

            // 4.-
            String sql2 = "insert into Feature (idMetadata, featureType, magnitude, place, time, updated, tz," +
                    "status, tsunami, sig, net, code, magType, propType, title, geomType, coord0,"+
                    "coord1, coord2, featureId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst2 = conn.prepareStatement(sql2);

            // 5.-
            JSONArray jsonArrFeatures = jsonResponse.getJSONArray("features");
            System.out.println("Largo: " + jsonArrFeatures.length());

            for(int pos = 0; pos < jsonArrFeatures.length(); pos++)
            {
                JSONObject jsonFeature = jsonArrFeatures.getJSONObject(pos);
                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");

                pst2.setLong(1, metadataId);
                pst2.setString(2, jsonFeature.getString("type"));
                pst2.setFloat(3, jsonProperties.getFloat("mag"));
                pst2.setString(4, jsonProperties.getString("place"));
                pst2.setLong(5, jsonProperties.getLong("time"));
                pst2.setLong(6, jsonProperties.getLong("updated"));
                pst2.setInt(7, jsonProperties.getInt("tz"));

                pst2.setString(8, jsonProperties.getString("status"));
                pst2.setInt(9, jsonProperties.getInt("tsunami"));
                pst2.setInt(10, jsonProperties.getInt("sig"));
                pst2.setString(11, jsonProperties.getString("net"));
                pst2.setString(12, jsonProperties.getString("code"));
                pst2.setString(13, jsonProperties.getString("magType"));
                pst2.setString(14, jsonProperties.getString("type"));
                pst2.setString(15, jsonProperties.getString("title"));
                pst2.setString(16, jsonFeature.getJSONObject("geometry").getString("type"));
                pst2.setDouble(17, jsonFeature.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));

                pst2.setDouble(18, jsonFeature.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));
                pst2.setDouble(19, jsonFeature.getJSONObject("geometry").getJSONArray("coordinates").getDouble(2));
                pst2.setString(20, jsonFeature.getString("id"));

                pst2.execute();
            }

            // 6.-
            String sql3 = "insert into Bbox (idMetadata, val0, val1, val2, val3, val4, val5) values (?,?,?,?,?,?,?)";
            PreparedStatement pst3 = conn.prepareStatement(sql3);

            JSONArray jsonArrBbox = jsonResponse.getJSONArray("bbox");

            pst3.setLong(1, metadataId);
            pst3.setDouble(2, jsonArrBbox.getDouble(0));
            pst3.setDouble(3, jsonArrBbox.getDouble(1));
            pst3.setDouble(4, jsonArrBbox.getDouble(2));
            pst3.setDouble(5, jsonArrBbox.getDouble(3));
            pst3.setDouble(6, jsonArrBbox.getDouble(4));
            pst3.setDouble(7, jsonArrBbox.getDouble(5));
            pst3.execute();

            H2Connection.close();

            log.info("¡Datos agregados a la BD!");
            return true;
        }
        catch(Exception e)
        {
            H2Connection.close();
            log.error("Excepción: " + e);
            return false;
        }
    }



}
