package cl.emergya.mlopez.earthquakerestWStest.db;

import java.sql.*;
import org.apache.log4j.*;
import org.h2.*;

public class H2Connection
{
    private static String userName = "sa";
    private static String password = "";
    private static String dbPath   = "~/earthquakes.mlopez.h2.db";
    private static Connection conn = null;

    public static Connection getConnection()
    {
        Logger log = Logger.getLogger(H2Connection.class);
        //BasicConfigurator.configure();

        try
        {
            Class.forName("org.h2.Driver");
            if(conn == null)
                conn = DriverManager.getConnection("jdbc:h2:" + dbPath, userName, password);

            return conn;
        }
        catch(ClassNotFoundException e)
        {
            log.error("Excepción ClassNotFoundException: " + e);
            return null;
        }
        catch(SQLException e)
        {
            log.error("Excepción SQLException: " + e);
            return null;
        }
    }

    public static void close()
    {
        Logger log = Logger.getLogger(H2Connection.class);

        try
        {
            if(conn != null)
            {
                conn.close();
                conn = null;
            }
        }
        catch(SQLException e)
        {
            log.error("Excepción SQLException: " + e);
        }
    }

    public void setUserName(String un)
    {
        userName = un;
    }

    public void setPassword(String pw)
    {
        password = pw;
    }

    public void setDbPath(String dbp)
    {
        dbPath = dbp;
    }

}
