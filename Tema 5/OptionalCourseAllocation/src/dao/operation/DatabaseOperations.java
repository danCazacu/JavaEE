package dao.operation;

import dao.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class DatabaseOperations<T> {
    protected Connection connection = DatabaseConnection.getInstance().getConnection();
    protected ResultSet resultSet;
    protected PreparedStatement pstmt;
    protected Statement stmt;

    public abstract ArrayList<T> getAll();
    public abstract String insert(T newRecord);
    public abstract void delete(String deleteRecord);
    public abstract String getForEdit(String primaryKey);
    public abstract String update(T updateRecord);

    public void delete(int deleteRecord){
        delete(""+deleteRecord);
    }
    public String getForEdit(int primaryKey){
        return getForEdit(""+primaryKey);
    }
//    public String update(T updateRecord, int primaryKey){
//        return update(updateRecord,""+primaryKey);
//    }
}
