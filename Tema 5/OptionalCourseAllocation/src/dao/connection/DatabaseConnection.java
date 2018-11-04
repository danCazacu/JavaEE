package dao.connection;

import util.Constants;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ManagedBean(eager = true)
@SessionScoped
public class DatabaseConnection {
    private Connection connection;
    private static DatabaseConnection ourInstance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return ourInstance;
    }

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(Constants.Database.URL, Constants.Database.USER, Constants.Database.PASSWORD);
            System.out.println("Connection completed.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO showError(ex);

        }
    }

    public Connection getConnection() {
        return connection;
    }
}
