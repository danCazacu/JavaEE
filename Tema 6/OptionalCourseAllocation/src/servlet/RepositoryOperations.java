package servlet;

import dao.connection.PostgreConnection;
import util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Map;

@WebServlet(name = "RepositoryOperations", urlPatterns = "/tema6/repository-operations")
public class RepositoryOperations extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operationType = request.getParameter("op");
        if (operationType.equals("insert")) {
            processInsert(request, response);
        } else if (operationType.equals("select")) {
            processSelect(request, response);
        }
    }

    private void processInsert(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = PostgreConnection.getNewConnection();
         long time = System.currentTimeMillis();
        String addr = request.getRemoteAddr();
        String params = "op="+request.getParameter("op");
//        StringBuilder builder;
//        Map<String,String[]> parameters = request.getParameterMap();
//        for (String key:parameters.keySet()) {
//            String[] aux = parameters.get(key);
//        }
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("insert into connection (request_time, remote_addr, request_params) values( ?, ? , ?)");
            pstmt.setLong(1, time);
            pstmt.setString(2, addr);
            pstmt.setString(3, params);
            pstmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processSelect(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = PostgreConnection.getConnection();
        Statement stmt;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from connection");
            while (resultSet.next()) {
                System.out.print(resultSet.getString("id "));
                System.out.print(resultSet.getString(" request_time "));
                System.out.print(resultSet.getString(" remote_addr "));
                System.out.println(resultSet.getString("request_params"));
//                .setName(resultSet.getString(Constants.Lecturer.Table.COLUMN_NAME));
//                lecturerObj.setEmail(resultSet.getString(Constants.Lecturer.Table.COLUMN_EMAIL));
//                lecturerObj.setGender(resultSet.getString(Constants.Lecturer.Table.COLUMN_GENDER));
//                lecturerObj.setUrl(resultSet.getString(Constants.Lecturer.Table.COLUMN_URL));
//
//                allLecturers.add(lecturerObj);
            }
            //connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
