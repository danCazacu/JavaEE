package dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgreConnection {
    private static Connection connection;

    private PostgreConnection() {

    }

    public static Connection getConnection(){
        if(connection==null)
             createConnection();
        return connection;
    }
    private static void createConnection(){
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/post");
            connection = ds.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
