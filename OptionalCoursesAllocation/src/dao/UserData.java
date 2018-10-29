package dao;

import bean.LecturerBean;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userData", eager = true)
@SessionScoped
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;


    public List<LecturerBean> getBLaBla() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection con = getConnection();
        String stm = "Select * from lecturer";
        List<LecturerBean> records = new ArrayList<LecturerBean>();

        try {
            if(con != null) {
                pst = con.prepareStatement(stm);
                pst.execute();
                rs = pst.getResultSet();

                while (rs.next()) {
                    LecturerBean lecturer = new LecturerBean();
                    lecturer.setName(rs.getString(2));
                    lecturer.setName(rs.getString(3));
                    records.add(lecturer);
                }
            }else{
                LecturerBean lecturer = new LecturerBean();
                lecturer.setName("con is null");
                records.add(lecturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public Connection getConnection() {
        Connection con = null;
        String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "postgres";
        String password = "andreea";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url,user, password);
            System.out.println("Connection completed.");
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        finally {
        }

        return con;
    }
}
