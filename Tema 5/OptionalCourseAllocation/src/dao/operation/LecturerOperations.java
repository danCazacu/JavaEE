package dao.operation;

import bean.LecturerBean;

import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class LecturerOperations extends DatabaseOperations<LecturerBean>{

    @Override
    public ArrayList<LecturerBean> getAll() {
        ArrayList<LecturerBean> allLecturers = new ArrayList<>();
        try {

            if(connection !=null) {
                stmt = connection.createStatement();
                resultSet = stmt.executeQuery("select * from lecturer");
                while (resultSet.next()) {

                    LecturerBean lecturerObj = new LecturerBean();
                    lecturerObj.setName(resultSet.getString("name"));
                    lecturerObj.setEmail(resultSet.getString("email"));
                    lecturerObj.setGender(resultSet.getString("gender"));
                    lecturerObj.setUrl(resultSet.getString("url"));

                    allLecturers.add(lecturerObj);
                }
            }
        } catch (Exception sqlSelectException) {
            sqlSelectException.printStackTrace();
            //TODO showError(sqlSelectException);
        }

        return allLecturers;
    }

    /**
     *
     * @param newRecord
     * @return string as navigation result - where to route next
     */
    @Override
    public String insert(LecturerBean newRecord) {

        int inserted = 0;
        String navigationResult = "";

        try{
            if(newRecord != null) {

                pstmt = connection.prepareStatement("insert into lecturer(name, email, gender, url) values(?, ?, ? , ?)");
                pstmt.setString(1, newRecord.getName());
                pstmt.setString(2, newRecord.getEmail());
                pstmt.setString(3, newRecord.getGender());
                pstmt.setString(4, newRecord.getUrl());
            }

            inserted = pstmt.executeUpdate();
        } catch (SQLException e) {
            //TODO showError(e);
            navigationResult = "addLecturer";
        }

        if(inserted != 0){

            navigationResult = "viewLecturers";
        }else{

            navigationResult = "addLecturer";
        }

        return navigationResult;
    }

    @Override
    public void delete(String deleteRecord) {
        try {

            pstmt = connection.prepareStatement("delete from lecturer where name = ?" );
            pstmt.setString(1, deleteRecord);
            pstmt.executeUpdate();
        } catch(Exception sqlException){
            sqlException.printStackTrace();
            //TODO showError(sqlException);
        }

        //TODO reload();
    }

    @Override
    public String getForEdit(String primaryKey) {
        LecturerBean editRecord = null;
        /* Setting The Particular Lecturer Details In Session */
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        //TODO watch out for this it has been commented out and you need to find a soultion to be passed as parameter to update String editLecturerName = primaryKey;

        try {

            pstmt = connection.prepareStatement("select * from lecturer where name = ?");
            pstmt.setString(1, primaryKey);
            resultSet = pstmt.executeQuery();
            if(resultSet != null) {
                resultSet.next();
                editRecord = new LecturerBean();
                editRecord.setName(resultSet.getString("name"));
                editRecord.setEmail(resultSet.getString("email"));
                editRecord.setGender(resultSet.getString("gender"));
                editRecord.setUrl(resultSet.getString("url"));
            }
            sessionMapObj.put("editRecordObj", editRecord);

        } catch(Exception sqlException) {
            sqlException.printStackTrace();
            //TODO showError(sqlException);
            return "editLecturer";
        }

        return "editLecturer";
    }

    @Override
    public String update(LecturerBean updateRecord, String primaryKey) {
        try {
            pstmt = connection.prepareStatement("update lecturer set name=?, email=?, gender=?, url=? where name=?");
            pstmt.setString(1,updateRecord.getName());
            pstmt.setString(2,updateRecord.getEmail());
            pstmt.setString(3,updateRecord.getGender());
            pstmt.setString(4,updateRecord.getUrl());
            pstmt.setString(5,primaryKey);
            pstmt.executeUpdate();

        } catch(Exception sqlException) {

            //TODO showError(sqlException);
            return "editLecturers";
        }

        return "viewLecturers";
    }
}
