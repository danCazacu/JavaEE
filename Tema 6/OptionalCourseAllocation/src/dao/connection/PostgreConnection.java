package dao.connection;

import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgreConnection {
    private static Connection connection;
    private static DataSource ds;
//    private static PGConnectionPoolDataSource ds;

    static {
        try {
            ds = (DataSource) new InitialContext().lookup("jdbc/post");
            connection = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PostgreConnection() {}

    public static Connection getConnection(){
        return connection;
    }

    private static Connection createConnection(){
        Connection con = null;
        try {
            if(ds==null){
                lookup();
            }
            con = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    private static void lookup(){
        Context ctx = null;
        try {
            ctx = new InitialContext();
            ds = (DataSource)ctx.lookup("jdbc/post");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static Connection getNewConnection(){
        return createConnection();
    }
}
